<template>
  <div class="landing-page">
    <div class="text-section">
      <h1>최우선의 라이프 스타일을 꿈꾸다</h1>
      <p>AI 아파트는 미래 주거공간으로 아파트의 가치를 높이고, 새로운 경험과 안전을 최우선으로 하는 라이프 스타일을 제공합니다.</p>
    </div>
    <div class="image-section">
      <ImageSlider />
    </div>
    <div v-if="showButton && !hasSessionData" class="scroll-button">
      <!-- Sheet Component -->
      <Sheet v-model:open="sheetOpen">
        <SheetTrigger as-child>
          <button class="transparent-button">Insert Code</button>
        </SheetTrigger>
        <SheetContent>
          <SheetHeader>
            <SheetTitle>우리 집 정보를 입력하세요</SheetTitle>
            <SheetDescription>
              우리 집 아파트코드, 동호수를 선택하고 저장을 눌러주세요.
            </SheetDescription>
          </SheetHeader>
          <div class="grid gap-4 py-4">
            <div class="grid grid-cols-4 items-center gap-4">
              <!-- Dropdown에서 선택된 값을 aptCd에 반영 -->
              <Dropdown :options="options1" @update:selectedOption="aptCd = $event" />
            </div>
            <div class="grid grid-cols-4 items-center gap-4">
              <!-- Dropdown에서 선택된 값을 roomDong에 반영 -->
              <Dropdown :options="options2" @update:selectedOption="roomDong = $event" />
            </div>        
            <div class="grid grid-cols-4 items-center gap-4">  
              <!-- Dropdown에서 선택된 값을 roomHosu에 반영 -->
              <Dropdown :options="options3" @update:selectedOption="roomHosu = $event" />
            
            </div>
          </div>
          <SheetFooter>
            <SheetClose as-child>
              <Button @click="submitSelections">
                  Save changes
                </Button>
            </SheetClose>
          </SheetFooter>
        </SheetContent>
      </Sheet>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted, computed } from 'vue';
import ImageSlider from '../components/ImageSlider.vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { Button } from '@/components/ui/button';
import Dropdown from '../components/Dropdown.vue';

import {
  Sheet,
  SheetClose,
  SheetContent,
  SheetDescription,
  SheetFooter,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from '@/components/ui/sheet';

const isLoading = ref(true);
const options1 = ref([]);
const options2 = ref([]);
const options3 = ref([]);
const aptCd = ref('');
const roomDong = ref('');
const roomHosu = ref('');
const showButton = ref(false); // Show button based on scroll position
const sheetOpen = ref(false); // Track Sheet open state

export default {
  components: {
    ImageSlider,
    Sheet,
    SheetClose,
    SheetContent,
    SheetDescription,
    SheetFooter,
    SheetHeader,
    SheetTitle,
    SheetTrigger,
    Dropdown,
    Button
  },
  setup() {
    const showButton = ref(false);
    const router = useRouter();

    const hasSessionData = computed(() => {
      const hasData = sessionStorage.getItem('apt_cd') &&
                      sessionStorage.getItem('room_dong') &&
                      sessionStorage.getItem('room_hosu');
      return hasData;
    });

    const redirectToLogin = () => {
      router.push('/user');
    };

    const handleScroll = () => {
      if (window.scrollY > 160) { // Change 200 to the scroll position you want
        showButton.value = true;
      } else {
        showButton.value = false;
      }
    };

    const fetchData = async () => {
      try {
        const [response1, response2, response3] = await Promise.all([
          axios.get('http://localhost:8080/api/landing/aptCd'),
          axios.get('http://localhost:8080/api/landing/roomDong'),
          axios.get('http://localhost:8080/api/landing/roomHosu')
        ]);
        options1.value = response1.data;
        options2.value = response2.data;
        options3.value = response3.data;
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        isLoading.value = false;
      }
    };

    // Fetch data when the component is mounted
    onMounted(() => {
      fetchData();
      window.addEventListener('scroll', handleScroll);
      handleScroll(); // 컴포넌트가 마운트될 때 초기 상태 확인
    });

    onUnmounted(() => {
      window.removeEventListener('scroll', handleScroll);
    });

    const submitSelections = () => {
      sessionStorage.setItem('apt_cd', aptCd.value);
      sessionStorage.setItem('room_dong', roomDong.value);
      sessionStorage.setItem('room_hosu', roomHosu.value);

      router.push('/landing')
            .then(() => {
                  window.location.reload();
                  next();
            });
    };

    return {
      showButton,
      redirectToLogin,
      hasSessionData,
      submitSelections,
      isLoading,
      options1,
      options2,
      options3,
      aptCd,
      roomDong,
      roomHosu,
      showButton,
      sheetOpen,
    };
  }
};
</script>

<style scoped>
.landing-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  background-color: #f8f9fa; /* 밝은 배경 색상 */
  font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu, Cantarell, Fira Sans, Droid Sans, Helvetica Neue, sans-serif; /* 현대적인 폰트 스택 */
}

