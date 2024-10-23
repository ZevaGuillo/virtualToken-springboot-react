export interface User {
    id?: number
    username?: string
    email?: string
}

export interface RegisterUser {
    username: string
    email: string
    password: string
}