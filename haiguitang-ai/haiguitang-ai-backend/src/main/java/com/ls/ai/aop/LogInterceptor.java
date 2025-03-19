package com.ls.ai.aop;


import com.ls.ai.exeception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.UUID;


/**
 * 日志 AOP
 */
@Component
@Aspect
@Slf4j
public class LogInterceptor {


    @Around("execution(* com.ls.ai.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {

        // 记时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 获取请求的路径
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 生成请求日志唯一id
        String requestId = UUID.randomUUID().toString();

        // 请求的ip
        String requestIp = request.getRemoteAddr();

//        // 请求的主机名称
        String clientHostName = getClientHostName(requestIp);

        // 请求的mac地址
        String macAddress = getMacAddress(requestIp);


        // 发起请求的用户
        Object user = request.getSession().getAttribute("user");

        // 请求url
        String url = request.getRequestURL().toString();



        // todo 请求参数, 注意隐私，特别是密码要进行加密
        Object[] args = point.getArgs();
        String reqParam = "[" + StringUtils.join(args, ",") + "]";

        // 输出请求日志
        log.info("request start: requestId:{}, userId:{},requestIp:{},url:{}, reqParam:{}", requestId,user,requestIp, url, reqParam);
//        log.info("request start: requestId:{}, userId:{},requestIp:{},clientHostName:{},macAddress:{},url:{}", requestId,user,requestIp,clientHostName,macAddress, url);




        Object result = null;
        try {

            // 执行目标方法并获取响应结果
            result = point.proceed();

            // 将响应结果转换为字符串
            String respParam = result != null ? result.toString() : "error";

            // 输出响应日志
            stopWatch.stop();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();
            log.info("request end: requestId: {}, cost: {}ms, respParam: {}", requestId, totalTimeMillis, respParam);
        } catch (BusinessException e) {
            stopWatch.stop();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();
            log.error("request end Business exception caught: requestId: {}, cost: {}ms, code: {}, message: {}",
                    requestId, totalTimeMillis, e.getCode(), e.getMessage());
            throw e; // 保持 BusinessException 类型不变
        } catch (Exception e) {
            stopWatch.stop();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();
            log.error("request end System exception caught: requestId: {}, cost: {}ms, message: {}",
                    requestId, totalTimeMillis, e.getMessage());
            throw e; // 保持 Exception 类型不变
        }
        return result;

    }


    /**
     * 获取客户端主机名
     * @param clientIp
     * @return
     */
    public static String getClientHostName(String clientIp) {
        try {
            // 如果是IPv6的本地回环地址，转换为IPv4
            if ("0:0:0:0:0:0:0:1".equals(clientIp) || "127.0.0.1".equals(clientIp)) {
                // 获取本地主机名 这里不要通过ip 去解析 主机名，可能会有误，因为通过ip解析主机名称，是查dns系统 ip 对应的主机名，可能会有误。
                InetAddress localHost = InetAddress.getLocalHost();
                return localHost.getHostName();
            }

            // 不是本地的请求 就根据ip 获得IntAddress, 然后在获取主机名称
            InetAddress inetAddress = InetAddress.getByName(clientIp);
            return inetAddress.getHostName();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }

    /**
     * 获取mac 地址
     * @param ipAddress
     * @return
     */
    public static String getMacAddress(String ipAddress) {
        try {
            // 如果是本地回环地址，直接返回本机MAC地址
            if ("0:0:0:0:0:0:0:1".equals(ipAddress) || "127.0.0.1".equals(ipAddress)) {
                return getLocalMacAddress();
            }
            Process process = Runtime.getRuntime().exec("arp -a " + ipAddress);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(ipAddress)) {
                    String[] parts = line.split("\\s+");
                    return parts[2]; // MAC地址通常是第三列， 打断点就知道
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "无法获取MAC地址";
    }

    /**
     * 获取本机的mac 地址
     * @return
     */
    public static String getLocalMacAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "无法获取MAC地址";
        }
    }







}