.text-section {
  text-align: center;
  margin: 80px 20px; /* 여백을 늘려서 더 넓은 공간 확보 */
}

.text-section h1 {
  font-size: 2.5rem;
  font-weight: 700;
  color: #333; /* 대비를 위한 어두운 텍스트 색상 */
  line-height: 1.2; /* 깔끔한 외관을 위한 줄 높이 조정 */
}

.text-section p {
  font-size: 1.125rem;
  color: #666; /* 약간 밝은 텍스트 색상 */
  line-height: 1.6; /* 가독성을 위한 줄 높이 조정 */
  margin-top: 20px;
}

.image-section {
  margin-bottom: 60px; /* 여백을 늘려서 더 넓은 공간 확보 */
}

.scroll-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: opacity 0.3s ease, transform 0.3s ease;
  color: #000; /* 텍스트 색상 (검정색) */
  font-weight: bold; /* 글씨를 볼드체로 설정 */
  font-size: 5rem; /* 글씨 크기 조정 */
}
.transparent-button:hover {
  color: #007bff; 
}

.transparent-button {
  background: transparent;
  border: none;
  color: #000; /* 텍스트 색상 (검정색) */
  font-weight: bold; /* 글씨를 볼드체로 설정 */
  font-size: 1rem; /* 글씨 크기 조정 */
  cursor: pointer;
  padding: 8px 16px; /* 버튼 안쪽 여백 조정 */
  transition: color 0.3s ease; /* 호버 시 색상 변화 부드럽게 */
}
.scroll-button.hidden {
  opacity: 0;
  pointer-events: none;
}

.scroll-button button:hover {
  color: #e0e0e0; /* 호버 시 색상 변화 */
}

/* 시트 스타일링 */
.sheet {
  background-color: #fff; /* 시트의 배경 색상 */
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.sheet-header {
  border-bottom: 1px solid #ddd; /* 구분을 위한 가벼운 경계선 */
  padding: 16px;
  background-color: #f7f7f7; /* 헤더의 배경 색상 */
}

.sheet-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #333;
}

.sheet-description {
  font-size: 1rem;
  color: #666;
}

.sheet-content {
  padding: 16px;
}

.sheet-footer {
  border-top: 1px solid #ddd; /* 구분을 위한 가벼운 경계선 */
  padding: 16px;
  text-align: right;
}

.sheet-close button {
  background-color: #007bff; /* 기본 버튼 색상 */
  color: #fff;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.sheet-close button:hover {
  background-color: #0056b3; /* 호버 시 색상 변화 */
}

.grid {
  display: grid;
  gap: 16px;
}

.grid-cols-4 {
  grid-template-columns: repeat(4, 1fr);
}

.items-center {
  align-items: center;
}

button {
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}
</style>
