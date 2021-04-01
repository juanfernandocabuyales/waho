export class MonedaDto {
    idMoneda: string;
    nombreMoneda: string;
    idTerritorio: string;
}

export class UnidadDto{
    idUnidad: string;
    nombreUnidad: string;
}

export class TarifaDto{
    codigo: string;
    pais: string;
    valor: number;
    moneda: string;
    unidad: string;
}

export class ServicioDto{
    codigo: string;
    nombre: string;
    imagen: string;
    codigoImagen: string;
    categoria: string;
    pais: string;
    descripcion: string;
    listTarifas: TarifaDto[];
}

export class Categoria {
    idCategoria: string;
    nombreCategoria: string;
}

export class ImagenDto {
    idImagen: string;
    nombreImagen: string;
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

export class OpcionesDto{
    nombre: string;
    imagen: string;
    ruta: string;
}
