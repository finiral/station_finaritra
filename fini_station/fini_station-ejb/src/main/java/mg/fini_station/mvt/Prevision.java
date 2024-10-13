package mg.fini_station.mvt;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ejb_fini.ComptaBeanClient;
import mg.cnaps.compta.ComptaSousEcriture;

public class Prevision {
    String id;
    String designation;
    String montant;
    String idTier;
    String dt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getIdTier() {
        return idTier;
    }

    public void setIdTier(String idTier) {
        this.idTier = idTier;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Prevision(String id, String designation, String montant, String idTier, String dt) {
        this.id = id;
        this.designation = designation;
        this.montant = montant;
        this.idTier = idTier;
        this.dt = dt;
    }
    

    public Prevision() {
    }

    public List<Prevision> getAll() throws Exception {
        ComptaBeanClient compta = new ComptaBeanClient();
        List<Encaissement> ls=new Encaissement().getAll();
        List<Prevision> res=new ArrayList<Prevision>();
        for (Encaissement encaissement : ls) {
            String[] datas=compta.lookupComptaBeanLocal().getPrevData(encaissement.getIdVente());
            res.add(new Prevision(datas[0],datas[1],datas[2],datas[3],datas[4]));
        }
        return res;
    }
}
