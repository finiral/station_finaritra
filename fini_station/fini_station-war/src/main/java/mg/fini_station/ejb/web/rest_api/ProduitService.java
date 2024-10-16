package mg.fini_station.ejb.web.rest_api;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
@Path("hello")
public class ProduitService {
	
	@Path("mimi")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String hello(String name) {
		if ((name == null) || name.trim().isEmpty())  {
			name = "world";
		}
		return "HELLO WORLD !";
	}

}