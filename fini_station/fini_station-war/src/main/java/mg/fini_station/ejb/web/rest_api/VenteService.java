package mg.fini_station.ejb.web.rest_api;

import annexe.Produit;
import avoir.AvoirFC;
import avoir.AvoirFCFille;
import bean.CGenUtil;
import caisse.MvtCaisse;
import caisse.ReportCaisse;
import client.Client;
import mg.fini_station.ejb.web.rest_api.repos.ProduitRepo;
import mg.fini_station.ejb.web.rest_api.repos.StockRepo;
import stock.MvtStock;
import stock.MvtStockFille;
import user.UserEJBClient;
import utilitaire.UtilDB;
import vente.Vente;
import java.util.ArrayList;
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
	StockRepo repo=new StockRepo();

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response ecrireVente(Vente v,@QueryParam("isDirect") int isDirect) throws Exception {
		Connection c = null;
		try {
			System.out.println(isDirect);
			if(isDirect==1){
				///direct donc tode client divers
				v.setIdClient("CLI000054");
			}
			System.out.println(v.getIdClient());
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
			/* v.setIdClient("CLI000054"); */
			v.setDesignation("PRODUCT SELLING ");
			/* v.setIdMagasin(MAGASIN); */
			v.setEstPrevu(1);
			v.createObject(USER,c);
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
			v.validerObject(USER, c);
			///si paiement direct
			if(isDirect==1){
				v.payer(USER, c);
			}
			///si créditation
			else if(isDirect==-1){
				v.genererAvoir(USER, c,v.getId());
				System.out.println("de ato zani créditer e");

			}
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

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Vente> getAll() throws Exception {
		Connection c = null;
		try {
			c = new UtilDB().GetConn();
			c.setAutoCommit(false);
			List<Vente> res =repo.getAllVentes(c) ;
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

	@GET
	@Path("details")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<VenteDetails> getAllDetails(@QueryParam("idVente") String idVente) throws Exception {
		Connection c = null;
		try {
			c = new UtilDB().GetConn();
			c.setAutoCommit(false);
			List<VenteDetails> res =repo.getAllVenteDetails(c,idVente) ;
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

	@POST
	@Path("details")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response annuler(List<VenteDetails> ls_v) throws Exception {
		Connection c = null;
		try {
			c=new UtilDB().GetConn();
			c.setAutoCommit(false);
			///TRAITEMENT DETAIL VENTE
			String id=ls_v.get(0).getId();
			System.out.println("ID VENTE DETAIL :"+id);
			///Prendre ventedetail
			Object[] ose=CGenUtil.rechercher(new VenteDetails(), null,null, "and ID = '"+id+"'");
			VenteDetails ve=(VenteDetails)ose[0];
			///Prendre la vente
			System.out.println("TONGA ATOOO");
			Object[] os=CGenUtil.rechercher(new Vente(), null,null, "and ID = '"+ve.getIdVente()+"'");
			Vente v=(Vente)os[0];
			List<VenteDetails> les_ventes=repo.getAllVenteDetails(c, v.getId());
			for (int i=0 ; i<les_ventes.size() ; i++) {
				System.out.println(les_ventes.get(i).getId()+" : "+les_ventes.get(i).getIdProduit());
			}

			// ///MOUVEMENT STOCK
			MvtStock mvtStock = new MvtStock();
			mvtStock.setIdMagasin(v.getIdMagasin());
			mvtStock.setDaty(v.getDaty());
			mvtStock.setDesignation("STOCK MIVERINAAAAAAA");
			mvtStock.setIdTypeMvStock("TPMVST000001");
			mvtStock.setIdVente(v.getId());
			mvtStock.insertToTable();
			mvtStock.setEtat(11);
			AvoirFC avoirfc=Vente.transformerFactureToAvoir(v);
			avoirfc.setEtat(11);
			avoirfc.insertToTable();
			for (int i=0 ; i<ls_v.size() ; i++) {
				ls_v.get(i).setIdVente(v.getId());
				AvoirFCFille avoirFCFille=Vente.transformerFactureToAvoirFille(ls_v.get(i));
				avoirFCFille.setIdAvoirFC(avoirfc.getId());
				avoirFCFille.insertToTable();
				///MVT STOCK MVERINA
				///inserer si ls_v.qte et les_ventes.qte different
				if(ls_v.get(i).getQte()<les_ventes.get(i).getQte()){
					MvtStockFille stock = new MvtStockFille();
					stock.setIdMvtStock(mvtStock.getId());
					stock.setEntree(les_ventes.get(i).getQte()-ls_v.get(i).getQte());
					System.out.println("ETOO : "+(les_ventes.get(i).getQte()-ls_v.get(i).getQte()));
					stock.setIdProduit(ls_v.get(i).getIdProduit());
					stock.setSortie(0);
					stock.setIdVenteDetail(ls_v.get(i).getId());
					stock.insertToTable();
				}
			}
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