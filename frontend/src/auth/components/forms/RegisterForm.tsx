import { Input } from "@/components/ui/input";
import { RegisterFormSchema } from "@/lib/validations/form";
import { useAuthStore } from "@/store/useAuthStore";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { z } from "zod";

type RegisterInput = z.infer<typeof RegisterFormSchema>;

export const RegisterForm = () => {
  const navigate = useNavigate();
  const registerUser = useAuthStore((state) => state.registerUser);
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<RegisterInput>({
    resolver: zodResolver(RegisterFormSchema),
  });

  const onSubmit = async (data: RegisterInput) => {

    const { username, password, email } = data;
    try {
      await registerUser({username, password, email});
      navigate("/");
    } catch (error) {
      console.log("No se pudo autenticar :", error);
    }
  };


  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="mb-0 mt-6 space-y-2 rounded-lg p-4 shadow-lg sm:p-6 lg:p-8">
      <p className="text-center text-lg font-medium">Crea tu usuario</p>

      <div className="py-6 flex flex-col gap-3">
        <label htmlFor="username">
          Username
          <Input
            type="text"
            id="username"
            placeholder=""
            {...register("username")}
          />
          {errors?.username?.message && (
            <p className="text-sm text-red-400 opacity-60">
              {errors.username.message}
            </p>
          )}
        </label>

        <label htmlFor="email">
          Email
          <Input
            type="text"
            id="email"
            placeholder=""
            {...register("email")}
          />
          {errors?.email?.message && (
            <p className="text-sm text-red-400 opacity-60">
              {errors.email.message}
            </p>
          )}
        </label>

        <label htmlFor="password">
          Contraseña
          <Input
            type="password"
            id="password"
            placeholder=""
            {...register("password")}
          />
          {errors?.password?.message && (
            <p className="text-sm text-red-400 opacity-60">
              {errors.password.message}
            </p>
          )}
        </label>

        <label htmlFor="repeatPassword">
          Repite la contraseña 
          <Input
            type="password"
            id="repeatPassword"
            placeholder=""
            {...register("repeatPassword")}
          />
          {errors?.repeatPassword?.message && (
            <p className="text-sm text-red-400 opacity-60">
              {errors.repeatPassword.message}
            </p>
          )}
        </label>
      </div>
      <button className="px-12 w-full py-3 bg-slate-800 text-white rounded-lg mb-7">
        Ingresar
      </button>

      <p className="text-center text-sm text-gray-500">
        Ya tienes una cuenta?
        <a
          className="underline"
          href="/login">
          Inicia sesión
        </a>
      </p>
    </form>
  );
};
