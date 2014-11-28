package es.smartweekend.web.backend.model.sponsor;

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
import es.smartweekend.web.backend.model.event.Event;


/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
@Table(name="Sponsor")
public class Sponsor {
	
	@Column(name = "Sponsor_id")
	@SequenceGenerator(name = "SponsorIdGenerator", sequenceName = "SponsorSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SponsorIdGenerator")
	private int sponsorId;

	@Column(name = "Sponsor_name")
	private String name;
	
	@Column(name = "Sponsor_url")
	private String url;

	@Column(name = "Sponsor_imageurl")
	private String imageurl;
	
	@JsonSerialize(using = JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Sponsor_event_id")
	private Event event;
	
	public Sponsor() {
		
	}

	public int getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(int sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	

}
