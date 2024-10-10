<template>
    <div class="main-page" :class="{ 'shifted': isSidebarOpen }">
      <Sidebar @toggleSidebar="toggleSidebar" />
      <!-- 사이드바 열림 상태 전달 -->
      <div :class="['content', { 'dimmed': isSidebarOpen }]">
        <router-view />
        <Toaster />
      </div>
    </div>
  </template>
  
  <script>
  import { ref, computed } from 'vue';
  import Sidebar from '@/components/Sidebar.vue';
  import { Toaster } from '@/components/ui/sonner';

  
  export default {
    components: {
      Sidebar,
      Toaster
    },
    setup() {
      const isSidebarOpen = ref(false);
  
      // 사이드바 상태 변경 함수
      const toggleSidebar = () => {
        isSidebarOpen.value = !isSidebarOpen.value;
      };
  
      // 세션에서 현재 로그인된 아파트이름, 동, 호수 가져옴
      const hasSessionData = computed(() => {
        const hasData = sessionStorage.getItem('apt_cd') &&
                        sessionStorage.getItem('room_dong') &&
                        sessionStorage.getItem('room_hosu');
        return hasData;
      });
  
      return {
        isSidebarOpen,
        toggleSidebar,
        hasSessionData
      };
    }
  };
  </script>
  
  <style scoped>
.main-page {
  display: flex;
  min-height: 100vh;
  transition: transform 0.3s ease;
}

.shifted .content {
  transform: translateX(250px); /* 사이드바 열렸을 때 250px 오른쪽으로 이동 */
}

.content {
  flex: 1;
  padding: 20px;
  transition: transform 0.3s ease, background-color 0.3s ease; /* 이동 및 배경색 애니메이션 */
}

/* 사이드바가 열렸을 때 배경에 음영 적용 */
.dimmed {
  background-color: rgba(0, 0, 0, 0.5); /* 음영 */
}

  </style>
  