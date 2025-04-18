import { configureStore } from "@reduxjs/toolkit";
import { persistReducer, persistStore } from "redux-persist";
import storage from "redux-persist/lib/storage"; 
import authReducer from "./authSlice";
// Define  reducers
const authPersistConfig = {
  key: "auth",
  storage,
};




// Persist reducers 
const persistedAuthReducer = persistReducer(authPersistConfig, authReducer);
// Create the Redux store
const store = configureStore({
  reducer: {
    auth: persistedAuthReducer, // Use the persisted auth reducer
  },
  devTools: process.env.NODE_ENV !== "production", // Enable Redux DevTools in development mode only
});

// Create the persistor
const persistor = persistStore(store);

// Export the store and persistor
export { store, persistor };

// Type definitions for convenience in TypeScript
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;