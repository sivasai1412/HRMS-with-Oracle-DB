package bin;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.io.Console;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.xml.transform.OutputKeys;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;

public class CreateUser{

	static Logger log = Logger.getLogger(CreateUser.class);
	/**
	*This method is used to insert the details of the employees to store in the database.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void insertEmployee() throws Exception{
	try{
		Employee employee = new Employee();
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		java.util.ArrayList<String> managerList = new java.util.ArrayList<String>();
		java.util.ArrayList<String> activeManagerList = new java.util.ArrayList<String>();
		String filepath = "../Encrypted_Database/employees_data.xml";
		File fileOne = new File(filepath);
		DES des = new DES();
		if(!fileOne.exists()){
			String stringTwo = "../Database/employee_data.xml";
			File fileTwo = new File(stringTwo);
			fileTwo.createNewFile();
			java.io.FileOutputStream fos = new java.io.FileOutputStream(stringTwo);
			String str="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><employee-details>" + "\n" + "</employee-details>";
			byte b[] = str.getBytes();
 
        		for(int i=0;i<b.length;i++){
		            fos.write(b[i]);
        		}
         
        		fos.close();
		}
 		else{
			des.employeeFileDecryption();
		}

		Validation validations = new Validation();
		String employeeFirstName = "", employeeLastName = "", designation = "", department = "", salary = "", reporting = "", emailID = "", phoneNumber = "", address = "", dateOfJoin = "", status = ""; 
		String filePathOne = "../Database/employee_data.xml";
		File file = new File(filePathOne);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		Element rootElement = document.getDocumentElement();
		int choice = 0;
		boolean flag = true;
		User user = new User();		
		String employeeId = "";
		NodeList list = document.getElementsByTagName("employee");

			
			//String sample = document.getElementsByTagName("employee").item(list.getLength()-1).getAttributes().getNamedItem("Id").getNodeValue();
		String sample = "";
		String code = validations.organizationCode();
		if(code.equals("INEM")){
			int count = 0;
			java.util.List<String> indianEmployee = new java.util.ArrayList<>(); 
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				sample = document.getElementsByTagName("employee").item(loopCounter).getAttributes().getNamedItem("Id").getNodeValue();
				if(code.equals(sample.substring(0, 4))){
					indianEmployee.add(sample);
					count++;
					employeeId = indianEmployee.get(indianEmployee.size()-1);
					int id = Integer.parseInt(employeeId.substring(4));
					id++;
					employeeId = code + String.valueOf(id);
				}
			}
			if(count == 0){
				employeeId = "INEM1001";
			}

		}

		else if(code.equals("USEM")){
			sample = "";
			java.util.List<String> employeelist = new java.util.ArrayList<>();
			int length =0;
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				
				sample = document.getElementsByTagName("employee").item(loopCounter).getAttributes().getNamedItem("Id").getNodeValue();
				if(code.equals(sample.substring(0, 4))){
					employeelist.add(sample);
					length++;
					employeeId = employeelist.get(employeelist.size()-1);
					int id = Integer.parseInt(employeeId.substring(4));
					id++;
					employeeId = code + String.valueOf(id);
				}

			}
			if(length == 0){
				employeeId = "USEM1001";
			}
				
			
		}

			

		log.info("\t\tThe dynamically generated employee id: " + employeeId);
		System.out.println();
		if(list.getLength() == 0){
			System.out.print("\t\t\t\t\t\t\tEnter the employee first name: ");
			employeeFirstName = bufferedReaderObject.readLine();
			while(!validations.nameValidation(employeeFirstName)){
				log.warn("\t\tPlease enter the correct valid name");
				System.out.print("\t\t\t\t\tEnter the employee first name: ");
				employeeFirstName = bufferedReaderObject.readLine();
			}
			user.setFirstName(employeeFirstName);
			employeeFirstName = user.getFirstName();
		
	
			System.out.print("\t\t\t\t\tEnter the employee last name: ");
			employeeLastName = bufferedReaderObject.readLine();
			while(!validations.nameValidation(employeeLastName)){
				log.warn("\t\tPlease enter the valid name");
				System.out.print("\t\t\t\t\tEnter the employee last name: ");
				employeeLastName = bufferedReaderObject.readLine();
			}	
			user.setLastName(employeeLastName);
			employeeLastName = user.getLastName();
	

			emailID = employeeLastName + "." + employeeFirstName + "@enhisecure.com";

			log.info("Employee emailID: " + emailID + "\n");
			user.setEmailID(emailID);
			emailID = user.getEmailID();
		

			designation = "Head of Manager"; 
			department = "HR";
			salary= "1000000";
			boolean s = true;

			reporting = "None";			

			status = "Active";		
		}
		else{
			System.out.print("\t\t\t\t\tEnter the employee first name: ");
			employeeFirstName = bufferedReaderObject.readLine();
			while(!validations.nameValidation(employeeFirstName)){
				log.warn("\t\tPlease enter the correct valid name");
				System.out.print("\t\t\t\t\tEnter the employee first name: ");
				employeeFirstName = bufferedReaderObject.readLine();
			}
			user.setFirstName(employeeFirstName);
			employeeFirstName = user.getFirstName();
		

			System.out.print("\t\t\t\t\tEnter the employee last name: ");
			employeeLastName = bufferedReaderObject.readLine();
			while(!validations.nameValidation(employeeLastName)){
				log.warn("\t\tPlease enter the correct valid name");
				System.out.print("\t\t\t\t\tEnter the employee last name: ");
				employeeLastName = bufferedReaderObject.readLine();
			}	
			user.setLastName(employeeLastName);
			employeeLastName = user.getLastName();
		
			emailID = employeeLastName + "." + employeeFirstName + "@enhisecure.com";
			log.info("\t\tEmployee emailID: " + emailID + "\n");
			user.setEmailID(emailID);
			emailID = user.getEmailID();
		

				//designation = null, department = , salary= "";
			boolean s = true;


			 reporting = null;
			while(s){
				System.out.print("\t\t\t\t\tIs this employee is a manager (yes or no): ");
				String managerInput = bufferedReaderObject.readLine();
				if(managerInput.equalsIgnoreCase("yes")){
					s = false;
					Node nodes = list.item(0);
					Element element = (Element) nodes;
					designation = "Manager";
					department = "HR";
					reporting = element.getAttribute("Id");
					salary = "1000000";
				}
				else if(managerInput.equalsIgnoreCase("no")){
					s = false;
					department = validations.departmentValidation();
					user.setDepartment(department);
					department = user.getDepartment();

					designation = validations.designationValidation(department);
					user.setDesignation(designation);
					designation = user.getDesignation();
				
					int count = 0;
					des.employeeFileDecryption();
					String filePath = "../Database/employee_data.xml";
					File fileTwo = new File(filePath);
					Document documentOne = documentBuilder.parse(fileTwo);
					NodeList lists = documentOne.getElementsByTagName("employee");
					int counter = 0;
					for(int loopCounter = 0; loopCounter < lists.getLength(); loopCounter++){
			
						Node nodes = lists.item(loopCounter);
						Element elements = (Element) nodes;
						if(elements.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager")){
							counter++;
						}
					}
					if(counter == 0){
						Node nodes = list.item(0);
						Element element = (Element) nodes;
						reporting = element.getAttribute("Id");
						salary = validations.salaryValidation(designation);		
						log.info("\t\tEmployee salary: " + salary + "\n");
					}
					else{
						int activeManagerCount = 0;
						for(int loopCounter = 0; loopCounter < lists.getLength(); loopCounter++){
							Node node = lists.item(loopCounter);
							Element element = (Element) node;
							if(element.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager") && element.getElementsByTagName("Status").item(0).getTextContent().equals("Active")){
								activeManagerCount++;
							}
						}
						
						if(activeManagerCount == 0){
							reporting = "None";
						}
						else{
							int counts = 1;
							log.info("\t\tList of all Active Managers with their manager ID\n");
							log.info("\t\t =======================\n\t\t\t\t\t\t\t\t\t|Manager ID " + "    " + "Manager Name |\n\t\t\t\t\t\t\t\t\t =======================\n");
							for(int loopCounter = 0; loopCounter < lists.getLength(); loopCounter++){
								Node node = lists.item(loopCounter);
								Element element = (Element) node;
								if(element.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager") && element.getElementsByTagName("Status").item(0).getTextContent().equals("Active")){
									//System.out.println("\t\t\t\t\t\t  " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
									System.out.println("\t\t\t\t\t\t " + counts + ". "+ element.getAttribute("Id") + "	" + element.getElementsByTagName("FirstName").item(0).getTextContent());
									counts++;
									activeManagerList.add(element.getAttribute("Id"));
								
								}
							}

							
							
							reporting = assigningManager(activeManagerList, filePathOne, counts);
						}

							salary = validations.salaryValidation(designation);		
							System.out.println("\t\t\t\t\tEmployee salary: " + salary);
							
					}		
					 		
				}
				else{
					s = true;
					System.out.println("\t\t\t\t\tPlease enter correct option");
				}
			}

		}
			user.setReporting(reporting);

			System.out.print("\t\t\t\t\tEnter the employee phone number: ");
			phoneNumber = bufferedReaderObject.readLine();
			while(!validations.phoneNumberValidation(phoneNumber)){
				log.info("\t\tPlease enter valid mobile number");
				System.out.print("\t\t\t\t\t\t\tEnter the employee phone number: ");
				phoneNumber = bufferedReaderObject.readLine();
			}
			user.setPhoneNumber(phoneNumber);
			phoneNumber = user.getPhoneNumber();
			
			

			//System.out.print("\t\t\t\t\tEnter the address of employee: ");
			address = validations.addressValidation(employeeId);
			while(address.equals("Error")){
				System.out.println("\t\t\t\tPlease enter valid input");
				address = validations.addressValidation(employeeId);
			}
			System.out.println("\t\t\t\t\tAddress of the Employee: " + address);
			user.setAddress(address);
			address = user.getAddress();

			dateOfJoin = employee.getEmployeeDateDetails();		
			user.setDateOfJoin(dateOfJoin);
			//System.out.println(dateOfJoin);

			status = validations.statusEmployee();
			user.setStatus(status);

			System.out.print("\t\t\t\tIs all the information given is correct?[y/n]: ");
			String informationCheck = bufferedReaderObject.readLine();
			while(!((informationCheck.equalsIgnoreCase("y")) || (informationCheck.equalsIgnoreCase("n")))){
				System.out.println("Please enter valid input");
				System.out.print("Do you want to assign the new manager [y/n]: ");
				informationCheck = bufferedReaderObject.readLine();	
			}		
	
			if(informationCheck.equalsIgnoreCase("n")){
				insertEmployee();
			}
			else{
				Element element = document.createElement("employee");
				rootElement.appendChild(element);
				Attr attribute = document.createAttribute("Id");
				attribute.setValue(employeeId);
				element.setAttributeNode(attribute);

				Element firstNameElement = document.createElement("FirstName");
				firstNameElement.appendChild(document.createTextNode(employeeFirstName));
				element.appendChild(firstNameElement);
	
	
				Element lastNameElement = document.createElement("LastName");
				lastNameElement.appendChild(document.createTextNode(employeeLastName));
				element.appendChild(lastNameElement);


				Element designationElement = document.createElement("Designation");
				designationElement.appendChild(document.createTextNode(designation));
				element.appendChild(designationElement);

				Element departmentElement = document.createElement("Department");
				departmentElement.appendChild(document.createTextNode(department));
				element.appendChild(departmentElement);
			
				Element salaryElement = document.createElement("Salary");
				salaryElement.appendChild(document.createTextNode(salary));
				element.appendChild(salaryElement);

				Element reportingElement = document.createElement("Reporting");
				reportingElement.appendChild(document.createTextNode(reporting));
				element.appendChild(reportingElement);

				Element emailIDElement = document.createElement("EmailID");
				emailIDElement.appendChild(document.createTextNode(emailID));
				element.appendChild(emailIDElement);

				Element phoneNumberElement = document.createElement("PhoneNumber");
				phoneNumberElement.appendChild(document.createTextNode(phoneNumber));
				element.appendChild(phoneNumberElement);

				Element addressElement = document.createElement("address");
				addressElement.appendChild(document.createTextNode(address));
				element.appendChild(addressElement);

				Element dateElement = document.createElement("DateOfJoin");
				dateElement.appendChild(document.createTextNode(dateOfJoin));
				element.appendChild(dateElement);

				Element statusElement = document.createElement("Status");
				statusElement.appendChild(document.createTextNode(status));
				element.appendChild(statusElement);


		
					
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(document);
			
				StreamResult streamResult = new StreamResult(new java.io.File(filePathOne));

				transformer.transform(source, streamResult);
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				des.employeeFileEncryption();
				System.out.println("The employee data given is encrypted successfully");
				
	
			//csv file
			String csvFile = "../Login_Database/logindetails.csv";

				Console console = System.console();
				if(console == null){
					System.out.println("\t\t\t\t\tPlease enter password");
					return;
				}
				EncryptionDecryption ed = new EncryptionDecryption();
				System.out.print("\t\t\t\t\tEnter the password: ");
				char[] characters  = console.readPassword();
				String password = String.valueOf(characters);
				String message = "Dear " + employeeFirstName + ",\nThe Login credentials provided by the admin.\nUsername: " + employeeId + "Password: " + password + "\nPlease change the password.";
				smsSending(message, phoneNumber);
				System.out.println("\t\t\t\t\t\tMessage sent successfully............");
				String passEnc = ed.encrypt(password); 
				saveRecord(employeeId, passEnc, csvFile);

			SendEmail email = new SendEmail();
			email.employeeJoiningMail(employeeFirstName, dateOfJoin);
			email.sendEmail(emailID, employeeFirstName, dateOfJoin);
		}
		}catch(Exception e){
			System.out.println("\t\t\t\t\tDue to some error you have to give the inputs again");
			insertEmployee();
		}	
		
	}
	

	public static void smsSending(String message, String number) throws Exception{
		try
		{
		
		String apiKey="iqwcrAnuVQIE8lRaHvz6kJbZT3df9x7DpCYM12g5KOBUyhmsjtqzpFLWmvYHnQoNxRkJK4eDBZ9b8uOh";
		String sendId="FSTSMS";
		//important step...
		message=URLEncoder.encode(message, "UTF-8");
		String language="english";
		
		String route="p";
		
		
		String myUrl="https://www.fast2sms.com/dev/bulk?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;
		
		//sending get request using java..
		
		URL url=new URL(myUrl);
		
		HttpsURLConnection con=(HttpsURLConnection)url.openConnection();
		
		
		con.setRequestMethod("GET");
		
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("cache-control", "no-cache");
		
		
		int code=con.getResponseCode();
		
		StringBuffer response=new StringBuffer();
		
		BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		while(true)
		{
			String line=br.readLine();
			if(line==null)
			{
				break;
			}
			response.append(line);
		}
			
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String assigningManager(java.util.ArrayList<String> activeManagerList, String filePathOne, int counts) throws Exception{
			String reporting = "";
	try{
		File file = new File(filePathOne);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);

		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("\t\t\t\t\t\tSelect the Manager from the list: ");
		int choice = Integer.parseInt(bufferedReaderObject.readLine());
		NodeList list = document.getElementsByTagName("employee");
		for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
			Node nodeOne = list.item(loopCounter);
			Element element = (Element) nodeOne;
			if(choice > counts-1 || choice <= 0){
				System.out.println("Please select correct option");
				reporting = assigningManager(activeManagerList, filePathOne, counts);
				break;
			}
			else if(element.getAttribute("Id").equals(activeManagerList.get(choice-1))){
				reporting = element.getAttribute("Id");
			}
		}
	}catch(NumberFormatException e){
		System.out.println("Please enter only numbers");
	}
	return reporting;
	}


	/**
	*This method is used to save login details of the employee or the contractor given by the Master.
	*@param username This parameter is used to take the employee or contractor ID as the input for the method.
	*@param password This parameter is used to take the password provided by the Master
	*@param filePath This parameter is used to mention the path for the file of login details.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void saveRecord(String username, String password, String filePath){
		try{
			java.io.FileWriter fileWriter = new java.io.FileWriter(filePath, true);
			java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(fileWriter);
			java.io.PrintWriter printWriter = new java.io.PrintWriter(bufferedWriter);

			printWriter.println(username + "," + password);
			printWriter.flush();
			printWriter.close();

			System.out.println("\t\t\t\t\tLogin record for the employee from the Admin is given successfully");

		}catch(Exception e){
			System.out.println("\t\t\t\t\tLogin details are not stored due to some internal error");	
		}

	}


	/**
	*This method is used to insert the details of the contractors to store in the database.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void insertContractor() throws Exception{
	try{
		DES des = new DES();
		//des.contractorFileDecryption();
		String filepath = "../Encrypted_Database/contractors_data.xml";
		File fileOne = new File(filepath);
		//DES des = new DES();
		if(!fileOne.exists()){
			String stringTwo = "../Database/contractor_data.xml";
			File fileTwo = new File(stringTwo);
			fileTwo.createNewFile();
			java.io.FileOutputStream fos = new java.io.FileOutputStream(stringTwo);
			String str="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><contractor-details>" + "\n" + "</contractor-details>";
			byte b[] = str.getBytes();
 
        		for(int i=0;i<b.length;i++){
		            fos.write(b[i]);
        		}
         
        		fos.close();
		}
 		else{
			des.contractorFileDecryption();
		}

		String emailID = "";
		Contractor contractor = new Contractor();
		Validation validations = new Validation();
		String filePathOne = "../Database/contractor_data.xml";
		File file = new File(filePathOne);
		java.util.ArrayList<String> activeManagerList = new java.util.ArrayList<String>();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		Element rootElement = document.getDocumentElement();
		int choice = 0;
		boolean flag = true;
		User user = new User();
		
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		
		String contractorId = "";
		NodeList list = document.getElementsByTagName("contractor");
		String sample = "";

		String code = validations.organizationContractorCode();
		if(code.equals("INCN")){
			int count = 0;
			java.util.List<String> indianContractor = new java.util.ArrayList<>();
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				sample = document.getElementsByTagName("contractor").item(loopCounter).getAttributes().getNamedItem("Id").getNodeValue();
				if(code.equals(sample.substring(0, 4))){
					indianContractor.add(sample);
					count++;
					contractorId = indianContractor.get(indianContractor.size()-1);
					int id = Integer.parseInt(contractorId.substring(4));
					id++;
					contractorId = code + String.valueOf(id);
				}
			}
			if(count == 0){
				contractorId = "INCN1001";
			}

		}

		else if(code.equals("USCN")){
			sample = "";
			int length =0;
				java.util.List<String> contractorlist = new java.util.ArrayList<>();
				for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
					
					sample = document.getElementsByTagName("contractor").item(loopCounter).getAttributes().getNamedItem("Id").getNodeValue();
					if(code.equals(sample.substring(0, 4))){
						contractorlist.add(sample);
						length++;
						contractorId = contractorlist.get(contractorlist.size()-1);
						int id = Integer.parseInt(contractorId.substring(4));
						id++;
						contractorId = code + String.valueOf(id);
					}

				}
				if(length == 0){
					contractorId = "USCN1001";
				}
	
		}

		System.out.println("\t\t\t\t\tThe dynamically generated contractor id: " + contractorId);

		

		System.out.print("\t\t\t\t\tEnter the contractor first name: ");
		String employeeFirstName = bufferedReaderObject.readLine();
		while(!validations.nameValidation(employeeFirstName)){
			System.out.println("\t\t\t\t\tPlease enter the valid first name");
			System.out.print("\t\t\t\t\tEnter the contractor first name: ");
			employeeFirstName = bufferedReaderObject.readLine();
		}
		user.setFirstName(employeeFirstName);
		employeeFirstName = user.getFirstName();
		

		System.out.print("\t\t\t\t\tEnter the contractor last name: ");
		String employeeLastName = bufferedReaderObject.readLine();
		while(!validations.nameValidation(employeeLastName)){
			System.out.print("\t\t\t\t\tPlease enter the valida last name");
			System.out.print("\t\t\t\t\tEnter the contractor last name: ");
			employeeLastName = bufferedReaderObject.readLine();
		}	
		user.setLastName(employeeLastName);
		employeeLastName = user.getLastName();
		


		emailID = employeeLastName + "." + employeeFirstName + "@enhisecure.com";

		System.out.println("Contractor emailID: " + emailID);
		user.setEmailID(emailID);
		emailID = user.getEmailID();

		String designation = null, department = null, salary= "";

			String reporting = null;
				department = validations.departmentValidation();
				user.setDepartment(department);
				department = user.getDepartment();

				designation = validations.designationValidation(department);
				user.setDesignation(designation);
				designation = user.getDesignation();
				
				int count = 0;
				des.employeeFileDecryption();
				String filePath = "../Database/employee_data.xml";
				File fileTwo = new File(filePath);
				Document documentOne = documentBuilder.parse(fileTwo);
				NodeList lists = documentOne.getElementsByTagName("employee");
				int counter = 0;
				for(int loopCounter = 0; loopCounter < lists.getLength(); loopCounter++){

					Node nodes = lists.item(loopCounter);
					Element elements = (Element) nodes;
					if(elements.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager")){
						counter++;
					}
				}
				if(counter == 0){
					reporting = "None";
					salary = validations.salaryValidation(designation);		
					System.out.println("\t\t\t\t\tEmployee salary: " + salary);
				}
				else{
					int counts = 1;
					System.out.println("\t\t\t\t\t\tList of all Active Managers with their manager ID");
					System.out.println("\t\t\t\t\t\t =======================\n\t\t\t\t\t\t|Manager ID " + "    " + "Manager Name |\n\t\t\t\t\t\t =======================");
					for(int loopCounter = 0; loopCounter < lists.getLength(); loopCounter++){
						Node node = lists.item(loopCounter);
						Element element = (Element) node;
						if(element.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager") && element.getElementsByTagName("Status").item(0).getTextContent().equals("Active")){
						
							System.out.println("\t\t\t\t\t\t " + counts + ". "+ element.getAttribute("Id") + "	" + element.getElementsByTagName("FirstName").item(0).getTextContent());
							counts++;
							activeManagerList.add(element.getAttribute("Id"));
						}
			
					}
					
					reporting = assigningManager(activeManagerList, "../Database/employee_data.xml", counts);
				}

					salary = validations.salaryValidation(designation);		
					System.out.println("\t\t\t\t\tEmployee salary: " + salary);
						
			
					 				

		user.setReporting(reporting);

		System.out.print("\t\t\t\t\tEnter the contractor phone number: ");
		String phoneNumber = bufferedReaderObject.readLine();
		while(!validations.phoneNumberValidation(phoneNumber)){
			System.out.println("\t\t\t\t\tPlease enter valid mobile number");
			System.out.print("\t\t\t\t\tEnter the employee phone number: ");
			phoneNumber = bufferedReaderObject.readLine();
		}
		user.setPhoneNumber(phoneNumber);
		phoneNumber = user.getPhoneNumber();

		String address = validations.addressValidation(contractorId);
		while(address.equals("Error")){
			System.out.println("\t\t\t\tPlease enter valid input");
			address = validations.addressValidation(contractorId);
		}
		System.out.println("\t\t\t\t\tAddress of the Employee: " + address);
		user.setAddress(address);
		address = user.getAddress();
		
		String dateOfJoin = contractor.getContractorJoinDateDetails();
		user.setDateOfJoin(dateOfJoin);

		String dateOfTermination = contractor.getContractorTerminationDateDetails(dateOfJoin);
		user.setDateOfTermination(dateOfTermination);	
		
		String status = validations.statusEmployee();
		user.setStatus(status);

		Element element = document.createElement("contractor");
		rootElement.appendChild(element);
		Attr attribute = document.createAttribute("Id");
		attribute.setValue(contractorId);
		element.setAttributeNode(attribute);

		Element firstNameElement = document.createElement("FirstName");
		firstNameElement.appendChild(document.createTextNode(employeeFirstName));
		element.appendChild(firstNameElement);


		Element lastNameElement = document.createElement("LastName");
		lastNameElement.appendChild(document.createTextNode(employeeLastName));
		element.appendChild(lastNameElement);


		Element designationElement = document.createElement("Designation");
		designationElement.appendChild(document.createTextNode(designation));
		element.appendChild(designationElement);

		Element departmentElement = document.createElement("Department");
		departmentElement.appendChild(document.createTextNode(department));
		element.appendChild(departmentElement);
		
		Element salaryElement = document.createElement("Salary");
		salaryElement.appendChild(document.createTextNode(salary));
		element.appendChild(salaryElement);

		Element emailIDElement = document.createElement("EmailID");
		emailIDElement.appendChild(document.createTextNode(emailID));
		element.appendChild(emailIDElement);


		Element reportingElement = document.createElement("Reporting");
		reportingElement.appendChild(document.createTextNode(reporting));
		element.appendChild(reportingElement);

		Element phoneNumberElement = document.createElement("PhoneNumber");
		phoneNumberElement.appendChild(document.createTextNode(phoneNumber));
		element.appendChild(phoneNumberElement);

		Element addressElement = document.createElement("address");
		addressElement.appendChild(document.createTextNode(address));
		element.appendChild(addressElement);

		Element dateOneElement = document.createElement("DateOfJoin");
		dateOneElement.appendChild(document.createTextNode(dateOfJoin));
		element.appendChild(dateOneElement);

		Element dateTwoElement = document.createElement("DateOfTermination");
		dateTwoElement.appendChild(document.createTextNode(dateOfTermination));
		element.appendChild(dateTwoElement);

		Element statusElement = document.createElement("Status");
		statusElement.appendChild(document.createTextNode(status));
		element.appendChild(statusElement);

				
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		
		StreamResult streamResult = new StreamResult(new java.io.File(filePathOne));

		transformer.transform(source, streamResult);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		//DES des = new DES();
		des.contractorFileEncryption();
		//System.out.println("the data of contractors are encrypted successfully");
		des.contractorFileDecryption();

			String csvFile = "../Login_Database/logindetails.csv";

				Console console = System.console();
				if(console == null){
					System.out.println("\t\t\t\t\tPlease enter password");
					return;
				}
				EncryptionDecryption ed = new EncryptionDecryption();
				System.out.print("\t\t\t\t\tEnter the password: ");
				char[] characters  = console.readPassword();
				String password = String.valueOf(characters); 
				String passEnc = ed.encrypt(password);
				saveRecord(contractorId, passEnc, csvFile);
				SendEmail email = new SendEmail();
				email.contractorJoiningMail(employeeFirstName, dateOfJoin, dateOfTermination);
				email.sendContractorEmail(emailID, employeeFirstName, dateOfJoin, dateOfTermination);	
			
	}catch(Exception e){
		System.out.println("\t\t\t\t\tDue to some input error you have to give the details again");
		insertContractor();
	}
	
	}

	/**
	*This method is used to perform the inser operation for the employee and contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	 static void addDetails() throws Exception{
		int input = 0;
		try{
			do{
				BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
				log.info("\t\tEnter your choice\n\t\t\t\t\t\t\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n\t\t\t\t\t\t\t\t1. Employee\n\t\t\t\t\t\t\t\t2. Contractor\n\t\t\t\t\t\t\t\t3. Back\n");
				System.out.print("\t\t\t\t\t\t\tEnter option: ");
				input = Integer.parseInt(bufferedReaderObject.readLine());
				if(input == 1){
					insertEmployee();
				}
				else if(input == 2){
					insertContractor();
				}
				else{
					log.debug("\t\tPlease wait.....");
					break;
				}
			}while(input != 3);
		}catch(NumberFormatException e){
			System.out.println("Please enter numbers");
		}
	}
}
