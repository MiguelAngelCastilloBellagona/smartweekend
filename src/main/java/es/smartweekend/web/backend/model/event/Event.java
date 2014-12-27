package es.smartweekend.web.backend.model.event;

import java.util.Calendar;

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

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.smartweekend.web.backend.jersey.util.JsonDateDeserializer;
import es.smartweekend.web.backend.jersey.util.JsonDateSerializer;
import es.smartweekend.web.backend.jersey.util.JsonEntityIdSerializer;
import es.smartweekend.web.backend.model.emailTemplate.EmailTemplate;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Entity
public class Event {

	@Column(name = "Event_id")
    @SequenceGenerator(name = "eventIdGenerator", sequenceName = "eventSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "eventIdGenerator")
    private int eventId;
	
    @Column(name = "Event_name")
    private String name;

    @Column(name = "Event_description")
    private String description;

    @Column(name = "Event_num_participants")
    private int numParticipants;

    @Column(name = "Event_minimunAge")
    private int minimunAge;
	
	@Column(name = "Event_price") 
    private int price; 

	@JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
    @Column(name = "Event_date_start")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar startDate;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
    @Column(name = "Event_date_end")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar endDate;
    
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
    @Column(name = "Event_reg_date_open")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar registrationOpenDate;
    
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using=JsonDateSerializer.class)
    @Column(name = "Event_reg_date_close")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar registrationCloseDate;
    
    @JsonSerialize(using=JsonEntityIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Event_setPaidTemplate_id")
    private EmailTemplate setPaidTemplate;

    @JsonSerialize(using=JsonEntityIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Event_onQueueTemplate_id")
    private EmailTemplate onQueueTemplate;

    @JsonSerialize(using=JsonEntityIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Event_outstandingTemplate_id")
    private EmailTemplate outstandingTemplate;

    @JsonSerialize(using=JsonEntityIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Event_outOfDateTemplate_id")
    private EmailTemplate outOfDateTemplate;

    @JsonSerialize(using=JsonEntityIdSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Event_fromQueueToOutstanding_id")
    private EmailTemplate fromQueueToOutstanding;

	@Column(name = "Event_rules")
    private String normas;
    

    public Event() {
    }

    public Event(int eventId, String name, String description, int numParticipants,
            Calendar startDate, Calendar endDate, Calendar registrationOpenDate,
            Calendar registrationCloseDate, EmailTemplate setPaidTemplate, 
            EmailTemplate onQueueTemplate, EmailTemplate outstandingTemplate, 
            EmailTemplate outOfDateTemplate, EmailTemplate fromQueueToOutstanding) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.numParticipants = numParticipants;
        this.minimunAge = 0;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationOpenDate = registrationOpenDate;
        this.registrationCloseDate = registrationCloseDate;
        this.setPaidTemplate = setPaidTemplate;
        this.onQueueTemplate = onQueueTemplate;
        this.outstandingTemplate = outstandingTemplate; 
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() { 
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumParticipants() {
        return numParticipants;
    }

    public void setNumParticipants(int numParticipants) {
        this.numParticipants = numParticipants;
    }

    public int getMinimunAge() {
		return minimunAge;
	}

	public void setMinimunAge(int minimunAge) {
		this.minimunAge = minimunAge;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getRegistrationOpenDate() {
        return registrationOpenDate;
    }

    public void setRegistrationOpenDate(Calendar registrationOpenDate) {
        this.registrationOpenDate = registrationOpenDate;
    }

    public Calendar getRegistrationCloseDate() {
        return registrationCloseDate;
    }

    public void setRegistrationCloseDate(Calendar registrationCloseDate) {
        this.registrationCloseDate = registrationCloseDate;
    }

	public EmailTemplate getSetPaidTemplate() {
		return setPaidTemplate;
	}

	public void setSetPaidTemplate(EmailTemplate setPaidTemplate) {
		this.setPaidTemplate = setPaidTemplate;
	}

	public EmailTemplate getOnQueueTemplate() {
		return onQueueTemplate;
	}

	public void setOnQueueTemplate(EmailTemplate onQueueTemplate) {
		this.onQueueTemplate = onQueueTemplate;
	}

	public EmailTemplate getOutstandingTemplate() {
		return outstandingTemplate;
	}

	public void setOutstandingTemplate(EmailTemplate outstandingTemplate) {
		this.outstandingTemplate = outstandingTemplate;
	}

	public EmailTemplate getOutOfDateTemplate() {
		return outOfDateTemplate;
	}

	public void setOutOfDateTemplate(EmailTemplate outOfDateTemplate) {
		this.outOfDateTemplate = outOfDateTemplate;
	}

	public EmailTemplate getFromQueueToOutstanding() {
		return fromQueueToOutstanding;
	}

	public void setFromQueueToOutstanding(EmailTemplate fromQueueToOutstanding) {
		this.fromQueueToOutstanding = fromQueueToOutstanding;
	}

	public String getNormas() {
		return normas;
	}

	public void setNormas(String normas) {
		this.normas = normas;
	}
	
	public class EventBasicData {
		
		public String name;
		public long id;
		
		public EventBasicData(String name, long id) {
			this.name = name;
			this.id = id;
		}
	}
	
	public EventBasicData getEventBasicData() {
		return new EventBasicData(this.name,this.eventId);
	}

}
