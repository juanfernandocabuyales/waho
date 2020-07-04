package co.com.woaho.utilidades;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import co.com.respuestas.RespuestaNegativa;
import co.com.woaho.enumeraciones.EnumGeneral;

public class Utilidades {

	private static Utilidades utilidades;

	private RegistrarLog logs = new RegistrarLog(Utilidades.class);

	private final static String UTF_8 = "utf-8";

	private Utilidades() {

	}

	public static Utilidades getInstance() {
		if(utilidades == null) {
			utilidades = new Utilidades();
		}
		return utilidades;
	}

	public String procesarException(int pServicio,String pStrMensaje) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
			respuestaNegativa.setCodigoServicio(pServicio);
			respuestaNegativa.setRespuesta(pStrMensaje);
			return mapper.writeValueAsString(respuestaNegativa);
		}catch (Exception e) {
			logs.registrarLogError("procesarException", "No se ha podido procesar la peticion", e);
			return "";
		}
	}

	public String encriptarTexto(String pTexto) {
		String secretKey = EnumGeneral.LLAVE_CIFRADO.getValor();		
		String base64EncryptedString = "";
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes(UTF_8));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = pTexto.getBytes(UTF_8);
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			base64EncryptedString = new String(base64Bytes);

		} catch (Exception ex) {
			logs.registrarLogError("procesarException", "No se ha podido procesar la peticion", ex);
		}
		return base64EncryptedString;
	}


	public String desencriptarTexto(String textoEncriptado) throws Exception {		
		String secretKey = EnumGeneral.LLAVE_CIFRADO.getValor(); 
		String base64EncryptedString = "";

		try {
			byte[] message = Base64.decodeBase64(textoEncriptado.getBytes(UTF_8));
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes(UTF_8));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");

			Cipher decipher = Cipher.getInstance("DESede");
			decipher.init(Cipher.DECRYPT_MODE, key);

			byte[] plainText = decipher.doFinal(message);

			base64EncryptedString = new String(plainText, UTF_8);

		} catch (Exception ex) {
			logs.registrarLogError("procesarException", "No se ha podido procesar la peticion", ex);
		}		
		return base64EncryptedString;
	}
}
