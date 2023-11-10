package bin;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
public class FetchUser{
	/**
	*This method is used to print the hierarchy of the active employees and contractors.
	*This method will show the tree structure of the employee according to their reporting.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void printHierarchy() throws Exception{
		DES des = new DES();
		HashMap<String, ArrayList<String>> hmap = new HashMap<String, ArrayList<String>>();
		HashMap<String, String> hmapOne = new HashMap<String, String>();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		des.employeeFileDecryption();
		ArrayList<String> employeeList = new ArrayList<String>();
		ArrayList<String> managerList = new ArrayList<String>();
		des.contractorFileDecryption();
		String filePath = "../Database/employee_data.xml";
		String filePathOne = "../Database/contractor_data.xml";
		File file = new File(filePath);
		File fileOne = new File(filePathOne);
		String employeeId = "", root = "";
		if(file.exists()){
			Document document = documentBuilder.parse(file);
			NodeList list = document.getElementsByTagName("employee");
			System.out.println("\t\t\t\t\t================================================\n\t\t\t\t\t Hierarchy of the employees in the organization\n\t\t\t\t\t================================================");
			Node node = list.item(0);
			Element elements = (Element) node;
			int count = 0, contractorCount = 0;

			
			root = elements.getAttribute("Id");

			managerList = managersUnderHead(root);
			ArrayList<String> managerNames = new ArrayList<String>();


			if(fileOne.exists()){
				
				NodeList contractorList = document.getElementsByTagName("contractor");
				for(int loopCounter = 0; loopCounter < contractorList.getLength(); loopCounter++){
					Node nodes = list.item(loopCounter);
					Element element = (Element) nodes;
					if("Active".equals(element.getElementsByTagName("Status").item(0).getTextContent())){
						contractorCount++;
					}		
				}
			count = count+contractorCount+1;
			}


			String employeeName = "";
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodes = list.item(loopCounter);
				Element element = (Element) nodes;
				if("Active".equals(element.getElementsByTagName("Status").item(0).getTextContent())){
					count++;
				}
			}
			
			
			String arr[][] = new String[count][4];
			arr[0][0] = root + "(" + elements.getElementsByTagName("FirstName").item(0).getTextContent() + ")";
			arr[0][1] = "";
			arr[0][2] = "";
			arr[0][3] = "";
			
			for(int loopCounter = 1; loopCounter < count; loopCounter++){
				
				for(int i = 0; i < managerList.size(); i++){
					arr[loopCounter][0] = "|";
					arr[loopCounter][1] = "|----";
					arr[loopCounter][2] = managerList.get(i);
					arr[loopCounter][3] = "";
					String managerId = managerList.get(i);
					employeeList = employeeUnderManager(managerId.substring(0,8));
					int counter = employeeList.size();
					
					if(counter >= 0){
						loopCounter++;
						for(int j = 0; j < counter; j++){
							arr[loopCounter][0] = "|";
							arr[loopCounter][1] = "|";
							
							arr[loopCounter][2] = "|_________";
							arr[loopCounter][3] = employeeList.get(j);
							loopCounter++;
						}
					}
				}
			}
			System.out.println();
			for(int i = 0; i  < count; i++){
				
				System.out.println();
				System.out.print("\t\t\t\t\t\t");
				for(int j = 0; j < 4; j++){
					System.out.format("%-5s%-4s", arr[i][j], "");
				}
				System.out.println();
			}
			System.out.println("\n");
			des.employeeFileEncryption();	
			des.contractorFileEncryption();
		}	
	}


	/**
	*This method is used to get the list of managers that are reporting to the particular head.
	*@param headId This parameter is used to get the ID of the head of manager.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static ArrayList<String> managersUnderHead(String headId) throws Exception{
		ArrayList<String> managerList = new ArrayList<String>();
		String managerId = "";
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		String filePath = "../Database/employee_data.xml";
		String filePathOne = "../Database/contractor_data.xml";
		File file = new File(filePath);
		File fileOne = new File(filePathOne);
		Document document = documentBuilder.parse(file);
		
		NodeList list = document.getElementsByTagName("employee");
		
		if(fileOne.exists()){
			Document documentOne = documentBuilder.parse(fileOne);
			NodeList listOne = documentOne.getElementsByTagName("contractor");
			for(int loopCounter = 0; loopCounter < listOne.getLength(); loopCounter++){
				Node node = listOne.item(loopCounter);
				Element element = (Element) node;
				if(headId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent())){
					managerId = element.getAttribute("Id") + "(" + element.getElementsByTagName("Designation").item(0).getTextContent() + ")";
					managerList.add(managerId);
				}
			}

		}
		for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
			Node node = list.item(loopCounter);
			Element element = (Element) node;
			if(headId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent()) && "Active".equals(element.getElementsByTagName("Status").item(0).getTextContent())){
				managerId = element.getAttribute("Id") + "(" + element.getElementsByTagName("Designation").item(0).getTextContent() + ")";
				managerList.add(managerId);
				
			}
		}

		
		return managerList;
	}

	/**
	*This method is used to print the list of all the employees that are reporting to the particular manager.
	*@param managerId This parameter is get the ID of the manager.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static ArrayList<String> employeeUnderManager(String managerId) throws Exception{
		ArrayList<String> employeeList = new ArrayList<String>();
		String employeeId = "";
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		String filePath = "../Database/employee_data.xml";
		String filePathOne = "../Database/contractor_data.xml";
		File file = new File(filePath);
		File fileOne = new File(filePathOne);
		Document document = documentBuilder.parse(file);
		NodeList list = document.getElementsByTagName("employee");
		if(fileOne.exists()){
			Document documentOne = documentBuilder.parse(fileOne);
			NodeList listOne = documentOne.getElementsByTagName("contractor");
			for(int loopCounter = 0; loopCounter < listOne.getLength(); loopCounter++){
				Node node = listOne.item(loopCounter);
				Element element = (Element) node;
				if(managerId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent()) && "Active".equals(element.getElementsByTagName("Status").item(0).getTextContent())){
					employeeId = element.getAttribute("Id") + "(" + element.getElementsByTagName("Designation").item(0).getTextContent() + ")";
					employeeList.add(employeeId);
				}
			}
		}
		
		for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
			Node node = list.item(loopCounter);
			Element element = (Element) node;
			if(managerId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent()) && "Active".equals(element.getElementsByTagName("Status").item(0).getTextContent())){
				employeeId = element.getAttribute("Id") + "(" + element.getElementsByTagName("Designation").item(0).getTextContent() + ")";
				employeeList.add(employeeId);
			}
		}

		return employeeList;
	}

	/**
	*This method is used to retrieve the details of the employees present in the organisation.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void fetchDetails() throws Exception{
		ArrayList<String> employeeList = new ArrayList<String>();
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		DES des = new DES();
		int input = 0;
		try{
			do{
				System.out.println("\t\t\t\t\t==========================\n\t\t\t\t\tFetching Details\n\t\t\t\t\t==========================\n\t\t\t\t\t1. Fetch Employee Details\n\t\t\t\t\t2. Fetch Contractor Details\n\t\t\t\t\t3. Fetch Employee/Contractor by Manager\n\t\t\t\t\t4. Print Hierarchy\n\t\t\t\t\t5. Back");
				System.out.print("\t\t\t\t\tEnter option: ");
				input = Integer.parseInt(bufferedReaderObject.readLine());
				System.out.println();
				if(input == 1){
				
					des.employeeFileDecryption();
					String filePath = "../Database/employee_data.xml";
					File file = new File(filePath);
					Document document = documentBuilder.parse(file);
					
					NodeList list = document.getElementsByTagName("employee");
					System.out.println("\t\t\t\t\t\tList of all employees with employee ID");
					int counts = 1;
					System.out.println("\t\t\t\t\t\t =======================\n\t\t\t\t\t\t| ID " + "    " + "Employee Name |\n\t\t\t\t\t\t =======================");
					for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
						Node node = list.item(loopCounter);
						Element element = (Element) node;
						System.out.println("\t\t\t\t\t\t  " + counts + ". " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
						employeeList.add(element.getAttribute("Id"));
						counts++;
					}
					int count = 0;
					String employeeId = "";
					System.out.print("\t\t\t\t\t\tSelect the employee from the list: ");
					int choice = Integer.parseInt(bufferedReaderObject.readLine());
					for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
						Node nodeOne = list.item(loopCounter);
						Element element = (Element) nodeOne;
						if(choice > counts-1 || choice <= 0){
							System.out.println("Please select correct option");
							fetchDetails();
						}
						else if(element.getAttribute("Id").equals(employeeList.get(choice-1))){
							employeeId = element.getAttribute("Id");
						}
					}
					
					for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
						Node node = list.item(loopCounter);	
						Element element = (Element) node;
						if(employeeId.equalsIgnoreCase(element.getAttribute("Id"))){
							System.out.println("\t\t\t\t\t\t=============================\n\t\t\t\t\t\tDetails of the given employee\n\t\t\t\t\t\t=============================");
							String firstName = element.getElementsByTagName("FirstName").item(0).getTextContent();
							String lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
							String designation = element.getElementsByTagName("Designation").item(0).getTextContent();
							String department = element.getElementsByTagName("Department").item(0).getTextContent();
							String phoneNumber = element.getElementsByTagName("PhoneNumber").item(0).getTextContent();
							String salary = element.getElementsByTagName("Salary").item(0).getTextContent();
							String emailId = element.getElementsByTagName("EmailID").item(0).getTextContent();
							String address = element.getElementsByTagName("address").item(0).getTextContent();
							String status = element.getElementsByTagName("Status").item(0).getTextContent();
							String dateOfJoin = element.getElementsByTagName("DateOfJoin").item(0).getTextContent();
							try {
      								DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    								Date date = sdf.parse(dateOfJoin);
								dateOfJoin = new SimpleDateFormat("dd MMM yyyy").format(date);
    							} catch (ParseException e) {
      								e.printStackTrace();
    							}

							String reporting = element.getElementsByTagName("Reporting").item(0).getTextContent();
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
							des.employeeFileEncryption();
							break;
						}
						else{
							count++;
						}
					}
					if(count == list.getLength()){
						System.out.println("\t\t\t\t\t\tThere is no employee with given employee ID");
					}
				}
				else if(input == 2){
				try{
					des.contractorFileDecryption();
					String filePathOne = "../Database/contractor_data.xml";
					File file = new File(filePathOne);
					Document document = documentBuilder.parse(file);
					String sample = "";
					NodeList list = document.getElementsByTagName("contractor");
					System.out.println("\t\t\t\t\t\tList of all Indian contractors with contractor ID");
					System.out.println("\t\t\t\t\t\t =======================\n\t\t\t\t\t\t| ID " + "    " + "Employee Name |\n\t\t\t\t\t\t =======================");
					for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
						//sample = document.getElementsByTagName("employee").item(loopCounter).getAttributes().getNamedItem("Id").getNodeValue();
						Node node = list.item(loopCounter);
						Element element = (Element) node;
						
							System.out.println("\t\t\t\t\t\t  " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
					}
					int count = 0;
					System.out.print("\t\t\t\t\t\tEnter the contractor ID: ");
					String contractorId = bufferedReaderObject.readLine();
					for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
						Node node = list.item(loopCounter);	
						Element element = (Element) node;
						if(contractorId.equals(element.getAttribute("Id"))){
							System.out.println("\t\t\t\t\t\t=====================\n\t\t\t\t\t\tDetails of the given employee\n\t\t\t\t\t\t=====================");
							String firstName = element.getElementsByTagName("FirstName").item(0).getTextContent();
							String lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
							String designation = element.getElementsByTagName("Designation").item(0).getTextContent();
							String department = element.getElementsByTagName("Department").item(0).getTextContent();
							String phoneNumber = element.getElementsByTagName("PhoneNumber").item(0).getTextContent();
							String emailId = element.getElementsByTagName("EmailID").item(0).getTextContent();
							String address = element.getElementsByTagName("address").item(0).getTextContent();
							String salary = element.getElementsByTagName("Salary").item(0).getTextContent();
							String reporting = element.getElementsByTagName("Reporting").item(0).getTextContent();
							String dateOfJoin = element.getElementsByTagName("DateOfJoin").item(0).getTextContent();
							String dateOfTermination = element.getElementsByTagName("DateOfTermination").item(0).getTextContent();




							String status = element.getElementsByTagName("Status").item(0).getTextContent();
							Validation validations = new Validation();
							if(!validations.checkingDates(dateOfTermination)){
								status = "In-Active";
							}
							else{
								status = "Active";
							}

							try {
      								DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    								Date date = sdf.parse(dateOfJoin);
								Date dateOne = sdf.parse(dateOfTermination);
								dateOfJoin = new SimpleDateFormat("dd MMM yyyy").format(date);
								dateOfTermination = new SimpleDateFormat("dd MMM yyyy").format(dateOne);
    							} catch (ParseException e) {
      								e.printStackTrace();
    							}

							String fullName = firstName + " " + lastName;
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Contractor Name" , "|", fullName);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Email Id", "|", emailId);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Designation", "|", designation);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Department", "|", department);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Salary", "|", salary);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Phone number", "|", phoneNumber);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Address", "|", address);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Date of join", "|", dateOfJoin);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Reporting", "|", reporting);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Status", "|", status);
							System.out.format("\t\t\t\t\t\t%-19s%-2s%-8s\n", "Date of termination", "|", dateOfTermination);
							des.contractorFileEncryption();
							break;
						}
						else{
							count++;
						}
					}
					if(count == list.getLength()){
						System.out.println("\t\t\t\t\t\tThere is no contractor with given contractor ID");
					}
				}catch(FileNotFoundException e){
					System.out.println("\t\t\t\t\t\tThere is no contractor in the organisation");
				}	
				}
				else if(input == 3){
				try{
					String managerId = "";
					des.employeeFileDecryption();
					
					String filePath = "../Database/employee_data.xml";
					int count = 0;
					File file = new File(filePath);
					
					Document document = documentBuilder.parse(file);
					
					NodeList list = document.getElementsByTagName("employee");
					if(!file.exists()){

					}
					else{
						int counter = 0;
						System.out.println("\t\t\t\t\t\tList of all Managers with manager ID");
						System.out.println("\t\t\t\t\t\t =======================\n\t\t\t\t\t\t| ID " + "    " + "Manager Name |\n\t\t\t\t\t\t =======================");
						for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
							Node node = list.item(loopCounter);
							Element element = (Element) node;
							if(element.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager")){
								System.out.println("\t\t\t\t\t\t  " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
							}

						}
							
						
						System.out.print("\t\t\t\t\t\tEnter the Manager ID: ");
						managerId = bufferedReaderObject.readLine();	
						int counts = 0;
						System.out.println("\t\t\t\t\t\t========================================\n\t\t\t\t\t\tDetails of the employees for the manager\n\t\t\t\t\t\t========================================");
						for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
							Node node = list.item(loopCounter);	
							Element element = (Element) node;
							if(managerId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent())){
								counts++;
								System.out.println("\t\t\t\t\t\t=============================\n\t\t\t\t\t\t Employee " + counts + "\n\t\t\t\t\t\t=============================");
								
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
			
		
								try {
      									DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    									Date date = sdf.parse(dateOfJoin);
									dateOfJoin = new SimpleDateFormat("dd MMM yyyy").format(date);
							
    								} catch (ParseException e) {
      									e.printStackTrace();
    								}
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
							System.out.println("\t\t\t\t\t\tThere is no employees under this manager");
						}						
					}

					des.contractorFileDecryption();
					String filePathOne = "../Database/contractor_data.xml";

					File fileOne = new File(filePathOne);
					if(!fileOne.exists()){
						System.out.println("");
					}
					else{
						Document documentOne = documentBuilder.parse(fileOne);
						NodeList listOne = documentOne.getElementsByTagName("contractor");
						for(int loopCounter = 0; loopCounter < listOne.getLength(); loopCounter++){
							Node node = listOne.item(loopCounter);
							int counters = 0;	
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
								String emailId = element.getElementsByTagName("EmailID").item(0).getTextContent();
								String address = element.getElementsByTagName("address").item(0).getTextContent();
								String reporting = element.getElementsByTagName("Reporting").item(0).getTextContent();
								String status = element.getElementsByTagName("Status").item(0).getTextContent();
								String dateOfJoin = element.getElementsByTagName("DateOfJoin").item(0).getTextContent();
								String dateOfTermination = element.getElementsByTagName("DateOfTermination").item(0).getTextContent();
							
								try {
      									DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    									Date date = sdf.parse(dateOfJoin);
									Date dateOne = sdf.parse(dateOfTermination);
									dateOfJoin = new SimpleDateFormat("dd MMM yyyy").format(date);
									dateOfTermination = new SimpleDateFormat("dd MMM yyyy").format(dateOne);
    								} catch (ParseException e) {
      									e.printStackTrace();
    								}		
	
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
					}
					des.contractorFileEncryption();
					if(count != list.getLength()){
						System.out.println("\t\t\t\t\t\tThere is no contractor with under this manager");
					}
				

				
				}catch(FileNotFoundException e){
					System.out.println("");
				}	
				}
				else if(input == 4){
					printHierarchy();
					break;
				}
			
			}while(input != 5);	
		}catch(NumberFormatException e){
			System.out.println("\t\t\t\t\t\tplease enter numbers");
			fetchDetails();
		}
		catch(FileNotFoundException e){
			System.out.println("\t\t\t\t\t\tFile not found");
		}
	}
}