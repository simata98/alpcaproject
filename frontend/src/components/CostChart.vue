<template>
  <div class="card">
    <div class="chart-accordion-container">
      <!-- Line chart on the left -->
      <div class="line-chart">
        <apexchart type="line" :options="chartOptions" :series="chartSeries" />
      </div>
      <!-- Accordion on the right -->
      <div class="accordion-container">
        <div v-for="(month, index) in monthlySummaries" :key="index" class="accordion">
          <div class="accordion-header" @click="toggleDetails(index)">
            <h4>
              <strong class="month-amount">{{ month.name }}: {{ formatCurrency(month.total) }} 원</strong>
              <span v-if="month.change !== null" :class="{'increase': month.change > 0, 'decrease': month.change < 0}">
                (<strong>{{ month.change > 0 ? '▲' : '▼' }} {{ formatCurrency(Math.abs(month.change)) }} 원</strong>)
              </span>
              <br>
              <span v-if="month.largestChangeDetail" class="largest-change">
                <strong>{{ month.largestChangeDetail.name }} </strong>가 가장 큰 폭으로 
                <span :class="{'increase': month.largestChangeDetail.change > 0, 'decrease': month.largestChangeDetail.change < 0}">
                  <strong class="largest-change-text">{{ month.largestChangeDetail.change > 0 ? '상승' : '하락' }}</strong>
                </span>했습니다.
              </span>
            </h4>
            <!-- <span>{{ activeIndex === index ? '-' : '+' }}</span> -->
          </div>
          <div v-if="activeIndex === index" class="accordion-body">
            <ul>
              <li v-for="(item, idx) in month.details" :key="idx">
                {{ item.name }}: {{ formatCurrency(item.amount) }}원
                <span v-if="item.change !== null && !isNaN(item.change)" :class="{'increase': item.change > 0, 'decrease': item.change < 0}">
                  ({{ item.change > 0 ? '▲' : '▼' }} {{ formatCurrency(Math.abs(item.change)) }}원)
                </span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import ApexCharts from 'vue3-apexcharts';

export default {
  components: {
    apexchart: ApexCharts
  },
  props: {
    roomData: {
      type: Array,
      required: true,
    },
  },
  setup(props) {
    const activeIndex = ref(null);

    const formatCurrency = (value) => {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    };

    const monthlySummaries = computed(() => {
      const dataByMonth = {};
      let prevTotal = null;

      props.roomData.forEach(item => {
        const monthKey = `${item.year}-${item.month}`;
        if (!dataByMonth[monthKey]) {
          dataByMonth[monthKey] = {
            name: `${item.year}년 ${item.month}월`,
            total: 0,
            details: [],
            change: null,
            largestChangeDetail: null,
          };
        }
        dataByMonth[monthKey].total += item.totExp;
        dataByMonth[monthKey].details.push(
            { name: '세대전기료', amount: item.hhdElectricExp },
            { name: '세대수도료', amount: item.hhdWaterExp },
            { name: '세대급탕비', amount: item.hhdHotWaterExp },
            { name: '세대난방비', amount: item.hhdHeatExp },
        );
      });

      const result = Object.values(dataByMonth).reverse();
      result.forEach((month, index) => {
        if (index > 0) {
          month.change = month.total - result[index - 1].total;
        }
        month.details.forEach((item, idx) => {
          if (index > 0) {
            const prevItem = result[index - 1].details.find(detail => detail.name === item.name);
            item.change = prevItem ? item.amount - prevItem.amount : null;
          }
        });

        month.largestChangeDetail = month.details.reduce((max, item) => {
          if (item.change !== null && !isNaN(item.change) && (!max || Math.abs(item.change) > Math.abs(max.change))) {
            return item;
          }
          return max;
        }, null);
      });

      return result;
    });

    const chartOptions = computed(() => ({
      chart: {
        type: 'line',
        height: 350
      },
      stroke: {
        curve: 'smooth',
        width: 2
      },
      xaxis: {        
        categories: monthlySummaries.value.map(item => item.name),
      },
      tooltip: {
        y: {
          formatter: (value) => `${value.toLocaleString()} 원`
        }
      }
    }));

    const chartSeries = computed(() => [
      {
        name: '전체 관리비',
        data: monthlySummaries.value.map(item => item.total)
      }
    ]);

    const toggleDetails = (index) => {
      activeIndex.value = activeIndex.value === index ? null : index;
    };

    return {
      monthlySummaries,
      activeIndex,
      toggleDetails,
      formatCurrency,
      chartOptions,
      chartSeries
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

.chart-accordion-container {
  display: flex;
  gap: 20px;
}

.line-chart {
  flex: 1; /* Adjusts to take up 50% of the space */
}

.accordion-container {
  flex: 1; /* Adjusts to take up 50% of the space */
}

.accordion {
  margin-bottom: 10px;
}

.accordion-header {
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  padding: 10px;
  background: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.accordion-header h4 {
  font-weight: bold;
  font-size: 17px; /* Increased font size for month and amount */
}

.month-amount {
  font-size: 17px; /* Increased font size for month and amount */
}

.accordion-body {
  padding: 10px;
  border: 1px solid #ddd;
  border-top: none;
  background: #f9f9f9;
  font-size: 11px;
}

.increase {
  color: #F87171; /* 채도 낮춘 빨간색 */
  font-weight: bold;
  font-size: 12px;
}

.decrease {
  color: #3B82F6; /* 조정된 색상 */
  font-weight: bold;
  font-size: 12px;
}

.largest-change {
  font-size: 10px; /* Reduced font size for the change text */
  font-weight: normal; /* Normal weight for the change text */
}

.largest-change .increase {
  color: #F87171; /* 채도 낮춘 빨간색 */
}

.largest-change .decrease {
  color: #3B82F6; /* 조정된 색상 */
}

.largest-change-text {
  font-weight: bold;
}
</style>
