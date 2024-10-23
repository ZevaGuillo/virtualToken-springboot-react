import { RegisterUser, User } from "@/auth/interfaces/User";
import { AuthService } from "@/auth/service/auth.service";
import { toast } from "sonner";
import { create, StateCreator } from "zustand";
import { devtools, persist } from "zustand/middleware";


export type AuthStatus = 'checking' | 'not-authenticated' | 'authenticated'
export interface AuthStoreInterface {
    status: AuthStatus
    user?: User
    authToken?: string;

    loginUser: (email: string, password: string) => Promise<void>;
    logoutUser: () => void;
    registerUser: (data: RegisterUser) => Promise<void>;
}

const storeApi: StateCreator<AuthStoreInterface> = (set) => ({
    status: "not-authenticated",
    authToken: undefined,
    user: undefined,
    loginUser: async (email: string, password: string) => {
      try {
        const { jwt, user } = await AuthService.login(email, password);
        set({ status: "authenticated", authToken: jwt, user });
      } catch (error) {
        set({ status: "not-authenticated", authToken: undefined, user: undefined });
        console.log(error);
        toast.error("Credenciales incorrectas");
      }
    },
    logoutUser: () => {
      set({ status: "not-authenticated", authToken: undefined, user: undefined });
    },
    registerUser: async (data: RegisterUser) => {
      try {
          await AuthService.registerUser(data);
      } catch (error) {
          throw new Error(`${error}`);
      }
    }
  });

export const useAuthStore = create<AuthStoreInterface>()(
    devtools(
        persist(
            storeApi, { name: "auth-storage" }
        ))
);