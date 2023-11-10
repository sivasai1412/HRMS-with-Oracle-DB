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
import javax.xml.transform.OutputKeys;
import java.io.FileNotFoundException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
*This class is used to implement the update operations on the employees and contractors details.
*/
public class UpdateUser{
	
	static Logger log = Logger.getLogger(UpdateUser.class);
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
				log.info("\t\t\tUpdating Details\n\t\t\t\t\t\t\t\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n\t\t\t\t\t\t\t\t\t1. Employee\n\t\t\t\t\t\t\t\t\t2. Contractor\n\t\t\t\t\t\t\t\t\t3. Back\n");
				System.out.print("\t\t\t\t\t\t\tEnter option: ");
				input = Integer.parseInt(bufferedReaderObject.readLine());
				if(input == 1){
					updateEmployeeDetails();
				}
				else if(input == 2){
					updateContractorDetails();
				}
				else{
					log.debug("\t\tPlease wait....");
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
			String filePath = "../Database/employee_data.xml";
			File file = new File(filePath);	
			//boolean flag = true;
			//int choice = 0;
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList list = document.getElementsByTagName("employee");
			log.info("\t\tList of all employees with employee ID\n");
			log.info("\t\t| ID " + "    " + "Employee Name |\n\t\t\t\t\t\t\t\t ~~~~~~~~~~~~~~~~~~~~~~");
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodeOne = list.item(loopCounter);
				Element element = (Element) nodeOne;
				System.out.println("\t\t\t\t\t\t\t\t  " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
			}
			System.out.print("\t\t\t\t\t\t\tEnter the employee Id: ");
			String employeeId = bufferedReaderObject.readLine();
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
			String employeeName = null, address=null;
			String emailID = null, phoneNumber = null;
			String oldValue = "";
			while(flag){
				log.info("\t\tSelect any option\n\t\t\t\t\t\t\t\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n\t\t\t\t\t\t\t\t1. Name\n\t\t\t\t\t\t\t\t2. EmailId\n\t\t\t\t\t\t\t\t3. Phone number\n\t\t\t\t\t\t\t\t4. Address\n\t\t\t\t\t\t\t\t5. Back\n");
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
							log.debug("\t\tDone successfully\n");
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
								log.debug("\t\tDone successfully\n");
							//flag = false;
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
							log.debug("\t\tDone successfully\n");
							//flag = false;
						}
					}
				}
				}
				else if(choice == 4){

					String oldAddress = "";
					for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
						Node nodeThree = list.item(loopCounter);
						Element element = (Element) nodeThree;
						
						oldAddress = element.getElementsByTagName("address").item(0).getTextContent();
						
					}
					log.info("\t\tThe old address you want to change: " + oldAddress);

					System.out.print("\t\t\t\t\t\t\tEnter the employee address: ");
					System.out.println();
					address = validations.addressValidation(employeeId);
					System.out.println();
					while(address.equals("Error")){
						System.out.println("\t\t\t\t\t\t\tEnter the employee address: ");
						address = validations.addressValidation(employeeId);
						System.out.println();
					}
					
					for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
						Node nodeThree = employeesList.item(loopCounter);
						if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
							Element employeeElement = (Element) nodeThree;
							if("address".equals(employeeElement.getNodeName())){
								employeeElement.setTextContent(address);
								log.debug("\t\tDone successfully\n");
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
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			des.employeeFileEncryption();
			//System.out.println("\t\t\t\t\t\t\tThe employee data given is encrypted successfully");

		}catch(SAXException | NumberFormatException e){
			e.printStackTrace();
			updateEmployeeDetails();
		}
		catch(FileNotFoundException e){
			log.error("\t\tFile not found");
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
			log.info("\t\tList of all employees with contractor ID\n");
			log.info("\t\t| ID " + "    " + "Contractor Name |\n\t\t\t\t\t\t\t\t ~~~~~~~~~~~~~~~~~~~~~~");
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodeOne = list.item(loopCounter);
				Element element = (Element) nodeOne;
				System.out.println("\t\t\t\t\t\t\t\t  " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
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
				log.info("\t\tSelect any option\n\t\t\t\t\t\t\t\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n\t\t\t\t\t\t\t\t1. Name\n\t\t\t\t\t\t\t\t2. EmailId\n\t\t\t\t\t\t\t\t3. Phone number\n\t\t\t\t\t\t\t\t4. Address\n\t\t\t\t\t\t\t\t5. Back\n");
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
							log.debug("\t\tDone successfully\n");
							//flag = false;
						}
					}
				}
				}
				else if(choice == 2){
				System.out.print("\t\t\t\t\t\t\tEnter the contractor emailID: ");
				emailID = bufferedReaderObject.readLine();
				while(!validations.emailValidation(emailID)){
					log.warn("\t\tPlease enter the correct email id");
					System.out.print("\t\t\t\t\t\t\tEnter the employee email id: ");
					emailID = bufferedReaderObject.readLine();
				}
				for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
					Node nodeThree = employeesList.item(loopCounter);
					if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
						Element employeeElement = (Element) nodeThree;
						if("EmailID".equals(employeeElement.getNodeName())){
							employeeElement.setTextContent(emailID);
							log.debug("\t\tDone successfully\n");
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
							log.debug("\t\t\t\t\t\t\tDone successfully\n");
							//flag = false;
						}
					}
				}
				}
				else if(choice == 4){
					String oldAddress = "";
					for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
						Node nodeThree = list.item(loopCounter);
						Element element = (Element) nodeThree;
						
						oldAddress = element.getElementsByTagName("address").item(0).getTextContent();
						log.info("\t\tThe old address you want to change: " + oldAddress);
					}
					
					System.out.print("\t\t\t\t\t\t\tEnter the contractor address: ");
					address = bufferedReaderObject.readLine();
					for(int loopCounter = 0; loopCounter < employeesList.getLength(); loopCounter++){
						Node nodeThree = employeesList.item(loopCounter);
						if(nodeThree.getNodeType() == Node.ELEMENT_NODE){
							Element employeeElement = (Element) nodeThree;
							if("address".equals(employeeElement.getNodeName())){
								employeeElement.setTextContent(address);
								log.debug("\t\tDone successfully\n");
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
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			des.contractorFileEncryption();
			//System.out.println("The employee data given is encrypted successfully");

		}catch(SAXException | NumberFormatException e){
			e.printStackTrace();
			updateEmployeeDetails();
		}
		catch(FileNotFoundException e){
			log.error("file not found");
		}
			

	}

}