// Composables
import { createRouter, createWebHistory } from 'vue-router';
import {getEnvOrDefault} from "@/config/env";

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/default/Default.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
          import(/* webpackChunkName: "home" */ '@/views/Home.vue'),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(getEnvOrDefault().VITE_APP_BASE_URL),
  routes,
});

export default router;
