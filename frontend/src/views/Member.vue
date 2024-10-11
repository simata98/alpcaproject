<template>
    <div class="flex space-x-4">
      <!-- 첫 번째 컴포넌트: 가입 -->
      <Card class="w-[350px]">
        <CardHeader>
          <CardTitle>Join</CardTitle>
          <CardDescription>나의 정보를 입력하고 가족의 승인을 받습니다. </CardDescription>
        </CardHeader>
        <CardContent>
          <!-- submit함수 추가 -->
          <form @submit.prevent=""> 
            <div class="grid items-center w-full gap-4">
              <div class="flex flex-col space-y-1.5">
                <Label for="familyId">FamilyId</Label>
                <Input
                  id="familyId"
                  v-model="familyId"
                  placeholder="FamilyId"
                />
              </div>
              <div class="flex flex-col space-y-1.5">
                <Label for="customerId">ID</Label>
                <Input
                  id="customerId"
                  v-model="customerId"
                  placeholder="CustomerId"
                />
              </div>
            </div>
          </form>
        </CardContent>
        <CardFooter class="flex justify-between px-6 pb-6">
          <Button @click="handleStartClick">Submit</Button>
        </CardFooter>
      </Card>
      <!-- 첫 번째 컴포넌트: 주소 입력 및 위치 추가 -->
      <Card class="w-[350px]">
        <CardHeader>
          <CardTitle>Member Device</CardTitle>
          <CardDescription>Member가 나의 출발지를 등록하고 출발버튼을 누릅니다.</CardDescription>
        </CardHeader>
        <CardContent>
          <form @submit.prevent="fetchCoordinates">
            <div class="grid items-center w-full gap-4">
              <div class="flex flex-col space-y-1.5">
                <Label for="familyId">FamilyId</Label>
                <Input
                  id="familyId"
                  v-model="familyId"
                  placeholder="FamilyId"
                />
              </div>
              <div class="flex flex-col space-y-1.5">
                <Label for="customerId">ID</Label>
                <Input
                  id="customerId"
                  v-model="customerId"
                  placeholder="CustomerId"
                />
              </div>
              <div class="flex flex-col space-y-1.5">
                <Label for="StartLoc">출발지 주소</Label>
                <Input id="StartLoc" v-model="startAddress" placeholder="주소를 입력하세요" />
              </div>
              <div class="flex flex-col space-y-1.5">
                <Label for="GoalLoc">도착지 주소</Label>
                <Input id="GoalLoc" v-model="goalAddress" placeholder="주소를 입력하세요" />
              </div>
            </div>
          </form>
        </CardContent>
        <CardFooter class="flex justify-between px-6 pb-6">
          <Button @click="deleteLocation" variant="outline">
            Cancel
          </Button>
          <Button @click="handleStartClick">Start</Button>
        </CardFooter>
      </Card>
  
      <!-- 두 번째 컴포넌트: 스텝퍼 및 폴링 -->
      <Card class="w-[350px]">
        <CardHeader>
          <CardTitle>이동 상태({{ moveId }})</CardTitle>
          <CardDescription>현재 이동 상태를 확인합니다.</CardDescription>
        </CardHeader>
        <CardContent>
          <Stepper orientation="vertical" class="mx-auto flex w-full max-w-md flex-col justify-start gap-10">
            <StepperItem
              v-for="step in steps"
              :key="step.step"
              v-slot="{ state }"
              class="relative flex w-full items-start gap-6"
              :step="step.step"
            >
              <StepperSeparator
                v-if="step.step !== steps[steps.length - 1].step"
                class="absolute left-[18px] top-[38px] block h-[105%] w-0.5 shrink-0 rounded-full bg-muted group-data-[state=completed]:bg-primary"
              />
  
              
            <StepperTrigger as-child>
              <Button
                :variant="getButtonVariant(step.step)"
                size="icon"
                class="z-10 rounded-full shrink-0"
                :class="[activeStep === step.step && 'ring-2 ring-ring ring-offset-2 ring-offset-background']"
              >
                <CheckIcon v-if="step.step < activeStep" class="size-5" />
                <CircleIcon v-if="step.step === activeStep" />
                <DotIcon v-if="step.step > activeStep" />
              </Button>
            </StepperTrigger>
  
              <div class="flex flex-col gap-1">
                <StepperTitle
                  :class="[activeStep === step.step ? 'text-primary' : '']"
                  class="text-sm font-semibold transition lg:text-base"
                >
                  {{ step.title }}
                </StepperTitle>
                <StepperDescription
                  :class="[activeStep === step.step ? 'text-primary' : '']"
                  class="sr-only text-xs text-muted-foreground transition md:not-sr-only lg:text-sm"
                >
                  {{ step.description }}
                </StepperDescription>
              </div>
            </StepperItem>
          </Stepper>
  
          <!-- 폴링 취소 버튼 -->
          <Button @click="stopPolling" class="mt-4 px-4 py-2 bg-red-500 text-white rounded">
            이동 취소
          </Button>
        </CardContent>
      </Card>
    </div>
    <div class="alarm-section">
        <Alarm :TriggerData="TriggerData" />
      </div>
        <router-view />
    <!-- <Toaster /> -->
  </template>
  
  <script setup lang="js">
  import { ref, onMounted, onBeforeUnmount } from 'vue';
  import axios from 'axios';
  import { Button } from '@/components/ui/button';
  import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
  } from '@/components/ui/card';
  import { Input } from '@/components/ui/input';
  import { Label } from '@/components/ui/label';
  import { CheckIcon, CircleIcon, DotIcon } from '@radix-icons/vue';
  import { Stepper, StepperDescription, StepperItem, StepperSeparator, StepperTitle, StepperTrigger } from '@/components/ui/stepper';
  import { Toaster } from '@/components/ui/sonner';
  // import { Alarm } from '@/components/Alarm.vue'

  // 주소 입력 관련 변수
  const startAddress = ref(''); // 입력된 출발지 주소
  const startCoordinates = ref({ x: '', y: '' }); // 변환된 출발지 좌표
  const goalAddress = ref(''); // 입력된 도착지 주소
  const goalCoordinates = ref({ x: '', y: '' }); // 변환된 도착지 좌표
  const customerId = ref(''); // 폼의 ID로 사용할 customerId
  const familyId = ref(''); // 폼의 ID로 사용할 familyId
  const moveId = ref(''); // moveId를 위한 필드 추가
  const locId = ref('');
  
  // 스텝 설정
  const steps = [
    {
      step: 1,
      title: '출발',
      description: '목적지에서 집으로 출발하였습니다.',
    },
    {
      step: 2,
      title: '이동 중',
      description: '목적지에서 집으로 이동 중입니다.',
    },
    {
      step: 3,
      title: '도착',
      description: '목적지에서 집으로 도착하였습니다.',
    },
  ];
  
