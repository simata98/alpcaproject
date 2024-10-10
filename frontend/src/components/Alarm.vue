<script>
import { watch, ref, onMounted, onBeforeUnmount } from 'vue';
import { toast } from 'vue-sonner';
import axios from 'axios';

export default {
  props: {
    TriggerData: {
      type: Object,
      required: true,
    },
  },
  setup(props) {
    const hasShownToast = ref(false); // 토스트가 이미 표시되었는지 추적하는 플래그
    const pollingInterval = ref(null); // 폴링을 위한 interval 저장 변수

    // 10분 후에 hasShownToast를 false로 설정하는 함수
    const resetToastFlag = () => {
      setTimeout(() => {
        hasShownToast.value = false;
      }, 10 * 60 * 1000); // 10분 (10 * 60 * 1000ms)
    };

    const showToast = (message, description) => {
      if (!hasShownToast.value) {
        toast(message, {
          description,
          action: {
            label: 'Check',
            onClick: () => console.log('Check'),
          },
        });
        hasShownToast.value = true; // 토스트가 표시되었음을 기록
        resetToastFlag(); // 10분 후 플래그 리셋
      }
    };

    const checkMoveStatus = async () => {
      try {
        const response = await axios.get('http://localhost:8088/moves');
        const moves = response.data._embedded.moves; // 응답 내의 moves 리스트에 접근

        moves.forEach((move) => {
          if (move.status === 'start' && !hasShownToast.value) {
            const min = Math.floor(move.duration / 60000);
            showToast(`${move.callerNm}님이 출발했습니다.`, `${move.callerNm}님이 ${min}분 후 도착합니다.`);
          } else if (move.status === 'end' && !hasShownToast.value) {
            showToast(`${move.callerNm}님이 도착했습니다.`, `${move.callerNm}님이 목적지에 도착했습니다.`);
          }
        });
      } catch (error) {
        console.error('Error fetching move status:', error);
      }
    };

    const startPolling = () => {
      pollingInterval.value = setInterval(checkMoveStatus, 1000); // 1초마다 API 호출
    };

    const stopPolling = () => {
      clearInterval(pollingInterval.value);
    };

    onMounted(() => {
      startPolling(); // 컴포넌트가 마운트될 때 폴링 시작
    });

    onBeforeUnmount(() => {
      stopPolling(); // 컴포넌트가 언마운트될 때 폴링 중지
    });

    // TriggerData가 변경될 때도 토스트 확인
    watch(
      () => props.TriggerData,
      (newVal) => {
        if (newVal.isTrigger) {
          showToast(`${newVal.callerNm}님이 곧 도착합니다.`, `${newVal.callerNm}님이 ${Math.floor(newVal.duration / 60000)}분 후 도착합니다.`);
        }
      },
      { immediate: true, deep: true }
    );

    return {
      showToast,
      startPolling,
      stopPolling
    };
  }
};
</script>
