<template>
  <div class="card">
    <!-- 카드 타이틀 -->
    <h3 class="card-title">
      {{ currentYear }}년 {{ previousMonth }}월 에너지 소비 현황<span class="info-text"> *에너지 소비 현황이란? 전기, 수도, 온수, 난방 등 4대 에너지의 소비현황</span>
    </h3>
    <!-- 카드형식 세부내역 부분 -->
    <div class="details-container">
      <div v-for="(item, index) in feeDetails" :key="index" class="detail-card">
        <div class="detail-header">
          {{ item.name }}
        </div>
        <div class="detail-body">
          <span class="amount">{{ formatCurrency(item.amount) }} 원</span>
          <span :class="item.changeClass">{{ item.changeText }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue';

export default {
  props: {
    roomData: {
      type: Array,
      required: true,
    },
  },
  setup(props) {
    const previousMonth = ref(null);
    const currentYear = ref(null);
    const feeDetails = computed(() => {
      const currentDate = new Date();
      previousMonth.value = (currentDate.getMonth() - 1 + 12) % 12 || 12;
      currentYear.value = currentDate.getFullYear() - (previousMonth.value === 12 ? 1 : 0);

      const lastMonthData = props.roomData.find(item => item.year === currentYear.value && item.month === previousMonth.value);

      const previousData = props.roomData.find(item => {
        const prevMonth = (previousMonth.value - 1 + 12) % 12 || 12;
        const prevYear = currentYear.value - (prevMonth === 12 ? 1 : 0);
        return item.year === prevYear && item.month === prevMonth;
      });

      return lastMonthData
        ? [
            { 
              name: '세대전기료', 
              amount: lastMonthData.hhdElectricExp, 
              changeText: getChangeText('hhdElectricExp', lastMonthData, previousData),
              changeClass: getChangeClass('hhdElectricExp', lastMonthData, previousData)
            },
            { 
              name: '세대수도료', 
              amount: lastMonthData.hhdWaterExp, 
              changeText: getChangeText('hhdWaterExp', lastMonthData, previousData),
              changeClass: getChangeClass('hhdWaterExp', lastMonthData, previousData)
            },
            { 
              name: '세대급탕비', 
              amount: lastMonthData.hhdHotWaterExp, 
              changeText: getChangeText('hhdHotWaterExp', lastMonthData, previousData),
              changeClass: getChangeClass('hhdHotWaterExp', lastMonthData, previousData)
            },
            { 
              name: '세대난방비', 
              amount: lastMonthData.hhdHeatExp, 
              changeText: getChangeText('hhdHeatExp', lastMonthData, previousData),
              changeClass: getChangeClass('hhdHeatExp', lastMonthData, previousData)
            }
          ]
        : [];
    });

    const getChangeText = (key, lastMonthData, previousData) => {
      if (!previousData) return '';
      const change = lastMonthData[key] - previousData[key];
      const percentageChange = (change / previousData[key] * 100).toFixed(1);
      return change > 0 
        ? `▲ ${formatCurrency(Math.abs(change))} 원 (${percentageChange}%)` 
        : (change < 0 ? `▼ ${formatCurrency(Math.abs(change))} 원 (${percentageChange}%)` : `-`);
    };

    const getChangeClass = (key, lastMonthData, previousData) => {
      if (!previousData) return '';
      const change = lastMonthData[key] - previousData[key];
      return change > 0 
        ? 'increase' 
        : (change < 0 ? 'decrease' : 'no-change');
    };

    const formatCurrency = (value) => {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    };

    return {
      feeDetails,
      previousMonth,
      currentYear,
      formatCurrency,
    };
  },
};
</script>

<style scoped>
.card {
  background-color: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.card-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.info-text {
  font-size: 12px;
  color: #9CA3AF; /* 회색 */
}

.details-container {
  display: flex;
  flex-wrap: nowrap;
  gap: 10px;
}

.detail-card {
  background-color: #F9FAFB;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  padding: 10px;
  flex: 1 1 calc(25% - 10px);
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
}

.detail-header {
  font-size: 14px; /* 줄인 크기 */
  font-weight: bold;
  margin-bottom: 5px;
}

.detail-body {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.amount {
  font-size: 18px; /* 키운 크기 */
  font-weight: bold;
  color: #374151;
}

.increase {
  color: #F87171; /* 채도 낮춘 빨간색 */
  font-weight: bold;
}

.decrease {
  color: #60A5FA; /* 채도 낮춘 파란색 */
  font-weight: bold;
  font-size: 12px;
}

.no-change {
  color: #9CA3AF; /* 회색으로 변동 없음 표시 */
  font-weight: bold;
}
</style>
