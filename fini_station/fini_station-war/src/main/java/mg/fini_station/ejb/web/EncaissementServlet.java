package mg.fini_station.ejb.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.fini_station.mvt.Encaissement;
import mg.fini_station.pompes.Prelevement;

@WebServlet("/encaissement")
public class EncaissementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if(req.getParameter("error")!=null){
                req.setAttribute("etat",req.getParameter("error"));
            }
            else if(req.getParameter("success")!=null){
                req.setAttribute("etat","Insertion prelevemenet réussi");
            }
            req.setAttribute("prelevements", new Prelevement().getAll());
            req.getRequestDispatcher("pages/encaissementForm.jsp").forward(req, resp);
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
            Prelevement p=new Prelevement();
            p.setIdPrelevement(id_prelevement);
            new Encaissement(-89, montant,p, dt_time).encaisser();
            resp.sendRedirect("encaissement?success=1");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resp.sendRedirect("encaissement?error="+e.getMessage());

        }
    }

}
