package es.smartweekend.web.backend.model.email;

import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.smartweekend.web.backend.jersey.util.JsonDateDeserializer;
import es.smartweekend.web.backend.jersey.util.JsonDateSerializer;
import es.smartweekend.web.backend.jersey.util.JsonEntityIdSerializer;
import es.smartweekend.web.backend.model.adress.Adress;
import es.smartweekend.web.backend.model.user.User;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 2.1
 */
@Entity
@Table(name = "Email")
public class Email {

	@Column(name = "Email_id")
	@SequenceGenerator(name = "emailIdGenerator", sequenceName = "emailSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "emailIdGenerator")
	protected int emailId;
	
	@JsonSerialize(using = JsonEntityIdSerializer.class) 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Email_Adress_id")
	protected Adress direccionEnvio;
	
	@Column(name = "Email_confirmation")
	protected boolean confirmation;
	
	@Column(name = "Email_file")
	protected String rutaArchivo;
	
	@Column(name = "Email_fileName")
	protected String nombreArchivo;
	
	@JsonSerialize(using = JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Email_user_id")
	protected User destinatario;
	
	@Column(name = "case")
	protected String asunto;
	
	@Column(name = "Email_body")
	protected String mensaje;
	
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "Email_date")
	protected Calendar sendDate;
	
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "Email_senddate")
	protected Calendar date;

	public Email() {
		
	}

	public Email( Adress direccionEnvio, String rutaArchivo,String nombreArchivo, User destinatario, String asunto,String mensaje) {
		this.direccionEnvio = direccionEnvio;
		this.rutaArchivo = rutaArchivo;
		this.nombreArchivo = nombreArchivo;
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		this.sendDate = null;
		this.confirmation = false;
	}

	public Email(Adress direccionEnvio, User destinatario, String mensaje) {
		this( direccionEnvio, "", "", destinatario, "", mensaje);
	}

	public Email(Adress direccionEnvio, User destinatario, String asunto,
			String mensaje) {
		this( direccionEnvio, "", "", destinatario, asunto, mensaje);
	}

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	public boolean getConfirmation() {
		return this.confirmation;
	}

	void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	public Adress getDireccionEnvio() {
		return this.direccionEnvio;
	}

	public void setDireccionEnvio(Adress direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	public User getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(User destinatario) {
		this.destinatario = destinatario;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getSendDate() {
		return sendDate;
	}

	void setSendDate(Calendar sendDate) {
		this.sendDate = sendDate;
	}

	public boolean sendMailThread() {

		SendMailThread thread = new SendMailThread(this);
		thread.start();
		return true;
	}
	
	public boolean sendMail() {

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user",this.getDireccionEnvio().getUsuarioCorreo());
			props.setProperty("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props, null);
			BodyPart texto = new MimeBodyPart();
			texto.setText(this.getMensaje());

			BodyPart adjunto = new MimeBodyPart();
			if (!this.getRutaArchivo().equals("")) {
				adjunto.setDataHandler(new DataHandler(new FileDataSource(this.getRutaArchivo())));
				adjunto.setFileName(this.getNombreArchivo());
			}

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(texto);
			if (!this.getRutaArchivo().equals("")) {
				multiParte.addBodyPart(adjunto);
			}

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.getDireccionEnvio().getUsuarioCorreo()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.getDestinatario().getEmail()));
			message.setSubject(this.getAsunto());
			message.setContent(multiParte);

			Transport t = session.getTransport("smtp");
			t.connect(this.getDireccionEnvio().getUsuarioCorreo(),this.getDireccionEnvio().getPassword());
			t.sendMessage(message, message.getAllRecipients());
			t.close();

			this.setSendDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
			this.setConfirmation(true);

			return true;
		} 
		catch (MessagingException e) 
		{
			e.printStackTrace();

			this.setSendDate(null);
			this.setConfirmation(false);

			return false;
		}

	}
}
