<template>
  <router-view />
</template>

<script lang="ts" setup>
import { useTheme } from 'vuetify';
import {getEnvOrDefault} from "@/config/env";

const theme = useTheme();
theme.global.name.value = getEnvOrDefault().VITE_APP_DEFAULT_THEME;
</script>
