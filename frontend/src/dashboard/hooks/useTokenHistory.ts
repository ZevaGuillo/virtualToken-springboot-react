import { useEffect, useState } from "react";
import { TokenRecord } from "../components/TokenRecord";
import { useAuthStore } from "@/store/useAuthStore";
import { TokenService } from "../service/TokenService";

const useTokenHistory = (currentToken: string) => {
  const [tokenRecords, setTokenRecords] = useState<TokenRecord[]>([]);
  const [tokenFilter, setTokenFilter] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [usageFilter, setUsageFilter] = useState("all");
  const user = useAuthStore((state) => state.user);

  //pagination:
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);

  const paginate = (page: number) => {
    if (page < 1 || page > totalPages) return;
    setCurrentPage(page);
  };

  const fetchTokenHistory = async () => {
    try {
      const response = await TokenService.getTokenHistory({
        status: usageFilter,
        token: tokenFilter,
        startDate: startDate ? `${startDate}T00:00:00` : undefined,
        endDate: endDate ? `${endDate}T23:59:59` : undefined,
        page: currentPage-1,
        size: 10,
      });

      const records = response.content?.map(
        (record) =>
          ({
            id: record.id,
            token: record.token,
            generatedAt: record.tiempoGeneracion,
            expiredAt: record.tiempoExpiracion,
            status: record.status,
          } as TokenRecord)
      );
      setTokenRecords(records || []);
      setTotalPages(response.totalPages || 1);
    } catch (error) {
      console.error("Error al obtener el historial de tokens:", error);
    }
  };

  useEffect(() => {
    fetchTokenHistory();
  }, [tokenFilter, startDate, endDate, usageFilter, user, currentToken, currentPage]);

  return {
    tokenRecords,
    tokenFilter,
    setTokenFilter,
    startDate,
    setStartDate,
    endDate,
    setEndDate,
    usageFilter,
    setUsageFilter,
    paginate,
    currentPage,
    totalPages,
    setCurrentPage
  };
};

export default useTokenHistory;
