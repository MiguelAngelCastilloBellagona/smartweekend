package es.smartweekend.web.backend.model.emailTemplate;

import java.util.Enumeration;
import java.util.Hashtable;

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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.smartweekend.web.backend.jersey.util.JsonEntityIdSerializer;
import es.smartweekend.web.backend.model.adress.Adress;
import es.smartweekend.web.backend.model.email.Email;
import es.smartweekend.web.backend.model.user.User;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
@Table(name="EmailTemplate")
public class EmailTemplate {

	@Column(name = "EmailTemplate_id ")
	@SequenceGenerator(name = "EmailTemplateIdGenerator", sequenceName = "EmailTemplateSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "EmailTemplateIdGenerator")
	private int emailtemplateid;

	@Column(name = "EmailTemplate_name")
	private String name;

	@JsonSerialize(using = JsonEntityIdSerializer.class) 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EmailTemplate_Adress_id")
	private Adress adress;

	@Column(name = "EmailTemplate_file")
	private String filepath;

	@Column(name = "EmailTemplate_fileName")
	private String filename; 

	@Column(name = "EmailTemplate_case")
	private String asunto;

	@Column(name = "EmailTemplate_body")
	private String contenido;
	
	public EmailTemplate() {
		
	}

	public int getEmailtemplateid() {
		return emailtemplateid;
	}

	public void setEmailtemplateid(int emailtemplateid) {
		this.emailtemplateid = emailtemplateid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Email generateEmail(User destinatario, Hashtable<String,String> datos) {
		
		String contenido = this.getContenido();
		
		Enumeration<String> e = datos.keys();
		String clave;
		String valor;
		while( e.hasMoreElements() ){
		  clave = (String) e.nextElement();
		  valor = datos.get(clave);
		  contenido = contenido.replace(clave, valor);
		}
		
		return new Email(this.getAdress(),this.getFilepath(),this.getFilename(),destinatario,this.getAsunto(),contenido);

	}

}
