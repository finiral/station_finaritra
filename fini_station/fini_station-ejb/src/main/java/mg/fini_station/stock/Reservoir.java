package mg.fini_station.stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.utils.DbConn;

public class Reservoir {
    private String table_name = "Reservoir"; // Table name attribute
    private int idReservoir;
    private double qteMax;
    private double qteActuel;
    private Liquide typeLiquide;
    private MesureReservoir[] mesures;

    // Constructors
    public Reservoir(int idReservoir, double qteMax, double qteActuel, Liquide typeLiquide, MesureReservoir[] mesureReservoirs) {
        setIdReservoir(idReservoir);
        setQteMax(qteMax);
        setQteActuel(qteActuel);
        setTypeLiquide(typeLiquide);
        setMesures(mesureReservoirs);
    }

    public Reservoir() {
    }

    // Getters and Setters
    public int getIdReservoir() {
        return idReservoir;
    }

    public void setIdReservoir(int idReservoir) {
        this.idReservoir = idReservoir;
    }

    public double getQteMax() {
        return qteMax;
    }

    public void setQteMax(double qteMax) {
        this.qteMax = qteMax;
    }

    public double getQteActuel() {
        return qteActuel;
    }

    public void setQteActuel(double qteActuel) {
        this.qteActuel = qteActuel;
    }

    public Liquide getTypeLiquide() {
        return typeLiquide;
    }

    public void setTypeLiquide(Liquide typeLiquide) {
        this.typeLiquide = typeLiquide;
    }

    public MesureReservoir[] getMesures() {
        return mesures;
    }

    public void setMesures(MesureReservoir[] mesures) {
        this.mesures = mesures;
    }

    // DAO Methods

    // Insert a Reservoir record
    public void insert() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("INSERT INTO " + this.table_name + " (qte_max, id_liquide) VALUES (?,?)");
            s.setDouble(1, this.getQteMax());
            s.setInt(2, this.getTypeLiquide().getIdLiquide()); // Assuming Liquide class has a getIdLiquide method
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Retrieve all Reservoir records
    public List<Reservoir> getAll() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            List<Reservoir> res = new ArrayList<>();
            while (rs.next()) {
                Reservoir r = new Reservoir();
                r.setIdReservoir(rs.getInt("id_reservoir"));
                r.setQteMax(rs.getDouble("qte_max"));
                r.setTypeLiquide(new Liquide().getById(rs.getInt("id_liquide"))); // Assuming Liquide has a getById method
                res.add(r);
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

    // Retrieve a Reservoir by ID
    public Reservoir getById(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name + " WHERE id_reservoir = ?");
            s.setInt(1, id);
            rs = s.executeQuery();
            Reservoir r = null;
            if (rs.next()) {
                r = new Reservoir();
                r.setIdReservoir(rs.getInt("id_reservoir"));
                r.setQteMax(rs.getDouble("qte_max"));
                r.setTypeLiquide(new Liquide().getById(rs.getInt("id_liquide")));
            }
            return r;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Update a Reservoir record
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("UPDATE " + this.table_name + " SET qte_max = ?, id_liquide = ? WHERE id_reservoir = ?");
            s.setDouble(1, this.getQteMax());
            s.setInt(2, this.getTypeLiquide().getIdLiquide());
            s.setInt(3, this.getIdReservoir());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Delete a Reservoir record
    public void delete(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("DELETE FROM " + this.table_name + " WHERE id_reservoir = ?");
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
