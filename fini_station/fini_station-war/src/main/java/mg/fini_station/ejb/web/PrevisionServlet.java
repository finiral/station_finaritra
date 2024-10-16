package mg.fini_station.ejb.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.fini_station.mvt.Prevision;
import mg.fini_station.stock.Reservoir;
import mg.fini_station.utils.Utilitaire;

@WebServlet("/prevision")
public class PrevisionServlet extends HttpServlet {
    protected void prepDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getRequestDispatcher("pages/prevision.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Prevision> previsions = new Prevision().getAll();
            req.setAttribute("previsions", previsions);
            prepDispatch(req, resp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String dt=req.getParameter("dt");
            List<Prevision> previsions = new Prevision().getAllDt(dt);
            req.setAttribute("previsions", previsions);
            prepDispatch(req, resp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            req.setAttribute("etat", e.getMessage());
            try {
                prepDispatch(req, resp);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

}
