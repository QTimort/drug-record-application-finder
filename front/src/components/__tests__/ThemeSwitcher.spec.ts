import { describe, expect, it } from 'vitest';
import { mount } from '@vue/test-utils';
import vuetify from '@/plugins/vuetify';

import ThemeSwitcher from '../ThemeSwitcher.vue';

describe('ThemeSwitcher.vue', () => {
  it('mount component', async () => {
    expect(ThemeSwitcher).toBeTruthy();

    const wrapper = mount(ThemeSwitcher, {
      global: {
        plugins: [vuetify],
      },
    });

    expect(wrapper.text()).toContain('Dark mode');
  });
});
