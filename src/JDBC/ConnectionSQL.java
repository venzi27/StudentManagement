package JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSQL {
    
    private String url;
    private String user;
    private String pass;

    public ConnectionSQL() {
        loadConfig();
    }

    private void loadConfig() {
       Properties properties = new Properties();
       
       String path = "src/resources/config.properties";
       
       try (FileInputStream inp = new FileInputStream(path)) {
    	   properties.load(inp);
    	   this.url = properties.getProperty("db.url");
    	   this.user = properties.getProperty("db.user");
    	   this.pass = properties.getProperty("db.password");
       } catch (FileNotFoundException e) {
		e.printStackTrace();
	   } catch (IOException e) {
		e.printStackTrace();
	}
		
    }

    public Connection connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
