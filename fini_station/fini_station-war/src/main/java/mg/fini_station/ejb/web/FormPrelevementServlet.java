package mg.fini_station.ejb.web;

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

@WebServlet("/prelevement")
public class FormPrelevementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if(req.getParameter("error")!=null){
                req.setAttribute("etat",req.getParameter("error"));
            }
            else if(req.getParameter("sucess")!=null){
                req.setAttribute("etat","Insertion prelevemenet réussi");
            }
            List<Pompiste> ls_pompiste = new Pompiste().getAll();
            List<Pompe> ls_pompe = new Pompe().getAll();
            req.setAttribute("pompistes", ls_pompiste);
            req.setAttribute("pompes", ls_pompe);
            req.getRequestDispatcher("pages/prelevementForm.jsp").forward(req, resp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id_pompiste=Integer.parseInt(req.getParameter("id_pompiste"));
        Pompiste pompiste=new Pompiste();
        pompiste.setIdPompiste(id_pompiste);
        int id_pompe=Integer.parseInt(req.getParameter("id_pompe"));
        mg.fini_station.pompes.Pompe pompe=new Pompe();
        pompe.setIdPompe(id_pompe);
        String dt_time=req.getParameter("dt_time");
        double montant=Double.parseDouble(req.getParameter("montant"));
        Prelevement p=new Prelevement(-98,pompiste,pompe,montant,dt_time);
        try {
            p.insert();
            resp.sendRedirect("prelevement?success=1");
        } catch (Exception ex) {
            Logger.getLogger(FormPrelevementServlet.class.getName()).log(Level.SEVERE, null, ex);
            resp.sendRedirect("prelevement?error="+ex.getMessage());
        }
    }

}
