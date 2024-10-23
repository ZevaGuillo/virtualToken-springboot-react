import { useEffect, useState } from "react";
import { TokenResponse } from "../interface/Token";
import { TokenService } from "../service/TokenService";
import { useAuthStore } from "@/store/useAuthStore";

const useToken = () => {
  const [currentToken, setCurrentToken] = useState<string>("");
  const [timeLeft, setTimeLeft] = useState<number>(60);
  const user = useAuthStore((state) => state.user);

  const refreshToken = async () => {
    try {
      const tokenResponse: TokenResponse = await TokenService.generateToken(user!.id!);
      setCurrentToken(tokenResponse.token);
      setTimeLeft(60);
    } catch (error) {
      console.error("Error al generar token:", error);
    }
  };

  useEffect(() => {
    if (timeLeft > 0) {
      const timerId = setTimeout(() => setTimeLeft(timeLeft - 1), 1000);
      return () => clearTimeout(timerId);
    } else {
      refreshToken();
    }
  }, [timeLeft]);

  useEffect(() => {
    refreshToken();
  }, []);

  return { currentToken, timeLeft, refreshToken };
};

export default useToken;