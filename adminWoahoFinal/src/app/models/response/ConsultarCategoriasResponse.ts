import { BaseResponse } from './BaseResponse';

export class ConsultarCategoriasResponse extends BaseResponse{
    listCategorias: Categoria[];
}

export class Categoria {
    idCategoria: string;
    nombreCategoria: string;
}
