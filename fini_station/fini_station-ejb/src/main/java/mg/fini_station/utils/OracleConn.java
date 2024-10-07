package mg.fini_station.utils;

public class OracleConn extends UtilDb{

    public OracleConn(){
        
       setUrl("jdbc:oracle:thin:@//localhost:1521/ORCL");
       setPassword("station");
       setUser("station");
       setDrivername("oracle.jdbc.driver.OracleDriver");
    }
}