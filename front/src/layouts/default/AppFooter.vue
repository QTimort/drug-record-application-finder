<template>
  <v-footer class="bg-indigo-lighten-1" style="flex: none">
    <v-row justify="center" no-gutters>
      <div class="pt-0" id="footer-company-copyright">
        {{ copyright }} — {{ new Date().getFullYear() }}
      </div>
      <v-divider></v-divider>
      <v-col class="primary lighten-2 py-4 text-center white--text" cols="12">
        <strong>{{ serverVersion }}</strong>
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
