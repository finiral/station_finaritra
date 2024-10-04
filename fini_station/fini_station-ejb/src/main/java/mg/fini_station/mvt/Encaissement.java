package mg.fini_station.mvt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.utils.DbConn;

public class Encaissement {
    private String table_name = "Encaissement"; // Table name attribute
    private int idEncaissement;
    private double montant;
    private Timestamp dt; // Utilisation de Timestamp
    private String motif;

    // Constructors
    public Encaissement(int idEncaissement, double montant, String dt, String motif) {
        setIdEncaissement(idEncaissement);
        setMontant(montant);
        setDt(dt);
        setMotif(motif);
    }

    public Encaissement() {
    }

    // Getters and Setters
    public int getIdEncaissement() {
        return idEncaissement;
    }

    public void setIdEncaissement(int idEncaissement) {
        this.idEncaissement = idEncaissement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Timestamp getDt() {
        return dt;
    }

    public void setDt(Timestamp dt) {
        this.dt = dt;
    }

    public void setDt(String dt) {
        this.setDt(Timestamp.valueOf(dt));
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    // DAO Methods

    // Insert an Encaissement record
    public void insert() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("INSERT INTO " + this.table_name + " (montant, date_encaissement, motif) VALUES (?, ?, ?)");
            s.setDouble(1, this.getMontant());
            s.setTimestamp(2, this.getDt());
            s.setString(3, this.getMotif());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Retrieve all Encaissement records
    public List<Encaissement> getAll() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Encaissement> res = new ArrayList<>();
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            while (rs.next()) {
                Encaissement encaissement = new Encaissement();
                encaissement.setIdEncaissement(rs.getInt("id_encaissement"));
                encaissement.setMontant(rs.getDouble("montant"));
                encaissement.setDt(rs.getTimestamp("date_encaissement"));
                encaissement.setMotif(rs.getString("motif"));
                res.add(encaissement);
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

    // Retrieve an Encaissement by ID
    public Encaissement getById(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        Encaissement encaissement = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name + " WHERE id_encaissement = ?");
            s.setInt(1, id);
            rs = s.executeQuery();
            if (rs.next()) {
                encaissement = new Encaissement();
                encaissement.setIdEncaissement(rs.getInt("id_encaissement"));
                encaissement.setMontant(rs.getDouble("montant"));
                encaissement.setDt(rs.getTimestamp("date_encaissement"));
                encaissement.setMotif(rs.getString("motif"));
            }
            return encaissement;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Update an Encaissement record
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("UPDATE " + this.table_name + " SET montant = ?, date_encaissement = ?, motif = ? WHERE id_encaissement = ?");
            s.setDouble(1, this.getMontant());
            s.setTimestamp(2, this.getDt());
            s.setString(3, this.getMotif());
            s.setInt(4, this.getIdEncaissement());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Delete an Encaissement record
    public void delete(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("DELETE FROM " + this.table_name + " WHERE id_encaissement = ?");
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
