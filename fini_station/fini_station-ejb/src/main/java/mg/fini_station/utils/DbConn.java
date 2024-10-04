package mg.fini_station.utils;

public class DbConn extends UtilDb{

    public DbConn(){
        setUrl("jdbc:postgresql://localhost:5432/station_perso");
        setPassword("postgres");
        setUser("postgres");
        setDrivername("org.postgresql.Driver");
        
//        setUrl("jdbc:oracle:thin:@//localhost:1521/ORCL");;
//        setPassword("station_perso");
//        setUser("station_perso");
//        setDrivername("oracle.jdbc.driver.OracleDriver");
    }
}