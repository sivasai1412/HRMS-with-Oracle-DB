package bin;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.sql.*;

public class UpdateUser{
	

	/**
	*This method is used to perform update operations on the employee and contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	 static void updateDetails() throws Exception{
		int input = 0;
		try{
			do{
				BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("\t\t\t\t\t==============================\n\t\t\t\t\tUpdating Details\n\t\t\t\t\t==============================\n\t\t\t\t\t1. Employee\n\t\t\t\t\t2. Contractor\n\t\t\t\t\t3. Back");
				System.out.print("\t\t\t\t\tEnter option: ");
				input = Integer.parseInt(bufferedReaderObject.readLine());
				if(input == 1){
					updateEmployeeDetails();
				}
				else if(input == 2){
					updateContractorDetails();
				}
				else{
					System.out.println("\t\t\t\t\tPlease wait....");
					break;
				}
				
			}while(input != 3);
		}catch(NumberFormatException e){
			System.out.println("Please enter numbers");
			updateDetails();
		}
	}

	/**
	*This method is used to update the details of the employee in the organisation.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void updateEmployeeDetails() throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		DES des = new DES();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		int input = 0;
		try{
			des.employeeFileDecryption();
			User user = new User();
			String filePath = "../Database/employee_data.xml";
			File file = new File(filePath);	
			//boolean flag = true;
			//int choice = 0;
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList list = document.getElementsByTagName("employee");
			System.out.println("\t\t\t\t\t\tList of all employees with employee ID");
			System.out.println("\t\t\t\t\t\t =======================\n\t\t\t\t\t\t| ID " + "    " + "Employee Name |\n\t\t\t\t\t\t =======================");
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodeOne = list.item(loopCounter);
				Element element = (Element) nodeOne;
				System.out.println("\t\t\t\t\t\t  " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
			}
			System.out.print("\t\t\t\t\t\tEnter the employee Id: ");
			String employeeId = bufferedReaderObject.readLine();
			String employeeName = "", lastName = "", designation = "", department = "", address = "", emailID = "", salary = "", phoneNumber = "", reporting = "", status = "", dateOfJoin = ""; 

			/*for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node node = list.item(loopCounter);
				Element element = (Element) node;
				if(employeeId.equalsIgnoreCase(element.getAttribute("Id"))){
					employeeName = element.getElementsByTagName("FirstName").item(0).getTextContent();
					lastName = element.getElementsByTagName("LastName").item(0).getTextContent();
					designation = element.getElementsByTagName("Designation").item(0).getTextContent();
					department = element.getElementsByTagName("Department").item(0).getTextContent();
					phoneNumber = element.getElementsByTagName("PhoneNumber").item(0).getTextContent();
					salary = element.getElementsByTagName("Salary").item(0).getTextContent();
					reporting = element.getElementsByTagName("Reporting").item(0).getTextContent();
					emailID = element.getElementsByTagName("EmailID").item(0).getTextContent();
					address = element.getElementsByTagName("address").item(0).getTextContent();
					status = element.getElementsByTagName("Status").item(0).getTextContent();
					dateOfJoin = element.getElementsByTagName("DateOfJoin").item(0).getTextContent();
				}
			} */

