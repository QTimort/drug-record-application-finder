import { test, expect } from '@playwright/test';
import {config} from 'dotenv';

config({ path: "./.env" });

// See here how to get started:
// https://playwright.dev/docs/intro
test('visits the app root url', async ({ page }) => {
  await page.goto('/');
  const expectedCopyright = process.env.VITE_APP_COPYRIGHT || 'Application Copyright';
  await expect(page.locator('#footer-company-copyright')).toContainText(expectedCopyright);
});
