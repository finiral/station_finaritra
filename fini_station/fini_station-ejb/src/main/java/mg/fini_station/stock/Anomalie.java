package mg.fini_station.stock;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import mg.fini_station.mvt.PrelevementReservoir;
import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.Utilitaire;

public class Anomalie {
    Reservoir reservoir;
    double qteReservoir;
    double qtePrelevement;
    double difference;
    Date dt;

    public Date getDt() throws Exception {
        if (this.dt == null) {
            Connection c = new DbConn().getConnection();
            this.setDt(this.getDt(c));
            c.close();
        }
        return dt;
    }

    public Date getDt(Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement(
                    "SELECT * FROM PrelevementReservoir where id_reservoir=? order by date_prelevement desc");
            s.setInt(1, this.getReservoir().getIdReservoir());
            rs = s.executeQuery();
            Date dt = null;
            if (rs.next()) {
                dt = rs.getDate("date_prelevement");
            }
            return dt;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Reservoir getReservoir() {
        return reservoir;
    }

    public void setReservoir(Reservoir reservoir) {
        this.reservoir = reservoir;
    }

    public double getQteReservoir() throws Exception {
        if (this.qteReservoir != 0 && this.qteReservoir > 0) {
        } else {
            Connection c = new DbConn().getConnection();
            this.setQteReservoir(this.getQteReservoir(c));
            c.close();
        }
        return qteReservoir;
    }

    public double getQteReservoir(Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            Date old_dt = (Date) this.getDt(c).clone();
            Timestamp new_dt = Utilitaire.convertDateToTimestamp(Utilitaire.addDays(old_dt, 1));
            // Query to sum the quantities in StockReservoir for the specific Reservoir
            s = c.prepareStatement(
                    "SELECT SUM(qte_stock) as total_stock from StockReservoir where dt_stock<? and id_reservoir=?");
            s.setTimestamp(1, new_dt);
            s.setInt(2, this.getReservoir().getIdReservoir());
            rs = s.executeQuery();
            double qteTotal = 0;
            if (rs.next()) {
                // Set qteTotal based on the sum of all stock quantities for this reservoir
                qteTotal = rs.getDouble("total_stock");
            }
            return qteTotal;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }
    }

    public void setQteReservoir(double qteReservoir) {
        this.qteReservoir = qteReservoir;
    }

    public double getQtePrelevement() throws Exception {
        if (this.qtePrelevement != 0 && this.qtePrelevement > 0) {
        } else {
            Connection c = new DbConn().getConnection();
            this.setQtePrelevement(this.getQtePrelevement(c));
            c.close();
        }
        return qtePrelevement;
    }

    public double getQtePrelevement(Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            // Query to sum the quantities in StockReservoir for the specific Reservoir
            s = c.prepareStatement("SELECT * from PrelevementReservoir where date_prelevement=? and id_reservoir=?");
            s.setDate(1, this.getDt(c));
            s.setInt(2, this.getReservoir().getIdReservoir());
            rs = s.executeQuery();
            double qteTotal = 0;
            if (rs.next()) {
                // Set qteTotal based on the sum of all stock quantities for this reservoir
                qteTotal = rs.getDouble("volume_prelevement");
            }
            return qteTotal;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }
    }

    public void setQtePrelevement(double qtePrelevement) {
        this.qtePrelevement = qtePrelevement;
    }

    public double getDifference() throws Exception {
        return this.getQteReservoir() - this.getQtePrelevement();
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }
}
