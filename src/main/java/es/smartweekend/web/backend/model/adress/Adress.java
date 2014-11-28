package es.smartweekend.web.backend.model.adress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 2.0
 */
@Entity
@Table(name="Adress")
public class Adress {
	
	@Column(name = "Adress_id")
	@SequenceGenerator(name = "adressIdGenerator", sequenceName = "adressSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "adressIdGenerator")
	protected int adresslId;
	
	@Column(name = "Adress_user")
	protected String usuarioCorreo;
	
	@Column(name = "Adress_password")
	protected String password;
	
	
	public int getAdresslId() {
		return adresslId;
	}
	
	public void setAdresslId(int adresslId) {
		this.adresslId = adresslId;
	}
	
	
	public String getUsuarioCorreo() {
		return usuarioCorreo;
	}
	
	public void setUsuarioCorreo(String usuarioCorreo) {
		this.usuarioCorreo = usuarioCorreo;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
