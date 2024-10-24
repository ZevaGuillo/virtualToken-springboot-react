import { useAuthStore } from "@/store/useAuthStore";
import axios from "axios";

export const BACKEND_URL = import.meta.env.VITE_APP_BACKEND_ADDRESS
? import.meta.env.VITE_APP_BACKEND_ADDRESS
: ""

export const configApi = axios.create({
  baseURL: BACKEND_URL + "/api/v1",
});

// interceptor
configApi.interceptors.request.use((config) => {
  const token = useAuthStore.getState().authToken;
  if (token) config.headers["Authorization"] = `Bearer ${token}`;
  return config;
});
