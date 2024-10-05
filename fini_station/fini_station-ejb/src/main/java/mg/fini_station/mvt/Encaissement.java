package mg.fini_station.mvt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.ExceptionConverter;

import mg.fini_station.pompes.Prelevement;
import mg.fini_station.utils.DbConn;

public class Encaissement {
    private String table_name = "Encaissement"; // Table name attribute
    private int idEncaissement;
    private Prelevement prelevement;
    private double montant;
    private Timestamp dt; // Utilisation de Timestamp

    // Constructors
    public Encaissement(int idEncaissement, double montant, Prelevement p, String dt) {
        setIdEncaissement(idEncaissement);
        setMontant(montant);
        setDt(dt);
        setPrelevement(p);
    }

    public Encaissement() {
    }

    public Prelevement getPrelevement() {
        return prelevement;
    }

    public void setPrelevement(Prelevement prelevement) {
        this.prelevement = prelevement;
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

    // DAO Methods

    // Insert an Encaissement record
    public void insert() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("INSERT INTO " + this.table_name + " (montant, date_encaissement, id_prelevement) VALUES (?, ?, ?)");
            s.setDouble(1, this.getMontant());
            s.setTimestamp(2, this.getDt());
            s.setInt(3, this.getPrelevement().getIdPrelevement()); // Set Prelevement ID
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

                // Retrieve Prelevement object
                Prelevement prelevement = new Prelevement();
                prelevement = prelevement.getById(rs.getInt("id_prelevement")); // Set Prelevement from DB
                encaissement.setPrelevement(prelevement);

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

                // Retrieve Prelevement object
                Prelevement prelevement = new Prelevement();
                prelevement = prelevement.getById(rs.getInt("id_prelevement")); // Set Prelevement from DB
                encaissement.setPrelevement(prelevement);
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
            s = c.prepareStatement("UPDATE " + this.table_name + " SET montant = ?, date_encaissement = ?, id_prelevement = ? WHERE id_encaissement = ?");
            s.setDouble(1, this.getMontant());
            s.setTimestamp(2, this.getDt());
            s.setInt(3, this.getPrelevement().getIdPrelevement()); // Update Prelevement ID
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

    public void encaisser() throws Exception{
        this.insert();
    }
}
