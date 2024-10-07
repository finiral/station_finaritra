package mg.fini_station.mvt;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;

import java.util.ArrayList;
import java.sql.ResultSet;

import java.sql.Connection;

import mg.fini_station.stock.MesureReservoir;
import mg.fini_station.stock.Reservoir;
import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.Utilitaire;

public class PrelevementReservoir {
    private String table_name = "PrelevementReservoir";
    int idPrelevementReservoir;
    double longueur;
    Date dtPrelevement;
    double volume;
    Reservoir r;

    public int getIdPrelevementReservoir() {
        return idPrelevementReservoir;
    }

    public void setIdPrelevementReservoir(int idPrelevementReservoir) {
        this.idPrelevementReservoir = idPrelevementReservoir;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public void setLongueur(String longueur) throws Exception {
        setLongueur(Utilitaire.convertToCm(longueur));
    }

    public Date getdtPrelevement() {
        return dtPrelevement;
    }

    public void setdtPrelevement(Date dtPrelevement) {
        this.dtPrelevement = dtPrelevement;
    }

    public void setdtPrelevement(String dtPrelevement) {
        this.setdtPrelevement(Date.valueOf(dtPrelevement));
    }

    public double getVolume() throws Exception {

        if (this.volume != 0 && this.volume > 0) {
            return this.volume;
        }
        this.setVolume(getVolume(new DbConn().getConnection()));
        return this.volume;
    }

    public double getVolume(Connection c) throws Exception {
        // Récupérer toutes les mesures du réservoir associé
        MesureReservoir[] mr = this.getR().getMesures();

        // Si aucune mesure n'est trouvée, retourner 0 ou une valeur par défaut
        if (mr == null || mr.length == 0) {
            return 0.0;
        }

        // Créer une copie du tableau `mr` pour ne pas modifier l'original
        MesureReservoir[] mrCopy = new MesureReservoir[mr.length];
        System.arraycopy(mr, 0, mrCopy, 0, mr.length);

        // Trier le tableau `mr` par rapport à la valeur absolue de la différence entre
        // la longueur du prélèvement et la longueur de chaque `MesureReservoir`
        Arrays.sort(mrCopy, new Comparator<MesureReservoir>() {
            @Override
            public int compare(MesureReservoir m1, MesureReservoir m2) {
                double diff1 = Math.abs(longueur - m1.getMesureLongueur());
                double diff2 = Math.abs(longueur - m2.getMesureLongueur());
                return Double.compare(diff1, diff2); // Tri en ordre ascendant
            }
        });
        double volume = mrCopy[0].getMesureVolume();
        if (mrCopy[0].getMesureLongueur() == this.getLongueur()) {
        } else {
            double up = mrCopy[0].getMesureVolume();
            double down = mrCopy[1].getMesureVolume();
            double dy = (Math.abs(up - down));
            double dx = Math.abs(mrCopy[0].getMesureLongueur() - this.getLongueur());
            System.out.println(mrCopy[1].getMesureLongueur() - mrCopy[0].getMesureLongueur());
            volume = (dx * dy) / (mrCopy[1].getMesureLongueur() - mrCopy[0].getMesureLongueur()) + up;
        }
        return volume;

    }

   /*  public static void main(String[] args) throws Exception {
        PrelevementReservoir p = new PrelevementReservoir();
        p.setLongueur("9.9cm");
        p.setR(new Reservoir().getById(1));
        System.out.println(p.getVolume());
        System.out.println(p.getR().getMesures()[0].getMesureLongueur());
    } */

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Reservoir getR() {
        return r;
    }

    public void setR(Reservoir r) {
        this.r = r;
    }

    public PrelevementReservoir(int idPrelevementReservoir, double longueur, String dtPrelevement, double volume,
            Reservoir r) {
        setIdPrelevementReservoir(idPrelevementReservoir);
        setLongueur(longueur);
        setdtPrelevement(dtPrelevement);
        setVolume(volume);

    }

    public PrelevementReservoir(int idPrelevementReservoir, String longueur, String dtPrelevement, double volume,
            Reservoir r) throws Exception {
        setIdPrelevementReservoir(idPrelevementReservoir);
        setLongueur(longueur);
        setdtPrelevement(dtPrelevement);
        setVolume(volume);

    }

    public PrelevementReservoir() {
    }

    public void insert(Connection c) throws Exception {
        PreparedStatement s = null;
        try {
            // Prépare la requête d'insertion
            String query = "INSERT INTO " + this.table_name
                    + " (longueur, date_prelevement, id_reservoir) VALUES (?, ?, ?)";
            s = c.prepareStatement(query);

            // Définit les paramètres de la requête
            s.setDouble(1, this.getLongueur());
            s.setDate(2, this.getdtPrelevement());
            s.setInt(3, this.getR().getIdReservoir()); // ID du réservoir associé

            // Exécute la requête
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null)
                s.close();
        }
    }

    public PrelevementReservoir getById(int id, Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            // Prépare la requête pour récupérer un prélèvement par ID
            String query = "SELECT * FROM " + this.table_name + " WHERE id_prelevementreservoir = ?";
            s = c.prepareStatement(query);
            s.setInt(1, id);

            // Exécute la requête
            rs = s.executeQuery();

            PrelevementReservoir pr = null;
            if (rs.next()) {
                pr = new PrelevementReservoir();
                pr.setIdPrelevementReservoir(rs.getInt("id_prelevementreservoir"));
                pr.setLongueur(rs.getDouble("longueur"));
                pr.setdtPrelevement(rs.getDate("date_prelevement"));
                pr.setR(new Reservoir().getById(rs.getInt("id_reservoir"))); // Récupère le réservoir associé
            }

            return pr;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }
    }

    public List<PrelevementReservoir> getAll(Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            // Prépare la requête pour récupérer tous les prélèvements
            String query = "SELECT * FROM " + this.table_name;
            s = c.prepareStatement(query);

            // Exécute la requête
            rs = s.executeQuery();

            List<PrelevementReservoir> prelevements = new ArrayList<PrelevementReservoir>();
            while (rs.next()) {
                PrelevementReservoir pr = new PrelevementReservoir();
                pr.setIdPrelevementReservoir(rs.getInt("id_prelevementreservoir"));
                pr.setLongueur(rs.getDouble("longueur"));
                pr.setdtPrelevement(rs.getDate("date_prelevement"));
                pr.setR(new Reservoir().getById(rs.getInt("id_reservoir"))); // Récupère le réservoir associé
                prelevements.add(pr);
            }

            return prelevements;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }
    }

}
