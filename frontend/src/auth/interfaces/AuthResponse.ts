import { User } from "./User"

export interface AuthResponse{
    user: User
    message: string
    jwt: string
    status: boolean
}
