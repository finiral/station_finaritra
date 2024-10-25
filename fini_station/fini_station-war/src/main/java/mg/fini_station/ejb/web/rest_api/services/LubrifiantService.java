package mg.fini_station.ejb.web.rest_api.services;

import java.sql.Connection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import mg.fini_station.pompes.Lubrifiant;
import utilitaire.UtilDB;

@Path("lubrifiants")
public class LubrifiantService {
    @GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Lubrifiant> getAllLubrifiants() throws Exception {
		Connection c= null;
		try{
			c=new UtilDB().GetConn();
			c.setAutoCommit(false);
			List<Lubrifiant> allLubrifiants = new Lubrifiant().getAll(c);
			return allLubrifiants;
		} catch (Exception e) {
			if (c!=null) c.rollback();
			throw e;
		}
		finally {
            if (c!=null) c.close();
		}
    }
}
