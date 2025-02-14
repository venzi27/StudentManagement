package JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserInteface extends ConnectionSQL{
	
	private Students student;
	private Scanner scan = new Scanner(System.in);
    int selection = 0;
	public UserInteface(Students stud) {
		// TODO Auto-generated constructor stub
		this.student = stud;
	}
	
	
	public void runUI() throws InterruptedException {
		
		 while (selection != 4)  {
		    System.out.println(" -------------------------------- ");
	        System.out.println(" -         HELLO! " + student.getStudentName() + "         - " );
	        System.out.println(" -------------------------------- ");
	        System.out.println(" | 1. INFORMATION              | \n | 2. VIEW GRADES              |");
	        System.out.println(" | 3. SCHEDULE                 | ");
	        System.out.println(" | 4. EXIT                     |");
	        
	        System.out.print(" SELECT: ");
	        selection = scan.nextInt();
	        
	        switch (selection) {
			case 1: 
			   studentInformation();
			   break;
			case 2:
				studentViewGrade();
				break;
			case 3:
				studentSchedule();
				break;
			case 4:
				System.out.println(" EXITING.......");
		        Thread.sleep(4000);
		        System.out.println(" Thank you! Have a great day :) ");
				System.exit(0);
			default:
				throw new IllegalArgumentException("Unexpected value: " + selection);
			}
	        
	        
	}
	}
	
	private void studentInformation() {
		try {
	    PreparedStatement ps = connection().prepareStatement("SELECT * FROM STUDENTS WHERE Student_id = ? ");
	    ps.setInt(1, student.getId());
	    ResultSet rs = ps.executeQuery();
	    
	    while (rs.next()) {
	    	
	    	String name = rs.getString("Student_name");
	    	int age = rs.getInt("Student_age");
	    	String strand = rs.getString("Student_Section");
	    	int id = rs.getInt("Student_Id");
	    	
	    	System.out.println(" ------------------------- ");
	    	System.out.println(" Name: ".concat(name).toUpperCase());
	    	System.out.println(" Age: "+age);
	    	System.out.println(" Strand/Section: ".concat(strand).toUpperCase());
	    	System.out.println(" ID: "+id);
			
		}
	    		
		}catch (SQLException e) {
			System.out.println(" Error: "+e.getMessage());
		}
	}
	
    private void studentViewGrade() {
    	
    }
    
    private void studentSchedule() {
    	
    }
    
}