package co.com.woaho.principal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="parametracion-adicional")
public class Configuracion {

	private String appid;
	
	private String restKey;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getRestKey() {
		return restKey;
	}

	public void setRestKey(String restKey) {
		this.restKey = restKey;
	}
}
