export interface PeticionResponse{
    mensaje: string;
}

export interface ResponseGeneral{
    codigoRespuesta: string;
    mensajeRespuesta: string;
}

export interface LoginAdminResponse{
    response: ResponseGeneral;
    idUsuario: string;
}
