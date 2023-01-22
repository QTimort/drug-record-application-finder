<template>
  <v-app-bar flat>
    <v-app-bar-title>
      <v-icon icon="mdi-pill" />
      {{ title }}
    </v-app-bar-title>
    <theme-switcher></theme-switcher>
  </v-app-bar>
</template>

<script lang="ts" setup>
import ThemeSwitcher from '@/components/ThemeSwitcher.vue';
const title = import.meta.env.VITE_APP_TITLE || 'Application Name';
</script>
