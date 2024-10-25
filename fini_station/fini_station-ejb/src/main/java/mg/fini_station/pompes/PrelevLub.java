package mg.fini_station.pompes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.mvt.StockReservoir;
import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.Utilitaire;
import stock.MvtStockFille;
import utilitaire.UtilDB;
import vente.Vente;
import vente.VenteDetails;

public class PrelevLub {
    private String table_name = "PrelevLub";
    private int idPrelevement;
    private Pompiste pompiste;
    private Pompe pompe;
    private Lubrifiant lubrifiant;
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

    public Lubrifiant getLubrifiant() {
        return lubrifiant;
    }

    public void setLubrifiant(Lubrifiant lubrifiant) {
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

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public void setDateTime(String dateTime) {
        this.setDateTime(Utilitaire.parseToTimestamp(dateTime)); // Assuming Utilitaire handles parsing
    }

    public void insert(Connection c) throws Exception {
        PreparedStatement s = null;
        try {
            s = c.prepareStatement(
                    "INSERT INTO " + this.table_name
                            + " (id_pompiste, id_pompe,id_lubrifiant,date_prelevlub,qte,pu) VALUES (?,?,?,?)");
            s.setInt(1, this.getPompiste().getIdPompiste());
            s.setInt(2, this.getPompe().getIdPompe());
            s.setInt(3, this.getLubrifiant().getId());
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
                p.setLubrifiant(new Lubrifiant().getById(c, rs.getInt("id_lubrifiant")));
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

    public boolean isStatePair(Connection c) throws Exception {
        List<PrelevLub> ls = this.getByIdPompeAndTypeDtDesc(c, this.getPompe().getIdPompe(),
                this.getLubrifiant().getId());
        return ls.size() % 2 != 0;
    }

    public double getDifferenceQte(Connection c) throws Exception {
        List<PrelevLub> ls = this.getByIdPompeAndTypeDtDesc(c, this.getPompe().getIdPompe(),
                this.getLubrifiant().getId());
        return this.getQte() - ls.get(0).getQte();
    }

    public double getDifferenceQteVola(Connection c) throws Exception {
        double res = this.getDifferenceQte(c) * this.getPu();
        return res;
    }

    public void prelever() throws Exception {
        Connection c = null;
        Connection c_mr = null;
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
                if (this.getDifferenceQte(c_mr) < 0) {
                    throw new Exception("Qte prelever (" + this.getQte() + ") trop haut");
                }
                c_mr = new UtilDB().GetConn();
                c_mr.setAutoCommit(false);
                Vente v = new Vente();
                v.setDesignation("LUBRIFIANT SELLING ");
                v.setDaty(Utilitaire.convertTimestampToDate(this.getDateTime()));
                v.setIdClient(CLIENTDIVERS);
                v.setIdMagasin(MAGASIN);
                v.setEstPrevu(1);
                v.createObject(USER, c_mr);
                VenteDetails vd = new VenteDetails();
                vd.setIdProduit(this.getLubrifiant().getIdCentrale());
                vd.setPuVente(this.getPu());
                vd.setQte(this.getDifferenceQte(c));
                vd.setPu(vd.getPuVente());
                vd.setIdVente(v.getId());
                vd.setTauxDeChange(TAUX);
                vd.setIdDevise(DEVISE);
                vd.setCompte(COMPTE);
                vd.insertToTable(c);
                v.validerObject(USER, c);
                v.payer(USER, c);

            }
            c.commit();

        } catch (Exception e) {
            c.rollback();
            c_mr.rollback();
            throw e;
        } finally {
            if (c != null)
                c.close();
            if (c_mr != null)
                c_mr.close();
        }
    }

}
