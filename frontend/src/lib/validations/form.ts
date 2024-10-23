import { z } from 'zod';


export const LoginFormSchema = z.object({
    username: z
        .string()
        .min(4, { message: 'Username obligatorio' })
        .regex(/^[a-zA-Z0-9]{5,12}$/, { message: 'Solo letras y números, longitud entre 5 y 12' }),
    password: z
        .string()
        .min(1, { message: 'Contraseña obligatoria' }),
})


export const RegisterFormSchema = z.object({
    email: z
        .string()
        .min(1, { message: 'Email obligatorio' })
        .email({
            message: 'Email no válido, Ingrese un email válido'
        }),
    password: z
        .string()
        .min(6, { message: 'La contraseña debe tener al menos 6 caracteres' }),
    repeatPassword: z
        .string()
        .min(1, { message: 'Por favor, confirmar la contraseña' }),
    username: z
        .string()
        .min(4, { message: 'Username obligatorio' })
        .regex(/^[a-zA-Z0-9]{5,12}$/, { message: 'Solo letras y números, longitud entre 5 y 12' }),
    lastname: z
        .string()
        .min(1, { message: 'Apellido obligatorio' }),
})
    .refine((data) => data.password === data.repeatPassword, {
        path: ['repeatPassword'],
        message: 'Las contraseñas con coinciden'
    })