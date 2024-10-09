package mg.fini_station.stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ejb_fini.ComptaBeanClient;
import mg.cnaps.compta.ComptaSousEcriture;
import mg.fini_station.mvt.Depense;
import mg.fini_station.mvt.StockReservoir;
import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.OracleConn;
import mg.fini_station.utils.Utilitaire;

public class Reservoir {
    private String table_name = "Reservoir"; // Table name attribute
    private int idReservoir;
    private double qteMax;
    private double qteActuel;
    private Liquide typeLiquide;
    private MesureReservoir[] mesures;

    // Constructors
    public Reservoir(int idReservoir, double qteMax, double qteActuel, Liquide typeLiquide,
            MesureReservoir[] mesureReservoirs) {
        setIdReservoir(idReservoir);
        setQteMax(qteMax);
        setQteActuel(qteActuel);
        setTypeLiquide(typeLiquide);
        setMesures(mesureReservoirs);
    }

    public Reservoir() {
    }

    public MesureReservoir[] getMesures(Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        List<MesureReservoir> mesures = new ArrayList<>();
    
        try {
            // Prépare une requête pour récupérer toutes les mesures associées à un réservoir spécifique
            String query = "SELECT id_mesurereservoir, mesurelongueur, mesurevolume FROM MesureReservoir WHERE id_reservoir = ?";
            s = c.prepareStatement(query);
            s.setInt(1, this.getIdReservoir()); // Utilise l'ID du réservoir courant
    
            // Exécute la requête
            rs = s.executeQuery();
    
            // Parcours des résultats et création des objets MesureReservoir
            while (rs.next()) {
                MesureReservoir mesure = new MesureReservoir();
                mesure.setIdMesureReservoir(rs.getInt("id_mesurereservoir"));
                mesure.setMesureLongueur(rs.getDouble("mesurelongueur")); // Longueur mesurée
                mesure.setMesureVolume(rs.getDouble("mesurevolume"));     // Volume mesuré
    
                // Ajoute l'objet à la liste
                mesures.add(mesure);
            }
    
            // Conversion de la liste en tableau de MesureReservoir[]
            return mesures.toArray(new MesureReservoir[0]);
    
        } catch (Exception e) {
            throw e;
        } finally {
            // Ferme les ressources
            if (rs != null) rs.close();
            if (s != null) s.close();
        }
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

    public double getQteActuel() throws Exception {
        if(this.qteActuel!=0 && this.qteActuel>0){
            return qteActuel;
        }
        Connection c=new DbConn().getConnection();
        this.setQteActuel(this.getQteActuel(c));
        c.close();
        return this.qteActuel;

    }

    public double getQteActuel(Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            // Query to sum the quantities in StockReservoir for the specific Reservoir
            s = c.prepareStatement("SELECT SUM(qte_stock) AS totalStock FROM StockReservoir WHERE id_reservoir = ?");
            s.setInt(1, this.getIdReservoir());
            rs = s.executeQuery();
            double qteActuel=0;
            if (rs.next()) {
                // Set qteActuel based on the sum of all stock quantities for this reservoir
                qteActuel = rs.getDouble("totalStock");
            }
            return qteActuel;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }
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

    public MesureReservoir[] getMesures() throws Exception {
        if(this.mesures==null){
            Connection c=new DbConn().getConnection();
            this.setMesures(this.getMesures(c));
            c.close();
        }
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
            if (s != null)
                s.close();
            if (c != null)
                c.close();
        }
    }

    // Retrieve all Reservoir records
    public List<Reservoir> getAll(Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            List<Reservoir> res = new ArrayList<>();
            while (rs.next()) {
                Reservoir r = new Reservoir();
                r.setIdReservoir(rs.getInt("id_reservoir"));
                r.setQteMax(rs.getDouble("qte_max"));
                r.setTypeLiquide(new Liquide().getById(rs.getInt("id_liquide"))); // Assuming Liquide has a getById
                                                                                  // method
                res.add(r);
            }
            return res;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }
    }
    public List<Reservoir> getAll() throws Exception {
        Connection c = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            return this.getAll(c);
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null)
                c.close();
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
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
            if (c != null)
                c.close();
        }
    }

    // Update a Reservoir record
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement(
                    "UPDATE " + this.table_name + " SET qte_max = ?, id_liquide = ? WHERE id_reservoir = ?");
            s.setDouble(1, this.getQteMax());
            s.setInt(2, this.getTypeLiquide().getIdLiquide());
            s.setInt(3, this.getIdReservoir());
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
            if (s != null)
                s.close();
            if (c != null)
                c.close();
        }
    }

    public void acheter(String dt, double qte) throws Exception {
        Connection c = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            c.setAutoCommit(false);
            // Update le stock
            StockReservoir stock = new StockReservoir(-87, dt, qte, this);
            stock.insert(c);
            // Ajouter en tant que dépense
            double montant_depense = this.getTypeLiquide().getPrixUnitaireAchat() * qte;
            Depense d = new Depense(-89, qte, montant_depense, dt, "Achat de " + this.getTypeLiquide().getNomLiquide());
            d.insert(c);
            // Ecriture vers centrale
            ComptaBeanClient compta = new ComptaBeanClient();
            // Création sous ecriture 601
            ComptaSousEcriture cse = new ComptaSousEcriture();
            cse.setCompte("6070000000000");
            cse.setLibellePiece("Achat de " + this.getTypeLiquide().getNomLiquide());
            cse.setRemarque("Achat de " + this.getTypeLiquide().getNomLiquide());
            cse.setJournal("COMP000039");
            cse.setDebit(montant_depense);
            cse.setCredit(0);
            cse.setDaty(Utilitaire.htmlTimestampToSqlDate(dt));
            compta.lookupComptaBeanLocal().ecrireSousEcriture(new OracleConn().getConnection(), cse);
            // Création sous ecriture 401
            ComptaSousEcriture four = new ComptaSousEcriture();
            four.setCompte("4010000000000");
            four.setLibellePiece("Achat de " + this.getTypeLiquide().getNomLiquide());
            four.setRemarque("Achat de " + this.getTypeLiquide().getNomLiquide());
            four.setJournal("COMP000039");
            four.setDebit(0);
            four.setCredit(montant_depense);
            four.setDaty(Utilitaire.htmlTimestampToSqlDate(dt));
            compta.lookupComptaBeanLocal().ecrireSousEcriture(new OracleConn().getConnection(), four);
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
