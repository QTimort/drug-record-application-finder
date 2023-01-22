<template>
  <router-view />
</template>

<script lang="ts" setup>
import { useTheme } from 'vuetify';

const theme = useTheme();
theme.global.name.value = import.meta.env.VITE_APP_DEFAULT_THEME || 'light';
</script>
