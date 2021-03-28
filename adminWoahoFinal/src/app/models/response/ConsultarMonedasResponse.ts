import { BaseResponse } from './BaseResponse';
import { MonedaDto } from '../general/general';

export class ConsultarMonedasResponse extends BaseResponse{
    listMonedas: MonedaDto[];
}
