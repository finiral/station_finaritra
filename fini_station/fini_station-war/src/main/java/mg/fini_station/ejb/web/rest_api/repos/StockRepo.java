package mg.fini_station.ejb.web.rest_api.repos;

import annexe.Produit;
import bean.CGenUtil;
import stock.EtatStock;
import utilitaire.UtilDB;
import vente.Vente;
import vente.VenteDetails;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.ExceptionConverter;

public class StockRepo {
    public List<EtatStock> getAllEtatStocksBoutique(Connection c) throws Exception{
        try {
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            Object[] ls = CGenUtil.rechercher(new EtatStock(), null, null, c, "and IDTYPEPRODUIT = 'TP00001'");
            List<EtatStock> res = new ArrayList<EtatStock>();
            for (Object o : ls) {
                if (o instanceof EtatStock) {
                    res.add((EtatStock) o);
                }
            }
            return res;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<EtatStock> getAllEtatStocksLubrifiants(Connection c) throws Exception{
        try {
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            Object[] ls = CGenUtil.rechercher(new EtatStock(), null, null, c, "and IDTYPEPRODUIT = 'TP00005'");
            List<EtatStock> res = new ArrayList<EtatStock>();
            for (Object o : ls) {
                if (o instanceof EtatStock) {
                    res.add((EtatStock) o);
                }
            }
            return res;
        } catch (Exception e) {
            throw e;
        }
    }
    public List<Vente> getAllVentes(Connection c) throws Exception{
        try {
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            Object[] ls = CGenUtil.rechercher(new Vente(), null, null, c, "and IDMAGASIN = 'POMP001'");
            List<Vente> res = new ArrayList<Vente>();
            for (Object o : ls) {
                if (o instanceof Vente) {
                    Vente apidirina=(Vente) o;
                    res.add(apidirina);
                }
            }
            return res;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<VenteDetails> getAllVenteDetails(Connection c , String idVenteDetails) throws Exception{
        try {
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            Object[] ls = CGenUtil.rechercher(new VenteDetails(), null, null, c, "and IDVENTE = '"+idVenteDetails+"'");
            List<VenteDetails> res = new ArrayList<VenteDetails>();
            for (Object o : ls) {
                if (o instanceof VenteDetails) {
                    VenteDetails apidirina=(VenteDetails) o;
                    res.add(apidirina);
                }
            }
            return res;
        } catch (Exception e) {
            throw e;
        }
    }
}
