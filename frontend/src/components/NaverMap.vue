<template>
  <div>
    <div id="map" style="width: 100%; height: 400px;"></div>
  </div>
</template>

<script>
import { onMounted, ref, watch, onUnmounted } from 'vue';
import axios from 'axios';

export default {
  name: 'NaverMapWithApi',
  props: {
    MapCallerData: {
      type: Object,
      required: true
    }
  },
  setup(props, { emit }) {
    const map = ref(null);
    let polyline = null;
    let marker = null;
    let intervalId = null; // 인터벌 ID
    const updateInterval = 500; // 업데이트 간격 (1초)
    let ZOOM_VAL = 0;

    // 세션 데이터에서 토큰 가져오기
    const aptCd = sessionStorage.getItem('apt_cd');
    const roomDong = sessionStorage.getItem('room_dong');
    const roomHosu = sessionStorage.getItem('room_hosu');

    // 초기 위치를 API로부터 가져와서 지도를 설정하는 함수
    const initializeMapWithApiLocation = (lat, lng) => {
      map.value = new naver.maps.Map('map', {
        center: new naver.maps.LatLng(lat, lng), // API로부터 받은 위치를 중심으로 설정
        zoom: 50, // 초기 줌 레벨 설정
        size: new naver.maps.Size(window.innerWidth * 0.6, window.innerHeight * 0.9) 
      });
    };

    // API로부터 경로 좌표와 시작 위치를 가져오는 함수
    const fetchAndUpdatePath = () => {
      axios.get(`http://localhost:8080/api/router/latest?callerNm=${props.MapCallerData.callerNm}`, {
        headers: {
          'apt_cd': `${aptCd}`,
          'room_dong': `${roomDong}`,
          'room_hosu': `${roomHosu}`,
        }
      })
      .then((response) => {
        const { startLocX, startLocY, path, duration, callerNm, startRoadAddr } = response.data;

        // 도착 10분 전 알람 트리거
        if (duration < 600000) {
          emit('trigger', {
            'isTrigger': true,
            'callerNm': callerNm,
            'duration': duration,
            'startRoadAddr': startRoadAddr
          });
        } else {
          emit('trigger', {
            'isTrigger': false,
            'callerNm': callerNm,
            'duration': duration,
            'startRoadAddr': startRoadAddr
          });
        }

        // 초기 위치를 설정
        if (!map.value) {
          initializeMapWithApiLocation(startLocY, startLocX);
        }
        map.value.setCenter(new naver.maps.LatLng(startLocY, startLocX));
        if (ZOOM_VAL % 10 === 0) {
          map.value.setZoom(12, true);
        } else {
          map.value.setZoom(15, true);
        }
        ZOOM_VAL += 1;

        // 경로 좌표 파싱
        const newPathCoordinates = JSON.parse(path).map(coord => 
          new naver.maps.LatLng(coord[1], coord[0]) // LatLng 객체로 변환
        );
        if(marker) {
          marker.setOptions({
            position: new naver.maps.LatLng(startLocY, startLocX),
            map: map.value,
            icon: {
                url: `src/assets/image/avatar/${callerNm}_marker.png`,
            }
          })
        } else {
          marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(startLocY, startLocX),
            map: map.value,
            icon: {
                url: `src/assets/image/avatar/${callerNm}_marker.png`,
            }
          });
        }
        if (polyline) {
          polyline.setPath(newPathCoordinates);
        } else {
          polyline = new naver.maps.Polyline({
            map: map.value,
            path: newPathCoordinates,
            strokeColor: '#FF0000',
            strokeWeight: 5,
          });
        }
      })
      .catch(error => {
        console.error('경로 좌표 가져오기 오류:', error);
      });
    };

    onMounted(() => {
      fetchAndUpdatePath();
      intervalId = setInterval(fetchAndUpdatePath, updateInterval);
    });

    onUnmounted(() => {
      clearInterval(intervalId);
    });

    // TriggerData 변경 감지
    watch(() => props.TriggerData, (newTriggerData) => {
      if (newTriggerData && newTriggerData.startLocX && newTriggerData.startLocY) {
        if (!map.value) {
          initializeMapWithApiLocation(newTriggerData.startLocY, newTriggerData.startLocX);
        } else {
          map.value.setCenter(new naver.maps.LatLng(newTriggerData.startLocY, newTriggerData.startLocX));
        }
      }
    }, { deep: true });

    return {
      map,
    };
  },   
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 100%; /* 지도가 부모 높이를 채우도록 */
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>
