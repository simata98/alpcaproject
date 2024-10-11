<template>
  <div class="dropdown">
    <button @click="toggleDropdown" class="dropdown-button">
        {{ selectedOption || 'Select an option' }}
    </button>
    <div v-if="isOpen" class="dropdown-menu">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="Search.."
        class="dropdown-search"
      />
      <button
        v-for="option in filteredOptions"
        :key="option"
        class="dropdown-item"
        @click="selectOption(option)"
      >
        {{ option }}
      </button>
    </div>
  </div>
</template>

<script>
import { ref, computed, defineEmits } from 'vue';

export default {
  props: {
    options: {
      type: Array,
      required: true
    }
  },
  setup(props, { emit }) {
    const isOpen = ref(false);
    const selectedOption = ref('');
    const searchQuery = ref('');

    const toggleDropdown = () => {
      isOpen.value = !isOpen.value;
    };

    const selectOption = (option) => {
      selectedOption.value = option;
      isOpen.value = false;
      emit('update:selected-option', option); // 부모에게 선택한 옵션 전달
    };

    const filteredOptions = computed(() => {
      const query = searchQuery.value.toLowerCase();
      return props.options.filter(option =>
        option.toLowerCase().includes(query)
      );
    });

    return {
      isOpen,
      selectedOption,
      searchQuery,
      toggleDropdown,
      selectOption,
      filteredOptions
    };
  }
};
</script>

<style scoped>
/* 컨테이너의 가로 정렬 */
.dropdown-container {
  display: flex;
  gap: 5rem; /* 드롭다운 간의 간격 */
}

/* 드롭다운 버튼 */
.dropdown-button {
  background-color: #ffffff;
  border: 1px solid #d1d5db;
  border-radius: 0.375rem; /* 둥근 모서리 */
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  padding: 0.5rem 0.75rem;
  font-size: 1rem;
  color: #4b5563;
  cursor: pointer;
  text-align: left;
  width: 15rem; /* 10자리 길이 */
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

.dropdown-button:focus {
  border-color: #3b82f6; /* 포커스된 경계 색상 */
  box-shadow: 0 0 0 1px #3b82f6; /* 포커스된 그림자 */
  outline: none;
}

/* 드롭다운 메뉴 */
.dropdown-menu {
  background-color: #ffffff;
  border: 1px solid #d1d5db;
  border-radius: 0.375rem;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  margin-top: 0.25rem;
  position: absolute;
  width: 10rem; /* 드롭다운 메뉴와 버튼의 길이를 같게 설정 */
  z-index: 10;
}

/* 검색 입력 필드 */
.dropdown-search {
  border: 1px solid #d1d5db;
  border-radius: 0.375rem;
  padding: 0.5rem;
  width: 100%;
  box-sizing: border-box;
  font-size: 1rem;
  margin-bottom: 0.25rem;
}

/* 드롭다운 항목 */
.dropdown-item {
  background-color: #ffffff;
  border: none;
  border-bottom: 1px solid #d1d5db;
  padding: 0.5rem 0.75rem;
  text-align: left;
  cursor: pointer;
  width: 100%;
  transition: background-color 0.2s ease;
}

.dropdown-item:hover {
  background-color: #f1f5f9; /* 호버 시 배경 색상 */
}

.dropdown-item:last-child {
  border-bottom: none;
}
</style>