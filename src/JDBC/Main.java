package JDBC;


public class Main {

    public static void main(String[] args) {
       
    	 AdminInterface adm = new AdminInterface();
    	
    	   if (adm.connection() != null) {
    		  adm.run();
    	     }else {
			  System.out.println("Failed To Connect Database");
          }	
  
    }
}
