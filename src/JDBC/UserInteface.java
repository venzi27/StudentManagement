package JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
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
			 try {
		    System.out.println(" -------------------------------- ");
	        System.out.println(" -         HELLO! " + student.getStudentName() + "      - " );
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
				System.out.println(" Invalid input ");
			}
	        
			 }catch (InputMismatchException e) {
			     System.out.println(" Error: Invalid Input");
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
    	
    	try { 
    	   PreparedStatement ps = connection().prepareStatement("SELECT * FROM Students INNER JOIN grades ON Students.student_id = grades.student_id WHERE students.Student_id = ?");
    	   ps.setInt(1, student.getId());
    	   ResultSet rs = ps.executeQuery();
    	   
    	   if (!rs.next()) {
    		   System.out.println(" Grades not found : (");
    	   }
    	 
    	   
    	 do {
		         String name = rs.getString("Student_name");
		         String strand = rs.getString("Student_Section");
				 int ids = rs.getInt("Students.student_id");		 
				 int MATH = rs.getInt("MATH");
				 int SCI = rs.getInt("SCIENCE");
				 int fil = rs.getInt("Filipino");
				 int english = rs.getInt("English");
				 int Research = rs.getInt("RESEARCH");
				 int stats = rs.getInt("Statistics");
				 int perdev = rs.getInt("PERSONAL_DEVELOPMENT");
				 int chemis = rs.getInt("CHEMISTRY");
				 
				 int avg = (MATH + SCI + fil + english + Research + stats + perdev + chemis) / 8;
				 
				 String MARK = switch (avg) {
				   case 90,91,92,93,94,95,96,97,98,99,100 -> "Excellent";
				   case 89,88,87,86,85 -> "Very Good";
				   case 84,83,82,81,80 -> "Good";
				   case 79,78,77,76,75 -> "Passed";
				   default -> avg > 100 || avg < 75 ? "Failed" : "Invalid Input";
				 };
				 				                       
				 System.out.println("_______________________________________");
				 System.out.println("             STUDENT GRADE             ");
				 System.out.println("_______________________________________");
				 System.out.println(" Student Name: "+name);
				 System.out.println(" Student Strand: "+strand);
				 System.out.println(" Student ID: "+ids);
				 System.out.println("_______________________________________");
				 System.out.println(" MATH = "+MATH);
				 System.out.println(" SCIENCE = "+SCI);
				 System.out.println(" FILIPINO = "+fil);
				 System.out.println(" ENGLISH = "+english);
				 System.out.println(" RESEARCH = "+Research);
				 System.out.println(" STATISTICS = "+stats);
				 System.out.println(" Personal Development = "+perdev);
				 System.out.println(" CHEMISTRY = "+chemis);
				 System.out.println(" AVERAGE: "+avg+" - " + MARK);  
    		   
    	   }while (rs.next()); 
    	   
    	  }catch (SQLException e) {
		      System.out.println(" Error: "+e.getMessage());
		 }
    	
    }
    
    private void studentSchedule() {
        try (PreparedStatement pss = connection().prepareStatement("SELECT Student_Section FROM students WHERE Student_id = ?")) {
            pss.setInt(1, student.getId());
            ResultSet rs = pss.executeQuery();

            String section = null;
            if (rs.next()) {
                section = rs.getString("Student_Section"); // e.g., "ICT"
            }

            if (section == null) {
                System.out.println("No section found for student.");
                return;
            }

            student.setCourse(section); // Set the course, e.g., "ICT"

            // Select only the column that matches the student's section
            String sql = "SELECT " + student.getCourse() + " FROM sched"; 
            	
            try (PreparedStatement ps = connection().prepareStatement(sql);
                 ResultSet set = ps.executeQuery()) {

                List<String> scheduleList = new ArrayList<>();

                // Store schedules in a list
                while (set.next()) {
                    scheduleList.add(set.getString(student.getCourse()));
                }

                // Define correct day order
                List<String> dayOrder = Arrays.asList("MON", "TUES", "WED", "THUR", "FRI");

                // Custom sorting based on the day
                scheduleList.sort(Comparator.comparing(s -> {
                    for (int i = 0; i < dayOrder.size(); i++) {
                        if (s.startsWith(dayOrder.get(i))) {
                            return i; // Return index of the day in order
                        }
                    }
                    return Integer.MAX_VALUE; // Put unknown days at the end
                }));

                // Print sorted schedule
                for (String schedule : scheduleList) {
                    System.out.println(schedule);
                 }
             }

          } catch (SQLException e) {
            e.printStackTrace();
         }
    }
    
}