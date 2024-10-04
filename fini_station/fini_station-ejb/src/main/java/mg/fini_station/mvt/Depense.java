package mg.fini_station.mvt;

import java.sql.Connection;
import java.sql.Date; // Import java.sql.Date instead of Timestamp
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.utils.DbConn;

public class Depense {
    private String table_name = "Depense"; // Table name attribute
    private int idDepense;
    private double montant;
    private Date dt; // Change to Date
    private String motif;

    // Constructors
    public Depense(int idDepense, double montant, String dt, String motif) {
        setIdDepense(idDepense);
        setMontant(montant);
        setDt(dt); // Change to Date.valueOf
        setMotif(motif);
    }

    public Depense() {
    }

    // Getters and Setters
    public int getIdDepense() {
        return idDepense;
    }

    public void setIdDepense(int idDepense) {
        this.idDepense = idDepense;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDt() { // Change return type to Date
        return dt;
    }

    public void setDt(Date dt) { // Change parameter type to Date
        this.dt = dt;
    }

    public void setDt(String dt) {
        this.setDt(Date.valueOf(dt)); // Change to Date.valueOf
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    // DAO Methods

    // Insert a Depense record
    public void insert() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("INSERT INTO " + this.table_name + " (montant, date_depense, motif) VALUES (?, ?, ?)");
            s.setDouble(1, this.getMontant());
            s.setDate(2, this.getDt()); // Change to setDate
            s.setString(3, this.getMotif());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Retrieve all Depense records
    public List<Depense> getAll() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<Depense> res = new ArrayList<>();
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            while (rs.next()) {
                Depense depense = new Depense();
                depense.setIdDepense(rs.getInt("id_depense"));
                depense.setMontant(rs.getDouble("montant"));
                depense.setDt(rs.getDate("date_depense")); // Change to getDate
                depense.setMotif(rs.getString("motif"));
                res.add(depense);
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

    // Retrieve a Depense by ID
    public Depense getById(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        Depense depense = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name + " WHERE id_depense = ?");
            s.setInt(1, id);
            rs = s.executeQuery();
            if (rs.next()) {
                depense = new Depense();
                depense.setIdDepense(rs.getInt("id_depense"));
                depense.setMontant(rs.getDouble("montant"));
                depense.setDt(rs.getDate("date_depense")); // Change to getDate
                depense.setMotif(rs.getString("motif"));
            }
            return depense;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Update a Depense record
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("UPDATE " + this.table_name + " SET montant = ?, date_depense = ?, motif = ? WHERE id_depense = ?");
            s.setDouble(1, this.getMontant());
            s.setDate(2, this.getDt()); // Change to setDate
            s.setString(3, this.getMotif());
            s.setInt(4, this.getIdDepense());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Delete a Depense record
    public void delete(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("DELETE FROM " + this.table_name + " WHERE id_depense = ?");
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
