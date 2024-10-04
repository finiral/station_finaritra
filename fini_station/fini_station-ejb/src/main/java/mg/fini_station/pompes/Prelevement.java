package mg.fini_station.pompes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.Utilitaire;

public class Prelevement {
    private String table_name = "Prelevement";
    private int idPrelevement;
    private Pompiste pompiste;
    private Pompe pompe;
    private double compteur;
    private Timestamp dateTime; // Using java.sql.Timestamp

    // Constructors
    public Prelevement(int idPrelevement, Pompiste pompiste, Pompe pompe, double compteur, String dateTime) {
        setIdPrelevement(idPrelevement);
        setPompiste(pompiste);
        setPompe(pompe);
        setCompteur(compteur);
        setDateTime(dateTime);
    }

    public Prelevement() {
    }

    // Getters and Setters
    public int getIdPrelevement() {
        return idPrelevement;
    }

    public void setIdPrelevement(int idPrelevement) {
        this.idPrelevement = idPrelevement;
    }

    public Pompiste getPompiste() {
        return pompiste;
    }

    public void setPompiste(Pompiste pompiste) {
        this.pompiste = pompiste;
    }

    public Pompe getPompe() {
        return pompe;
    }

    public void setPompe(Pompe pompe) {
        this.pompe = pompe;
    }

    public double getCompteur() {
        return compteur;
    }

    public void setCompteur(double compteur) {
        this.compteur = compteur;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public void setDateTime(String dateTime) {
        this.setDateTime(Utilitaire.parseToTimestamp(dateTime)); // Assuming Utilitaire handles parsing
    }

    // DAO Methods

    // Insert a Prelevement record
    public void insert() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement(
                    "INSERT INTO Prelevement (id_pompiste, id_pompe, compteur_prelevement, dateheure_prelevement) VALUES (?,?,?,?)");
            s.setInt(1, this.getPompiste().getIdPompiste());
            s.setInt(2, this.getPompe().getIdPompe());
            s.setDouble(3, this.getCompteur());
            s.setTimestamp(4, this.getDateTime());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
            if (c != null) {
                c.close();
            }
        }
    }

    // Retrieve all Prelevement records
    public List<Prelevement> getAll() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            List<Prelevement> res = new ArrayList<>();
            while (rs.next()) {
                Prelevement p = new Prelevement();
                p.setIdPrelevement(rs.getInt("id_prelevement"));
                p.setCompteur(rs.getDouble("compteur_prelevement"));
                p.setDateTime(rs.getTimestamp("dateheure_prelevement"));
                // Retrieve and set Pompiste and Pompe objects (assuming you have appropriate DAO methods for them)
                p.setPompiste(new Pompiste().getById(rs.getInt("id_pompiste")));
                p.setPompe(new Pompe().getById(rs.getInt("id_pompe")));
                res.add(p);
            }
            return res;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
            if (c != null) {
                c.close();
            }
        }
    }

    // Retrieve a Prelevement by ID
    public Prelevement getById(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name + " WHERE id_prelevement = ?");
            s.setInt(1, id);
            rs = s.executeQuery();
            Prelevement p = null;
            if (rs.next()) {
                p = new Prelevement();
                p.setIdPrelevement(rs.getInt("id_prelevement"));
                p.setCompteur(rs.getDouble("compteur_prelevement"));
                p.setDateTime(rs.getTimestamp("dateheure_prelevement"));
                p.setPompiste(new Pompiste().getById(rs.getInt("id_pompiste")));
                p.setPompe(new Pompe().getById(rs.getInt("id_pompe")));
            }
            return p;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
            if (c != null) {
                c.close();
            }
        }
    }

    // Update a Prelevement record
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement(
                    "UPDATE Prelevement SET id_pompiste=?, id_pompe=?, compteur_prelevement=?, dateheure_prelevement=? WHERE id_prelevement=?");
            s.setInt(1, this.getPompiste().getIdPompiste());
            s.setInt(2, this.getPompe().getIdPompe());
            s.setDouble(3, this.getCompteur());
            s.setTimestamp(4, this.getDateTime());
            s.setInt(5, this.getIdPrelevement());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
            if (c != null) {
                c.close();
            }
        }
    }

    // Delete a Prelevement record
    public void delete(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("DELETE FROM " + this.table_name + " WHERE id_prelevement = ?");
            s.setInt(1, id);
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
            if (c != null) {
                c.close();
            }
        }
    }
}
