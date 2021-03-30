import { ServicioDto } from '../general/general';

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
