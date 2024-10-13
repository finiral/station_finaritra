package mg.fini_station.ejb.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.Client;
import ejb_fini.ComptaBeanClient;
import ejb_fini.ComptaBeanLocal;
import mg.fini_station.mvt.Encaissement;
import mg.fini_station.pompes.Prelevement;
import mg.fini_station.utils.DbConn;
import mg.fini_station.utils.OracleConn;

@WebServlet("/detailEncaissement")
public class DetailEncaissementServlet extends HttpServlet {
    protected void prepDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id=Integer.parseInt(req.getParameter("id"));
        Encaissement e=new Encaissement().getById(id);
        Connection c=new OracleConn().getConnection();
        List<Client> ls_client=new ComptaBeanClient().lookupComptaBeanLocal().getAllClient(c);
        c.close();
        req.setAttribute("encaissement", e);
        req.setAttribute("clients", ls_client);
        req.getRequestDispatcher("pages/detailEncaissement.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            prepDispatch(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(DetailEncaissementServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id_encaissement=Integer.parseInt(req.getParameter("id_encaissement"));
            Date dt_time = Date.valueOf(req.getParameter("dt"));
            String id_client = req.getParameter("id_client");
            double montant = Double.parseDouble(req.getParameter("montant"));
            Encaissement e=new Encaissement().getById(id_encaissement);
            e.avoir(id_client,dt_time,montant);
            req.setAttribute("etat","Avoir reussi !");
            prepDispatch(req, resp);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                req.setAttribute("etat", e.getMessage());
                prepDispatch(req, resp);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
    }

}
