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
    pais: string;
    valor: number;
    moneda: string;
    unidad: string;
}

export class ServicioDto{
    nombre: string;
    imagen: string;
    categoria: string;
    pais: string;
    descripcion: string;
    listTarifas: TarifaDto[];
}
