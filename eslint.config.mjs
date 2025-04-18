import globals from "globals";
import tseslint from "typescript-eslint";
import { defineConfig } from "eslint/config";

export default defineConfig([
  // For regular JS files
  { 
    files: ["**/*.js"], 
    languageOptions: { sourceType: "script" } 
  },

  // For TS/JS files with browser globals
  { 
    files: ["**/*.{js,mjs,cjs,ts,tsx}"], 
    languageOptions: { globals: globals.browser } 
  },

  // Recommended config from typescript-eslint
  ...tseslint.configs.recommended,

  // ✅ Custom rule override for `no-explicit-any`
  {
    files: ["**/*.{ts,tsx}"],
    rules: {
      "@typescript-eslint/no-explicit-any": "off", // or "warn" if you want warning only
    },
  },
]);
