package bin;
public class User{
	
	private String id;
	private String firstName;
	private String lastName;
	private String designation;
	private String department;
	private String emailID;
	private String salary;
	private String address;
	private String phoneNumber;
	private String reporting;
	private String dateOfJoin;
	private String dateOfTermination;
	private String status;
	
	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getFirstName(){
		return firstName;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getLastName(){
		return lastName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getDesignation(){
		return designation;
	}
	
	public void setDesignation(String designation){
		this.designation = designation;
	}

	public String getDepartment(){
		return department;
	}
	
	public void setDepartment(String department){
		this.department = department;
	}

	public String getEmailID(){
		return emailID;
	}
	
	public void setEmailID(String emailID){
		this.emailID = emailID;
	}

	public String getSalary(){
		return salary;
	}
	
	public void setSalary(String salary){
		this.salary = salary;
	}

	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}

	public String getReporting(){
		return reporting;
	}

	public void setReporting(String reporting){
		this.reporting = reporting;
	}

	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getDateOfJoin(){
		return dateOfJoin;
	}

	public void setDateOfJoin(String dateOfJoin){
		this.dateOfJoin = dateOfJoin;
	}

	public String getDateOfTermination(){
		return dateOfTermination;
	}		

	public void setDateOfTermination(String dateOfTermination){
		this.dateOfTermination = dateOfTermination;
	}	


}