import { useEffect, useState } from "react";

import { Button } from "@/components/ui/button"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Input } from "@/components/ui/input"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { RefreshCw } from "lucide-react"
import { Dialog, DialogContent, DialogTrigger, DialogHeader, DialogTitle } from '@/components/ui/dialog';


interface TokenRecord {
    id: number;
    token: string;
    generatedAt: Date;
    usedAt: Date | null;
  }
  
  const TokenVirtual: React.FC = () => {
    const [currentToken, setCurrentToken] = useState<string>('')
    const [timeLeft, setTimeLeft] = useState<number>(60)
    const [tokenRecords, setTokenRecords] = useState<TokenRecord[]>([])
    const [tokenFilter, setTokenFilter] = useState<string>('')
    const [startDate, setStartDate] = useState<string>('')
    const [endDate, setEndDate] = useState<string>('')
    const [usageFilter, setUsageFilter] = useState<string>('all')
  
    const generateToken = (): string => {
      return Math.random().toString().substr(2, 6)
    }
  
    const refreshToken = () => {
      const newToken = generateToken()
      setCurrentToken(newToken)
      setTimeLeft(60)
      setTokenRecords(prev => [
        { id: Date.now(), token: newToken, generatedAt: new Date(), usedAt: null },
        ...prev
      ])
    }
  
    useEffect(() => {
      if (timeLeft > 0) {
        const timerId = setTimeout(() => setTimeLeft(timeLeft - 1), 1000)
        return () => clearTimeout(timerId)
      } else {
        refreshToken()
      }
    }, [timeLeft])
  
    const filteredRecords = tokenRecords.filter(record => {
      const tokenMatch = record.token.includes(tokenFilter)
      const dateMatch = (!startDate || new Date(record.generatedAt) >= new Date(startDate)) &&
                        (!endDate || new Date(record.generatedAt) <= new Date(endDate))
      const usageMatch = usageFilter === 'all' ||
                         (usageFilter === 'used' && record.usedAt) ||
                         (usageFilter === 'unused' && !record.usedAt)
      return tokenMatch && dateMatch && usageMatch
    })
  
    return (
      <div className="container mx-auto p-4 max-w-4xl">
        <Card className="mb-8">
          <CardHeader>
            <CardTitle>Token Virtual</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="flex items-center justify-between">
              <div>
                <p className="text-3xl font-bold">{currentToken || '------'}</p>
                <p className="text-sm text-muted-foreground">Válido por: {timeLeft} segundos</p>
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
                    <p className="text-center text-2xl font-bold">{currentToken}</p>
                    <p className="text-center">Este token es válido por 60 segundos</p>
                  </div>
                </DialogContent>
              </Dialog>
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
                <Label htmlFor="token-filter" className="mb-2 block">Token</Label>
                <Input
                  id="token-filter"
                  type="text"
                  placeholder="Buscar token"
                  value={tokenFilter}
                  onChange={(e) => setTokenFilter(e.target.value)}
                />
              </div>
              <div>
                <Label htmlFor="start-date" className="mb-2 block">Fecha inicio</Label>
                <Input
                  id="start-date"
                  type="date"
                  value={startDate}
                  onChange={(e) => setStartDate(e.target.value)}
                />
              </div>
              <div>
                <Label htmlFor="end-date" className="mb-2 block">Fecha fin</Label>
                <Input
                  id="end-date"
                  type="date"
                  value={endDate}
                  onChange={(e) => setEndDate(e.target.value)}
                />
              </div>
              <div>
                <Label htmlFor="usage-filter" className="mb-2 block">Estado</Label>
                <Select value={usageFilter} onValueChange={setUsageFilter}>
                  <SelectTrigger id="usage-filter">
                    <SelectValue placeholder="Filtrar por uso" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="all">Todos</SelectItem>
                    <SelectItem value="used">Usados</SelectItem>
                    <SelectItem value="unused">No usados</SelectItem>
                  </SelectContent>
                </Select>
              </div>
            </div>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Token</TableHead>
                  <TableHead>Generado</TableHead>
                  <TableHead>Estado</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {filteredRecords.map((record) => (
                  <TableRow key={record.id}>
                    <TableCell className="font-medium">{record.token}</TableCell>
                    <TableCell>{record.generatedAt.toLocaleString()}</TableCell>
                    <TableCell>{record.usedAt ? 'Usado' : 'No usado'}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>
    )
  }
  
  export default TokenVirtual