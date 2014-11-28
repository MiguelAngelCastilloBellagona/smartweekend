package es.smartweekend.web.backend.jersey.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.smartweekend.web.backend.model.sponsor.Sponsor;
import es.smartweekend.web.backend.model.sponsorService.SponsorService;
import es.smartweekend.web.backend.model.util.exceptions.ServiceException;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
public class SponsorResource {

	@Autowired
	private SponsorService sponsorService;
	
	//ANONYMOUS
	
	@Path("/getAllSponsor")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Sponsor> getAllSponsor() throws ServiceException {
		return sponsorService.getAllSponsor();
	}
	
	//USER
	
	//ADMIN
	
	@Path("/admin/getSpsonsor/{sponsorId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Sponsor getSponsor(@HeaderParam("sessionId") String sessionId, @PathParam("sponsorId") int sponsorId) throws ServiceException {
		return sponsorService.getSponsorADMIN(sessionId, sponsorId);
	}
	
	@Path("/admin/addSpsonsor")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Sponsor addSponsor(@HeaderParam("sessionId") String sessionId, Sponsor sponsor) throws ServiceException {
		return sponsorService.addSponsorADMIN(sessionId, sponsor);
	}
	
	@Path("/admin/modyfySpsonsor/{sponsorId}")
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Sponsor changeSponsor(@HeaderParam("sessionId") String sessionId, @PathParam("sponsorId") int sponsorId, Sponsor sponsorData) throws ServiceException {
		return sponsorService.changeSponsorADMIN(sessionId, sponsorId, sponsorData);
	}
	
	@Path("/admin/removeSpsonsor/{sponsorId}")
	@DELETE
	public void removeSponsor(@HeaderParam("sessionId") String sessionId, int sponsorId) throws ServiceException {
		sponsorService.removeSponsorADMIN(sessionId, sponsorId);
	}
	
}
