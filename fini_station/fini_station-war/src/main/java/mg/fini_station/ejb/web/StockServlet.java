package mg.fini_station.ejb.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mg.fini_station.stock.Reservoir;

@WebServlet("/stock")
public class StockServlet extends HttpServlet {

    protected void prepDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        List<Reservoir> reservoirs=new Reservoir().getAll();
        req.setAttribute("reservoirs", reservoirs);
        req.getRequestDispatcher("pages/stocks.jsp").forward(req, resp);
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
        
    }
    
}
