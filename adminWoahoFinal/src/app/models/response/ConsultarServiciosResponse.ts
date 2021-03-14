import { BaseResponse } from './BaseResponse';

export class ConsultarServiciosResponse extends BaseResponse {
    listServicios: Servicio[];
}

export class Servicio {
    codigo: string;
    image: string;
    name: string;
    listTarifas: TarifaServicio[];
    category: string;
    clicks: number;
    description: string;
}

export class TarifaServicio {
    pais: string;
    valor: number;
    moneda: string;
    unidad: string;
}
