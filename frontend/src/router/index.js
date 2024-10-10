import { createRouter, createWebHistory } from 'vue-router'
import Main from '@/views/Main.vue';
import Landing from '@/views/Landing.vue';
import User from '@/views/User.vue';
import RouteCare from '@/views/RouteCare.vue';
import CostCare from '@/views/CostCare.vue';
import AirCare from '@/views/AirCare.vue';
import HomeMonitor from '@/views/HomeMonitor.vue';
import MusicSync from '@/views/MusicSync.vue';
import PetCare from '@/views/PetCare.vue';
import Member from '@/views/Member.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Member',
      component: Member,
    },
    {
      path: '/landing',
      name: 'Landing',
      component: Landing,
    },
    {
      path: '/user',
      name: 'User',
      component: User
    },
    {
      path: '/route',
      name: 'RouteCare',
      component: RouteCare
    },
    {
      path: '/cost',
      name: 'CostCare',
      component: CostCare
    },
    {
      path: '/pet',
      name: 'PetCare',
      component: PetCare
    },
    {
      path: '/air',
      name: 'AirCare',
      component: AirCare
    },
    {
      path: '/monitor',
      name: 'HomeMonitor',
      component: HomeMonitor
    },
    {
      path: '/music',
      name: 'MusicSync',
      component: MusicSync
    }
  ]
})

// 세션에 아파트, 동호수 정보 없으면 무조건 랜딩페이지로 리다이렉트하게 라우터 설정
// router.beforeEach((to, from, next) => {
//   const hasSessionData = sessionStorage.getItem('apt_cd') &&
//                         sessionStorage.getItem('room_dong') &&
//                         sessionStorage.getItem('room_hosu');

//   console.log('Navigating to:', to.name);
//   console.log('Has session data:', hasSessionData);

//   if (!hasSessionData && to.name !== 'Landing' && to.name !== 'User') {
//     console.log('Redirecting to Landing page');
//     next({ name: 'Landing' });
//   } else {
//     next();
//   }
// });

export default router
