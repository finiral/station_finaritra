package mg.fini_station.ejb.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.fini_station.stock.*;

@WebServlet("/reservoirPrelev")
public class PrelevementCuveServlet extends HttpServlet {

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
            req.getRequestDispatcher("reservoirPrelev.jsp").forward(req, resp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        req.getRequestDispatcher("pages/reservoirPrelev.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id_reservoir = Integer.parseInt(req.getParameter("id_reservoir"));
            String dt_time = req.getParameter("dt_time");
            String mesure = req.getParameter("mesure");
            Reservoir r = new Reservoir().getById(id_reservoir);
            r.prelever(mesure,dt_time);
            resp.sendRedirect("reservoirPrelev?success=1");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resp.sendRedirect("reservoirPrelev?error=" + e.getMessage());

        }
    }

}
