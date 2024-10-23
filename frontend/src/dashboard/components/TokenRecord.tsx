
import { Button } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Check,
  ChevronLeft,
  ChevronRight,
  Copy,
  RefreshCw,
} from "lucide-react";
import {
  Dialog,
  DialogContent,
  DialogTrigger,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Badge } from "@/components/ui/badge";
import useToken from "../hooks/useToken";
import useTokenHistory from "../hooks/useTokenHistory";
import useCurl from "../hooks/useCurl";

export interface TokenRecord {
  id: number;
  token: string;
  generatedAt: string;
  expiredAt: string;
  status: string;
}

const TokenVirtual: React.FC = () => {
  const { currentToken, timeLeft } = useToken();
  const {
    tokenRecords,
    tokenFilter,
    setTokenFilter,
    startDate,
    setStartDate,
    endDate,
    setEndDate,
    usageFilter,
    setUsageFilter,
    currentPage,
    totalPages,
    paginate,
    setCurrentPage
  } = useTokenHistory(currentToken);
  const { curlCommand, copyToClipboard, isCopied } = useCurl(currentToken);


  return (
    <div className="container mx-auto p-4 max-w-4xl">
      <Card className="mb-8">
        <CardHeader>
          <CardTitle>Token Virtual</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex items-center justify-between">
            <div>
              <p className="text-3xl font-bold">{currentToken || "------"}</p>
              <p className="text-sm text-muted-foreground">
                Válido por: {timeLeft} segundos
              </p>
            </div>
            <Dialog>
              <DialogTrigger asChild>
                <Button variant="outline">
                  <RefreshCw className="mr-2 h-4 w-4" />
                  Generar Nuevo Token
                </Button>
              </DialogTrigger>
              <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                  <DialogTitle>Nuevo Token Generado</DialogTitle>
                </DialogHeader>
                <div className="grid gap-4 py-4">
                  <p className="text-center text-2xl font-bold">
                    {currentToken}
                  </p>
                  <p className="text-center">
                    Este token es válido por 60 segundos
                  </p>
                </div>
              </DialogContent>
            </Dialog>
          </div>
          <h5 className="py-3">Usa el token con el siguiente curl:</h5>
          <div className="bg-muted p-4 rounded-md relative">
            <pre className="text-sm overflow-x-auto whitespace-pre-wrap break-all">
              {curlCommand}
            </pre>
            <Button
              variant="ghost"
              size="icon"
              className="absolute top-2 right-2"
              onClick={copyToClipboard}
            >
              {isCopied ? (
                <Check className="h-4 w-4" />
              ) : (
                <Copy className="h-4 w-4" />
              )}
            </Button>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Historial de Tokens</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-4">
            <div>
              <Label htmlFor="token-filter" className="mb-2 block">
                Token
              </Label>
              <Input
                id="token-filter"
                type="text"
                placeholder="Buscar token"
                value={tokenFilter}
                onChange={(e) => {
                  setCurrentPage(1);
                  setTokenFilter(e.target.value);
                }}
              />
            </div>
            <div>
              <Label htmlFor="start-date" className="mb-2 block">
                Fecha inicio
              </Label>
              <Input
                id="start-date"
                type="date"
                value={startDate}
                onChange={(e) => {
                  setCurrentPage(1);
                  setStartDate(e.target.value);
                }}
              />
            </div>
            <div>
              <Label htmlFor="end-date" className="mb-2 block">
                Fecha fin
              </Label>
              <Input
                id="end-date"
                type="date"
                value={endDate}
                onChange={(e) => {
                  setCurrentPage(1);
                  setEndDate(e.target.value);
                }}
              />
            </div>
            <div>
              <Label htmlFor="usage-filter" className="mb-2 block">
                Estado
              </Label>
              <Select
                value={usageFilter}
                onValueChange={(value) => {
                  setCurrentPage(1);
                  setUsageFilter(value);
                }}
              >
                <SelectTrigger id="usage-filter">
                  <SelectValue placeholder="Filtrar por uso" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="all">Todos</SelectItem>
                  <SelectItem value="activo">Activos</SelectItem>
                  <SelectItem value="usado">Usados</SelectItem>
                  <SelectItem value="inactivo">No usados</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </div>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Token</TableHead>
                <TableHead>Generado</TableHead>
                <TableHead>Expirado</TableHead>
                <TableHead>Estado</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {tokenRecords.map((record) => (
                <TableRow key={record.id}>
                  <TableCell className="font-medium">{record.token}</TableCell>
                  <TableCell>
                    {new Date(record.generatedAt).toLocaleString()}
                  </TableCell>
                  <TableCell>
                    {new Date(record.expiredAt).toLocaleString()}
                  </TableCell>
                  <TableCell>
                    <Badge
                      variant={
                        record.status == "usado" ? "outline" : "secondary"
                      }
                    >
                      {record.status}
                    </Badge>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          <div className="flex items-center justify-between space-x-2 py-4">
            <Button
              variant="outline"
              size="sm"
              onClick={() => paginate(currentPage - 1)}
              disabled={currentPage === 1}
            >
              <ChevronLeft className="h-4 w-4" />
              Anterior
            </Button>
            <div className="text-sm text-muted-foreground">
              Página {currentPage} de {totalPages}
            </div>
            <Button
              variant="outline"
              size="sm"
              onClick={() => paginate(currentPage + 1)}
              disabled={currentPage === totalPages}
            >
              Siguiente
              <ChevronRight className="h-4 w-4" />
            </Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default TokenVirtual;
