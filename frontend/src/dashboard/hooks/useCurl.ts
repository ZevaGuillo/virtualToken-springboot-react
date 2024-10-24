import { useAuthStore } from "@/store/useAuthStore";
import { useState } from "react";

const useCurl = (currentToken: string) => {
  const [isCopied, setIsCopied] = useState(false);
  const user = useAuthStore((state) => state.user);
  const token = useAuthStore((state) => state.authToken);

  const curlCommand = `curl --location --request POST "${
    import.meta.env.VITE_APP_BACKEND_ADDRESS
  }/api/v1/token/usarToken?cliente=${user!.id!}&token=${currentToken}" \
--header "Authorization: Bearer ${token}"`;

  const copyToClipboard = async () => {
    try {
      await navigator.clipboard.writeText(curlCommand);
      setIsCopied(true);
      setTimeout(() => setIsCopied(false), 2000); // Restablece después de 2 segundos
    } catch (err) {
      console.error("Error al copiar el texto:", err);
    }
  };

  return { curlCommand, copyToClipboard, isCopied };
};

export default useCurl;
