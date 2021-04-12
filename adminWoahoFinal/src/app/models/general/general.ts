export class MonedaDto {
    idMoneda: string;
    nombreMoneda: string;
    idTerritorio: string;
}

export class UnidadDto {
    idUnidad: string;
    nombreUnidad: string;
}

export class TarifaDto {
    codigo: string;
    pais: string;
    valor: number;
    moneda: string;
    unidad: string;
}

export class ServicioDto {
    codigo: string;
    nombre: string;
    imagen: string;
    codigoImagen: string;
    categoria: string;
    pais: string;
    descripcion: string;
    listTarifas: TarifaDto[];
}

export class CategoriaDto {
    id: string;
    nombre: string;
    idImagen: string;
}

export class ImagenDto {
    idImagen: string;
    nombreImagen: string;
    ruta: string;
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

export class PaisDTO {
    idTerritorio: string;
    nombreTerritorio: string;
}

export class OpcionesDto {
    nombre: string;
    imagen: string;
    ruta: string;
}

export class FileDto {
    file: File;
    nombre: string;
}

export class TipoDto {
    id: string;
    nombre: string;
}

export class TerritorioDto {
    id: string;
    nombre: string;
    idPadre: string;
    idTipo: string;
    codigo: string;
    idImagen: string;
}

export class UsuarioDto {
    id: string;
    nombres: string;
    apellidos: string;
    celular: string;
    correo: string;
    clave: string;
    idSuscriptor: string;
    referrealCode: string;
    tipoUsuario: string;
    terminos: boolean;
}
