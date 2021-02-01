export interface PeticionResponse {
    mensaje: string;
}

export class BaseResponse {
    codigoRespuesta: string;
    mensajeRespuesta: string;
}

export class Servicio {
    id: string;

    image: string;

    name: string;

    price: number;

    category: number;

    clicks: number;

    description: string;
}

export class LoginAdminResponse extends BaseResponse {
    idUsuario: string;
}

export class ConsultarServiciosResponse extends BaseResponse {
    listServicios: Servicio [];
}
