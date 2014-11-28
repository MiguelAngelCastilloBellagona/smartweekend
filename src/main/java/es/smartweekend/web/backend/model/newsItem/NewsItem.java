package es.smartweekend.web.backend.model.newsItem;

import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.smartweekend.web.backend.jersey.util.JsonDateDeserializer;
import es.smartweekend.web.backend.jersey.util.JsonDateSerializer;
import es.smartweekend.web.backend.jersey.util.JsonEntityIdSerializer;
import es.smartweekend.web.backend.model.event.Event;
import es.smartweekend.web.backend.model.user.User;


/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
public class NewsItem {

	@Column(name = "NewsItem_id")
	@SequenceGenerator(name = "NewsItemIdGenerator", sequenceName = "NewsItemSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NewsItemIdGenerator")
	private int newsItemId;

	@Column(name = "NewsItem_title")
	private String title;
	
	@Column(name = "NewsItem_image")
	private String imageurl;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name = "NewsItem_date_created")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Calendar creationDate;

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name = "NewsItem_date_publish")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Calendar publishDate;

	@Column(name = "NewsItem_content")
	private String content;

	@JsonSerialize(using=JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NewsItem_user_id")
	private User publisher;
	
	@JsonSerialize(using = JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NewsItem_event_id")
	private Event event;
	
	@Transient
	private String login;
	
	public NewsItem () {};
	
	public NewsItem (String title, Calendar publishDate, String content, int priorityHours) {
		super();
		this.title = title;
		this.creationDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		this.publishDate = publishDate;
		this.content = content;
	}

	public int getNewsItemId() {
		return newsItemId;
	}

	public void setNewsItemId(int newsItemId) {
		this.newsItemId = newsItemId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Calendar publishDate) {
		this.publishDate = publishDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
		this.login = this.publisher.getLogin();
	}

	@Transient
	public String getLogin() {
		return this.login;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
}
