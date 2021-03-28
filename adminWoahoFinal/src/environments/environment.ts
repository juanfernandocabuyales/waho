// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import { Constantes } from 'src/app/constants/constantes';

const url = 'http://198.54.123.142:8083/woahoAdmin/';

export const environment = {
  production: false,
  usuarioController: url + Constantes.CONTROLLER_USUARIO,
  servicioController: url + Constantes.CONTROLLER_SERVICIOS,
  territorioController: url + Constantes.CONTROLLER_TERRITORIOS,
  imagenController: url + Constantes.CONTROLLER_IMAGENES,
  categoriaController: url + Constantes.CONTROLLER_CATEGORIAS,
  monedaController: url + Constantes.CONTROLLER_MONEDAS
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
