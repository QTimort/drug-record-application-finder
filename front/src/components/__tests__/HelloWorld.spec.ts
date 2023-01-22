import { describe, expect, it } from 'vitest';
import { mount } from '@vue/test-utils';
import vuetify from '@/plugins/vuetify';

import HelloWorld from '../HelloWorld.vue';

describe('HelloWorld.ts', () => {
  it('mount component', async () => {
    expect(HelloWorld).toBeTruthy();

    const wrapper = mount(HelloWorld as any, {
      global: {
        plugins: [vuetify],
      },
    });

    expect(wrapper.text()).toContain('Welcome to');
  });
});
