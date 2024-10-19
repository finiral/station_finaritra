package mg.fini_station.ejb.web.rest_api;

import annexe.Produit;
import mg.fini_station.ejb.web.rest_api.repos.ProduitRepo;
import user.UserEJBClient;
import utilitaire.UtilDB;
import vente.Vente;
import vente.VenteDetails;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.Connection;
import java.util.List;

@Path("ventes")
public class VenteService {
	String USER = "1060"; // id de l'utilisateur
	String COMPTE = "712000";
	String CATEGORIE = "CTG000103";
	String MAGASIN = "POMP001";
	String CAISSE = "CAI000258";
	String TYPE_ENCAISSEMENT = "TE001";
	String DEVISE = "AR";
	double TAUX = 1.0;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response ecrireVente(Vente v) throws Exception {
		Connection c = null;
		try {
			c = new UtilDB().GetConn();
			c.setAutoCommit(false);
			v.setIdClient("CLI000054");
			v.setDesignation("PRODUCT SELLING ");
			v.setIdMagasin(MAGASIN);
			v.setEstPrevu(1);
			v.createObject(USER, c);
			for (VenteDetails vd : v.getVenteDetails()) {
				vd.setPu(vd.getPuVente());
				vd.setIdVente(v.getId());
				vd.setTauxDeChange(TAUX);
				vd.setIdDevise(DEVISE);
				vd.setCompte(COMPTE);
				System.out.println(vd.getIdProduit());
				vd.createObject(USER, c);
			}
			v.validerObject(USER, c);
			c.commit();
			return Response.ok().build();

		} catch (Exception e) {
			if (c != null)
				c.rollback();
			return Response.serverError().entity(e.getMessage()).build();
		} finally {
			if (c != null)
				c.close();
		}
	}

}