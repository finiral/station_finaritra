package mg.fini_station.stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.utils.DbConn;

public class MesureReservoir {
    private String table_name = "MesureReservoir"; // Nom de la table
    private int idMesureReservoir;
    private double mesureLongueur;
    private double mesureVolume;

    // Constructeurs
    public MesureReservoir(int idMesureReservoir, double mesureLongueur, double mesureVolume) {
        setIdMesureReservoir(idMesureReservoir);
        setMesureLongueur(mesureLongueur);
        setMesureVolume(mesureVolume);
    }

    public MesureReservoir() {}

    // Getters et Setters
    public int getIdMesureReservoir() {
        return idMesureReservoir;
    }

    public void setIdMesureReservoir(int idMesureReservoir) {
        this.idMesureReservoir = idMesureReservoir;
    }

    public double getMesureLongueur() {
        return mesureLongueur;
    }

    public void setMesureLongueur(double mesureLongueur) {
        this.mesureLongueur = mesureLongueur;
    }

    public double getMesureVolume() {
        return mesureVolume;
    }

    public void setMesureVolume(double mesureVolume) {
        this.mesureVolume = mesureVolume;
    }

    // Méthodes DAO

    // Insérer une nouvelle mesure de réservoir
    public void insert() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("INSERT INTO " + this.table_name + " (mesureLongueur, mesureVolume) VALUES (?,?)");
            s.setDouble(1, this.getMesureLongueur());
            s.setDouble(2, this.getMesureVolume());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Récupérer toutes les mesures de réservoir
    public List<MesureReservoir> getAll() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            List<MesureReservoir> res = new ArrayList<>();
            while (rs.next()) {
                MesureReservoir m = new MesureReservoir();
                m.setIdMesureReservoir(rs.getInt("id_mesureReservoir"));
                m.setMesureLongueur(rs.getDouble("mesureLongueur"));
                m.setMesureVolume(rs.getDouble("mesureVolume"));
                res.add(m);
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

    // Récupérer une mesure de réservoir par ID
    public MesureReservoir getById(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name + " WHERE id_mesureReservoir = ?");
            s.setInt(1, id);
            rs = s.executeQuery();
            MesureReservoir m = null;
            if (rs.next()) {
                m = new MesureReservoir();
                m.setIdMesureReservoir(rs.getInt("id_mesureReservoir"));
                m.setMesureLongueur(rs.getDouble("mesureLongueur"));
                m.setMesureVolume(rs.getDouble("mesureVolume"));
            }
            return m;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Mettre à jour une mesure de réservoir
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("UPDATE " + this.table_name + " SET mesureLongueur = ?, mesureVolume = ? WHERE id_mesureReservoir = ?");
            s.setDouble(1, this.getMesureLongueur());
            s.setDouble(2, this.getMesureVolume());
            s.setInt(3, this.getIdMesureReservoir());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Supprimer une mesure de réservoir par ID
    public void delete(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("DELETE FROM " + this.table_name + " WHERE id_mesureReservoir = ?");
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
