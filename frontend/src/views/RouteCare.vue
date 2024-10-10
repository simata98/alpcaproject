<template>
  <div class="parent-container">
    <div class="map-section">
      <div class="map-container">
        <NaverMap :MapCallerData="MapCallerData" @trigger="updateTriggerData"/>
      </div>
    </div>
    <div class="arrival-section">
      <div class="arrival-container">
        <ArrivalList @update-trigger="updateMapCallerData"/>
      </div>
      <div class="alarm-section">
        <Alarm :TriggerData="TriggerData" />
      </div>
    </div>
  </div>
</template>

<script>
import NaverMap from '@/components/NaverMap.vue';
import Alarm from '@/components/Alarm.vue';
import ArrivalList from '@/components/ArrivalList.vue';

export default {
  components: {
    NaverMap,
    Alarm,
    ArrivalList
  },
  data() {
    return {
      TriggerData: {}, // Alarm에서 사용할 데이터
      MapCallerData: {} // 어떤 가족의 지도를 보여줄지
    };
  },
  methods: {
    updateTriggerData(data) {
      this.TriggerData = data;
    },
    updateMapCallerData(data) {
      this.MapCallerData = data;
    }
  }
};
</script>

<style scoped>
.parent-container {
  display: flex;
  height: 100vh; /* 화면 전체 높이 */
  background-color: #f0f4f8;
  font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu, Cantarell, Fira Sans, Droid Sans, Helvetica Neue, sans-serif;
}

.map-section {
  flex: 2; /* 화면의 2/3 차지 */
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
}

.map-container {
  flex-grow: 1; /* 지도가 남은 공간을 모두 채우도록 */
}

.arrival-section {
  flex: 1; /* 화면의 1/3 차지 */
  padding: 20px;
  background-color: #f7f7f7;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.alarm-section h2 {
  font-size: 1.5rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
}

.alarm-section p {
  font-size: 1rem;
  color: #555;
}
</style>
