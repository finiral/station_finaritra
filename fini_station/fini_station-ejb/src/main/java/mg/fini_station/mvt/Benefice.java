package mg.fini_station.mvt;

import java.sql.Date;

import mg.cnaps.compta.ComptaSousEcriture;

public class Benefice {
    Date dt_debut;
    Date dt_fin;
    ComptaSousEcriture[] sousEcritures;
    double montant;
    public Date getDt_debut() {
        return dt_debut;
    }
    public void setDt_debut(Date dt_debut) {
        this.dt_debut = dt_debut;
    }
    public void setDt_debut(String dt_debut) {
        this.setDt_debut(Date.valueOf(dt_debut));
    }
    public Date getDt_fin() {
        return dt_fin;
    }
    public void setDt_fin(Date dt_fin) {
        this.dt_fin = dt_fin;
    }
    public void setDt_fin(String dt_fin) {
        this.setDt_fin(Date.valueOf(dt_fin));
    }
    public ComptaSousEcriture[] getSousEcritures() {
        return sousEcritures;
    }
    public void setSousEcritures(ComptaSousEcriture[] sousEcritures) {
        this.sousEcritures = sousEcritures;
    }
    public double getMontant() {
        return montant;
    }
    public void setMontant(double montant) {
        this.montant = montant;
    }
    public Benefice() {
    }
    public Benefice(String dt_debut, String dt_fin, ComptaSousEcriture[] sousEcritures, double montant) {
        setDt_debut(dt_debut);
        setDt_fin(dt_fin);
        setSousEcritures(sousEcritures);
        setMontant(montant);
    }

    public Benefice(String dt_debut , String dt_fin){
        setDt_debut(dt_debut);
        setDt_fin(dt_fin);
    }
    
}
