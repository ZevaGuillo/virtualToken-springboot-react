import { configApi } from "@/api/configApi";
import { TokenResponse, TokensListResponse } from "../interface/Token";
import { AxiosError } from "axios";

export class TokenService {
  // Método para generar el token
  static generateToken = async (clienteId: number): Promise<TokenResponse> => {
    try {
      const { data } = await configApi.get<TokenResponse>(
        "/token/generarToken",
        {
          params: { cliente: clienteId },
        }
      );
      return data;
    } catch (error) {
      if (error instanceof AxiosError) {
        console.log(error.response?.data);
        throw new Error(error.response?.data || "Error al generar el token");
      }
      console.log(error);
      throw new Error("Error inesperado al generar el token");
    }
  };

  // Método para usar el token
  static useToken = async (
    clienteId: string,
    token: string
  ): Promise<boolean> => {
    try {
      const { data } = await configApi.get<boolean>("/token/usarToken", {
        params: { cliente: clienteId, token },
      });
      return data;
    } catch (error) {
      if (error instanceof AxiosError) {
        console.log(error.response?.data);
        throw new Error(error.response?.data || "Error al usar el token");
      }
      console.log(error);
      throw new Error("Error inesperado al usar el token");
    }
  };

  // Método para obtener el historial de tokens
  static getTokenHistory = async ({
    status,
    token,
    startDate,
    endDate,
    page = 0,
    size = 10,
  }: {
    status?: string;
    token?: string;
    startDate?: string;
    endDate?: string;
    page?: number;
    size?: number;
  }): Promise<TokensListResponse> => {
    try {
      const { data } = await configApi.get<TokensListResponse>("/token/all", {
        params: {
          status,
          token,
          startDate,
          endDate,
          page,
          size,
        },
      });
      return data;
    } catch (error) {
      if (error instanceof AxiosError) {
        console.log(error.response?.data);
        throw new Error(
          error.response?.data || "Error al obtener el historial de tokens"
        );
      }
      console.log(error);
      throw new Error("Error inesperado al obtener el historial de tokens");
    }
  };
}
