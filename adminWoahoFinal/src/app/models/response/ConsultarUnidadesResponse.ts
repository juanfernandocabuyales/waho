import { UnidadDto } from '../general/general';
import { BaseResponse } from './BaseResponse';

export class ConsultarUnidadesResponse extends BaseResponse{
    listUnidades: UnidadDto[];
}
