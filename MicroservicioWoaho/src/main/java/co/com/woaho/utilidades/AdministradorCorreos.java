package co.com.woaho.utilidades;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import co.com.woaho.interfaces.IParametroDao;

public class AdministradorCorreos {

	private RegistrarLog logs = new RegistrarLog(AdministradorCorreos.class);
	
	private String hostSmtp;
	
	private String socketPort;
	
	private String port;
	
	private String smtpAthentication;
	
	private String correoEnvio;
	
	private String usuarioCuenta;
	
	private String claveCuenta;
	
	private String tipoConexion;
	
	private Properties propiedades = new Properties();

	public void enviarCorreo(final String pAsunto, final String pMensaje, final String pCorreoDestino,final IParametroDao pParametroDao)
			throws Exception {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					cargarConfiguraciones(pParametroDao);
					procesarEnvioCorreo(pCorreoDestino, pMensaje, pAsunto);
				} catch (Exception ex) {
					logs.registrarError(ex, "enviarCorreo");
				}
			}
		}).start();
	}

	
	private void cargarConfiguraciones(IParametroDao pParametroDao) throws Exception {
		
		cargarParametros(pParametroDao);
		
		hostSmtp = propiedades.getProperty("HOSTSMTP");
		socketPort = propiedades.getProperty("SOCKETPORT");
		port = propiedades.getProperty("PORT");
		smtpAthentication = propiedades.getProperty("SMTPATHENTICATION");
		correoEnvio = propiedades.getProperty("CORREOENVIO");
		usuarioCuenta = propiedades.getProperty("USUARIOCUENTA");
		claveCuenta = propiedades.getProperty("CLAVECUENTA");
		tipoConexion = propiedades.getProperty("TIPOCONEXION");

		if (hostSmtp == null || hostSmtp.equals("")) {
			throw new Exception("Error al enviar correo electronico, No se encontro la configuracion para el"
					+ "host, propiedad <smtpHost> en configserver.xml");
		}

		if (port == null || port.equals("")) {
			throw new Exception("Error al enviar correo electronico, No se encontro la configuracion para el"
					+ "puerto, propiedad <smtpPort> en configserver.xml");
		}

		if (smtpAthentication == null || smtpAthentication.equals("")) {
			throw new Exception("Error al enviar correo electronico, No se encontro la configuracion para identificar"
					+ "si se debe autenticar, propiedad <smtpAthentication> en configserver.xml");
		}

		if (correoEnvio == null || correoEnvio.equals("")) {
			throw new Exception("Error al enviar correo electronico, No se encontro la configuracion para identificar"
					+ "la cuenta de correo de origen, propiedad <from> en configserver.xml");
		}

		if (usuarioCuenta == null || usuarioCuenta.equals("")) {
			throw new Exception("Error al enviar correo electronico, No se encontro la configuracion para identificar"
					+ "el usuario de la cuenta de correo de origen, propiedad <account> en configserver.xml");
		}

		if (claveCuenta == null || claveCuenta.equals("")) {
			throw new Exception("Error al enviar correo electronico, No se encontro la configuracion para identificar"
					+ "la clave de la cuenta de correo de origen, propiedad <accountPassword> en configserver.xml");
		}

		if (tipoConexion == null || tipoConexion.equals("")) {
			throw new Exception("Error al enviar correo electronico, No se encontro la configuracion para identificar"
					+ "el tipo de conexion a usar, propiedad <typeconnection> en configserver.xml");
		}

		if (!tipoConexion.equalsIgnoreCase("TLS") && !tipoConexion.equalsIgnoreCase("SSL")) {
			throw new Exception("Error al enviar correo electronico, No se encontro una configuracion valida"
					+ "para el tipo de conexion a usar, propiedad <typeconnection> en configserver.xml");
		}

	}

	private void procesarEnvioCorreo(String pCorreoDestino, String pMensajeCorreo, String pAsuntoCorreo)
			throws Exception {

		if (tipoConexion.equalsIgnoreCase("TLS")) {
			propiedades.remove("mail.smtp.socketFactory.class");
			propiedades.remove("mail.smtp.socketFactory.port");
			propiedades.put("mail.smtp.starttls.enable", "true");
		} else if (tipoConexion.equalsIgnoreCase("SSL")) {
			propiedades.remove("mail.smtp.starttls.enable");
			propiedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			propiedades.put("mail.smtp.socketFactory.port", socketPort);
		}
		/* SE ADICIONAN PROPIEDADES QUE SON COMUNES PARA LAS DOS FORMAS DE ENVIO */
		propiedades.put("mail.smtp.host", hostSmtp);
		propiedades.put("mail.smtp.auth", smtpAthentication);
		propiedades.put("mail.smtp.port", port);

		Session session = Session.getDefaultInstance(propiedades, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(usuarioCuenta, claveCuenta);
			}
		});

		Message mensaje = new MimeMessage(session);
		mensaje.setFrom(new InternetAddress(correoEnvio));
		mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pCorreoDestino));
		mensaje.setSubject(pAsuntoCorreo);
		mensaje.setContent(pMensajeCorreo, "text/html; charset=utf-8");

		MimeMultipart multipart = new MimeMultipart();

		MimeBodyPart part = new MimeBodyPart();
		part.setContent(pMensajeCorreo, "text/html; charset=utf-8");

		multipart.addBodyPart(part);

		Transport.send(mensaje);
	}

	private void cargarParametros(IParametroDao parametroDao){
		List<String> listado = Arrays.asList(Constantes.HOSTSMTP,Constantes.SOCKETPORT,	Constantes.PORT,
				Constantes.SMTPATHENTICATION,Constantes.CORREOENVIO,Constantes.USUARIOCUENTA,
				Constantes.CLAVECUENTA,Constantes.TIPOCONEXION);
		Map<String, String> parametros = parametroDao.obtenerParametrosCorreo(listado);
		parametros.forEach( (key,value) -> propiedades.put(key, value) );
	}
}
