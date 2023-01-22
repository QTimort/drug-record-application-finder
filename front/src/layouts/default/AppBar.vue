<template>
  <v-app-bar flat>
    <v-app-bar-title>
      <v-icon icon="mdi-pill" />
      {{ title }}
    </v-app-bar-title>
    <theme-switcher />
  </v-app-bar>
</template>

<script lang="ts" setup>
import ThemeSwitcher from '@/components/ThemeSwitcher.vue';
import { getEnvOrDefault } from '@/config/env';

const title = getEnvOrDefault().VITE_APP_TITLE;
</script>
