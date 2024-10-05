package mg.fini_station.mvt;

import java.sql.Connection;
import java.sql.Timestamp; // Import java.sql.Timestamp
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.Utilitaire;

public class Depense {
    private String table_name = "Depense"; // Table name attribute
    private int idDepense;
    private double qte; // Added qte field
    private double montant;
    private Timestamp dt; // Change to Timestamp
    private String motif;

    // Constructors
    public Depense(int idDepense, double qte, double montant, String dt, String motif) { // Added qte in constructor
        setIdDepense(idDepense);
        setQte(qte); // Set qte
        setMontant(montant);
        setDt(dt); // Change to Timestamp.valueOf
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

    public double getQte() { // Added getter for qte
        return qte;
    }

    public void setQte(double qte) { // Added setter for qte
        this.qte = qte;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Timestamp getDt() { // Change return type to Timestamp
        return dt;
    }

    public void setDt(Timestamp dt) { // Change parameter type to Timestamp
        this.dt = dt;
    }

    public void setDt(String dt) {
        this.setDt(Utilitaire.parseToTimestamp(dt)); // Change to Timestamp.valueOf
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
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            insert(c);
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null) c.close();
        }
    }

    public void insert(Connection c) throws Exception {
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("INSERT INTO " + this.table_name + " (qte, montant, date_depense, motif) VALUES (?, ?, ?, ?)");
            s.setDouble(1, this.getQte()); // Set qte in the statement
            s.setDouble(2, this.getMontant());
            s.setTimestamp(3, this.getDt()); // Change to setTimestamp
            s.setString(4, this.getMotif());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
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
                depense.setQte(rs.getDouble("qte")); // Get qte from result set
                depense.setMontant(rs.getDouble("montant"));
                depense.setDt(rs.getTimestamp("date_depense")); // Change to getTimestamp
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
                depense.setQte(rs.getDouble("qte")); // Get qte from result set
                depense.setMontant(rs.getDouble("montant"));
                depense.setDt(rs.getTimestamp("date_depense")); // Change to getTimestamp
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
            s = c.prepareStatement("UPDATE " + this.table_name + " SET qte = ?, montant = ?, date_depense = ?, motif = ? WHERE id_depense = ?");
            s.setDouble(1, this.getQte()); // Set qte in the statement
            s.setDouble(2, this.getMontant());
            s.setTimestamp(3, this.getDt()); // Change to setTimestamp
            s.setString(4, this.getMotif());
            s.setInt(5, this.getIdDepense());
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
