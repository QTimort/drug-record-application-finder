<template>
  <v-container>
    <h1>Search</h1>
    <v-responsive class="d-flex align-center text-center fill-height">
      <v-row class="d-flex align-center justify-center">
        <v-col>
          <v-text-field label="FDA Manufacturer Name" />
        </v-col>
        <v-col>
          <v-text-field label="FDA Brand Name" />
        </v-col>
      </v-row>
      <v-row class="d-flex align-center justify-center">
        <!-- todo use virtual table ?-->
        <v-container fluid>
          <v-table fixed-header height="300px" class="">
            <thead>
              <tr>
                <th class="text-left">Manufacturer Name</th>
                <th class="text-left">FDA Brand Name</th>
                <th class="text-left">Product</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="drug in drugs" :key="drug.id">
                <td class="text-left">{{ drug.manufacturerName }}</td>
                <td class="text-left">{{ drug.fdaBrandName }}</td>
                <td class="text-left">{{ drug.product }}</td>
              </tr>
            </tbody>
          </v-table>
        </v-container>
      </v-row>
    </v-responsive>
  </v-container>
</template>

<script lang="ts" setup>
const drugs = genDummyData(100);

/**
 *
 */
function genDummyData(count: number) {
  const ar = new Array(count);
  for (let i = 0; i < count; ++i) {
    ar[i] = {
      id: i,
      manufacturerName: `Manufacturer ${i}`,
      fdaBrandName: `FDA Name ${i}`,
      product: `Product ${i}`,
    };
  }
  return ar;
}
</script>
