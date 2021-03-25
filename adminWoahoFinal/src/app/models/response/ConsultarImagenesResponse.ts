import { BaseResponse } from './BaseResponse';

export class ConsultarImagenesResponse extends BaseResponse{
    listImagenes: ImagenDto[];
}

export class ImagenDto {
    idImagen: string;
    nombreImagen: string;
}
