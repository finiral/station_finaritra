package mg.fini_station.ejb.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.List;

import mg.fini_station.mvt.Encaissement;
import mg.fini_station.pompes.Pompe;
import mg.fini_station.pompes.Pompiste;
import mg.fini_station.pompes.Prelevement;

@WebServlet("/encaissement")
public class EncaissementServlet extends HttpServlet {
    protected void prepDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("prelevements", new Prelevement().getAll());
        req.getRequestDispatcher("pages/encaissementForm.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            prepDispatch(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(EncaissementServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id_prelevement = Integer.parseInt(req.getParameter("id_prelevement"));
            String dt_time = req.getParameter("dt_time");
            double montant = Double.parseDouble(req.getParameter("montant"));
            Prelevement p = new Prelevement().getById(id_prelevement);
            new Encaissement(-89, montant, p, dt_time).encaisser();
            req.setAttribute("etat","Encaissement reussi !");
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
