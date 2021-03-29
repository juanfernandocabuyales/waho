import { BaseResponse } from './BaseResponse';
import { TarifaDto } from '../general/general';

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