			int position = 0;
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodeTwo = list.item(loopCounter);
				Element element = (Element) nodeTwo;
				if(employeeId.equalsIgnoreCase(element.getAttribute("Id"))){
						position = loopCounter;		

				}
			}
			Node employees = document.getElementsByTagName("employee").item(position);
			NodeList employeesList = employees.getChildNodes();
			Validation validations = new Validation();
			boolean flag = true;
			int choice = 0;
			
			
			String oldValue = "";
			while(flag){
				System.out.println("\t\t\t\t\t\t=======================\n\t\t\t\t\t\tSelect any option\n\t\t\t\t\t\t=======================\n\t\t\t\t\t\t1. Name\n\t\t\t\t\t\t2. EmailId\n\t\t\t\t\t\t3. Phone number\n\t\t\t\t\t\t4. Address\n\t\t\t\t\t\t5. Back");
				System.out.print("\t\t\t\t\t\tEnter option: ");
				choice = Integer.parseInt(bufferedReaderObject.readLine());
				if(choice == 1){
				System.out.print("\t\t\t\t\t\t\tEnter the employee name: ");
				employeeName = bufferedReaderObject.readLine();
				while(!validations.nameValidation(employeeName)){
					System.out.println("\t\t\t\t\t\t\tPlease enter the correct valid name");
					System.out.print("\t\t\t\t\t\t\tEnter the employee name: ");
					employeeName = bufferedReaderObject.readLine();
				}
				for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
					Node nodeThree = employeesList.item(loopCounter);
					if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
						Element employeeElement = (Element) nodeThree;
						if("FirstName".equals(employeeElement.getNodeName())){
							employeeElement.setTextContent(employeeName);
							System.out.println("\t\t\t\t\t\t\tDone successfully");
							String field = "First_name";
							updateUsingHibernate(field, employeeId, employeeName);
							//flag = false;
						}
					}
				}
				}
				else if(choice == 2){
				System.out.print("\t\t\t\t\t\t\tEnter the employee emailID: ");
				emailID = bufferedReaderObject.readLine();
				while(!validations.emailValidation(emailID)){
					System.out.println("\t\t\t\t\t\t\tPlease enter the correct email id");
					System.out.print("\t\t\t\t\t\t\tEnter the employee email id: ");
					emailID = bufferedReaderObject.readLine();
				}
				for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
					Node nodeThree = employeesList.item(loopCounter);
					if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
						Element employeeElement = (Element) nodeThree;
						if("EmailID".equals(employeeElement.getNodeName())){
							employeeElement.setTextContent(emailID);
							System.out.println("\t\t\t\t\t\t\tDone successfully");
							//flag = false;
							String field = "Email_id";
							updateUsingHibernate(field, employeeId, emailID);
						}
					}
				}
				}
				else if(choice == 3){
				System.out.print("\t\t\t\t\t\t\tEnter the employee phone number: ");
				phoneNumber = bufferedReaderObject.readLine();
				while(!validations.phoneNumberValidation(phoneNumber)){
					System.out.println("\t\t\t\t\t\t\tPlease enter valid mobile number");
					System.out.print("\t\t\t\t\t\t\tEnter the employee phone number: ");
					phoneNumber = bufferedReaderObject.readLine();
				}
				for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
					Node nodeThree = employeesList.item(loopCounter);
					if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
						Element employeeElement = (Element) nodeThree;
						if("PhoneNumber".equals(employeeElement.getNodeName())){
							employeeElement.setTextContent(phoneNumber);
							System.out.println("\t\t\t\t\t\t\tDone successfully");
							//flag = false;
							String field = "Phone_number";
							updateUsingHibernate(field, employeeId, phoneNumber);
						}
					}
				}
				}
				else if(choice == 4){
					System.out.print("\t\t\t\t\t\t\tEnter the employee address: ");
					address = bufferedReaderObject.readLine();
					for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
						Node nodeThree = employeesList.item(loopCounter);
						if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
							Element employeeElement = (Element) nodeThree;
							if("address".equals(employeeElement.getNodeName())){
								employeeElement.setTextContent(address);
								System.out.println("\t\t\t\t\t\t\tDone successfully");
								//flag = false;
							}
						}
					}
				}

				else{
					flag = false;
					break;
				}
			}
			/*user.setId(employeeId);
			user.setFirstName(employeeName);
			user.setLastName(lastName);
			user.setDesignation(designation);
			user.setDepartment(department);
			user.setAddress(address);
			user.setEmailID(emailID);
			user.setPhoneNumber(phoneNumber);
			user.setReporting(reporting);
			user.setStatus(status);
			user.setDateOfJoin(dateOfJoin); */
		
			/*Configuration con = new Configuration();
			con.configure("hibernate.cfg.xml");
			SessionFactory factory = con.buildSessionFactory();
			Session session = factory.openSession();

			Transaction transaction = session.beginTransaction();
			//System.out.println(user.getFirstName());
			User ud = session.load(User.class, employeeId);
			session.update(user);
			transaction.commit();
			factory.close();
			session.close();
			*/
				
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
		
			StreamResult streamResult = new StreamResult(new java.io.File(filePath));

			transformer.transform(source, streamResult);
		
			des.employeeFileEncryption();
			//System.out.println("\t\t\t\t\t\t\tThe employee data given is encrypted successfully");

		}catch(SAXException | NumberFormatException e){
			e.printStackTrace();
			updateEmployeeDetails();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found");
		}
	}


	public static void updateUsingHibernate(String field, String employeeId, String value) throws Exception{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@192.168.56.102:1521:orcl","SYSTEM", "SYSTEM");
			Statement stmt=con.createStatement();  
			String query = "update employee set " + field + "='" + value + "' where id='" + employeeId + "'";
			System.out.println("");
			ResultSet rs=stmt.executeQuery(query);  
 			/*while (rs.next()) {
                        	String name = rs.getString("name");
                        	System.out.println(name);
         		}  */
			con.close();  
		}catch(Exception e){
			System.out.println(e);
		}
	}

	/**
	*This method is used to update the details of the contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void updateContractorDetails() throws Exception{

		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		DES des = new DES();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		int input = 0;
		try{
			des.contractorFileDecryption();
			String filePath = "../Database/contractor_data.xml";
			File file = new File(filePath);	
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList list = document.getElementsByTagName("contractor");
			System.out.println("\t\t\t\t\t\tList of all employees with contractor ID");
			System.out.println("\t\t\t\t\t\t =======================\n\t\t\t\t\t\t| ID " + "    " + "Contractor Name |\n\t\t\t\t\t\t =======================");
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodeOne = list.item(loopCounter);
				Element element = (Element) nodeOne;
				System.out.println("\t\t\t\t\t\t  " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
			}
			System.out.print("\t\t\t\t\t\tEnter the contractor Id: ");
			String employeeId = bufferedReaderObject.readLine();
			int position = 0;
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodeTwo = list.item(loopCounter);
				Element element = (Element) nodeTwo;
				if(employeeId.equalsIgnoreCase(element.getAttribute("Id"))){
						position = loopCounter;		

				}
			}
			Node employees = document.getElementsByTagName("contractor").item(position);
			NodeList employeesList = employees.getChildNodes();
			Validation validations = new Validation();
			boolean flag = true;
			int choice = 0;
			String employeeName = null, address=null;
			String emailID = null, phoneNumber = null;
			String oldValue = "";
			while(flag){
				System.out.println("\t\t\t\t\t\t=======================\n\t\t\t\t\t\tSelect any option\n\t\t\t\t\t\t=======================\n\t\t\t\t\t\t1. Name\n\t\t\t\t\t\t2. EmailId\n\t\t\t\t\t\t3. Phone number\n\t\t\t\t\t\t4. Address\n\t\t\t\t\t\t5. Back");
				System.out.print("\t\t\t\t\t\tEnter option: ");
				choice = Integer.parseInt(bufferedReaderObject.readLine());
				if(choice == 1){
				System.out.print("\t\t\t\t\t\t\tEnter the contractor name: ");
				employeeName = bufferedReaderObject.readLine();
				while(!validations.nameValidation(employeeName)){
					System.out.println("\t\t\t\t\t\t\tPlease enter the correct valid name");
					System.out.print("\t\t\t\t\t\t\tEnter the contractor name: ");
					employeeName = bufferedReaderObject.readLine();
				}
				for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
					Node nodeThree = employeesList.item(loopCounter);
					if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
						Element employeeElement = (Element) nodeThree;
						if("FirstName".equals(employeeElement.getNodeName())){
							employeeElement.setTextContent(employeeName);
							System.out.println("\t\t\t\t\t\t\tDone successfully");
							//flag = false;
						}
					}
				}
				}
				else if(choice == 2){
				System.out.print("\t\t\t\t\t\t\tEnter the contractor emailID: ");
				emailID = bufferedReaderObject.readLine();
				while(!validations.emailValidation(emailID)){
					System.out.println("\t\t\t\t\t\t\tPlease enter the correct email id");
					System.out.print("\t\t\t\t\t\t\tEnter the employee email id: ");
					emailID = bufferedReaderObject.readLine();
				}
				for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
					Node nodeThree = employeesList.item(loopCounter);
					if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
						Element employeeElement = (Element) nodeThree;
						if("EmailID".equals(employeeElement.getNodeName())){
							employeeElement.setTextContent(emailID);
							System.out.println("\t\t\t\t\t\t\tDone successfully");
							//flag = false;
						}
					}
				}
				}
				else if(choice == 3){
				System.out.print("\t\t\t\t\t\t\tEnter the contractor phone number: ");
				phoneNumber = bufferedReaderObject.readLine();
				while(!validations.phoneNumberValidation(phoneNumber)){
					System.out.println("\t\t\t\t\t\t\tPlease enter valid mobile number");
					System.out.print("\t\t\t\t\t\t\tEnter the contractor phone number: ");
					phoneNumber = bufferedReaderObject.readLine();
				}
				for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
					Node nodeThree = employeesList.item(loopCounter);
					if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
						Element employeeElement = (Element) nodeThree;
						if("PhoneNumber".equals(employeeElement.getNodeName())){
							employeeElement.setTextContent(phoneNumber);
							System.out.println("\t\t\t\t\t\t\tDone successfully");
							//flag = false;
						}
					}
				}
				}
				else if(choice == 4){
					System.out.print("\t\t\t\t\t\t\tEnter the contractor address: ");
					address = bufferedReaderObject.readLine();
					for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
						Node nodeThree = employeesList.item(loopCounter);
						if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
							Element employeeElement = (Element) nodeThree;
							if("address".equals(employeeElement.getNodeName())){
								employeeElement.setTextContent(address);
								System.out.println("\t\t\t\t\t\t\tDone successfully");
								//flag = false;
							}
						}
					}
				}

				else{
					flag = false;
					break;
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
		
			StreamResult streamResult = new StreamResult(new java.io.File(filePath));

			transformer.transform(source, streamResult);
		
			des.contractorFileEncryption();
			//System.out.println("The employee data given is encrypted successfully");

		}catch(SAXException | NumberFormatException e){
			e.printStackTrace();
			updateEmployeeDetails();
		}
		catch(FileNotFoundException e){
			System.out.println("file not found");
		}
			

	}

}