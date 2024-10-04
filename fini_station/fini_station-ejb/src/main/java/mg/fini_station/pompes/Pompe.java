package mg.fini_station.pompes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.stock.Reservoir;
import mg.fini_station.utils.DbConn;

public class Pompe {
    private String table_name = "Pompe"; // Table name attribute
    private int idPompe;
    private String numeroPompe;
    private double qteMax;
    private Reservoir reservoir;

    // Constructors
    public Pompe(int idPompe, String numeroPompe, double qteMax, Reservoir reservoir) {
        setIdPompe(idPompe);
        setNumeroPompe(numeroPompe);
        setQteMax(qteMax);
        setReservoir(reservoir);
    }

    public Pompe() {
    }

    // Getters and Setters
    public int getIdPompe() {
        return idPompe;
    }

    public void setIdPompe(int idPompe) {
        this.idPompe = idPompe;
    }

    public String getNumeroPompe() {
        return numeroPompe;
    }

    public void setNumeroPompe(String numeroPompe) {
        this.numeroPompe = numeroPompe;
    }

    public double getQteMax() {
        return qteMax;
    }

    public void setQteMax(double qteMax) {
        this.qteMax = qteMax;
    }

    public Reservoir getReservoir() {
        return reservoir;
    }

    public void setReservoir(Reservoir reservoir) {
        this.reservoir = reservoir;
    }

    // DAO Methods

    // Insert a Pompe record
    public void insert() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("INSERT INTO " + this.table_name + " (numero_pompe, qteMax, id_reservoir) VALUES (?,?,?)");
            s.setString(1, this.getNumeroPompe());
            s.setDouble(2, this.getQteMax());
            s.setInt(3, this.getReservoir().getIdReservoir()); // Assuming Reservoir has a getIdReservoir() method
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Retrieve all Pompe records
    public List<Pompe> getAll() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            List<Pompe> res = new ArrayList<>();
            while (rs.next()) {
                Pompe p = new Pompe();
                p.setIdPompe(rs.getInt("id_pompe"));
                p.setNumeroPompe(rs.getString("numero_pompe"));
                p.setQteMax(rs.getDouble("qteMax"));
                p.setReservoir(new Reservoir().getById(rs.getInt("id_reservoir"))); // Assuming Reservoir has a getById method
                res.add(p);
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

    // Retrieve a Pompe by ID
    public Pompe getById(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name + " WHERE id_pompe = ?");
            s.setInt(1, id);
            rs = s.executeQuery();
            Pompe p = null;
            if (rs.next()) {
                p = new Pompe();
                p.setIdPompe(rs.getInt("id_pompe"));
                p.setNumeroPompe(rs.getString("numero_pompe"));
                p.setQteMax(rs.getDouble("qteMax"));
                p.setReservoir(new Reservoir().getById(rs.getInt("id_reservoir")));
            }
            return p;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Update a Pompe record
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("UPDATE " + this.table_name + " SET numero_pompe=?, qteMax=?, id_reservoir=? WHERE id_pompe=?");
            s.setString(1, this.getNumeroPompe());
            s.setDouble(2, this.getQteMax());
            s.setInt(3, this.getReservoir().getIdReservoir());
            s.setInt(4, this.getIdPompe());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Delete a Pompe record
    public void delete(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("DELETE FROM " + this.table_name + " WHERE id_pompe = ?");
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
