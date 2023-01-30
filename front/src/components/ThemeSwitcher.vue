<template>
  <v-switch
    id="theme-switch"
    :model-value="theme.global.current.value.dark"
    style="display: flex"
    hint="This toggles the application theme"
    inset
    label="Dark mode"
    persistent-hint
    @click="toggleTheme"
  />
</template>

<script setup lang="ts">
import { useTheme } from 'vuetify';

const theme = useTheme();

/**
 *
 */
function toggleTheme(): boolean {
  theme.global.name.value = theme.global.current.value.dark ? 'light' : 'dark';
  return theme.global.name.value === 'dark';
}
</script>
