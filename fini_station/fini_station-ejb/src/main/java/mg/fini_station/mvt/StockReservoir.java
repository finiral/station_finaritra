package mg.fini_station.mvt;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.stock.Reservoir;
import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.Utilitaire;

public class StockReservoir {
    private String table_name = "StockReservoir"; // Table name attribute
    private int idStockReservoir;
    private Timestamp dt; // Utilisation de Date
    private double qte;
    private Reservoir reserv;

    // Constructors
    public StockReservoir(int idStockReservoir, String dt, double qte, Reservoir reserv) {
        setIdStockReservoir(idStockReservoir);
        setDt(dt);
        setQte(qte);
        setReserv(reserv);
    }

    public StockReservoir(int idStockReservoir, Timestamp dt, double qte, Reservoir reserv) {
        setIdStockReservoir(idStockReservoir);
        setDt(dt);
        setQte(qte);
        setReserv(reserv);
    }

    public StockReservoir() {
    }

    // Getters and Setters
    public int getIdStockReservoir() {
        return idStockReservoir;
    }

    public void setIdStockReservoir(int idStockReservoir) {
        this.idStockReservoir = idStockReservoir;
    }

    public Timestamp getDt() {
        return dt;
    }

    public void setDt(Timestamp dt) {
        this.dt = dt;
    }

    public void setDt(String dt) {
        this.setDt(Utilitaire.parseToTimestamp(dt));
    }

    public double getQte() {
        return qte;
    }

    public void setQte(double qte) {
        this.qte = qte;
    }

    public Reservoir getReserv() {
        return reserv;
    }

    public void setReserv(Reservoir reserv) {
        this.reserv = reserv;
    }

    // DAO Methods

    // Insert a StockReservoir record
    public void insert() throws Exception {
        Connection c = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            this.insert();
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
            s = c.prepareStatement("INSERT INTO " + this.table_name + " (dt_stock, qte_stock, id_reservoir) VALUES (?, ?, ?)");
            s.setTimestamp(1, this.getDt());
            s.setDouble(2, this.getQte());
            s.setInt(3, this.getReserv().getIdReservoir());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
        }
    }

    // Retrieve all StockReservoir records
    public List<StockReservoir> getAll() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        List<StockReservoir> res = new ArrayList<>();
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            while (rs.next()) {
                StockReservoir stockReservoir = new StockReservoir();
                stockReservoir.setIdStockReservoir(rs.getInt("id_stockreservoir"));
                stockReservoir.setDt(rs.getTimestamp("dt_stock"));
                stockReservoir.setQte(rs.getDouble("qte_stock"));
                // You may want to retrieve the Reservoir object as well if necessary
                res.add(stockReservoir);
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

    // Retrieve a StockReservoir by ID
    public StockReservoir getById(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        StockReservoir stockReservoir = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name + " WHERE id_stockreservoir = ?");
            s.setInt(1, id);
            rs = s.executeQuery();
            if (rs.next()) {
                stockReservoir = new StockReservoir();
                stockReservoir.setIdStockReservoir(rs.getInt("id_stockreservoir"));
                stockReservoir.setDt(rs.getTimestamp("dt_stock"));
                stockReservoir.setQte(rs.getDouble("qte_stock"));
                // You may want to retrieve the Reservoir object as well if necessary
            }
            return stockReservoir;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Update a StockReservoir record
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("UPDATE " + this.table_name + " SET dt_stock = ?, qte_stock = ?, id_reservoir = ? WHERE id_stockreservoir = ?");
            s.setTimestamp(1, this.getDt());
            s.setDouble(2, this.getQte());
            s.setInt(3, this.getReserv().getIdReservoir());
            s.setInt(4, this.getIdStockReservoir());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Delete a StockReservoir record
    public void delete(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("DELETE FROM " + this.table_name + " WHERE id_stockreservoir = ?");
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
