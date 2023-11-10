package bin;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.w3c.dom.Document;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
/**
*This class contains all the validations that are done for the all employee and contractor attributes.
*/
public class Validation{

	/**
	*This method is used to generate the organization code.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static String organizationCode() throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		int choice = 0;
		String code = null;
		while(flag){
			System.out.println("\t\t\t\t\t==========================\n\t\t\t\t\tEnter any option\n\t\t\t\t\t==========================\n\t\t\t\t\t1. Indian Employee\n\t\t\t\t\t2. US Employee");
			System.out.print("\t\t\t\t\tEnter option: ");
			choice = Integer.parseInt(bufferedReaderObject.readLine());
			if(choice == 1){
				code =  "INEM";
				flag = false;
			}
			else if(choice == 2){
				code = "USEM";
				flag = false;
			}
			else{
				flag = true;
			}
		}
		return code;
	}

	/**
	*This method is used to parse the date which is given by the user.
	*@param date This parameter contains the date that should be contained in some format.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static boolean dateParse(String date){
		if (date.trim().equals("")){
		    return true;
		}
		else{
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd-MM-yyyy");
		    sdfrmt.setLenient(false);
	    	    try{
	        	Date javaDate = sdfrmt.parse(date); 
		    }
	    	    catch (ParseException e){
		        return false;
		    }
	    	return true;
		}	
	}
	/**
	*This method is used to generate the organization code for the contractors.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static String organizationContractorCode() throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		int choice = 0;
		String code = null;
		while(flag){
			System.out.println("\t\t\t\t\t=======================\n\t\t\t\t\tEnter any option\n\t\t\t\t\t=======================\n\t\t\t\t\t1. Indian Contractor\n\t\t\t\t\t2. US Contractor");
			System.out.print("\t\t\t\t\tEnter option: ");
			choice = Integer.parseInt(bufferedReaderObject.readLine());
			if(choice == 1){
				code =  "INCN";
				flag = false;
			}
			else if(choice == 2){
				code = "USCN";
				flag = false;
			}
			else{
				flag = true;
			}
		}
		return code;
	}

	/**
	*This method is used to validate the ID of the contractor.
	*@param contractorID This parameter contains the id of the particular contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static String contractorIDValidation(String contractorID) throws Exception{
		String inputCode = organizationContractorCode();
		String output = "";
		contractorID = contractorID.substring(4);
		int id =  Integer.parseInt(contractorID);

			if(id < 9999){
				id = id + 1;
			}
			else{
				System.out.println("Please increase the total size of the employees");
			}
		output = inputCode + String.valueOf(id);
		return output;
	}

	/**
	*This method is used to validate the ID of the employee.
	*@param employeeID This parameter contains the id of the particular employee.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static String employeeIDValidation(String employeeID) throws Exception{
		String inputCode = organizationCode();
		String output = "";
		employeeID = employeeID.substring(4);
		int id =  Integer.parseInt(employeeID);

			if(id < 9999){
				id = id + 1;
			}
			else{
				System.out.println("Please increase the total size of the employees");
			}
		output = inputCode + String.valueOf(id);
		return output;
	}

	/**
	*This method is used to validate the government holidays for the given date.
	*@param date This parameter is the date given by the user.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static boolean governmentHolidays(String date)throws Exception{
		int day = Integer.parseInt(date.substring(0, 2));
		int month = Integer.parseInt(date.substring(3, 5));
		String holiday = "";
		boolean flag =true;
		if((day == 1) && (month == 1)){
			System.out.print("New year Day");
			flag = false;
		}
		else if((day == 14) && (month == 1)){
			System.out.print("Sankranthi");
			flag = false; 
		}
		else if((day == 26) && (month == 1)){
			System.out.print("Republic Day");
		}
		else if((day == 2) && (month == 4)){
			System.out.print("Good Friday");
			flag = false;
		}
		else if((day == 13) && (month == 4)){
			System.out.print("Ugadhi");
			flag = false;
		}
		else if((day == 1) && (month == 5)){
			System.out.print("May Day");
			flag = false;
		}
		else if((day == 14) && (month == 5)){
			System.out.print("Ramzan");
			flag = false;
		}
		else if((day == 21) && (month == 7)){
			System.out.print("Bakrid");
			flag = false;
		}
		else if((day == 15) && (month == 8)){
			System.out.print("Independence Day");
			flag = false;
		}
		else if((day == 10) && (month == 9)){
			System.out.print("Ganesh Chaturthi");
			flag = false;
		}
		else if((day == 2) && (month == 10)){
			System.out.print("Gandhi Jayanthi");
			flag = false;
		}
		else if((day == 15) && (month == 10)){
			System.out.print("Dussehra");
			flag = false;
		}
		else if((day == 25) && (month == 12)){
			System.out.print("Christmas");
			flag = false;
		}
		return flag;
	}

	/**
	*This method is used to validate the date of join for the employee or the contractor.
	*@param dateOfJoin This parameter contains the joining date of the particular employee or the contractor.
	*/
	public static boolean dateOfJoinValidation(String dateOfJoin) {
		try{
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
		String dateOfEstablished = "15-06-2015";
		Date dateOne = simpleDate.parse(dateOfJoin);
		Date dateTwo = simpleDate.parse(dateOfEstablished);
		if(dateOne.compareTo(dateTwo) >= 0){
			return true;
		}
		else
			return false;
		}catch(ParseException e){
			return false;
		}
		
	}

