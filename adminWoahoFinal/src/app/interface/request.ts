export class PeticionRequest{
    strMensaje: string;
}

export class GeneralRequest{
    idioma: string;
}

export class LoginAdminRequest extends GeneralRequest{
    usuario: string;
    llave: string;
}

export class ConsultarServiciosRequest extends GeneralRequest{
}
