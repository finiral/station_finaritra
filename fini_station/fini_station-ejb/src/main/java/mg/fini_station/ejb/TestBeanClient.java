package mg.fini_station.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class TestBeanClient {
    public TestBeanLocal lookupTestBean() throws Exception{
         // Set up the environment for creating the initial context
            Properties jndiProperties = new Properties();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

            // Create the initial context
            Context context = new InitialContext(jndiProperties);

            // Perform the lookup for the remote EJB
            return (TestBeanLocal) context.lookup(
                    "java:global/fini_station-ear/fini_station-ejb/TestBean!mg.fini_station.ejb.TestBeanLocal");
    }

    public TestBeanLocal lookupTestBeanLocal() throws Exception{
        Context c = new InitialContext();
            return (TestBeanLocal) c.lookup("java:global/fini_station-ear/fini_station-ejb/TestBean!mg.fini_station.ejb.TestBeanLocal");
   }
}
