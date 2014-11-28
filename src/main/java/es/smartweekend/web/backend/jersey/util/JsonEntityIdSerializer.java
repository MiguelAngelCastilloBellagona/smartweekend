package es.smartweekend.web.backend.jersey.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import es.smartweekend.web.backend.model.adress.Adress;
import es.smartweekend.web.backend.model.email.Email;
import es.smartweekend.web.backend.model.emailTemplate.EmailTemplate;
import es.smartweekend.web.backend.model.newsItem.NewsItem;
import es.smartweekend.web.backend.model.sponsor.Sponsor;
import es.smartweekend.web.backend.model.user.User;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class JsonEntityIdSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object entity, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
		if(entity==null) gen.writeNull();
		if(entity instanceof Adress) gen.writeString(String.valueOf(((Adress) entity).getAdresslId()));	
		else if(entity instanceof Email) gen.writeString(String.valueOf(((Email) entity).getEmailId()));
		else if(entity instanceof EmailTemplate) gen.writeString(String.valueOf(((EmailTemplate) entity).getEmailtemplateid()));
		else if(entity instanceof NewsItem) gen.writeString(String.valueOf(((NewsItem) entity).getNewsItemId()));
		else if(entity instanceof Sponsor) gen.writeString(String.valueOf(((Sponsor) entity).getSponsorId()));
		else if(entity instanceof User) gen.writeString(String.valueOf(((User) entity).getUserId()));
	
	}

}
