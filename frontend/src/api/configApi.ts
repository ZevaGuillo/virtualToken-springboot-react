import { useAuthStore } from "@/store/useAuthStore";
import axios from "axios";

export const configApi = axios.create({
    baseURL: import.meta.env.VITE_BACKEND_URL
  });
  
  // interceptor
  configApi.interceptors.request.use((config) => {
    const token = useAuthStore.getState().authToken;
    if (token) config.headers["Authorization"] = `Bearer ${token}`;
    return config;
  });