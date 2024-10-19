package mg.fini_station.ejb.web.rest_api;

import annexe.Produit;
import mg.fini_station.ejb.web.rest_api.repos.ProduitRepo;
import utilitaire.UtilDB;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.util.List;
@Path("products")
public class ProduitService {
	ProduitRepo prodRepo=new ProduitRepo();
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Produit> getAllProduit(@QueryParam("idTypeProduit")String idTypeProduit) throws Exception {
		Connection c= null;
		try{
			c=new UtilDB().GetConn();
			c.setAutoCommit(false);
			if (idTypeProduit == null) return prodRepo.getAllProduit(c);
			return prodRepo.getAllProduit(idTypeProduit, c);
		} catch (Exception e) {
			if (c!=null) c.rollback();
			throw e;
		}
		finally {
            if (c!=null) c.close();
		}
    }


	@Path("boutique")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Produit> getAllProduitBoutique() throws Exception {
		Connection c= null;
		try{
			c=new UtilDB().GetConn();
			c.setAutoCommit(false);
			List<Produit> allProduitBoutique = prodRepo.getAllProduitBoutique(c);
			return allProduitBoutique;
		} catch (Exception e) {
			if (c!=null) c.rollback();
			throw e;
		}
		finally {
			if (c!=null) c.close();
		}
	}

}