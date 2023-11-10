package bin;
import java.io.FileNotFoundException;
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
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.sql.*;

public class DeleteUser{



	public static void deleteUsingHibernate(String field, String employeeId, String value) throws Exception{
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
	*This method is used to perform the deletion operations on the employee and contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void inactiveDetails() throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		Manager manager = new Manager();
		int choice = 0;
		try{
		do{
			System.out.println("\t\t\t\t\t==========================\n\t\t\t\t\tSelect any option\n\t\t\t\t\t==========================\n\t\t\t\t\t1. Remove Employee\n\t\t\t\t\t2. Remove Contractor\n\t\t\t\t\t3. Remove Manager\n\t\t\t\t\t4. Back");
			System.out.print("\t\t\t\t\tEnter option: ");
			choice = Integer.parseInt(bufferedReaderObject.readLine());
			if(choice == 1){
				inactiveEmployee();
			}
			else if(choice == 2){
				inactiveContractor();
			}
			else if(choice == 3){
				manager.inactiveManager();
			}
			else{
				System.out.println("Please wait...");
				break;
			}

		}while(choice != 4);
			
		}catch(NumberFormatException e){
			System.out.println("Please enter the numbers");
			inactiveDetails();
		}
	}
	

	

	/**
	*This method is used to make inactive the employees and update the details to the database.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void inactiveEmployee() throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		DES des = new DES();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		int input = 0;
		try{
			des.employeeFileDecryption();
			String filePath = "../Database/employee_data.xml";
			File file = new File(filePath);	
			boolean flag = true;
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
			String  reporting = "";
			System.out.print("\t\t\t\t\t\tEnter the employee Id: ");
			String employeeId = bufferedReaderObject.readLine();
			int position = 0;
				
			
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodeTwo = list.item(loopCounter);
				Element element = (Element) nodeTwo;
				if(employeeId.equalsIgnoreCase(element.getAttribute("Id"))){
					if(!element.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager")){
						position = loopCounter;
					}
					else{
						//System.out.println("The person is an manager");
						position = -1;
					}
				}
			}
			
			String dateOfTermination = "";
			if(position < 0){
				System.out.println("The person is an manager");
			}
			else{
				Node employees = document.getElementsByTagName("employee").item(position);
				NodeList employeesList = employees.getChildNodes();
				Validation validations = new Validation();
				//boolean flag = true;
				int choice = 0;
				String status = "In-Active";
				String oldValue = "";

					for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
						Node nodeThree = employeesList.item(loopCounter);
						Element employeeElement = (Element) nodeThree;
						if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
							if("Status".equals(employeeElement.getNodeName())){
								employeeElement.setTextContent(status);
								System.out.println("\t\t\t\t\t\tThe employee is removed from the organization");	
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
   								LocalDateTime now = LocalDateTime.now();
								dateOfTermination = dtf.format(now);
								Element dateOfTerminationElement = document.createElement("DateOfTermination");
								dateOfTerminationElement.appendChild(document.createTextNode(dateOfTermination));			
								employees.appendChild(dateOfTerminationElement);
								String field = "status";
								deleteUsingHibernate(field, employeeId, status);
								deleteUsingHibernate("date_of_termination", employeeId, dateOfTermination);
							} 
						}
					}

			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
		
			StreamResult streamResult = new StreamResult(new java.io.File(filePath));

			transformer.transform(source, streamResult);
			
			des.employeeFileEncryption();
						//System.out.println("The employee data given is encrypted successfully");

		
		}catch(SAXException | NumberFormatException e){
			e.printStackTrace();
			inactiveEmployee();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found");
		}
	}


	/**
	*This method is used to make inactive the contractors and update the details to the database.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void inactiveContractor() throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		DES des = new DES();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		int input = 0;
		try{
			des.contractorFileDecryption();
			String filePath = "../Database/contractor_data.xml";
			File file = new File(filePath);	
			//boolean flag = true;
			//int choice = 0;
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList list = document.getElementsByTagName("contractor");
			System.out.println("\t\t\t\t\t\tList of all contractors with contractor ID");
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
			String dateOfTermination = null;
			Node employees = document.getElementsByTagName("contractor").item(position);
			NodeList employeesList = employees.getChildNodes();
			Validation validations = new Validation();
			boolean flag = true;
			int choice = 0;
			String status = "In-Active";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
   			LocalDateTime now = LocalDateTime.now();
			dateOfTermination = dtf.format(now);
			String oldValue = "";
				for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
					Node nodeThree = employeesList.item(loopCounter);
					if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
						Element employeeElement = (Element) nodeThree;
						if("Status".equals(employeeElement.getNodeName())){
							employeeElement.setTextContent(status);
							System.out.println("\t\t\t\t\t\tThe contractor is removed from the organization");			
							
						}
						if("DateOfTermination".equals(employeeElement.getNodeName())){
							employeeElement.setTextContent(dateOfTermination);
						}

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
			inactiveEmployee();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found");
		}
		catch(Exception e){
			System.out.println("Error");
		}
	}
}