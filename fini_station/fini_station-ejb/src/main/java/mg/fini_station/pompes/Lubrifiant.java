package mg.fini_station.pompes;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.utils.DbConn;

public class Lubrifiant {
    private String table_name="Lubrifiant";
    private int id;
    private double prixAchat;
    private double prixVente;
    private String nom;
    private String idCentrale;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getPrixAchat() {
        return prixAchat;
    }
    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }
    public double getPrixVente() {
        return prixVente;
    }
    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getIdCentrale() {
        return idCentrale;
    }
    public void setIdCentrale(String idCentrale) {
        this.idCentrale = idCentrale;
    }


    public List<Lubrifiant> getAll() throws Exception {
        Connection c = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            return this.getAll(c);
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public List<Lubrifiant> getAll(Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            List<Lubrifiant> res = new ArrayList<>();
            while (rs.next()) {
                Lubrifiant p = new Lubrifiant();
                p.setId(rs.getInt("id_lubrifiant"));
                p.setIdCentrale(rs.getString("id_centrale"));
                p.setNom(rs.getString("nom"));
                p.setPrixAchat(rs.getDouble("prixAchat"));
                p.setPrixVente(rs.getDouble("prixVente"));
                res.add(p);
            }
            return res;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
        }
    }


}
