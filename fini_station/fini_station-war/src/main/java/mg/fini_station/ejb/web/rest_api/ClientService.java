package mg.fini_station.ejb.web.rest_api;

import annexe.Produit;
import bean.CGenUtil;
import client.Client;
import mg.fini_station.ejb.web.rest_api.repos.ProduitRepo;
import utilitaire.UtilDB;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Path("clients")
public class ClientService {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Client> getAll() throws Exception {
		Connection c = null;
		try {
			c = new UtilDB().GetConn();
			c.setAutoCommit(false);
			Object[] ls = CGenUtil.rechercher(new Client(), null, null, c, "");
			List<Client> res = new ArrayList<Client>();
			for (Object o : ls) {
				if (o instanceof Client) {
					res.add((Client) o);
				}
			}
			return res;
		} catch (Exception e) {
			if (c != null)
				c.rollback();
			throw e;
		} finally {
			if (c != null)
				c.close();
		}
	}
}