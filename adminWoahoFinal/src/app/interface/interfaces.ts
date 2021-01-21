export interface DataDialog{
    strTitulo: string;
    strInformacion: string;
    blnBotonCancelar: boolean;
}

export interface PeticionRequest{
    strMensaje: string;
}

export interface PeticionResponse{
    mensaje: string;
}

export interface ResponseGeneral{
    codigoRespuesta: string;
    mensajeRespuesta: string;
}
