package mg.fini_station.pompes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.mvt.StockReservoir;
import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.Utilitaire;

public class PrelevLub {
    private String table_name = "PrelevLub";
    private int idPrelevement;
    private Pompiste pompiste;
    private Pompe pompe;
    private Lubrifiant lubrifiant;
    private double qte;
    private double pu;
    private Timestamp dateTime; // Using java.sql.Timestamp

    public PrelevLub() {
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
    }  public Timestamp getDateTime() {
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
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            this.insert(c);
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public void insert(Connection c) throws Exception {
        PreparedStatement s = null;
        try {
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
        }
    }

    // Retrieve all Prelevement records
    public List<PrelevLub> getAll() throws Exception {
        Connection c = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            return this.getAll(c);
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public List<PrelevLub> getAll(Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            List<PrelevLub> res = new ArrayList<>();
            while (rs.next()) {
                PrelevLub p = new PrelevLub();
                p.setIdPrelevement(rs.getInt("id_prelevement"));
                p.setCompteur(rs.getDouble("compteur_prelevement"));
                p.setDateTime(rs.getTimestamp("dateheure_prelevement"));
                // Retrieve and set Pompiste and Pompe objects (assuming you have appropriate
                // DAO methods for them)
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
        }
    }

    // Retrieve a Prelevement by ID
    public PrelevLub getById(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name + " WHERE id_prelevement = ?");
            s.setInt(1, id);
            rs = s.executeQuery();
            PrelevLub p = null;
            if (rs.next()) {
                p = new PrelevLub();
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

    // Retrieve all Prelevement records by Pompe ID
    public List<PrelevLub> getByIdPompe(int idPompe) throws Exception {
        Connection c = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            return this.getByIdPompe(c, idPompe);
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null)
                c.close();
        }
    }

    public List<PrelevLub> getByIdPompe(Connection c, int idPompe) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        List<PrelevLub> res = new ArrayList<>();
        try {
            s = c.prepareStatement(
                    "SELECT * FROM " + this.table_name + " WHERE id_pompe = ? and dateheure_prelevement<?");
            s.setInt(1, idPompe);
            s.setTimestamp(2, this.getDateTime());
            rs = s.executeQuery();
            while (rs.next()) {
                PrelevLub p = new PrelevLub();
                p.setIdPrelevement(rs.getInt("id_prelevement"));
                p.setCompteur(rs.getDouble("compteur_prelevement"));
                p.setDateTime(rs.getTimestamp("dateheure_prelevement"));
                // Retrieve and set Pompiste and Pompe objects
                p.setPompiste(new Pompiste().getById(rs.getInt("id_pompiste")));
                p.setPompe(new Pompe().getById(rs.getInt("id_pompe")));
                res.add(p);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }
        return res;
    }

    // Retrieve all Prelevement records by Pompe ID, ordered by date in descending
    // order
    public List<PrelevLub> getByIdPompeDtDesc(int idPompe) throws Exception {
        Connection c = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            return this.getByIdPompeDtDesc(c, idPompe);
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null)
                c.close();
        }
    }

    public List<PrelevLub> getByIdPompeDtDesc(Connection c, int idPompe) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        List<PrelevLub> res = new ArrayList<>();
        try {
            // Modified query to order by date in descending order
            s = c.prepareStatement(
                    "SELECT * FROM " + this.table_name
                            + " WHERE id_pompe = ? and dateheure_prelevement < ? ORDER BY dateheure_prelevement DESC");
            s.setInt(1, idPompe);
            s.setTimestamp(2, this.getDateTime());
            rs = s.executeQuery();
            while (rs.next()) {
                PrelevLub p = new PrelevLub();
                p.setIdPrelevement(rs.getInt("id_prelevement"));
                p.setCompteur(rs.getDouble("compteur_prelevement"));
                p.setDateTime(rs.getTimestamp("dateheure_prelevement"));
                // Retrieve and set Pompiste and Pompe objects
                p.setPompiste(new Pompiste().getById(rs.getInt("id_pompiste")));
                p.setPompe(new Pompe().getById(rs.getInt("id_pompe")));
                res.add(p);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }
        return res;
    }

    public double getDifferenceCompteur(Connection c) throws Exception {
        List<PrelevLub> ls = this.getByIdPompeDtDesc(c, this.getPompe().getIdPompe());
        return this.getCompteur() - ls.get(0).getCompteur();
    }

    public double getDifferenceCompteur() throws Exception {
        Connection c=new DbConn().getConnection();
        double res=this.getDifferenceCompteur(c);
        c.close();
        return res;
    }

    public double getDifferenceCompteurVola(Connection c) throws Exception {
        double res=this.getDifferenceCompteur(c) * this.getPompe().getReservoir().getTypeLiquide().getPrixUnitaireVente();
        return res;
    }

    public double getDifferenceCompteurVola() throws Exception {
        Connection c=new DbConn().getConnection();
        double res=this.getDifferenceCompteurVola(c);
        c.close();
        return res;
    }

    public boolean isStatePair(Connection c) throws Exception {
        List<PrelevLub> ls = this.getByIdPompe(c, this.getPompe().getIdPompe());
        return ls.size() % 2 != 0;
    }

    public boolean isStatePair() throws Exception {
        List<PrelevLub> ls = this.getByIdPompe(this.getPompe().getIdPompe());
        return ls.size() % 2 != 0;
    }

    public void prelever() throws Exception {
        Connection c = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            c.setAutoCommit(false);
            if (!this.isStatePair(c)) {
                // c'est une entree
                this.insert(c);

            } else {
                // c'est une sortie
                this.insert(c);
                double new_qte = -this.getDifferenceCompteur(c);
                StockReservoir s = new StockReservoir(-78, this.getDateTime(), new_qte, this.getPompe().getReservoir());
                s.insert(c);
            }
            c.commit();

        } catch (Exception e) {
            c.rollback();
            throw e;
        } finally {
            if (c != null)
                c.close();
        }
    }
}
