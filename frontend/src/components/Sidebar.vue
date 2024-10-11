<template>
  <div :class="['sidebar', { 'sidebar-open': isOpen }]">
    <button class="toggle-btn" @click="toggleSidebar">☰</button>
    <div class="menu">
      <!-- 세션 안의 나의 아파트, 동, 호수 표시 -->
      <div v-if="hasSessionData">
        <p v-if="aptCd">{{ aptCd }} 아파트</p>
        <p v-if="roomDong">{{ roomDong }} 동</p>
        <p v-if="roomHosu">{{ roomHosu }} 호</p>
      </div>
      <!-- 메뉴 내용 -->
      <ul>
          <li><a href="#" @click="navigateTo('/air')">Air Care</a></li>
          <li><a href="#" @click="navigateTo('/cost')">Cost Care</a></li>
          <li><a href="#" @click="navigateTo('/monitor')">Home Monitor</a></li>
          <li><a href="#" @click="navigateTo('/music')">Music Sync</a></li>
          <li><a href="#" @click="navigateTo('/pet')">Pet Care</a></li>
          <li><a href="#" @click="navigateTo('/route')">Route Care</a></li>
      </ul>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router'; // vue-router 사용

export default {
  emits: ['toggleSidebar'],
  setup(props, { emit }) {
    const isOpen = ref(false);
    const router = useRouter(); // vue-router 인스턴스 사용

    const toggleSidebar = () => {
      isOpen.value = !isOpen.value;
      emit('toggleSidebar'); // 부모 컴포넌트에 사이드바 상태 전달
    };

    const aptCd = ref(sessionStorage.getItem('apt_cd'));
    const roomDong = ref(sessionStorage.getItem('room_dong'));
    const roomHosu = ref(sessionStorage.getItem('room_hosu'));

    // 세션 데이터가 있는지 확인
    const hasSessionData = computed(() => {
      return aptCd.value && roomDong.value && roomHosu.value;
    });

    // 메뉴 클릭 시 이동 처리
    const navigateTo = (path) => {
      if (hasSessionData.value) {
        router.push(path); // 세션 데이터가 있으면 해당 경로로 이동
      } else {
        router.push('/user'); // 세션 데이터가 없으면 로그인 페이지로 이동
      }
    };

    return {
      isOpen,
      toggleSidebar,
      aptCd,
      roomDong,
      roomHosu,
      hasSessionData,
      navigateTo
    };
  }
};
</script>

<style scoped>

.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  width: 250px;
  height: 100%;
  background-color: #ffffff; /* 사이드바 배경을 하얀색으로 설정 */
  color: #333; /* 텍스트 색상 어두운 회색으로 설정 */
  transform: translateX(-100%);
  transition: transform 0.3s ease;
  z-index: 1000;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2); /* 사이드바에 섀도우 추가 */
}

.sidebar-open {
  transform: translateX(0);
}

.toggle-btn {
  position: absolute;
  /* top: 15px; */
  right: -50px;
  /* background-color: #f0f0f0; */
  color: #333; /* 버튼 텍스트 색상 어두운 회색으로 설정 */
  border: none;
  /* padding: 12px; */
  cursor: pointer;
  font-size: 22px;
  border-radius: 4px; /* 모서리 둥글게 */
}

.menu {
  padding: 20px; /* 메뉴에 패딩 추가 */
}

.menu ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.menu li {
  padding: 15px 0; /* 항목 위아래 패딩 조정 */
  border-bottom: 1px solid #ddd; /* 항목 구분선 색상 밝은 회색으로 설정 */
  line-height: 1.6; /* 행간 조정 */
  transition: background-color 0.3s ease, padding 0.3s ease; /* 배경색 및 패딩 전환 효과 */
}

.menu li:hover {
  background-color: #f9f9f9; /* 호버 시 배경색을 밝은 회색으로 변경 */
  padding-left: 20px; /* 글자 하나 크기만큼 왼쪽 패딩 추가 */
  padding-right: 20px; /* 글자 하나 크기만큼 오른쪽 패딩 추가 */
}

.menu a {
  text-decoration: none;
  color: #333; /* 링크 텍스트 색상 어두운 회색으로 설정 */
  font-size: 16px; /* 폰트 크기 조정 */
  display: block;
  transition: color 0.3s ease; /* 텍스트 색상 전환 효과 */
}

.menu a:hover {
  color: #007bff; /* 링크 호버 시 색상을 파란색으로 변경 */
}

/* 사이드바 외부 화면에 어두운 음영 추가 */
body {
  transition: background-color 0.3s ease; /* 배경색 전환 효과 */
}

.sidebar-open ~ body {
  background-color: rgba(0, 0, 0, 0.5); /* 어두운 음영 추가 */
}


</style>
 