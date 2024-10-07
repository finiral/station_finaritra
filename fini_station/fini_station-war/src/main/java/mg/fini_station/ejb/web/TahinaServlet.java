package mg.fini_station.ejb.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet which performs a JNDI lookup for a stateless EJB
 */
@WebServlet("/tay")
public class TahinaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            /* writer.println(UserEJBClient.lookupUserEJBBeanLocal().getMaxId("COMPTA_ECRITURE")); */
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
