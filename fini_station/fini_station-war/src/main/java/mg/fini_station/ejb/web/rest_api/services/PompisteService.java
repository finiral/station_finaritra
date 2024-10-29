package mg.fini_station.ejb.web.rest_api.services;

import java.sql.Connection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import mg.fini_station.pompes.Pompe;
import mg.fini_station.pompes.Pompiste;
import mg.fini_station.utils.DbConn;
import utilitaire.UtilDB;

@Path("pompistes")
public class PompisteService {
    @GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Pompiste> getAllPompistes() throws Exception {
		Connection c= null;
		try{
			c=new DbConn().getConnection();
			c.setAutoCommit(false);
			List<Pompiste> allPompistes = new Pompiste().findAll(c);
			return allPompistes;
		} catch (Exception e) {
			if (c!=null) c.rollback();
			throw e;
		}
		finally {
            if (c!=null) c.close();
		}
    }
}
