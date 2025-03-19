<template>
  <div>
    <component :is="currentPage" @navigate="handleNavigation" />
  </div>
</template>

<script setup lang="ts">
import { shallowRef } from 'vue';
import HomePage from './components/HomePage.vue';
import ChatPage from './components/ChatPage.vue';

// 使用shallowRef提高性能
const currentPage = shallowRef(HomePage);

const handleNavigation = (page: string) => {
  if (page === 'chat') {
    currentPage.value = ChatPage;
  } else if (page === 'home') {
    currentPage.value = HomePage;
  }
};
</script>

<style>
/* 全局样式可以留在style.css中 */
</style>
