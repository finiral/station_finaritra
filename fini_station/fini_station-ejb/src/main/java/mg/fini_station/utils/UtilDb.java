package mg.fini_station.utils;
import java.sql.*;

public class UtilDb{
    String drivername;
    String url;
    String user;
    String password;
    
    public Connection getConnection() throws Exception{
        Class.forName(this.getDrivername());
        Connection toSet=DriverManager.getConnection(this.getUrl(), this.getUser(),this.getPassword());
        return toSet;
    }
    
    
    //Constructeur//
    public UtilDb()
    {
    }
    public String getDrivername() {
        return drivername;
    }


    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
