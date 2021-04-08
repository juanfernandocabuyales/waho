import { ServicioDto, TipoDto, TerritorioDto, UnidadDto } from '../general/general';

export class GeneralRequest {
    strMensaje: string;
}

export class BaseRequest{
    idioma: string;
}

export class ConsultarCategoriaRequest extends BaseRequest{
}

export class ConsultarImagenesRequest extends BaseRequest {
}

export class ConsultarMonedasRequest extends BaseRequest{
}

export class ConsultarServiciosRequest extends BaseRequest{
}

export class ConsultarTerritorioRequest extends BaseRequest{
    tipoTerritorio: string;
}

export class ConsultarUnidadesRequest extends BaseRequest{
}

export class CrearServicioRequest extends BaseRequest {
    servicioDto: ServicioDto;
}

export class LoginAdminRequest extends BaseRequest{
    usuario: string;
    llave: string;
}

export class CrearImagenRequest extends BaseRequest{
    idImagen: string;
    nombreImagen: string;
    alto: string;
    ancho: string;
}

export class EliminarRequest extends BaseRequest{
    id: string;
}

export class ConsultarTiposRequest extends BaseRequest{
}

export class CrearTipoRequest extends BaseRequest{
    tipoDto: TipoDto;
}

export class ConsultarTerritoriosRequest extends BaseRequest{
}

export class CrearTerritoriosRequest extends BaseRequest{
    territorioDto: TerritorioDto;
}

export class CrearUnidadRequest extends BaseRequest{
    unidad: UnidadDto;
}
