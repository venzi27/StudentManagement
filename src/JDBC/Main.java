package JDBC;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main extends ConnectionSQL{
	
	private static Scanner scan = new Scanner(System.in);
	private static Students stud = new Students();
	private static AdminInterface ad = null;
	private static UserInteface user = null;
	private static String name;
	private static int id;
    private static String url;
    private static String username;
    private static String pass;
	private static Properties prop = new Properties();
	private static boolean Running = true;
    
    
    public static void main(String[] args) throws InterruptedException, SQLException {
    	
    while (Running) {
        config();
    	System.out.println(" ------------------------- ");
    	System.out.println(" -  STUDENT MANAGEMENT   - ");
    	System.out.println(" ------------------------- ");
    	Thread.sleep(1000);
    	System.out.println(" Enter following credentials. ");
    	System.out.println();
    	System.out.print(" Enter Name: ");
        name = scan.nextLine();
    	
        while (name.isEmpty()) {
        	System.out.print(" Name is empty. Enter Name: ");
        	name = scan.nextLine();
        	continue;
        }
        
        //check if name exist
        while (!NameExist(name)) {
        	System.out.print(" Record Not Found. Enter a name again: ");
        	name = scan.nextLine();
        	continue;
        }
        
        stud.setStudent(name);
        System.out.print(" Enter ID: ");
        id = scan.nextInt();
        scan.nextLine();
        
        //check if id exist
        if (!idexist(id)) {
        	System.out.println(" ID DOESN'T EXIST ");
        	continue;
        }
        
        else if (prop.getProperty("admin.user").equals(name) && prop.getProperty("admin.id").equals(String.valueOf(id))) {
        	ad = new AdminInterface();
        	ad.run();
        	break;
        }
        
        else {
        	stud.setID(id);
        	user = new UserInteface(stud);
        	user.runUI();
        	break;
        	
		}
      }
    }
    
    private static boolean NameExist(String name) {
    	try {
    	   PreparedStatement ps = con().prepareStatement("SELECT * FROM Students WHERE Student_name = ?");
    	   ps.setString(1, name);
    	   ResultSet rs = ps.executeQuery();
    
           return rs.next() && rs.getString(1).contains(name);
    	
    	}catch (SQLException e) {
			return false;
			
		}
    }
   
    
    private static boolean idexist(int id)  {
    	
    	try {		
    	  PreparedStatement ps = con().prepareStatement("SELECT * FROM Students WHERE Student_id = ?");
    	  ps.setInt(1, id);
    	  ResultSet rs = ps.executeQuery();
    	
    	  return rs.next() && rs.getString(1).contains(stud.getStudentName());
    	
    	    }catch (SQLException e) {
    		   System.out.println(" Error: Cannot match ID");
			   return false;
		}
    	
    }
   
    public static void config() {
    	String path = "src/resources/config.properties";
    	
    	try (FileInputStream inp = new FileInputStream(path)){
    		 prop.load(inp);
		     url = prop.getProperty("db.url");
		     username = prop.getProperty("db.user");
		     pass = prop.getProperty("db.password");
		    	
		}catch (Exception e) {
			throw new IllegalAccessError();
	   }
    }
    
    
    private static Connection con() {
    	
    	try {
    		return DriverManager.getConnection(url,username,pass);
    	}catch (SQLException e) {
		  return null;
		  
		}
    }
}