	/**
	*This method is used to check the date format.
	*@param dateOfJoin This parameter contains the joining date of the particular employee or the contractor.
	*@param dateOfTermination This parameter contains the termination date of the particular employee or the contractor.
	*/
	public static boolean dateChecking(String dateOfJoin, String dateOfTermination){
		try{
			SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
			Date dateOne = simpleDate.parse(dateOfJoin);
			Date dateTwo = simpleDate.parse(dateOfTermination);
			if(dateOne.before(dateTwo)){
				return true;
			}
			else{
				return false;
			}
		}catch(ParseException e){
			return false;
		}
	}

	/**
	*This method is used to calculate the weekends in every month.
	*@param dateOfJoin This parameter contains the joining date of the particular employee or the contractor.
	*/
	public static String weekendValidation(String dateOfJoin){
		int day = Integer.parseInt(dateOfJoin.substring(0, 2));
		int month = Integer.parseInt(dateOfJoin.substring(3, 5));
		int year = Integer.parseInt(dateOfJoin.substring(6,10));
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.set(year, month-1, day);
		String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		int n;
		n = c.get(c.DAY_OF_WEEK);
		return (days[n-1]);
	}

	/**
	*This method is used to validate the name of the employee or the contractor.
	*@param employeeName This parameter will take the employee name as the input.
	*/
	public static boolean nameValidation(String employeeName){
		Pattern pattern = Pattern.compile("^[A-Z][a-z]*$");
		if(employeeName == null)
			return false;
		Matcher match = pattern.matcher(employeeName);
		if(match.matches()){
			return match.matches();
		}
		else
			return false;
	}

	/**
	*This method is used to validate emailID of the employee or the contractor.
	*@param emailID This parameter contains the emailID of the particular employee or the contractor.
	*/
	public static boolean emailValidation(String emailID){
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
		if(emailID == null)
			return false;
		Matcher match = pattern.matcher(emailID);
		if(match.matches()){
			return match.matches();
		}
		else
			return false;
	}
	/**
	*This method is used to validate phone number of the employee or the contractor.
	*@param phoneNumber This parameter contains the phone number of the particular employee or the contractor.
	*/
	public static boolean phoneNumberValidation(String phoneNumber){
		Pattern pattern = Pattern.compile("^[7-9]{1}[0-9]{9}$");
		if(phoneNumber == null)
			return false;
		Matcher match = pattern.matcher(phoneNumber);
		if(match.matches()){
			return match.matches();
		}
		else
			return false;
	}

