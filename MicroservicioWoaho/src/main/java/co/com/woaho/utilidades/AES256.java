package co.com.woaho.utilidades;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class AES256 {

	private static final String ALGORITMO = "AES";
	private  byte[] keyValue;

	/**
	 * Constructor de la clase.
	 */
	public AES256() {
	}

	private Key generatekey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGORITMO);
		return key;
	}

	public void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			byte [] key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			keyValue = key;            
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


	public String encrypt(String cadena,String pLlave) throws Exception {
		setKey(pLlave);
		Key key = generatekey();
		Cipher c = Cipher.getInstance(ALGORITMO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(cadena.getBytes());
		String encryptedValue = new BASE64Encoder().encode(encVal);
		return encryptedValue.replaceAll("[\n\r]","");
	}


	public String decrypt(String encryptedData, String keyEncrypt) throws Exception {
		setKey(keyEncrypt);
		Key key = generatekey();
		Cipher c = Cipher.getInstance(ALGORITMO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		return new String(decValue);
	}
	
	public static void main(String []args) {
    	try {
    		AES256 aes = new AES256();
    		String cifrado = aes.encrypt("bAR5mH0S422ZMkBe8OmG2NsTiMooYIGEO/BQDm4wOnJP4laNTpOPtHjzL8CJXwvmC3fAj2IfB0DUIdBQsK+7yBia1Ea7l52zxLhCJyTjRfarIG1d7rSGZanxHnJAKOkgu+WufdXnlaUDZ0rqIvchM4f1yoUVCzSsJN2DnCKi6KwsSPua2WwNyvZZlzabtPeq", "clave!");
    		System.out.println("Este es el texto cifrado: " + cifrado);
    		
    		String respuesta = aes.decrypt("7LuNzj1o6uMY0R/TG4/YPVwmgczRZNBZTq3FfCIir6bau6qZ16tL3Tl5j7zLrIjOZiWbaZte+D1qk5dNc1jB18TxSSQdJ9pwrHkZyTTuleiJsUlIGhNHOgyWhNNtuT+Z4Pa4RTqb/aZdggk8jgtjBw==", "clave!");
    		System.out.println("Este es el texto descifrado: " +respuesta);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
