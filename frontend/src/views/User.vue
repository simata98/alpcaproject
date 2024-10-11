<script setup lang="js">
import { ref, onMounted, onUnmounted } from 'vue';
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

const router = useRouter();
const isLoading = ref(true);
const options1 = ref([]);
const options2 = ref([]);
const options3 = ref([]);
const aptCd = ref('');
const roomDong = ref('');
const roomHosu = ref('');
const showButton = ref(false); // Show button based on scroll position
const sheetOpen = ref(false); // Track Sheet open state

const handleScroll = () => {
  if (window.scrollY > 200) { // Change 200 to the scroll position you want
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
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});

const submitSelections = () => {
  sessionStorage.setItem('apt_cd', aptCd.value);
  sessionStorage.setItem('room_dong', roomDong.value);
  sessionStorage.setItem('room_hosu', roomHosu.value);

  router.push('/');
};
</script>

<template>
  <div>
    
    <!-- Sheet Component -->
    <Sheet v-model:open="sheetOpen">
      <SheetTrigger as-child>
        <Button variant="outline">
          Open
        </Button>
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
</template>
