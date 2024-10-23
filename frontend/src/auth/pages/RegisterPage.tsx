import { RegisterForm } from "../components/forms/RegisterForm";

export const RegisterPage = () => {
  return (
    <div className="mx-auto max-w-screen-xl px-4 py-16 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-lg">
        <h1 className="text-center text-2xl font-bold text-indigo-600 sm:text-3xl">
          PRUEBA DE DESARROLLO V4 - Guillermo Zevallos
        </h1>

        <p className="mx-auto mt-4 max-w-md text-center text-gray-500">
          Protege tu cuenta con nuestro Token Virtual, una clave segura y única
          que se actualiza cada 60 segundos para brindarte máxima seguridad. ¡Tu
          tranquilidad, nuestra prioridad!
        </p>

        <RegisterForm />
      </div>
    </div>
  );
};
