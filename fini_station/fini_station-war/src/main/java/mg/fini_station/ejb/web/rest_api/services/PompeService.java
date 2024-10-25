package mg.fini_station.ejb.web.rest_api.services;

import java.sql.Connection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import mg.fini_station.pompes.Pompe;
import utilitaire.UtilDB;

@Path("pompes")
public class PompeService {
    @GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Pompe> getAllPompes() throws Exception {
		Connection c= null;
		try{
			c=new UtilDB().GetConn();
			c.setAutoCommit(false);
			List<Pompe> allPompes = new Pompe().getAll(c);
			return allPompes;
		} catch (Exception e) {
			if (c!=null) c.rollback();
			throw e;
		}
		finally {
            if (c!=null) c.close();
		}
    }
}
