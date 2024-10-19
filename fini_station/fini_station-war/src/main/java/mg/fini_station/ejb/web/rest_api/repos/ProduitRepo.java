package mg.fini_station.ejb.web.rest_api.repos;

import annexe.Produit;
import bean.CGenUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ProduitRepo {
    public List<Produit> getAllProduit(String idTypeProduit, Connection c) throws Exception {
        Object[] ls= CGenUtil.rechercher(new Produit(),null,null,c,"and IDTYPEPRODUIT = '"+idTypeProduit+"'");
        List<Produit> res=new ArrayList<Produit>();
        for(Object o:ls){
            if(o instanceof Produit){
                res.add((Produit)o);
            }
        }
        return res;
    }

    public List<Produit> getAllProduit(Connection c) throws Exception {
        Object[] ls= CGenUtil.rechercher(new Produit(),null,null,c,"");
        List<Produit> res=new ArrayList<Produit>();
        for(Object o:ls){
            if(o instanceof Produit){
                res.add((Produit)o);
            }
        }
        return res;
    }

    public List<Produit> getAllProduitBoutique(Connection c) throws  Exception{
        List<Produit> res=this.getAllProduit("TP00001",c);
        return res;
    }
}
