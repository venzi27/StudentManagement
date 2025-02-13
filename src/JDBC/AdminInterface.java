package JDBC;

import java.sql.*;
import java.util.Scanner;

public class AdminInterface extends ConnectionSQL{


	//VARIABLE
    private Scanner scan = new Scanner(System.in);
    private Students stud = new Students();
    ConnectionSQL sql = new ConnectionSQL();
    private int choices = 0;

    
    //RUNNABLE AI
     public void run() {
    	while (choices != 8) {
    	    try {
    	        Thread.sleep(3000);
    	        System.out.println(" -------------------------------- ");
    	        System.out.println("  -         ADMIN EDIT          - ");
    	        System.out.println(" -------------------------------- ");
    	        System.out.println(" | 1. ADD STUDENTS INFORMATION | \n | 2. ADD GRADES               |");
    	        System.out.println(" | 3. VIEW STUDENTS            | ");
    	        System.out.println(" | 4. SEARCH STUDENT           |");
    	        System.out.println(" | 5. UPDATE GRADES            | ");
    	        System.out.println(" | 6. DELETE STUDENT           | ");
    	        System.out.println(" | 7. VIEW GRADES              |" ); 
    	        System.out.println(" | 8. EXIT                     |");
    	        System.out.println(" --------------------------------- ");
    	      

    	        System.out.print(" | SELECT: ");
    	        String input = scan.nextLine().trim();
                // ✅ Check if input is a number from 1 to 7
                if (input.matches("[1-8]")) {
                    choices = Integer.parseInt(input);
                } else {
                    System.out.println(" Invalid input! Please enter a number from 1-8.");
                    continue;  // 🔄 Restart loop
                }
    	        // ✅ Process valid choices
    	        switch (choices) {
    	            case 1:
    	                addStudent();
    	                break;
    	            case 2:
    	                addGrades();
    	                break;
    	            case 3:
    	                viewStudents();
    	                break;
    	            case 4:
    	                searchStudent();
    	                break;
    	            case 5:
    	                updateGrades();
    	                break;
    	            case 6:
    	                deleteStudents();
    	                break;
    	            case 7:
    	            	viewgrades();
    	            	break;
    	            case 8:
    	                System.out.println(" Exiting... Goodbye!");
    	                break;
    	            default:
    	                System.out.println(" Invalid choice! Please enter a number from 1-7.");
    	        }

    	    } catch (InterruptedException e) {
    	        System.out.println(" Unexpected error: " + e.getMessage());
    	    }
    	}
    }

