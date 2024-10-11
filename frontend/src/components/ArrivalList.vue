<template>
    <div class="parent-container">
      <div v-if="loading" class="loading">Loading...</div>
      <div v-else class="card-list">
        <ArrivalCard
          v-for="(data, index) in cominigFamilyList"
          :key="index"
          :comingFamily="data"
          @click="handleCardClick(data)"
        />
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import ArrivalCard from '@/components/ArrivalCard.vue';
  
  export default {
    components: {
      ArrivalCard
    },
    data() {
      return {
        cominigFamilyList: [],
        loading: true
      };
    },
    mounted() {
        this.fetchTriggerData();
        this.intervalId = setInterval(this.fetchTriggerData, 1000); 
    },
    beforeUnmount() {
        if (this.intervalId) {
        clearInterval(this.intervalId); // 컴포넌트 언마운트 시 인터벌 클리어
        }
    },
    emits: ['update-trigger'],
    methods: {
      async fetchTriggerData() {
        try {
          // 세션 데이터에서 토큰 가져오기
          const aptCd = sessionStorage.getItem('apt_cd');
          const roomDong = sessionStorage.getItem('room_dong');
          const roomHosu = sessionStorage.getItem('room_hosu');
          const response = await axios.get('http://localhost:8080/api/router/coming', {
            headers: {
              'apt_cd': `${aptCd}`,
              'room_dong': `${roomDong}`,
              'room_hosu': `${roomHosu}`,
            }
          });
          this.cominigFamilyList = response.data;
          this.loading = false;
        } catch (error) {
          console.error('Error fetching trigger data:', error);
          this.loading = false;
        }
      },
      handleCardClick(data) {
        this.$emit('update-trigger', data);
      }
    }
  };
  </script>
  
  <style scoped>
  .parent-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
    background-color: #f5f5f5;
  }
  
  .card-list {
    display: flex;
    flex-direction: column; /* 세로 정렬 */
    align-items: center;
  }
  
  .loading {
    font-size: 18px;
    color: #333;
  }
  </style>
  