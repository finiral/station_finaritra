package mg.fini_station.pompes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.mvt.StockReservoir;
import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.Utilitaire;
import stock.MvtStock;
import stock.MvtStockFille;
import utilitaire.UtilDB;
import vente.Vente;
import vente.VenteDetails;

public class PrelevLub {
    private String table_name = "PrelevLub";
    private int idPrelevement;
    private int pompiste;
    private int pompe;
    private int lubrifiant;
    private double qte;
    private double pu;

    private String USER = "1060"; // id de l'utilisateur
    private String COMPTE = "712000";
    private String CATEGORIE = "CTG000103";
    private String MAGASIN = "POMP001";
    private String CAISSE = "CAI000239";
    private String TYPE_ENCAISSEMENT = "TE001";
    private String DEVISE = "AR";
    private String TYPEMVTSTOCK = "TPMVST000022";
    private double TAUX = 1.0;
    private String CLIENTDIVERS = "CLI000054";

    public int getLubrifiant() {
        return lubrifiant;
    }

    public void setLubrifiant(int lubrifiant) {
        this.lubrifiant = lubrifiant;
    }

    public double getQte() {
        return qte;
    }

    public void setQte(double qte) {
        this.qte = qte;
    }

    public double getPu() {
        return pu;
    }

    public void setPu(double pu) {
        this.pu = pu;
    }

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

    public int getPompiste() {
        return pompiste;
    }

    public void setPompiste(int pompiste) {
        this.pompiste = pompiste;
    }

    public int getPompe() {
        return pompe;
    }

    public void setPompe(int pompe) {
        this.pompe = pompe;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public void insert(Connection c) throws Exception {
        PreparedStatement s = null;
        try {
            s = c.prepareStatement(
                    "INSERT INTO " + this.table_name
                            + " (id_pompiste, id_pompe,id_lubrifiant,date_prelevlub,qte,pu) VALUES (?,?,?,?,?,?)");
            s.setInt(1, this.getPompiste());
            s.setInt(2, this.getPompe());
            s.setInt(3, this.getLubrifiant());
            s.setTimestamp(4, this.getDateTime());
            s.setDouble(5, this.getQte());
            s.setDouble(6, this.getPu());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public List<PrelevLub> getByIdPompeAndTypeDtDesc(Connection c, int idPompe, int idLubrifiant) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        List<PrelevLub> res = new ArrayList<>();
        try {
            // Modified query to order by date in descending order
            s = c.prepareStatement(
                    "SELECT * FROM " + this.table_name
                            + " WHERE id_pompe = ? and id_lubrifiant=? and date_prelevlub < ? ORDER BY date_prelevlub DESC");
            s.setInt(1, idPompe);
            s.setInt(2, idLubrifiant);
            s.setTimestamp(3, this.getDateTime());
            rs = s.executeQuery();
            while (rs.next()) {
                PrelevLub p = new PrelevLub();
                p.setIdPrelevement(rs.getInt("id_prelevlub"));
                p.setQte(rs.getDouble("qte"));
                p.setPu(rs.getDouble("pu"));
                p.setDateTime(rs.getTimestamp("date_PrelevLub"));
                p.setLubrifiant(rs.getInt("id_lubrifiant"));
                // Retrieve and set Pompiste and Pompe objects
                p.setPompiste(rs.getInt("id_pompiste"));
                p.setPompe(rs.getInt("id_pompe"));
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

    public boolean isStatePair(Connection c) throws Exception {
        List<PrelevLub> ls = this.getByIdPompeAndTypeDtDesc(c, this.getPompe(),
                this.getLubrifiant());
        return ls.size() % 2 != 0;
    }

    public double getDifferenceQte(Connection c) throws Exception {
        List<PrelevLub> ls = this.getByIdPompeAndTypeDtDesc(c, this.getPompe(),
                this.getLubrifiant());
        return ls.get(0).getQte() - this.getQte();
    }

    public double getDifferenceQteVola(Connection c) throws Exception {
        double res = this.getDifferenceQte(c) * this.getPu();
        return res;
    }

    public void prelever(Connection c, Connection c_mr) throws Exception {
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            c.setAutoCommit(false);
            this.insert(c);
            if (!this.isStatePair(c)) {
                // c'est une entree
            } else {
                // c'est une sortie
                /// VERIFICATION
                if (this.getDifferenceQte(c) <= 0) {
                    throw new Exception("Qte prelever (" + this.getQte() + ") trop haut");
                }
                c_mr = new UtilDB().GetConn();
                c_mr.setAutoCommit(false);
            ///VENTE
                Vente v = new Vente();
                v.setDesignation("LUBRIFIANT SELLING ");
                v.setDaty(Utilitaire.convertTimestampToDate(this.getDateTime()));
                v.setIdClient(CLIENTDIVERS);
                v.setIdMagasin(MAGASIN);
                v.setEstPrevu(1);
                v.createObject(USER, c_mr);
            /// MOUVEMENT STOCK
                MvtStock mvtStock = new MvtStock();
                mvtStock.setIdMagasin(v.getIdMagasin());
                mvtStock.setDaty(v.getDaty());
                mvtStock.setDesignation("STOCK SORTIE LUBRIFIANT");
                mvtStock.setIdTypeMvStock(TYPEMVTSTOCK);
                mvtStock.setIdVente(v.getId());
                mvtStock.createObject(USER, c_mr);
            ///VENTE DETAIL
                VenteDetails vd = new VenteDetails();
                vd.setIdProduit(new Lubrifiant().findById(c, this.getLubrifiant()).getIdCentrale());
                vd.setPuVente(this.getPu());
                vd.setQte(this.getDifferenceQte(c));
                vd.setPu(vd.getPuVente());
                vd.setIdVente(v.getId());
                vd.setTauxDeChange(TAUX);
                vd.setIdDevise(DEVISE);
                vd.setCompte(COMPTE);
                //// CHECK ETAT STOCK
                vd.CheckEtatStock(c_mr);
                vd.insertToTable(c_mr);
            ///STOCK FILLE
				MvtStockFille stock = new MvtStockFille();
				stock.setSortie(vd.getQte());
				stock.setIdProduit(vd.getIdProduit());
				stock.setEntree(0);
				stock.setIdVenteDetail(vd.getId());
				stock.setIdMvtStock(mvtStock.getId());
				stock.insertToTable(c_mr);
                /// VALIDATIONS
                v.validerObject(USER, c_mr);
                mvtStock.validerObject(USER, c_mr);
                v.payer(USER, c_mr);

            }
            c.commit();
            c_mr.commit();

        } catch (Exception e) {
            c.rollback();
            c_mr.rollback();
            throw e;
        }
    }

}
