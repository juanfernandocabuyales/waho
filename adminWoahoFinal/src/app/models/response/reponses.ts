import { MonedaDto, UnidadDto, Categoria, ImagenDto, ServicioDto, PaisDTO } from '../general/general';

export class GeneralResponse{
    mensaje: string;
}

export class BaseResponse {
    codigoRespuesta: string;
    mensajeRespuesta: string;
}

export class ConsultarCategoriasResponse extends BaseResponse{
    listCategorias: Categoria[];
}

export class ConsultarImagenesResponse extends BaseResponse{
    listImagenes: ImagenDto[];
}

export class ConsultarMonedasResponse extends BaseResponse{
    listMonedas: MonedaDto[];
}

export class ConsultarServiciosResponse extends BaseResponse {
    listServicios: ServicioDto[];
}

export class ConsultarTerritorioResponse extends BaseResponse {
    lisPaisesDto: PaisDTO[];
}

export class ConsultarUnidadesResponse extends BaseResponse{
    listUnidades: UnidadDto[];
}

export class CrearServicioResponse extends BaseResponse{
}

export class LoginAdminResponse extends BaseResponse{
    idUsuario: string;
}