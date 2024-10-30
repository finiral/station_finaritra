package mg.fini_station.ejb.web.rest_api;

import annexe.Produit;
import mg.fini_station.ejb.web.rest_api.repos.ProduitRepo;
import mg.fini_station.ejb.web.rest_api.repos.StockRepo;
import mg.fini_station.utils.Utilitaire;
import stock.EtatStock;
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
import java.sql.Date;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("stocks")
public class StockService {
	String USER = "1060"; // id de l'utilisateur
	String COMPTE = "712000";
	String CATEGORIE = "CTG000103";
	String MAGASIN = "POMP001";
	String CAISSE = "CAI000258";
	String TYPE_ENCAISSEMENT = "TE001";
	String DEVISE = "AR";
	double TAUX = 1.0;
	String TYPEMVTSTOCK="TPMVST000001";

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response ajouterStock(List<MvtStockFille> stocks,
	@QueryParam("idMagasin") String idMagasin, @QueryParam("timestamp") long timestamp) throws Exception {
		Connection c = null;
        Date date = new Date(timestamp);
		try {
			c = new UtilDB().GetConn();
			c.setAutoCommit(false);
			MvtStock mvtStock = new MvtStock();
			mvtStock.setIdMagasin(idMagasin);
			mvtStock.setDaty(date);
			mvtStock.setDesignation("STOCK MOVEMENT");
			mvtStock.setIdTypeMvStock(TYPEMVTSTOCK);
			mvtStock.createObject(USER, c);
			for (MvtStockFille stock : stocks) {
				stock.setIdMvtStock(mvtStock.getId());
				stock.createObject(USER, c);
				System.out.println(stock.getIdProduit());
			}
			mvtStock.validerObject(USER, c);
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

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<EtatStock> getAllEtatStocks() throws Exception {
		Connection c= null;
		try{
			c=new UtilDB().GetConn();
			c.setAutoCommit(false);
			List<EtatStock> allEtatStocks = new StockRepo().getAllEtatStocksBoutique(c);
			return allEtatStocks;
		} catch (Exception e) {
			if (c!=null) c.rollback();
			throw e;
		}
		finally {
            if (c!=null) c.close();
		}
    }

	@Path("lubrifiants")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<EtatStock> getAllEtatStocksLubr() throws Exception {
		Connection c= null;
		try{
			c=new UtilDB().GetConn();
			c.setAutoCommit(false);
			List<EtatStock> allEtatStocks = new StockRepo().getAllEtatStocksLubrifiants(c);
			return allEtatStocks;
		} catch (Exception e) {
			if (c!=null) c.rollback();
			throw e;
		}
		finally {
            if (c!=null) c.close();
		}
    }

}