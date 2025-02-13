package JDBC;

import java.io.FileInputStream;
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
        
        // TEMPORARY: Try absolute path (replace with your actual path)
        String path = "src/resources/config.properties"; 
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
            this.url = properties.getProperty("db.url");
            this.user = properties.getProperty("db.user");
            this.pass = properties.getProperty("db.password");
            System.out.println("✅ Config loaded successfully!");
        } catch (IOException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
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
