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
    protected void prepDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Reservoir> ls_reservoir = new Reservoir().getAll();
        req.setAttribute("ls_reservoir", ls_reservoir);
        req.getRequestDispatcher("reservoirPrelev.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            prepDispatch(req, resp);
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
            r.prelever(mesure, dt_time);
            req.setAttribute("etat", "Insertion Prelevement reussi ");
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
