package JDBC;

public class Students {
	
    private String student_name;
    private int Age; 
    private String Course; 
    private int id;
    
    public String getStudentName() {
    	return student_name;
    }
    public int getAge() {
		return Age;
	}
    public String getCourse(){
    	return Course;
    }
    public int getId() {
    	return id;
    }
    public void setStudent(String name) {
    	this.student_name = name;
    }
    public void setAge(int age) {
    	this.Age = age;
    }
    public void setCourse(String course) {
    	this.Course = course;
    }
    public void setID(int  id) {
    	this.id = id;
    }




}
