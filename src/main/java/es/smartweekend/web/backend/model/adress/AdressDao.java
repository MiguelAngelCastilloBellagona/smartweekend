package es.smartweekend.web.backend.model.adress;

import es.smartweekend.web.backend.model.util.dao.GenericDao;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
public interface AdressDao extends GenericDao<Adress, Integer> {
	
	public Adress findAdressByName(String adressName);

}
