package bin;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.File;
import java.io.FileNotFoundException;
public class Employee{

	public static void sample(String managerId) throws Exception{
		try{
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				DES des = new DES();
					des.employeeFileDecryption();
					
					String filePath = "../Database/employee_data.xml";
					
					File file = new File(filePath);
					
					Document document = documentBuilder.parse(file);
					
					NodeList list = document.getElementsByTagName("employee");
					
					int counter = 0;
					for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
	
						Node nodes = list.item(loopCounter);
						Element elements = (Element) nodes;
						if(elements.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager")){
							counter++;
						}
					}


					
					int count = 0;
					//System.out.println("\t\t\t\t\t\t========================================\n\t\t\t\t\t\tDetails of the employees for the manager\n\t\t\t\t\t\t========================================");
					for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
						Node node = list.item(loopCounter);	
						Element element = (Element) node;
						if(managerId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent())){
				
							System.out.println("\t\t\t\t\t\t=============================\n\t\t\t\t\t\t Employee " + loopCounter + "\n\t\t\t\t\t\t=============================");
							
							String firstName = element.getElementsByTagName("FirstName").item(0).getTextContent();
							String lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
							String designation = element.getElementsByTagName("Designation").item(0).getTextContent();
							String department = element.getElementsByTagName("Department").item(0).getTextContent();
							String phoneNumber = element.getElementsByTagName("PhoneNumber").item(0).getTextContent();
							String salary = element.getElementsByTagName("Salary").item(0).getTextContent();
							String emailId = element.getElementsByTagName("EmailID").item(0).getTextContent();
							String address = element.getElementsByTagName("address").item(0).getTextContent();
							String reporting = element.getElementsByTagName("Reporting").item(0).getTextContent();
							String status = element.getElementsByTagName("Status").item(0).getTextContent();
							String dateOfJoin = element.getElementsByTagName("DateOfJoin").item(0).getTextContent();
							String fullName = firstName + " " + lastName;
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Employee Name" , "|", fullName);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Email Id", "|", emailId);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Designation", "|", designation);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Department", "|", department);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Salary", "|", salary);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Phone number", "|", phoneNumber);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Address", "|", address);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Date of join", "|", dateOfJoin);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Reporting", "|", reporting);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Status", "|", status);
							if(status.equals("In-Active")){
								String dateOfTermination = element.getElementsByTagName("DateOfTermination").item(0).getTextContent();
								System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Date of Termination", "|", dateOfTermination);
							}
							
							//break;
						}
						else{
							count++;
						}
						
					}
					des.employeeFileEncryption();
					
					if(count == list.getLength()){
						//System.out.println("\t\t\t\t\t\tThere is no employees/contractors under this manager");
					}
					des.contractorFileDecryption();	
					String filePathOne = "../Database/contractor_data.xml";
					File fileOne = new File(filePathOne);
					Document documentOne = documentBuilder.parse(fileOne);
					NodeList listOne = documentOne.getElementsByTagName("contractor");

					for(int loopCounter = 0; loopCounter < listOne.getLength(); loopCounter++){
						int counters = 0;
						Node node = listOne.item(loopCounter);	
						Element element = (Element) node;
						if(managerId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent())){
							counters++;
							System.out.println("\t\t\t\t\t\t=============================\n\t\t\t\t\t\t Contractor " + counters + "\n\t\t\t\t\t\t=============================");
							
							String firstName = element.getElementsByTagName("FirstName").item(0).getTextContent();
							String lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
							String designation = element.getElementsByTagName("Designation").item(0).getTextContent();
							String department = element.getElementsByTagName("Department").item(0).getTextContent();
							String phoneNumber = element.getElementsByTagName("PhoneNumber").item(0).getTextContent();
							String salary = element.getElementsByTagName("Salary").item(0).getTextContent();
							//String emailId = element.getElementsByTagName("EmailID").item(0).getTextContent();
							String address = element.getElementsByTagName("address").item(0).getTextContent();
							String reporting = element.getElementsByTagName("Reporting").item(0).getTextContent();
							String status = element.getElementsByTagName("Status").item(0).getTextContent();
							String dateOfJoin = element.getElementsByTagName("DateOfJoin").item(0).getTextContent();
							String dateOfTermination = element.getElementsByTagName("DateOfTermination").item(0).getTextContent();
							String fullName = firstName + " " + lastName;
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Employee Name" , "|", fullName);
							//System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Email Id", "|", emailId);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Designation", "|", designation);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Department", "|", department);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Salary", "|", salary);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Phone number", "|", phoneNumber);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Address", "|", address);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Date of join", "|", dateOfJoin);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Reporting", "|", reporting);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Date of Termination", "|", dateOfTermination);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Status", "|", status);
							/*if(status.equals("In-Active")){
								String dateOfTermination = element.getElementsByTagName("DateOfTermination").item(0).getTextContent();
								System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Date of Termination", "|", dateOfTermination);
							}
							*/
							//break;
						}
						else{
							count++;
						}
						
					}
					des.contractorFileEncryption();
					if(count == list.getLength()){
						//System.out.println("\t\t\t\t\t\tThere is no contractor with under this manager");
					}
				
	}catch(FileNotFoundException e){
		System.out.println("");
	}
	}

	public static void yourDetails(String employeeId) throws Exception{
		try{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		DES des = new DES();
		String filePath = "../Database/employee_data.xml";
		File file = new File(filePath);
		int count = 0;
		Document document = documentBuilder.parse(file);
		NodeList list = document.getElementsByTagName("employee");
		for(int i = 0; i < list.getLength(); i++){
			Node node = list.item(i);	
			Element element = (Element) node;
			if(employeeId.equalsIgnoreCase(element.getAttribute("Id"))){
				System.out.println("\t\t\t\t\t===============================\n\t\t\t\t\tYour Details\n\t\t\t\t\t===============================");
				String firstName = element.getElementsByTagName("FirstName").item(0).getTextContent();
				String lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
				String designation = element.getElementsByTagName("Designation").item(0).getTextContent();
				String department = element.getElementsByTagName("Department").item(0).getTextContent();
				String salary = element.getElementsByTagName("Salary").item(0).getTextContent();
				String phoneNumber = element.getElementsByTagName("PhoneNumber").item(0).getTextContent();
				String emailId = element.getElementsByTagName("EmailID").item(0).getTextContent();
				String address = element.getElementsByTagName("address").item(0).getTextContent();
				String reporting = element.getElementsByTagName("Reporting").item(0).getTextContent();
				String status = element.getElementsByTagName("Status").item(0).getTextContent();
				String dateOfJoin = element.getElementsByTagName("DateOfJoin").item(0).getTextContent();
				System.out.println("\t\t\t\t\tEmployee Name: " + firstName + " " + lastName);
				System.out.println("\t\t\t\t\tEmail Id: " + emailId);
				System.out.println("\t\t\t\t\tDesignation: " + designation);	
							
				System.out.println("\t\t\t\t\tDepartment: " + department);
				System.out.println("\t\t\t\t\tReporting: " + reporting);
				System.out.println("\t\t\t\t\tPhone number: " + phoneNumber);
				System.out.println("\t\t\t\t\tAddress: " + address);
							System.out.println("\t\t\t\t\tDate of Join: " + dateOfJoin);
							Salary s = new Salary();
							System.out.println("\t\t\t\t\t===============================\n\t\t\t\t\tYour Salary Structure\n\t\t\t\t\t===============================");
							s.salaryStructure(salary);
					
							break;
			}
			else{
				count++;
			}
		}
		if(count == list.getLength()){
			System.out.println("There is no employee with given employee ID");
		}
		des.employeeFileEncryption();
	}catch(FileNotFoundException e){
		System.out.println("");
	}
			
	}

	public static String getEmployeeDateDetails() throws Exception{
		WebSample webService = new WebSample();
		User user = new User();
		java.io.BufferedReader bufferedReaderObject = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		Employee employee = new Employee();
		Validation validations = new Validation();
		boolean flag =true;
		System.out.print("\t\t\t\t\tEnter the Joining date of the employee: ");
		String dateOfJoin = bufferedReaderObject.readLine();
		while(!validations.dateParse(dateOfJoin)){
			System.out.println("\t\t\t\t\tPlease enter the correct date");
			dateOfJoin = getEmployeeDateDetails();
		}
		while(validations.weekendValidation(dateOfJoin).equals("Saturday") || validations.weekendValidation(dateOfJoin).equals("Sunday")){
			if(validations.weekendValidation(dateOfJoin).equals("Saturday")){
				System.out.println("\t\t\t\t\tThe date given is saturday so please give another date.");
			}
			else{
				System.out.println("\t\t\t\t\tThe date given is sunday so please give another date.");
			}
			System.out.print("\t\t\t\t\tEnter the Joining date of the employee: ");
			dateOfJoin = bufferedReaderObject.readLine();
		}
			
		while(!validations.governmentHolidays(dateOfJoin)){
			
			String day = dateOfJoin.substring(0, 2);
			String month = dateOfJoin.substring(3, 5);
			String newDate = month+"/"+day+"/"+"2020";
			String holiday = webService.publicHolidays(newDate);
			
			System.out.println("The given date is public holiday i.e., " + holiday + "Please enter another date");
			
			dateOfJoin = getEmployeeDateDetails();
		}
		
		while(!validations.dateOfJoinValidation(dateOfJoin)){
			System.out.println("\t\t\t\t\tPlease enter the correct date");
			dateOfJoin = getEmployeeDateDetails();
			
		}
		
		//user.setDateOfJoin(dateOfJoin);
		//dateOfJoin = user.getDateOfJoin();
		//System.out.println(dateOfJoin);
		return dateOfJoin;
	}




	public static void updateDetails(String employeeId) throws Exception{
		try{
		System.out.println("\n");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		DES des = new DES();
		des.employeeFileDecryption();
		int count = 0;
		String filePath = "../Database/employee_data.xml";
		File file = new File(filePath);
		Document document = documentBuilder.parse(file);
		int step = 0;
		NodeList list = document.getElementsByTagName("employee");
		for(int i = 0; i < list.getLength(); i++){
			Node node = list.item(i);	
			Element element = (Element) node;
			if(element.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager")){
				step = 1;
			}
			else{
				step = 2;
			}
			
			if(step == 1){
				yourDetails(employeeId);
				sample(employeeId);
				break;
			}
			else if(step == 2){		
				yourDetails(employeeId);
				break;
			}
		}
	}catch(FileNotFoundException e){
		System.out.println("");
	}
}
}
