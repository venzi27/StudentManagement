package JDBC;


public class Main {

    public static void main(String[] args) {
       
    	AdminInterface adm = new AdminInterface();
    	
    	if (adm.connection() != null) {
    		System.out.println(" Connect Success");
    		adm.run();
    	}
    	
    	else {
			System.out.println("Failed");
    }
    	
  
    }
}
