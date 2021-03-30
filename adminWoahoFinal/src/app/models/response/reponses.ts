import { MonedaDto, TarifaDto, UnidadDto } from '../general/general';

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

export class Categoria {
    idCategoria: string;
    nombreCategoria: string;
}

export class ConsultarImagenesResponse extends BaseResponse{
    listImagenes: ImagenDto[];
}

export class ImagenDto {
    idImagen: string;
    nombreImagen: string;
}

export class ConsultarMonedasResponse extends BaseResponse{
    listMonedas: MonedaDto[];
}

export class ConsultarServiciosResponse extends BaseResponse {
    listServicios: Servicio[];
}

export class Servicio {
    codigo: string;
    image: string;
    name: string;
    listTarifas: TarifaDto[];
    category: string;
    clicks: number;
    description: string;
}

export class ConsultarTerritorioResponse extends BaseResponse {
    lisPaisesDto: PaisDTO[];
}

export class PaisDTO {
    idTerritorio: string;
    nombreTerritorio: string;
}

export class ConsultarUnidadesResponse extends BaseResponse{
    listUnidades: UnidadDto[];
}

export class CrearServicioResponse extends BaseResponse{
}

export class LoginAdminResponse extends BaseResponse{
    idUsuario: string;
}
