import { BaseRequest } from './BaseRequest';
import { ServicioDto } from '../general/general';

export class CrearServicioRequest extends BaseRequest {
    servicioDto: ServicioDto;
}
