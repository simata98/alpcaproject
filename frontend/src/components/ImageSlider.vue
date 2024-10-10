<template>
  <div class="slider">
    <div class="slides">
      <div
        class="slide"
        v-for="(image, index) in images"
        :key="index"
        :style="{ transform: `translateX(-${currentIndex * 100}%)` }"
      >
        <img :src="image" />
      </div>
    </div>
    <div class="dots">
      <span
        v-for="(image, index) in images"
        :key="index"
        :class="{ active: index === currentIndex }"
        @click="currentIndex = index"
      ></span>
    </div>
  </div>
</template>

<script>
  import img1 from '@/assets/image/AI_AS_001_svdt_06_01.png';
  import img2 from '@/assets/image/AI_AS_001_svdt_06_03.png';
  import img3 from '@/assets/image/AI_AS_001_svdt_07.png';

export default {
  data() {
    return {
      currentIndex: 0,
      images: [img1, img2, img3]
    };
  },
  mounted() {
    setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.images.length;
    }, 3000);
  }
};
</script>

<style scoped>
.slider {
  position: relative;
  width: 100%;
  overflow: hidden;
}
.slides {
  display: flex;
  transition: transform 0.5s ease-in-out;
  width: 100%;
}
.slide {
  min-width: 100%;
  box-sizing: border-box;
}
.slide img {
  width: 100%;
  height: auto;
}
.dots {
  position: absolute;
  bottom: 10px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 5px;
  align-items: center; /* 수직 중앙 정렬 */
}
.dots span {
  width: 10px;
  height: 10px;
  background-color: #bbb;
  border-radius: 50%;
  display: block;
  cursor: pointer;
}
.dots .active {
  background-color: #333;
}
</style>
