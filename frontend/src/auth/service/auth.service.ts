import { configApi } from "@/api/configApi";
import { AuthResponse } from "../interfaces/AuthResponse"
import { RegisterUser } from "../interfaces/User"
import { AxiosError } from "axios";

export class AuthService {
    static login = async(username: string, password: string): Promise<AuthResponse> => {
        try {
            const { data } = await configApi.post<AuthResponse>('/auth/login', {username, password});
            return data;
        } catch (error) {
            if(error instanceof AxiosError){
                console.log(error.response?.data);
                throw new Error(error.response?.data)
            }
            console.log(error);
            throw new Error('No puede iniciar sesión')
        }
    }

    static registerUser = async(dataUser: RegisterUser): Promise<AuthResponse> => {
        try {
            const { data } = await configApi.post<AuthResponse>('/auth/register', dataUser);
            return data;
        } catch (error) {
            if(error instanceof AxiosError){
                console.log(error.response?.data);
                throw new Error(error.response?.data)
            }
            console.log(error);
            throw new Error('No puede registrar al usuario')
        }
    }
}