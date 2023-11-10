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
public class Contractor{

	public static String getContractorJoinDateDetails() throws Exception{
		java.io.BufferedReader bufferedReaderObject = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		Contractor contractor = new Contractor();
		User user = new User();
		Validation validations = new Validation();
		System.out.print("\t\t\t\t\tEnter the Joining date of the contractor: ");
		String dateOfJoin = bufferedReaderObject.readLine();
		while(validations.weekendValidation(dateOfJoin).equals("Saturday") || validations.weekendValidation(dateOfJoin).equals("Sunday")){
			if(validations.weekendValidation(dateOfJoin).equals("Saturday")){
				System.out.println("\t\t\t\t\tThe date given is saturday so please give another date.");
			}
			else{
				System.out.println("\t\t\t\t\tThe date given is sunday so please give another date.");
			}
			
			System.out.print("\t\t\t\t\tEnter the Joining date of the contractor: ");
			dateOfJoin = bufferedReaderObject.readLine();
		}
		while(!validations.dateOfJoinValidation(dateOfJoin)){
			System.out.println("\t\t\t\t\tPlease enter the correct date");
			dateOfJoin = getContractorJoinDateDetails();
			//System.out.print("Enter the Joining date of the contractor: ");
			//dateOfJoin = bufferedReaderObject.readLine();
		}

		user.setDateOfJoin(dateOfJoin);
		dateOfJoin = user.getDateOfJoin();
		return dateOfJoin;
	}

	public static String getContractorTerminationDateDetails(String dateOfJoin) throws Exception{
		java.io.BufferedReader bufferedReaderObject = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		Contractor contractor = new Contractor();
		User user = new User();
		Validation validations = new Validation();
		System.out.print("\t\t\t\t\tEnter the Termination date of the contractor: ");
		String dateOfTermination = bufferedReaderObject.readLine();
		while(validations.weekendValidation(dateOfTermination).equals("Saturday") || validations.weekendValidation(dateOfTermination).equals("Sunday")){
			if(validations.weekendValidation(dateOfTermination).equals("Saturday")){
				System.out.println("\t\t\t\t\tThe date given is saturday so please give another date.");
			}
			else{
				System.out.println("\t\t\t\t\tThe date given is sunday so please give another date.");
			}
			System.out.print("\t\t\t\t\tEnter the Termination date of the contractor: ");
			dateOfTermination = bufferedReaderObject.readLine();
		}
		while(!validations.dateOfJoinValidation(dateOfTermination)){
			System.out.println("\t\t\t\t\tPlease enter the correct date");
			dateOfTermination = getContractorTerminationDateDetails(dateOfJoin);
			//System.out.print("Enter the Joining date of the contractor: ");
			//dateOfJoin = bufferedReaderObject.readLine();
		}
		while(!validations.checkingDates(dateOfTermination)){
			System.out.println("\t\t\t\t\tPlease give another date");
			dateOfTermination = getContractorTerminationDateDetails(dateOfJoin);
		}
		while(!validations.dateChecking(dateOfJoin, dateOfTermination)){
			System.out.println("\t\t\t\t\tPlease enter the correct date");
			dateOfTermination = getContractorTerminationDateDetails(dateOfJoin);
		}
		user.setDateOfTermination(dateOfTermination);
		dateOfTermination = user.getDateOfTermination();
		return dateOfTermination;		
	}

	public static void updateDetails(String employeeId) throws Exception{
		try{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		DES des = new DES();
		des.contractorFileDecryption();
		int count = 0;
		String filePath = "../Database/contractor_data.xml";
		File file = new File(filePath);
		Document document = documentBuilder.parse(file);
					NodeList list = document.getElementsByTagName("contractor");
					for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
						Node node = list.item(loopCounter);	
						Element element = (Element) node;
						if(employeeId.equalsIgnoreCase(element.getAttribute("Id"))){
							System.out.println("\t\t\t\t\t=====================\n\t\t\t\t\tYour Details\n\t\t\t\t\t=====================");
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
							String dateOfTermination = element.getElementsByTagName("DateOfTermination").item(0).getTextContent();
							String fullName = firstName + lastName;
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Employee Name" , "|", fullName);
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Email Id", "|", emailId);
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Designation", "|", designation);
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Department", "|", department);
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Salary", "|", salary);
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Phone number", "|", phoneNumber);
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Address", "|", address);
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Date of join", "|", dateOfJoin);
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Reporting", "|", reporting);
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Status", "|", status);
							System.out.format("\t\t\t\t\t%-19s%-2s%-8s\n", "Date of Termination", "|", dateOfTermination);
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
						System.out.println("There is no contractor with given contractor ID");
					}
		des.contractorFileEncryption();
	
		}catch(FileNotFoundException e){
			System.out.println("");
		}
	}


}