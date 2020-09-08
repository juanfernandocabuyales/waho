package co.com.woaho.alertas;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.ImmutableMap;

import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEnvioNotificacion;
import co.com.woaho.principal.Configuracion;
import co.com.woaho.utilidades.Constantes;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("rawtypes")
public class EnviarNotificacion implements IEnvioNotificacion {

	private RegistrarLog logs = new RegistrarLog(EnviarNotificacion.class);
	
	@Autowired
	private Configuracion configuracion;
	
	
	@Override
	public void notificarCodigoResgistro(HashMap<String, String> pParametros){
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", configuracion.getRestKey());
	        headers.set("Content-Type", "application/json");	        
	     
	        
	        Map<String, Object> body = ImmutableMap.of(
	                "app_id", configuracion.getAppid(),
	                "contents", ImmutableMap.of("es", pParametros.get(Constantes.CONTENIDO),"en","message english"),
	                "headings",ImmutableMap.of("es", pParametros.get(Constantes.CABECERA),"en","message english"),
	                "include_player_ids",Arrays.asList(pParametros.get(Constantes.ID_DEVICE))
	            );
	        
	        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

	        ResponseEntity<Map> sample = new RestTemplate().postForEntity("https://onesignal.com/api/v1/notifications", request,
	                Map.class);
	        logs.registrarInfo(sample.getBody());
		}catch(Exception e) {
			logs.registrarError(e,EnumMensajes.INCONVENIENTE_EN_NOTIFICACION.getMensaje());
		}
		
	}

	
}
