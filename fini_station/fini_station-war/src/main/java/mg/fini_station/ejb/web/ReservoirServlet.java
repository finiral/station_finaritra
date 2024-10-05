package mg.fini_station.ejb.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.fini_station.stock.*;

@WebServlet("/reservoir")
public class ReservoirServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("error") != null) {
                req.setAttribute("etat", req.getParameter("error"));
            } else if (req.getParameter("success") != null) {
                req.setAttribute("etat", "Achat reservoir réussi");
            }
            List<Reservoir> ls_reservoir = new Reservoir().getAll();
            req.setAttribute("ls_reservoir", ls_reservoir);
            req.getRequestDispatcher("pages/reservoirForm.jsp").forward(req, resp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id_reservoir = Integer.parseInt(req.getParameter("id_reservoir"));
            String dt = req.getParameter("dt");
            double qte = Double.parseDouble(req.getParameter("qte"));
            Reservoir r = new Reservoir().getById(id_reservoir);
            r.acheter(dt,qte);
            resp.sendRedirect("reservoir?success=1");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resp.sendRedirect("reservoir?error=" + e.getMessage());

        }
    }

}
