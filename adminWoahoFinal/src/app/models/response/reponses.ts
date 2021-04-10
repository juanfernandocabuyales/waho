import { MonedaDto, UnidadDto, CategoriaDto, TerritorioDto, UsuarioDto } from '../general/general';
import { ImagenDto, ServicioDto, PaisDTO, TipoDto } from '../general/general';

export class GeneralResponse{
    mensaje: string;
}

export class BaseResponse {
    codigoRespuesta: string;
    mensajeRespuesta: string;
}

export class ConsultarCategoriasResponse extends BaseResponse{
    listCategorias: CategoriaDto[];
}

export class ConsultarImagenesResponse extends BaseResponse{
    listImagenes: ImagenDto[];
}

export class ConsultarMonedasResponse extends BaseResponse{
    listMonedas: MonedaDto[];
}

export class ConsultarServiciosResponse extends BaseResponse {
    listServicios: ServicioDto[];
}

export class ConsultarTerritorioResponse extends BaseResponse {
    lisPaisesDto: PaisDTO[];
}

export class ConsultarUnidadesResponse extends BaseResponse{
    listUnidades: UnidadDto[];
}

export class CrearServicioResponse extends BaseResponse{
}

export class LoginAdminResponse extends BaseResponse{
    idUsuario: string;
}

export class CrearImagenResponse extends BaseResponse{
    urlImagen: string;
}

export class EliminarResponse extends BaseResponse{
}

export class ConsultarTiposResponse extends BaseResponse{
    listTipos: TipoDto[];
}

export class CrearResponse extends BaseResponse{
}

export class ConsultarTerritoriosResponse extends BaseResponse{
    listTerritorios: TerritorioDto[];
}

export class CrearUnidadResponse extends BaseResponse{
}

export class ConsultarUsuariosResponse extends BaseResponse{
    listUsuarios: UsuarioDto[];
}