// 각 스텝에 따른 버튼 스타일을 결정하는 함수
const getButtonVariant = (step) => {
  if (step < activeStep.value) {
    return 'default'; // 완료된 스텝
  } else if (step === activeStep.value) {
    return 'default'; // 현재 활성화된 스텝
  } else {
    return 'outline'; // 활성화되지 않은 스텝
  }
};

  // 폴링 관련 변수
  const isPolling = ref(false);
  const status = ref('start');
  const intervalId = ref(null);
  const pollCount = ref(0);
  const activeStep = ref(1); // 현재 활성화된 스텝
  
  const fetchStartCoordinates = async () => {
    if (!startAddress.value) {
      alert('주소를 입력해주세요.');
      return;
    }
  
    return new Promise((resolve, reject) => {
      // Naver Maps API를 사용하여 출발지 주소를 좌표로 변환
      naver.maps.Service.geocode(
        { query: startAddress.value },
        function (status, response) {
          if (status !== naver.maps.Service.Status.OK) {
            return reject('Something went wrong!');
          }
  
          const result = response.v2;
          const items = result.addresses;
  
          if (items.length > 0) {
            startCoordinates.value = {
              x: items[0].x,
              y: items[0].y,
            };
            resolve(); // 성공적으로 좌표를 찾았으므로 resolve
          } else {
            alert('검색 결과가 없습니다.');
            reject('No results found.');
          }
        }
      );
    });
  };
  const fetchGoalCoordinates = async () => {
    if (!goalAddress.value) {
      alert('주소를 입력해주세요.');
      return;
    }
  
    return new Promise((resolve, reject) => {
  
      // Naver Maps API를 사용하여 도착지 주소를 좌표로 변환
      naver.maps.Service.geocode(
        { query: goalAddress.value },
        function (status, response) {
          if (status !== naver.maps.Service.Status.OK) {
            return reject('Something went wrong!');
          }
  
          const result = response.v2;
          const items = result.addresses;
  
          if (items.length > 0) {
            goalCoordinates.value = {
              x: items[0].x,
              y: items[0].y,
            };
            resolve(); // 성공적으로 좌표를 찾았으므로 resolve
          } else {
            alert('검색 결과가 없습니다.');
            reject('No results found.');
          }
        }
      );
    });
  };
    // 새로운 위치를 추가하는 함수
  const fetchJoin = async () => {
    const url = 'http://localhost:8088/customer'; // API 엔드포인트 추가
    try {
      const response = await axios.post(url, {
        // 아래 body 추가 
      }, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
  
      alert('가입에 성공했습니다: ' + response.data);
    } catch (error) {
      console.error('Error adding location:', error);
      alert('가입 중 오류가 발생했습니다.');
    }
  };
  // 새로운 위치를 추가하는 함수
  const addLocation = async (moveId) => {
    const url = 'http://localhost:8088/locations'; // API 엔드포인트
    const currentDate = new Date();
    const yyyy = currentDate.getFullYear();
    const MM = String(currentDate.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const dd = String(currentDate.getDate()).padStart(2, '0');
    const HH = String(currentDate.getHours()).padStart(2, '0'); // 시
    const mm = String(currentDate.getMinutes()).padStart(2, '0'); // 분
    const ss = String(currentDate.getSeconds()).padStart(2, '0'); // 초
locId.value = "loc-" + yyyy + MM + dd + HH + mm + ss;
    try {
      const response = await axios.post(url, {
        locId: locId.value, // 예시 locId
        customerId: customerId.value, // 사용자 입력 customerId
        locX: parseFloat(startCoordinates.value.x), // 변환된 X좌표
        locY: parseFloat(startCoordinates.value.y), // 변환된 Y좌표
        moveId: moveId || null, // 사용자 입력 moveId (없으면 null)
      }, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
  
      alert('위치가 성공적으로 추가되었습니다: ' + response.data);
    } catch (error) {
      console.error('Error adding location:', error);
      alert('위치 추가 중 오류가 발생했습니다.');
    }
  };
  
  // 이동 시작 정보를 API로 보내는 함수
  const startMove = async () => {
  const url = 'http://localhost:8088/moves/startmove'; // API 엔드포인트

  try {
    const response = await axios.post(url, {
      // moveId: moveId.value || null,
      familyId: familyId.value || null,
      // role: role.value || null,
      startRdnAddr: startAddress.value,
      startLocX: parseFloat(startCoordinates.value.x), // 변환된 X좌표
      startLocY: parseFloat(startCoordinates.value.y), // 변환된 Y좌표
      goalLocX: parseFloat(goalCoordinates.value.x),  // 목표 X좌표
      goalLocY: parseFloat(goalCoordinates.value.y),  // 목표 Y좌표
      // distance: null, // 거리
      // duration: null, // 소요시간
      // path: null, // 경로
      // status: null, // 상태
      customerId: customerId.value, // 사용자 입력 customerId
    }, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    moveId.value = response.data.moveId;
    return response.data;
  } catch (error) {
    console.error('Error starting move:', error);
    alert('이동 시작 중 오류가 발생했습니다.');
  }
  };
  
  // 폴링 시작
  const startPolling = async () => {
  intervalId.value = setInterval(async () => {
    try {
      // 현재 이동 상태를 가져옵니다.
      const response = await axios.get(`http://localhost:8088/moves/${moveId.value}`);
      console.log('Polling response:', response.data);

      // 상태에 따라 활성 스텝 변경
      if (response.data.status === 'start' || response.data.status === 'moving') {
        activeStep.value = 2; // 이동 중 스텝 활성화
        
        // 이동 중일 때 update API 호출
        await axios.post('http://localhost:8088/moves/updatemove', {
            moveId: moveId.value,
        });

      } else if (response.data.status === 'end' || response.data.duration < 600000) {
        activeStep.value = 3; // 도착 스텝 활성화
        
        // 이동 완료 시 end API 호출
        await axios.post('http://localhost:8088/moves/endmove', {
          moveId: moveId.value,
          // 필요 시 추가 데이터
        });
        
        stopPolling(); // 완료되면 폴링 중지
      }
    } catch (error) {
      console.error('Error polling status:', error);
      alert('상태 폴링 중 오류가 발생했습니다.');
      stopPolling(); // 오류 발생 시 폴링 중지
    }
  }, 10000);
};
  
  // 폴링 중지
  const stopPolling = () => {
    clearInterval(intervalId.value);
    isPolling.value = false; // 폴링 상태 업데이트
    activeStep.value = 1; // 스텝 리셋
  };
  
    // 시작 버튼 클릭 시 호출되는 함수
  const handleStartClick = async () => {
    await fetchStartCoordinates(); // 좌표 가져오기
    await fetchGoalCoordinates();
    
    // 이동 시작하기 및 응답에서 moveId 추출
    const responseData = await startMove(); // startMove에서 response.data 반환
    console.log("responseData" + responseData);
    const moveIdFromResponse = responseData.moveId; // 응답에서 moveId 가져오기
    
    console.log("moveIdFromResponse" + moveIdFromResponse);
    await addLocation(moveIdFromResponse); // 위치 추가하기

    await startPolling()
    };

  
  // 삭제 버튼 클릭 시 호출되는 함수
  const deleteLocation = async () => {
  if (!customerId.value) {
    return alert('customerId를 입력하세요.');
  }

  const url = 'http://localhost:8088/locations'; // API 엔드포인트
  try {
    const response = await axios.delete(url, {
      headers: {
        'Content-Type': 'application/json',
      },
      data: {
        locId: locId.value, // locId는 null로 설정
        customerId: customerId.value, // customerId는 사용자 입력 값으로 설정
        locX: parseFloat(startCoordinates.value.x), // 변환된 X좌표
        locY: parseFloat(startCoordinates.value.y), // 변환된 Y좌표
        moveId: moveId.value, // moveId는 null로 설정
      },
    });

    alert('위치가 성공적으로 삭제되었습니다: ' + response.data);
  } catch (error) {
    console.error('Error deleting location:', error);
    alert('삭제 중 오류가 발생했습니다.');
  }
};

// 컴포넌트가 언마운트될 때 폴링 중지
onBeforeUnmount(() => {
  stopPolling()
})
  </script>
  
  <style scoped>
  /* 필요한 스타일 추가 */
  </style>
  