<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { Toaster } from '@/components/ui/sonner'
</script>

<template>
  <header>
    <div class="wrapper">
      <nav>
      </nav>
    </div>
  </header>
  <Toaster />
  <RouterView />
</template>

<style scoped>
</style>