     //SEARCH GRADES AND VIEW GRADES OF STUDENTS
     private void viewgrades() {
		try {
			 PreparedStatement ps = connection().prepareStatement("SELECT Student_name,students.Student_id,Student_section,\r\n"
			 		+ "MATH,SCIENCE,FILIPINO,ENGLISH,RESEARCH,STATISTICS,PERSONAL_DEVELOPMENT,CHEMISTRY \r\n"
			 		+ "FROM students left join grades  ON students.Student_Id = grades.Student_id\r\n"
			 		+ "WHERE students.Student_Id = ?");
			 Statement stmnt = connection().createStatement();
	    	 ResultSet check = stmnt.executeQuery("SELECT * FROM students ORDER BY student_name");

	                if (!check.next()) {
	                    System.out.println(" NO STUDENT RECORD FOUND.");
	                    return;
	                }
			 
			 System.out.print(" Enter Student ID: ");
			 String id = scan.nextLine();
			 int parseId = Integer.parseInt(id);
			 
			 
			 if (!idExists(parseId)) {
				 System.out.println(" ID DOESN'T EXIST "); 
				 return;
			 }
			 
			 else {
			 ps.setInt(1, parseId);
			 ResultSet rs = ps.executeQuery();
			 
			 while (rs.next()){
				 String name = rs.getString("Student_name");
				 int ids = rs.getInt("Students.student_id");
				 String sec = rs.getString("Student_Section");
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
				 				                       
				 System.out.println();
				 System.out.println("             STUDENT GRADE             ");
				 System.out.println("_______________________________________");
				 System.out.println(" Student Name: "+name);
				 System.out.println(" Student ID: "+ids);
				 System.out.println(" Section: "+sec);
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
				  
				 
			   }
			 }
			
		} catch (SQLException e) {
			System.out.println(" Error: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println(" Error: "+e.getMessage());
		}
    		   
     }
     
   //DELETE STUDENTS
	private void deleteStudents() {
    	
    	try {
    		
    		String deleteStudents = "DELETE FROM students WHERE student_id = ?";
    		String deleteGrades = "DELETE FROM grades WHERE student_id = ? ";
    	    PreparedStatement ps = connection().prepareStatement(deleteStudents);
    	    PreparedStatement grdel = connection().prepareStatement(deleteGrades);
    	    Statement stmnt = connection().createStatement();
    	    
    	    ResultSet rs = stmnt.executeQuery("SELECT * FROM students ORDER BY student_name");

                if (!rs.next()) {
                    System.out.println(" NO STUDENT RECORD FOUND.");
                    return;
                }
    	    
    	    System.out.print(" Enter Student ID: ");
    	    String id = scan.nextLine();
    	    int idInt = Integer.parseInt(id);
    	    
    	    if (!idExists(idInt)) {
    	    	throw new SQLException(" ID Doesn't exist :( ");
    	    }
    	    
    	    grdel.setInt(1, idInt);
    	    int gr = grdel.executeUpdate();
    	    ps.setInt(1, idInt);
    	    int exe = ps.executeUpdate();
    	   
    	    
    	    if (exe > 0) {
    	    	System.out.println(" Successfully Deleted: "+idInt+" - "+gr);
    	    }else {
				System.out.println(" Cannot Delete: "+idInt);
			}
			
		
    	}catch (SQLException e) {
			System.out.println(" Error: "+e.getMessage());
    	}catch (NumberFormatException e) {
			System.out.println(" Error: "+ e.getMessage());
		}
	}
	
    //UPDATE GRADES OF STUDENTS
	 private void updateGrades() {
		
		try { 
		PreparedStatement check = connection().prepareStatement("SELECT * FROM grades WHERE student_id = ? ");
		
		String[] subjects = {"Math", "Science", "Filipino",
		                     "English", "Research", "Statistics",
		                     "Personal_Development", "Chemistry"};
		boolean validSub = false;
		System.out.print(" Enter ID of Student: ");
		String id = scan.nextLine();
		int parseid = Integer.parseInt(id);
		
		check.setInt(1, parseid);
		ResultSet rs = check.executeQuery();
		
		if (!idExists(parseid)) {
			System.out.println(" ID DOESN'T EXIST");
			
		}else if (!rs.next()) {
			System.out.println(" Student have no grades ");
		}
		else {
		System.out.print(" Enter Subject Update: ");
		String subs = scan.nextLine().replace(" ", "_").toUpperCase();
	  
		for (int i =0; i<subjects.length;i++) {
			if (subjects[i].equalsIgnoreCase(subs)) {
				validSub = true;
				break;
			}
		}
		
		  if (!validSub) {
			  System.out.println(" Subject is not available :( ");
		  }else {
			  String updates = "UPDATE grades SET " + subs +  "= ? WHERE student_id = ?";
			  PreparedStatement ps = connection().prepareStatement(updates);
				
			  System.out.print(" Enter Update Grade: ");
			  String grade = scan.nextLine();
			  int parseGrade = Integer.parseInt(grade);

			  ps.setInt(1, parseGrade);
			  ps.setInt(2, parseid);
			  
			  int update =  ps.executeUpdate();
			  
			  if (update > 0) {
				  System.out.println(" Update Success");
			  }
			  else {
				System.out.println(" Cannot update");
			}  
		  }
		}	
		}catch (SQLException e) {
			System.out.println(" Error: "+e.getMessage());
		}catch (NumberFormatException e) {
			System.out.println(" Error: "+e.getMessage());
		}
	}
     //SEARCH STUDENTS
	  private void searchStudent() {
		 
		 try {
		String search = "SELECT * FROM students WHERE student_id = ?";
		PreparedStatement ps = connection().prepareStatement(search);
		
		System.out.print(" ENTER STUDENT ID: ");
		String id = scan.nextLine();
		int parseId = Integer.parseInt(id);
		
		 if (!idExists(parseId)) {
			 System.out.println(" ID DOESN'T EXIST ");
		   }
		 else {
		 
			ps.setInt(1, parseId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			do {
				
				String name = rs.getString("Student_name");
				int age = rs.getInt("Student_age");
				String section = rs.getString("Student_Section");
				int id_s = rs.getInt("Student_Id");
				
				System.out.println(" ______________________________________");
				System.out.println(" |Student Name: "+name+"      ");
				System.out.println(" |Student Age: "+age+"          ");
				System.out.println(" |Student Section: "+section+"    ");
				System.out.println(" |Student ID: "+id_s+"         ");
				System.out.println(" ----------------------------------------");
				
			
			}while (rs.next());
		 }
		
		 }catch (SQLException e) {
			
		 }catch (NumberFormatException e) {
			System.out.println(" Error: "+e.getMessage());
		}
	}
    
	//ADD STUDENTS
	 private void addStudent() {
        try {
            System.out.print(" Enter Student Name: ");
            String name = scan.nextLine().trim();
            while (name.isEmpty() || name.matches(".*\\d.*")) {
                System.out.print(" Invalid name. Enter a valid name: ");
                name = scan.nextLine().trim();
            }

            System.out.print(" Enter Age: ");
            while (!scan.hasNextInt()) {
                System.out.print(" Invalid age. Enter a number: ");
                scan.next();
            }
            int age = scan.nextInt();
            scan.nextLine();

            if (age < 18) {
                System.out.println(" Student must be at least 18 years old.");
                return;
            }

            System.out.print(" Enter Section: ");
            String course = scan.nextLine().trim();
            while (course.isEmpty()) {
                System.out.print(" Course cannot be empty. Enter Section: ");
                course = scan.nextLine().trim();
            }

            System.out.print(" Enter Student ID: ");
            while (!scan.hasNextInt()) {
                System.out.print(" Invalid ID. Enter a number: ");
                scan.next();
            }
            int id = scan.nextInt();
            scan.nextLine();

            if (idExists(id)) {
                System.out.println(" Error: ID already exists.");
                return;
            }

            stud.setStudent(name);
            stud.setAge(age);
            stud.setCourse(course);
            stud.setID(id);
            insertStudent(stud.getStudentName(), stud.getAge(), stud.getCourse(), stud.getId());

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

	 //ADD GRADE IN THE STUDENT
     private void addGrades() {
        try {
            System.out.print(" Enter Student ID: ");
            
            while (!scan.hasNextInt()) {
                System.out.print(" Invalid ID. Enter a number: ");
                scan.next();
            }
         
            int id = scan.nextInt();
            scan.nextLine();
            
            if (!idExists(id)) {
                System.out.println(" Error: ID does not exist.");
                return;
            }
            
            PreparedStatement ps = connection().prepareStatement("SELECT * FROM grades WHERE student_id = ? ");
            ps.setInt(1, id);
            ResultSet set = ps.executeQuery();
            
            if (set.next()) {
            	throw new SQLException(" ID ALREADY INSERTED");

            }

            String[] subjects = {"Math", "Science", "Filipino", "English", "Research", "Statistics", "Personal Development", "Chemistry"};
            String[] grades = new String[subjects.length];
            int[] neww = new int[grades.length];

            for (int i = 0; i < subjects.length; i++) {
            	
                System.out.print(" Enter " + subjects[i] + " score: ");
                grades[i] = scan.nextLine();
                neww[i] = Integer.parseInt(grades[i]);
                
            }

            insertGrades(id, neww);

        } catch (SQLException e) {
            System.out.println(" ERROR: " + e.getMessage());
        }catch (NumberFormatException e) {
			System.out.println(" Error: Invalid Input");
        }
    }

     //VIEW ALL STUDENT
     private void viewStudents() {
        try (Statement stmnt = connection().createStatement();
             ResultSet rs = stmnt.executeQuery("SELECT * FROM students ORDER BY student_name")) {

            if (!rs.next()) {
                System.out.println(" NO STUDENT RECORD FOUND.");
                return;
            }

            int i = 1;
            do {
                System.out.println(" Student " + i + ":");
                System.out.println(" Name: " + rs.getString("Student_name"));
                System.out.println(" Age: " + rs.getString("Student_age"));
                System.out.println(" Section: " + rs.getString("Student_Section"));
                System.out.println(" ID: " + rs.getString("Student_id"));
                System.out.println("-------------------------");
                i++;
            } while (rs.next());

        } catch (SQLException e) {
            System.out.println(" ERROR: " + e.getMessage());
        }
    }

     private void insertStudent(String name, int age, String course, int id) throws SQLException {
        String insert = "INSERT INTO students VALUES (?, ?, ?, ?)";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(insert)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);
            ps.setInt(4, id);
            ps.executeUpdate();
            System.out.println(" Successfully Added Information ");
        }
    }

     //check if id is exist
     private boolean idExists(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM students WHERE student_id = ?";
        try (Connection conn = connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

     //Inserting a Grade
     private void insertGrades(int id, int[] grades) throws SQLException {
        String query = "INSERT INTO GRADES VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try  (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            for (int i = 0; i < grades.length; i++) {
                ps.setInt(i + 2, grades[i]);
            }
            if (ps.executeUpdate() > 0) {
                System.out.println(" Successfully Inserted Grades");
            } else {
                System.out.println(" Failed to Insert Grades");
            }
        }
    }
}
