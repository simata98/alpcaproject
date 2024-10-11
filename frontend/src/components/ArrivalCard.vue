<template>
  <div class="card-container" @click="handleClick">
    <div class="card">
      <div class="profile">
        <img v-if="imageSrc" :src="imageSrc" alt="Profile" class="avatar" />
        <div v-else class="placeholder"></div>
        <div class="info">
          <p class="name">{{ comingFamily.callerNm }}</p>
          <p class="departure">출발지: {{ comingFamily.startRoadAddr }}</p>
          <p class="departure">
            소요시간: 
            <span v-if="hours > 0">{{ hours }} 시간 </span>
            {{ minutes }} 분
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    comingFamily: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      imageSrc: null // 이미지 경로를 저장할 변수
    };
  },
  async created() {
    const images = import.meta.glob('/src/assets/image/avatar/*.png');
    const path = `/src/assets/image/avatar/${this.comingFamily.callerNm}.png`;

    if (images[path]) {
      const module = await images[path](); // 이미지 모듈을 비동기로 가져옴
      this.imageSrc = module.default; // 이미지 URL 설정
    } else {
      console.error(`Image not found: ${path}`);
    }
  },
  computed: {
    hours() {
      return Math.floor(this.comingFamily.duration / 3600000); // 밀리세컨드를 시간으로 변환
    },
    minutes() {
      return Math.floor((this.comingFamily.duration % 3600000) / 60000); // 밀리세컨드를 분으로 변환
    }
  },
  methods: {
    handleClick() {
      this.$emit('card-clicked', this.comingFamily);
    }
  }
};
</script>

<style scoped>
.card-container {
  display: flex;
  flex-direction: column;
  background-color: transparent;
  border-radius: 12px;
  width: 380px;
  margin: 10px auto;
  font-family: "Helvetica Neue", Arial, sans-serif;
}

.card {
  display: flex;
  align-items: center;
  padding: 15px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s, box-shadow 0.3s;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.profile {
  display: flex;
  align-items: center;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 16px;
  border: 2px solid #e0e0e0;
}

.placeholder {
  width: 50px;
  height: 50px;
  background-color: #f0f0f0;
  border-radius: 50%;
  margin-right: 16px;
}

.info {
  display: flex;
  flex-direction: column;
}

.name {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.departure {
  font-size: 14px;
  color: #777;
  margin-top: 4px;
}
</style>
