package mg.fini_station.ejb.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.fini_station.ejb.TestBeanClient;
import mg.fini_station.ejb.TestBeanLocal;

/**
 * A servlet which performs a JNDI lookup for a stateless EJB
 */
@WebServlet("/hello")
public class EJBServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();

        try {
            TestBeanLocal statelessBean=new TestBeanClient().lookupTestBeanLocal();
            String message = statelessBean.hello();

            // Output the message to the servlet response
            writer.println(message);

        } catch (Exception e) {
            writer.println("Error performing JNDI lookup: " + e.getMessage());
            e.printStackTrace(writer);
        }
    }
}
