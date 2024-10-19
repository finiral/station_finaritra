package mg.fini_station.ejb.web.rest_api.repos;

import annexe.Produit;
import bean.CGenUtil;
import stock.EtatStock;
import utilitaire.UtilDB;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
}