	/**
	*This method is used to validate address of the employee or the contractor.
	*@param employeeId This parameter contains the employeeId of the particular employee or the contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static String addressValidation(String employeeId) throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		java.util.ArrayList<String> arrayListOne = new java.util.ArrayList<String>();
		java.util.ArrayList<String> arrayListTwo = new java.util.ArrayList<String>();
		String filePath = "../Database/location.xml";
		File file = new File(filePath);
		Document document = documentBuilder.parse(file);
		int input = 0;
		String address = "";
		NodeList list = document.getElementsByTagName("pincode");
		int i = 1, count = 0, counter = 0;
		System.out.println("\t\t\t\t=========================\n\t\t\t\t\tSelect Pincode\n\t\t\t\t=========================");
		for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
			
			
			Node node = list.item(loopCounter);
			Element element = (Element) node;
			if(employeeId.substring(0, 2).equals("IN")){
				
				if("India".equals(element.getElementsByTagName("country").item(0).getTextContent())){
					System.out.println("\t\t\t\t" + i + ". " +element.getAttribute("pin"));
					i++;
					count++;
					arrayListOne.add(element.getAttribute("pin"));
				}						
			}
			else if(employeeId.substring(0, 2).equals("US")){
				if("United states".equals(element.getElementsByTagName("country").item(0).getTextContent())){
					System.out.println("\t\t\t\t" + i + ". " + element.getAttribute("pin"));
					i++;
					counter++;
					arrayListTwo.add(element.getAttribute("pin"));
				}
			}
		}
		System.out.print("\t\t\t\tEnter the input: ");
		input = Integer.parseInt(bufferedReaderObject.readLine());
		if(employeeId.substring(0, 2).equals("IN")){
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node node = list.item(loopCounter);
				Element element = (Element) node;
				if(input > count || input <= 0){
					address = "Error";
				}
				else{
				
					if(element.getAttribute("pin").equals(arrayListOne.get(input-1))){
						String subRegion = element.getElementsByTagName("sub-region").item(0).getTextContent();
						String region = element.getElementsByTagName("region").item(0).getTextContent();
						String city = element.getElementsByTagName("city").item(0).getTextContent();
						String state = element.getElementsByTagName("state").item(0).getTextContent();
						String country = element.getElementsByTagName("country").item(0).getTextContent();
						address = subRegion + ", " + region + ", " + city + ", " + state + ", " + country + "-"+ element.getAttribute("pin");
					}
							
				}
			}
		}
		else if(employeeId.substring(0, 2).equals("US")){
			for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
				Node node = list.item(loopCounter);
				Element element = (Element) node;
				if(input > counter || input <= 0){
					address = "Error";
				}
				else{
					
					if(element.getAttribute("pin").equals(arrayListTwo.get(input-1))){
						String subRegion = element.getElementsByTagName("sub-region").item(0).getTextContent();
						String region = element.getElementsByTagName("region").item(0).getTextContent();
						String city = element.getElementsByTagName("city").item(0).getTextContent();
						String country = element.getElementsByTagName("country").item(0).getTextContent();
						address = subRegion + ", " + region + ", " + city + ", " + country + "-"+ element.getAttribute("pin");
					}
								
				}
			}
		}

	
		return address;
		
	}

	/**
	*This method is used to validate department for the employee or the contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static String departmentValidation() throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		int choice = 0;
		String department = null;
		while(flag){
			System.out.println("\t\t\t\t\t===================\n\t\t\t\t\tEnter any option\n\t\t\t\t\t===================\n\t\t\t\t\t1. IT department\n\t\t\t\t\t2. IAM department");
			System.out.print("\t\t\t\t\tEnter option: ");
			choice = Integer.parseInt(bufferedReaderObject.readLine());
			if(choice == 1){
				department = "IT department";
				flag = false;
			}
			else if(choice == 2){
				department = "IAM department";
				flag = false;
			}
			else{
				flag = true;
			}		
		}
		return department;
	}

	/**
	*This method is used to validate designation for the employee or the contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static String designationValidation(String department) throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		int choice = 0;
		String designation = null;
		if(department.equalsIgnoreCase("IT department")){
			while(flag){
				System.out.println("\t\t\t\t\t===================\n\t\t\t\t\tEnter any option\n\t\t\t\t\t===================\n\t\t\t\t\t1. IT\n\t\t\t\t\t2. System engineer");
				System.out.print("\t\t\t\t\tEnter option: ");
				choice = Integer.parseInt(bufferedReaderObject.readLine());
				if(choice == 1){
					designation = "IT";
					flag = false;
				}
				else if(choice == 2){
					designation = "System Engineer";
					flag = false;
				}
				else{
					flag = true;
				}
			}
		}
		else if(department.equalsIgnoreCase("IAM department")){
			while(flag){
				System.out.println("\t\t\t\t\t===================\n\t\t\t\t\tEnter any option\n\t\t\t\t\t===================\n\t\t\t\t\t1. Intern\n\t\t\t\t\t2. IAM Engineer");
				System.out.print("\t\t\t\t\tEnter option: ");
				choice = Integer.parseInt(bufferedReaderObject.readLine());
				if(choice == 1){
					designation = "Intern";
					flag = false;
				}
				else if(choice == 2){
					designation = "IAM Engineer";
					flag = false;
				}
				else{
					flag = true;
				}
			}
		}		
		return designation;
	}

	/**
	*This method is used to validate salary for the employee or the contractor.
	*/
	public static String salaryValidation(String designation){
		String salary = "";
		if(designation.equals("IT")){
			salary = "350000";
		}
		else if(designation.equals("Intern")){
			salary = "390000";
		}
		else if(designation.equals("IAM Engineer")){
			salary = "900000";
		}
		else if(designation.equals("System Engineer")){
			salary = "400000";
		}
		return salary;
	}

	/**
	*This method is used to validate status of the employee or the contractor.
	*/
	public static String statusEmployee(){
		return "Active";
	}

	/**
	*This method is used to validate date of termination for the employee or the contractor.
	*@param dateOfTermination This parameter is used to get the date of termination of the particular employee or the contractor. 
	*/
	public static boolean checkingDates(String dateOfTermination){
		boolean flag = false;
		try{
			SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
			Date dateOne = simpleDate.parse(dateOfTermination);
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
    			Date dateTwo = new Date();  
		
			if(dateOne.before(dateTwo)){
				flag = false;
			}
			else{
				flag = true;
			}
		}catch(ParseException e){
			System.out.println("Error in giving date");
		}
		return flag;
	}



}