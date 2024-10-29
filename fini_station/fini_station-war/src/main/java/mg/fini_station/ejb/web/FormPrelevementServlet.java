package mg.fini_station.ejb.web;
import java.sql.Connection;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.fini_station.pompes.Pompe;
import mg.fini_station.pompes.Pompiste;
import mg.fini_station.pompes.Prelevement;
import mg.fini_station.utils.DbConn;

@WebServlet("/prelevement")
public class FormPrelevementServlet extends HttpServlet {

    protected void prepDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Connection c=new DbConn().getConnection();
        List<Prelevement> ls_prelevement=new Prelevement().getAll(c);
        List<Pompiste> ls_pompiste = new Pompiste().findAll(c);
        List<Pompe> ls_pompe = new Pompe().findAll(c);
        c.close();
        req.setAttribute("prelevements", ls_prelevement);
        req.setAttribute("pompistes", ls_pompiste);
        req.setAttribute("pompes", ls_pompe);
        req.getRequestDispatcher("pages/prelevementForm.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            prepDispatch(req, resp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id_pompiste = Integer.parseInt(req.getParameter("id_pompiste"));
            Pompiste pompiste = new Pompiste().findById(id_pompiste);
            int id_pompe = Integer.parseInt(req.getParameter("id_pompe"));
            mg.fini_station.pompes.Pompe pompe = new Pompe().findById(id_pompe);
            String dt_time = req.getParameter("dt_time");
            double montant = Double.parseDouble(req.getParameter("montant"));
            Prelevement p = new Prelevement(-98, pompiste, pompe, montant, dt_time);
            p.prelever();
            req.setAttribute("etat", "Insertion prelevemenet réussi");
            prepDispatch(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(FormPrelevementServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("etat", ex.getMessage());
            try {
                prepDispatch(req, resp);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
