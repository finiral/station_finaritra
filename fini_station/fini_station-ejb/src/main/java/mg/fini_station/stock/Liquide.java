package mg.fini_station.stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.utils.DbConn;

public class Liquide {
    private String table_name = "Liquide"; // Table name attribute
    private int idLiquide;
    private String nomLiquide;
    private double prixUnitaireAchat;
    private double prixUnitaireVente;

    // Constructors
    public Liquide(int idLiquide, String nomLiquide, double prixUnitaireAchat, double prixUnitaireVente) {
        setIdLiquide(idLiquide);
        setNomLiquide(nomLiquide);
        setPrixUnitaireAchat(prixUnitaireAchat);
        setPrixUnitaireVente(prixUnitaireVente);
    }

    public Liquide() {
    }

    // Getters and Setters
    public int getIdLiquide() {
        return idLiquide;
    }

    public void setIdLiquide(int idLiquide) {
        this.idLiquide = idLiquide;
    }

    public String getNomLiquide() {
        return nomLiquide;
    }

    public void setNomLiquide(String nomLiquide) {
        this.nomLiquide = nomLiquide;
    }

    public double getPrixUnitaireAchat() {
        return prixUnitaireAchat;
    }

    public void setPrixUnitaireAchat(double prixUnitaireAchat) {
        this.prixUnitaireAchat = prixUnitaireAchat;
    }

    public double getPrixUnitaireVente() {
        return prixUnitaireVente;
    }

    public void setPrixUnitaireVente(double prixUnitaireVente) {
        this.prixUnitaireVente = prixUnitaireVente;
    }

    // DAO Methods

    // Insert a Liquide record
    public void insert() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("INSERT INTO " + this.table_name + " (nom_liquide, prixUnitaireAchat, prixUnitaireVente) VALUES (?,?,?)");
            s.setString(1, this.getNomLiquide());
            s.setDouble(2, this.getPrixUnitaireAchat());
            s.setDouble(3, this.getPrixUnitaireVente());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Retrieve all Liquide records
    public List<Liquide> getAll() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            List<Liquide> res = new ArrayList<>();
            while (rs.next()) {
                Liquide l = new Liquide();
                l.setIdLiquide(rs.getInt("id_liquide"));
                l.setNomLiquide(rs.getString("nom_liquide"));
                l.setPrixUnitaireAchat(rs.getDouble("prixUnitaireAchat"));
                l.setPrixUnitaireVente(rs.getDouble("prixUnitaireVente"));
                res.add(l);
            }
            return res;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Retrieve a Liquide by ID
    public Liquide getById(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name + " WHERE id_liquide = ?");
            s.setInt(1, id);
            rs = s.executeQuery();
            Liquide l = null;
            if (rs.next()) {
                l = new Liquide();
                l.setIdLiquide(rs.getInt("id_liquide"));
                l.setNomLiquide(rs.getString("nom_liquide"));
                l.setPrixUnitaireAchat(rs.getDouble("prixUnitaireAchat"));
                l.setPrixUnitaireVente(rs.getDouble("prixUnitaireVente"));
            }
            return l;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Update a Liquide record
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("UPDATE " + this.table_name + " SET nom_liquide = ?, prixUnitaireAchat = ?, prixUnitaireVente = ? WHERE id_liquide = ?");
            s.setString(1, this.getNomLiquide());
            s.setDouble(2, this.getPrixUnitaireAchat());
            s.setDouble(3, this.getPrixUnitaireVente());
            s.setInt(4, this.getIdLiquide());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Delete a Liquide record
    public void delete(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("DELETE FROM " + this.table_name + " WHERE id_liquide = ?");
            s.setInt(1, id);
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }
}
