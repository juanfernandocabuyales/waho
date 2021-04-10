package co.com.woaho.utilidades;

import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.modelo.EquivalenciaIdioma;

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

	public String obtenerEquivalencia(String pCadena,String pIdioma,IEquivalenciaIdiomaDao equivalenciaIdiomaDao) {
		EquivalenciaIdioma equivalenciaIdioma = equivalenciaIdiomaDao.obtenerEquivalencia(pCadena);
		if(equivalenciaIdioma == null || pIdioma == null) {
			return pCadena;
		}else {
			if(pIdioma.equalsIgnoreCase(Constantes.IDIOMA_INGLES)) {
				return equivalenciaIdioma.getEquivalenciaIdiomaIngles();
			}else {
				return equivalenciaIdioma.getEquivalenciaIdiomaOriginal();
			}
		}
	}
	
	public String obtenerEquivalencia(String pCadena,String pIdioma,IEquivalenciaIdiomaDao equivalenciaIdiomaDao,Object ... pParametros) {
		EquivalenciaIdioma equivalenciaIdioma = equivalenciaIdiomaDao.obtenerEquivalencia(pCadena);
		if(equivalenciaIdioma == null || pIdioma == null) {
			return pCadena;
		}else {
			if(pIdioma.equalsIgnoreCase(Constantes.IDIOMA_INGLES)) {
				return MessageFormat.format(equivalenciaIdioma.getEquivalenciaIdiomaIngles(), pParametros);
			}else {
				return MessageFormat.format(equivalenciaIdioma.getEquivalenciaIdiomaOriginal(), pParametros);
			}
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


	public String desencriptarTexto(String textoEncriptado) {		
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
	
	public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {  
        double radioTierra = 6371;//en kilómetros  
        double dLat = Math.toRadians(lat2 - lat1);  
        double dLng = Math.toRadians(lng2 - lng1);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        return radioTierra * va2;    
    }
}
