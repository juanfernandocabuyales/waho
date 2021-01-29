export interface PeticionResponse{
    mensaje: string;
}

export class BaseResponse {
    codigoRespuesta: string;
    mensajeRespuesta: string;
}

export class LoginAdminResponse extends BaseResponse{
    idUsuario: string;
}
