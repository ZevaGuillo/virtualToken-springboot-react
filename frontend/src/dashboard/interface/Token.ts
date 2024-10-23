export interface TokenResponse {
  id: number;
  token: string;
  tiempoGeneracion: string;
  tiempoExpiracion: string;
  status: string;
}

export interface TokensListResponse {
    content?:          TokenResponse[];
    pageable?:         Pageable;
    last?:             boolean;
    totalElements?:    number;
    totalPages?:       number;
    size?:             number;
    number?:           number;
    sort?:             Sort;
    numberOfElements?: number;
    first?:            boolean;
    empty?:            boolean;
}


export interface Pageable {
    pageNumber?: number;
    pageSize?:   number;
    sort?:       Sort;
    offset?:     number;
    paged?:      boolean;
    unpaged?:    boolean;
}

export interface Sort {
    empty?:    boolean;
    sorted?:   boolean;
    unsorted?: boolean;
}