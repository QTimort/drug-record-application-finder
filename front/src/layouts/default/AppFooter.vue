<template>
  <v-footer class="bg-indigo-lighten-1" style="flex: none">
    <v-row justify="center" no-gutters>
      <div class="pt-0" id="footer-company-copyright">
        {{ copyright }}
      </div>
      <v-divider></v-divider>
      <v-col class="primary lighten-2 py-4 text-center white--text" cols="12">
        {{ new Date().getFullYear() }} —
        <strong>DRAF</strong>
      </v-col>
    </v-row>
  </v-footer>
</template>

<script lang="ts" setup>
const copyright = import.meta.env.VITE_APP_COPYRIGHT || 'Application Copyright';
</script>
