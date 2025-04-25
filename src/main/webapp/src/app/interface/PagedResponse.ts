// Interface for PagedResponse
export interface PagedResponse<T> {
  content: T[];
  pageable: Pageable;
  last: boolean;
  totalPages: number;
  totalElements: number;
  first: boolean;
  numberOfElements: number;
  size: number;
  number: number;
  sort: Sort;
  empty: boolean;
}

// Interface for Sort
interface Sort {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
}

// Interface for Pageable
interface Pageable {
  pageNumber: number;
  pageSize: number;
  sort: Sort;
  offset: number;
  unpaged: boolean;
  paged: boolean;
}
