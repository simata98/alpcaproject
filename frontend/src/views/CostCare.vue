<template>
  <div class="cost-section">
    <!-- Container for the chart and details -->
    <div class="cards-details-row">
      <!-- ChartCard will be on the left -->
      <CostCards :roomData="roomData" />
    </div>
      
      <!-- CostChart will be on the right -->
      <div class="chart-container">
        <CostChart :roomData="roomData" />
      </div>
  </div>
</template>

<script>
import axios from 'axios';
import CostChart from '@/components/CostChart.vue';
import CostCards from '@/components/CostCards.vue';

export default {
  components: {
    CostChart,
    CostCards,
  },
  data() {
    return {
      roomData: []
    };
  },
  mounted() {
    this.fetchRoomData();
  },
  methods: {
    async fetchRoomData() {
      try {
        const aptCd = sessionStorage.getItem('apt_cd');
        const roomDong = sessionStorage.getItem('room_dong');
        const roomHosu = sessionStorage.getItem('room_hosu');
        const response = await axios.get('http://localhost:8080/api/cost/all', {
          headers: {
            'apt_cd': aptCd,
            'room_dong': roomDong,
            'room_hosu': roomHosu,
            'Cache-Control': 'no-cache'
          }
        });
        this.roomData = response.data;
      } catch (error) {
        console.error(error);
      }
    }
  }
};
</script>

<style scoped>
.cost-section {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.cost-details-row {
  /* display: flex; */
  gap: 20px;
}

.chart-container {
  flex: 1;
  display: flex;
  flex-direction: column;
}

@media (max-width: 768px) {
  .cost-details-row {
    flex-direction: column;
  }
}
</style>
