<template>
  <v-app>
    <v-theme-provider>
      <default-bar />
      <default-view />
      <default-footer />
    </v-theme-provider>
  </v-app>
</template>

<script lang="ts" setup>
import DefaultBar from './AppBar.vue';
import DefaultFooter from './AppFooter.vue';
import DefaultView from './View.vue';
</script>
