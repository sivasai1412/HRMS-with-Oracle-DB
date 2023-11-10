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
public class Manager{
	
	/**
	*This method is used to list the contractors with the manager Id.
	*@param employeeId This parameter is the ID of the particular employee through which we can check the status.
	*@param c This parameter is the count of the employees under the manager.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void listContractors(String employeeId, int c) throws Exception{
		java.util.ArrayList<String> contractorList = new java.util.ArrayList<String>();
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		DES des = new DES();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		String input = "";
		try{
			des.contractorFileDecryption();
			String filePathOne = "../Database/contractor_data.xml";
			File fileOne = new File(filePathOne);

			boolean flag = true;
			//int choice = 0;
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(fileOne);

			NodeList list = document.getElementsByTagName("contractor");
			
			int count =0;
			//String employeeId = "";
			int position = 0;			
			//System.out.println("\t\t\t\t\t\tBelow contractors under the employee: ");
			//System.out.println("\t\t\t\t\t\t =====================================\n\t\t\t\t\t\t| ID " + "    " + "Employee Name |\n\t\t\t\t\t\t =====================================");
			//int j = 1;
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
			
				Node node = list.item(loopCounter);
				Element element = (Element) node;
				if(employeeId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent())){
					System.out.println("\t\t\t\t\t\t  " + c +".   " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
					c++;
				
				}		

			}
			//changeReportingContractors(employeeId);
			des.contractorFileEncryption();
		}catch(SAXException | NumberFormatException e){
			e.printStackTrace();
			inactiveManager();
		}
		catch(FileNotFoundException e){
			System.out.println("There are no contractors in the organisation");
		}
	
	}

	/**
	*This method is used to change the reporting managers for the contractors.
	*@param employeeId This parameter is the ID of the particular employee through which we can check the status.
	*@param manager This parameter is the name of the manager that is newly assigned.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void changeReportingContractors(String employeeId, String manager) throws Exception{
		java.util.ArrayList<String> contractorList = new java.util.ArrayList<String>();
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		DES des = new DES();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		String input = "";
		try{
			des.contractorFileDecryption();
			String filePathOne = "../Database/contractor_data.xml";
			File fileOne = new File(filePathOne);

			boolean flag = true;
			//int choice = 0;

			Document documentOne = documentBuilder.parse(fileOne);
			NodeList list = documentOne.getElementsByTagName("contractor");
			
				for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				
					Node node = list.item(loopCounter);
					Element element = (Element) node;
					if(employeeId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent())){
						
						NodeList li = element.getChildNodes();
						for(int i = 0; i < li.getLength(); i++){
							Node n = li.item(i);
							Element e = (Element) n;
							if("Reporting".equals(e.getNodeName())){
								if(e.getTextContent().equalsIgnoreCase(employeeId)){
									e.setTextContent(manager);
									flag = true;
								}
							}
						}
					}
					else{
						flag = false;
					}		

				}
				if(!flag){
					System.out.println("There is no contractors under this manager");
				}			


			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource sourceOne = new DOMSource(documentOne);
			
			StreamResult streamResultOne = new StreamResult(new java.io.File(filePathOne));
			
			transformer.transform(sourceOne, streamResultOne);
			
			
			des.contractorFileEncryption();
		}catch(SAXException | NumberFormatException e){
			e.printStackTrace();
			inactiveManager();
		}
		catch(FileNotFoundException e){
			System.out.println("There are no contractors in the organisation");
		}



	}

	/**
	*This method is used to assign the manager for the employees whose manager is removed from the organisation.
	*@param employeeId This parameter is the ID of the particular employee through which we can check the status.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static String assigningManager(String employeeId) throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		String filePath = "../Database/employee_data.xml";
		File file = new File(filePath);
		String reporting = "", idList = "";
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		java.util.ArrayList<String> managerList = new java.util.ArrayList<String>();
		int counters = 0;
		NodeList list = document.getElementsByTagName("employee");
		System.out.print("Do you want to assign the new manager [y/n]: ");
		String y = bufferedReaderObject.readLine();
		while(!((y.equalsIgnoreCase("y")) || (y.equalsIgnoreCase("n")))){
			System.out.println("Please enter valid input");
			System.out.print("Do you want to assign the new manager [y/n]: ");
			y = bufferedReaderObject.readLine();	
		}
		if(y.equalsIgnoreCase("y")){
			int countingActiveManagers = 1;
			System.out.println("\t\t\t\t\t\tList of all Active Managers after removing with manager ID");
			System.out.println("\t\t\t\t\t\t =====================\n\t\t\t\t\t\t| ID " + "    " + "Manager Name |\n\t\t\t\t\t\t =====================");
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodeOne = list.item(loopCounter);
				Element element = (Element) nodeOne;	
				if(element.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager") && element.getElementsByTagName("Status").item(0).getTextContent().equals("Active")){
					counters++;
					idList = element.getAttribute("Id");
					System.out.println("\t\t\t\t\t\t  " + countingActiveManagers + ".   " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
					managerList.add(idList);
					countingActiveManagers++;
				}
			}
			int newManagerOption;
			System.out.print("Select option: ");
			newManagerOption = Integer.parseInt(bufferedReaderObject.readLine());
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodeOne = list.item(loopCounter);
				Element element = (Element) nodeOne;
				if(newManagerOption > counters || newManagerOption <= 0){
					System.out.println("Please select correct option");
					assigningManager(employeeId);
				}
	
				if(element.getAttribute("Id").equals(managerList.get(newManagerOption-1))){
					reporting = element.getAttribute("Id");
				}
			}
		}
		else if(y.equalsIgnoreCase("n")){
	
		}


		
		return reporting;
	}

	/**
	*This method is used to inactive the manager and all the employee under this manager will be reporting to the other manager.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void inactiveManager() throws Exception{
		java.util.ArrayList<String> managerList = new java.util.ArrayList<String>();
		java.util.ArrayList<String> employeeList = new java.util.ArrayList<String>();
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		DES des = new DES();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		String input = "";
		try{
			des.employeeFileDecryption();
			des.contractorFileDecryption();
			String filePath = "../Database/employee_data.xml";
			String filePathOne = "../Database/contractor_data.xml";
			File file = new File(filePath);	
			File fileOne = new File(filePathOne);
			String idList = "";
			boolean flag = true;
			int inputChoice = 0;
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);

			NodeList list = document.getElementsByTagName("employee");
			
			int count =0;
			System.out.println("\t\t\t\t\t\tList of all Active Managers with manager ID");
			System.out.println("\t\t\t\t\t\t =====================\n\t\t\t\t\t\t| ID " + "    " + "Manager Name |\n\t\t\t\t\t\t =====================");
			int j = 1;
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node nodeOne = list.item(loopCounter);
				Element element = (Element) nodeOne;	
				if(element.getElementsByTagName("Designation").item(0).getTextContent().equals("Manager") && element.getElementsByTagName("Status").item(0).getTextContent().equals("Active")){
					count++;
					idList = element.getAttribute("Id");
					System.out.println("\t\t\t\t\t\t  " + j + ".   " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
					managerList.add(idList);
					j++;
				}
			}
			String employeeId = "";
			int position = 0;
			int x = 0;
			if(count == 0){
				System.out.println("\t\t\t\t\t\tThere is no manager");
			}
			else{

				String  reporting = "";
				System.out.print("\t\t\t\t\t\tSelect the manager you want to remove: ");
				inputChoice = Integer.parseInt(bufferedReaderObject.readLine());
				
				for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
					Node nodeOne = list.item(loopCounter);
					Element element = (Element) nodeOne;	
					while(inputChoice > count || inputChoice <= 0){
						System.out.println("Please select correct option");
						inactiveManager();
					}
					System.out.println(managerList);
					if(element.getAttribute("Id").equals(managerList.get(inputChoice-1))){
						if(element.getElementsByTagName("Status").item(0).getTextContent().equals("In-Active")){
							System.out.println("The Manager is already removed from the organisation");
						}
						else{
							employeeId = element.getAttribute("Id");
							x = 1;
							position = loopCounter;
							//break;
						}
					}
				}
			
			if(x == 1){	
				int c = 1;
				System.out.println("\t\t\t\t\t\tBelow employees that are reporting to the manager");
				System.out.println("\t\t\t\t\t\t =======================================\n\t\t\t\t\t\t|S.No" + "	 ID " + "    " + "Employee/Contractor Name |\n\t\t\t\t\t\t =======================================");

				for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				
					Node node = list.item(loopCounter);
					Element element = (Element) node;
					if(employeeId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent())){
						System.out.println("\t\t\t\t\t\t  " + c + ".   " + element.getAttribute("Id") + "     " + element.getElementsByTagName("FirstName").item(0).getTextContent());
						c++;
					}
					else{
						c = 0;
					}		

				}



				

				String dateOfTermination = "";
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
							if("Status".equals(employeeElement.getNodeName()) && employeeElement.getTextContent().equals("Active")){
								System.out.println(employeeElement.getTextContent());
								employeeElement.setTextContent(status);
								System.out.println("\t\t\t\t\t\tThe employee is removed from the organization");	
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
   								LocalDateTime now = LocalDateTime.now();
								dateOfTermination = dtf.format(now);
								Element dateOfTerminationElement = document.createElement("DateOfTermination");
								dateOfTerminationElement.appendChild(document.createTextNode(dateOfTermination));			
								employees.appendChild(dateOfTerminationElement);
							} 
						}
					}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			
			StreamResult streamResult = new StreamResult(new java.io.File(filePath));
			
			transformer.transform(source, streamResult);

			String manager = assigningManager(employeeId);
				for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				
					Node node = list.item(loopCounter);
					Element element = (Element) node;
					if(employeeId.equalsIgnoreCase(element.getElementsByTagName("Reporting").item(0).getTextContent())){
						
						NodeList li = element.getChildNodes();
						for(int i = 0; i < li.getLength(); i++){
							Node n = li.item(i);
							Element e = (Element) n;
							if("Reporting".equals(e.getNodeName())){
								if(e.getTextContent().equalsIgnoreCase(employeeId)){
									e.setTextContent(manager);
								}
							}
						}
					}		

				}
				changeReportingContractors(employeeId, manager);

			TransformerFactory transformerFactoryOne = TransformerFactory.newInstance();
			Transformer transformerOne = transformerFactoryOne.newTransformer();
			DOMSource sourceOne = new DOMSource(document);
			
			StreamResult streamResultOne = new StreamResult(new java.io.File(filePath));
			
			transformer.transform(sourceOne, streamResultOne);
			
				
			des.employeeFileEncryption(); 
		
		}
					}
		}catch(SAXException | NumberFormatException e){
			e.printStackTrace();
			inactiveManager();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found");
		}


	}
}