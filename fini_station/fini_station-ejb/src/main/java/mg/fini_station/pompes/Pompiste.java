package mg.fini_station.pompes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mg.fini_station.utils.DbConn;

public class Pompiste {
    private String table_name = "Pompiste"; // Table name attribute
    private int idPompiste;
    private String nomPompiste;

    // Constructors
    public Pompiste(int idPompiste, String nomPompiste) {
        setIdPompiste(idPompiste);
        setNomPompiste(nomPompiste);
    }

    public Pompiste() {
    }

    // Getters and Setters
    public int getIdPompiste() {
        return idPompiste;
    }

    public void setIdPompiste(int idPompiste) {
        this.idPompiste = idPompiste;
    }

    public String getNomPompiste() {
        return nomPompiste;
    }

    public void setNomPompiste(String nomPompiste) {
        this.nomPompiste = nomPompiste;
    }

    // DAO Methods

    // Insert a Pompiste record
    public void insert() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("INSERT INTO " + this.table_name + " (nom_pompiste) VALUES (?)");
            s.setString(1, this.getNomPompiste());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Retrieve all Pompiste records
    public List<Pompiste> getAll() throws Exception {
        Connection c = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            return this.getAll(c);
        } catch (Exception e) {
            throw e;
        } finally {
            if (c != null) c.close();
        }
    }

    public List<Pompiste> getAll(Connection c) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            s = c.prepareStatement("SELECT * FROM " + this.table_name);
            rs = s.executeQuery();
            List<Pompiste> res = new ArrayList<>();
            while (rs.next()) {
                Pompiste p = new Pompiste();
                p.setIdPompiste(rs.getInt("id_pompiste"));
                p.setNomPompiste(rs.getString("nom_pompiste"));
                res.add(p);
            }
            return res;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (s != null) s.close();
        }
    }

    // Retrieve a Pompiste by ID
    public Pompiste getById(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("SELECT * FROM " + this.table_name + " WHERE id_pompiste = ?");
            s.setInt(1, id);
            rs = s.executeQuery();
            Pompiste p = null;
            if (rs.next()) {
                p = new Pompiste();
                p.setIdPompiste(rs.getInt("id_pompiste"));
                p.setNomPompiste(rs.getString("nom_pompiste"));
            }
            return p;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Update a Pompiste record
    public void update() throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("UPDATE " + this.table_name + " SET nom_pompiste = ? WHERE id_pompiste = ?");
            s.setString(1, this.getNomPompiste());
            s.setInt(2, this.getIdPompiste());
            s.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (s != null) s.close();
            if (c != null) c.close();
        }
    }

    // Delete a Pompiste record
    public void delete(int id) throws Exception {
        Connection c = null;
        PreparedStatement s = null;
        try {
            DbConn db = new DbConn();
            c = db.getConnection();
            s = c.prepareStatement("DELETE FROM " + this.table_name + " WHERE id_pompiste = ?");
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
