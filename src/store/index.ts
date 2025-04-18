import { configureStore } from "@reduxjs/toolkit";
import { persistReducer, persistStore } from "redux-persist";
import storage from "redux-persist/lib/storage"; 
import authReducer from "./authSlice";
import emergencyReducer from "./emergencySlice";
// Define  reducers
const authPersistConfig = {
  key: "auth",
  storage,
};

const emergencyPersistConfig = {
    key: "emergency",
    storage,
  };
  



// Persist reducers 
const persistedAuthReducer = persistReducer(authPersistConfig, authReducer);
const persistedEmergencyReducer = persistReducer(
    emergencyPersistConfig,
    emergencyReducer
  );
// Create the Redux store
const store = configureStore({
  reducer: {
    auth: persistedAuthReducer, // Use the persisted auth reducer
    emergency: persistedEmergencyReducer, // Use the persisted emergency reducer
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