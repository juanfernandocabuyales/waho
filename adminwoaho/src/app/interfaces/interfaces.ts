export interface PeticionRequest{
    strMensaje : string
}

export interface PeticionResponse{
    mensaje : string
}

export interface ResponseGeneral{
    codigoRespuesta : string,
    mensajeRespuesta : string
}

export interface Servicio{
    id : string,
    image: string,
    name: string,
    price: number,
    category: string
}