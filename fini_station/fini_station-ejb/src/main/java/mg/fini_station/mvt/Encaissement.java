package mg.fini_station.mvt;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.ExceptionConverter;

import ejb_fini.ComptaBeanClient;
import mg.cnaps.compta.ComptaSousEcriture;
import mg.fini_station.pompes.Prelevement;
import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.OracleConn;
import mg.fini_station.utils.Utilitaire;

public class Encaissement {
    private String table_name = "Encaissement"; // Table name attribute
    private int idEncaissement;
    private Prelevement prelevement;
    private double montant;
    private Timestamp dt; // Utilisation de Timestamp
    private String idVente;
    private String idVenteDetail;
    private double avoir;
    private double avoirPaie;

    public double getAvoirPaie() {
        return avoirPaie;
    }

    public void setAvoirPaie(double avoirPaie) {
        this.avoirPaie = avoirPaie;
    }

    public double getAvoir() {
        return avoir;
    }

    public void setAvoir(double avoir) {
        this.avoir = avoir;
    }

    public String getIdVente() {
        return idVente;
    }

    public void setIdVente(String idVente) {
        this.idVente = idVente;
    }

    public String getIdVenteDetail() {
        return idVenteDetail;
    }

    public void setIdVenteDetail(String idVenteDetail) {
        this.idVenteDetail = idVenteDetail;
    }

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
        this.setDt(Utilitaire.parseToTimestamp(dt));
    }

    // DAO Methods

    // Insert an Encaissement record
    public void insert() throws Exception {
        Connection c = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            this.insert(c);
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null)
                c.close();
        }
    }

    public void insert(Connection c) throws Exception {
        PreparedStatement s = null;
        try {
            s = c.prepareStatement("INSERT INTO " + this.table_name
                    + " (montant_encaissement, dateheure_encaissement, id_prelevement,id_vente,id_ventedetail,avoir,avoir_paie) VALUES (?, ?, ?,?,?,?,?)");
            s.setDouble(1, this.getMontant());
            s.setTimestamp(2, this.getDt());
            s.setInt(3, this.getPrelevement().getIdPrelevement()); // Set Prelevement ID
            s.setString(4, getIdVente());
            s.setString(5, getIdVenteDetail());
            s.setDouble(6, getAvoir());
            s.setDouble(7,0);
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null)
                s.close();
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
                encaissement.setMontant(rs.getDouble("montant_encaissement"));
                encaissement.setDt(rs.getTimestamp("dateheure_encaissement"));
                encaissement.setIdVente(rs.getString("id_vente"));
                encaissement.setIdVenteDetail(rs.getString("id_ventedetail"));
                encaissement.setAvoir(rs.getDouble("avoir"));
                encaissement.setAvoirPaie(rs.getDouble("avoir_paie"));

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
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
            if (c != null)
                c.close();
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
                encaissement.setMontant(rs.getDouble("montant_encaissement"));
                encaissement.setDt(rs.getTimestamp("dateheure_encaissement"));
                encaissement.setIdVente(rs.getString("id_vente"));
                encaissement.setIdVenteDetail(rs.getString("id_ventedetail"));
                encaissement.setAvoir(rs.getDouble("avoir"));
                encaissement.setAvoirPaie(rs.getDouble("avoir_paie"));

                // Retrieve Prelevement object
                Prelevement prelevement = new Prelevement();
                prelevement = prelevement.getById(rs.getInt("id_prelevement")); // Set Prelevement from DB
                encaissement.setPrelevement(prelevement);
            }
            return encaissement;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
            if (c != null)
                c.close();
        }
    }

    // Update an Encaissement record
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("UPDATE " + this.table_name
                    + " SET montant_encaissement = ?, dateheure_encaissement = ?, id_prelevement = ?,id_vente=?,id_ventedetail=?,avoir=?,avoir_paie=? WHERE id_encaissement = ?");
            s.setDouble(1, this.getMontant());
            s.setTimestamp(2, this.getDt());
            s.setInt(3, this.getPrelevement().getIdPrelevement()); // Update Prelevement ID
            s.setString(4, this.getIdVente());
            s.setString(5, this.getIdVenteDetail());
            s.setDouble(6, this.getAvoir());
            s.setDouble(7, this.getAvoirPaie());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null)
                s.close();
            if (c != null)
                c.close();
        }
    }

    public void updateAvoirPaie() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("UPDATE " + this.table_name
                    + " SET avoir_paie=? WHERE id_encaissement = ?");
            s.setDouble(1, this.getAvoirPaie());
            s.setInt(2, this.getIdEncaissement());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null)
                s.close();
            if (c != null)
                c.close();
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
            if (s != null)
                s.close();
            if (c != null)
                c.close();
        }
    }

    public double getDifferencePrelevementEtEncaisser() throws Exception {
        return this.getPrelevement().getDifferenceCompteurVola() - this.getMontant();
    }

    // public void encaisser() throws Exception {
    // Connection c = null;
    // try {
    // DbConn db = new DbConn();
    // c = db.getConnection();
    // c.setAutoCommit(false);
    // this.insert(c);
    // // Ecriture vers centrale
    // ComptaBeanClient compta = new ComptaBeanClient();
    // // Création sous ecriture CAISSE
    // ComptaSousEcriture cse = new ComptaSousEcriture();
    // cse.setCompte("5300000000000");
    // cse.setLibellePiece("Encaissement depuis pompe " +
    // this.getPrelevement().getPompe().getNumeroPompe());
    // cse.setRemarque("Encaissement depuis pompe " +
    // this.getPrelevement().getPompe().getNumeroPompe());
    // cse.setJournal("COMP000039");
    // cse.setDebit(this.getMontant());
    // cse.setCredit(0);
    // cse.setDaty(Utilitaire.convertTimestampToDate(this.getDt()));
    // compta.lookupComptaBeanLocal().ecrireSousEcriture(new
    // OracleConn().getConnection(), cse);
    // // Création sous ecriture 712
    // ComptaSousEcriture four = new ComptaSousEcriture();
    // four.setCompte("712000");
    // four.setLibellePiece("Vente depuis pompe " +
    // this.getPrelevement().getPompe().getNumeroPompe());
    // four.setRemarque("Vente depuis pompe " +
    // this.getPrelevement().getPompe().getNumeroPompe());
    // four.setJournal("COMP000039");
    // four.setDebit(this.getPrelevement().getDifferenceCompteurVola(c)-this.getMontant());
    // four.setCredit(this.getPrelevement().getDifferenceCompteurVola(c));
    // four.setDaty(Utilitaire.convertTimestampToDate(this.getDt()));
    // compta.lookupComptaBeanLocal().ecrireSousEcriture(new
    // OracleConn().getConnection(), four);
    // // Creditation client divers (RAHA TSISI AVOIR)
    // ComptaSousEcriture client = new ComptaSousEcriture();
    // client.setCompte("4110000000000");
    // client.setLibellePiece("Avoir " +
    // this.getPrelevement().getPompe().getNumeroPompe());
    // client.setRemarque("Avoir " +
    // this.getPrelevement().getPompe().getNumeroPompe());
    // client.setJournal("COMP000039");
    // client.setDebit(0);
    // client.setCredit(this.getMontant());
    // client.setDaty(Utilitaire.convertTimestampToDate(this.getDt()));
    // compta.lookupComptaBeanLocal().ecrireSousEcriture(new
    // OracleConn().getConnection(), client);
    // c.commit();
    // } catch (Exception e) {
    // c.rollback();
    // throw e;
    // } finally {
    // if (c != null)
    // c.close();
    // }
    // }

    public void encaissement() throws Exception {
        Connection c = null;
        try {
            /// CENTRALE
            ComptaBeanClient compta = new ComptaBeanClient();
            String idproduit = this.getPrelevement().getPompe().getReservoir().getTypeLiquide().getIdCentrale();
            String designation = "" + this.getPrelevement().getPompe().getNumeroPompe();
            Date dt = Utilitaire.convertTimestampToDate(this.getDt());
            double montantCompteur = this.getPrelevement().getDifferenceCompteurVola();
            String[] ids = compta.lookupComptaBeanLocal().encaissement(designation, dt, this.getMontant(),
                    montantCompteur, idproduit);

            /// INSERTION ENCAISSEMENT LOCAL
            DbConn db = new DbConn();
            c = db.getConnection();
            c.setAutoCommit(false);
            this.setIdVente(ids[0]);
            this.setIdVenteDetail(ids[1]);
            this.insert(c);
            c.commit();
        } catch (Exception e) {
            c.rollback();
            e.printStackTrace();
        } finally {
            c.close();
        }
    }

    public void avoir(String id_client, Date dt_time, double montant) throws Exception {
        /// CENTRALE
        ComptaBeanClient compta = new ComptaBeanClient();
        String idvente = this.getIdVente();
        String idventedetail = this.getIdVenteDetail();

        /// VERIFICATION
        double total_avoir = this.getAvoirPaie() + montant;
        if (total_avoir > this.getAvoir()) {
            throw new Exception("miohatra ny avoirnao");
        } else {

            Encaissement e = new Encaissement();
            e.setIdEncaissement(this.getIdEncaissement());
            e.setAvoirPaie(this.getAvoirPaie() + montant);
            e.updateAvoirPaie();
            System.out.println("VOA UPDATE NY AVOIR "+e.getAvoirPaie());
        }
        compta.lookupComptaBeanLocal().makeAvoir(id_client, idvente, idventedetail, dt_time, montant);
    }
}
