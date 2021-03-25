import { BaseResponse } from './BaseResponse';

export class ConsultarTerritorioResponse extends BaseResponse {
    lisPaisesDto: PaisDTO[];
}

export class PaisDTO {
    idTerritorio: string;
    nombreTerritorio: string;
}
