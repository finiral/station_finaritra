package mg.fini_station.ejb.web.rest_api;

import annexe.Produit;
import caisse.MvtCaisse;
import caisse.ReportCaisse;
import mg.fini_station.ejb.web.rest_api.repos.ProduitRepo;
import stock.MvtStock;
import stock.MvtStockFille;
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
	String CAISSE = "CAI000239";
	String TYPE_ENCAISSEMENT = "TE001";
	String DEVISE = "AR";
	String TYPEMVTSTOCK="TPMVST000022";
	double TAUX = 1.0;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response ecrireVente(Vente v) throws Exception {
		Connection c = null;
		try {
			c = new UtilDB().GetConn();
			c.setAutoCommit(false);
			
			///MOUVEMENT STOCK
			MvtStock mvtStock = new MvtStock();
			mvtStock.setIdMagasin(v.getIdMagasin());
			mvtStock.setDaty(v.getDaty());
			mvtStock.setDesignation("STOCK SORTIEEE");
			mvtStock.setIdTypeMvStock(TYPEMVTSTOCK);
			mvtStock.setIdVente(v.getId());
			mvtStock.createObject(USER, c);
			///VENTE
			v.setIdClient("CLI000054");
			v.setDesignation("PRODUCT SELLING ");
			/* v.setIdMagasin(MAGASIN); */
			v.setEstPrevu(1);
			v.createObject(USER, c);
			for (VenteDetails vd : v.getVenteDetails()) {
				vd.setPu(vd.getPuVente());
				vd.setIdVente(v.getId());
				vd.setTauxDeChange(TAUX);
				vd.setIdDevise(DEVISE);
				vd.setCompte(COMPTE);
				System.out.println(vd.getIdProduit());
				vd.insertToTable(c);
				vd.CheckEtatStock(c);
				///STOCK FILLE
				MvtStockFille stock = new MvtStockFille();
				stock.setSortie(vd.getQte());
				stock.setIdProduit(vd.getIdProduit());
				stock.setEntree(0);
				stock.setIdVenteDetail(vd.getId());
				stock.setIdMvtStock(mvtStock.getId());
				stock.insertToTable(c);
				///CAISSE
				/* ReportCaisse rep=new ReportCaisse();
				rep.setDaty(v.getDaty());
				rep.setIdCaisse(CAISSE);
				rep.createObject(USER, c);
				rep.validerObject(USER, c); */
				/* MvtCaisse caisse=new MvtCaisse();
				caisse.setDesignation("CAISSE MOVEMENT VENTE");
				caisse.setDaty(v.getDaty());
				caisse.setIdDevise(DEVISE);
				caisse.setIdCaisse(CAISSE);
				caisse.setIdVenteDetail(vd.getId());
				caisse.setCredit(vd.getPuVente()*vd.getQte());
				caisse.setTaux(TAUX);
				caisse.createObject(USER, c); */
				/* caisse.validerObject(USER, c); */
			}
			v.genererEcritureEncaissement(USER, c);
			v.validerObject(USER, c);
			mvtStock.validerObject(USER, c);

			c.commit();
			return Response.ok().build();

		} catch (Exception e) {
			e.printStackTrace();
			if (c != null)
				c.rollback();
			return Response.serverError().entity(e.getMessage()).build();
		} finally {
			if (c != null)
				c.close();
		}
	}

}