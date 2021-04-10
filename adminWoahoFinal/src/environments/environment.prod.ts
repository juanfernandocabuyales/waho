import { Constantes } from 'src/app/constants/constantes';

const url = 'http://localhost:8083/woahoAdmin/';

export const environment = {
  production: true,
  usuarioController: url + Constantes.CONTROLLER_USUARIO,
  servicioController: url + Constantes.CONTROLLER_SERVICIOS,
  territorioController: url + Constantes.CONTROLLER_TERRITORIOS,
  imagenController: url + Constantes.CONTROLLER_IMAGENES,
  categoriaController: url + Constantes.CONTROLLER_CATEGORIAS,
  monedaController: url + Constantes.CONTROLLER_MONEDAS,
  unidadController: url + Constantes.CONTROLLER_UNIDAD,
  tipoTerritorioController: url + Constantes.CONTROLLER_TIPO_TERRITORIOS
};
