<template>
  <v-app-bar flat class="px-4">
    <v-app-bar-title>
      <v-icon icon="mdi-pill" />
      <span class="hidden-xs">
        {{ title }}
      </span>
    </v-app-bar-title>
    <div>
      <theme-switcher />
    </div>
  </v-app-bar>
</template>

<script lang="ts" setup>
import ThemeSwitcher from '@/components/ThemeSwitcher.vue';
import { getEnvOrDefault } from '@/config/env';

const title = getEnvOrDefault().VITE_APP_TITLE;
</script>
