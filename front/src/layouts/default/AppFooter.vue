<template>
  <v-footer class="bg-indigo-lighten-1" style="flex: none">
    <v-row justify="center" no-gutters>
      <div id="footer-company-copyright" class="pt-0">
        {{ copyright }} — {{ new Date().getFullYear() }}
      </div>
      <v-divider />
      <v-col class="bg-primary-lighten-2 py-4 text-center text-white" cols="12">
        <span> Server build version — {{ serverVersion?.buildVersion || '?' }}</span>
      </v-col>
    </v-row>
  </v-footer>
</template>

<script lang="ts" setup>
import { getEnvOrDefault } from '@/config/env';
import { useAppStore } from '@/stores/app';
import { storeToRefs } from 'pinia';

const { serverVersion } = storeToRefs(useAppStore());
const copyright = getEnvOrDefault().VITE_APP_COPYRIGHT;
</script>
