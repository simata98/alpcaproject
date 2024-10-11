// import './assets/main.css'
import './assets/index.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import VueApexCharts from "vue3-apexcharts";

// import App from './App.vue'
import App from './views/Member.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(VueApexCharts);
app.use(router)

app.mount('#app')
