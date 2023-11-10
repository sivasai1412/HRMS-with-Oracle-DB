package bin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Console;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.FileNotFoundException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
public class Login{

	static Logger log = Logger.getLogger(Login.class);
	/**
	*This method is used to check whether the string is alphabetics or the string is in the form of numbers.
	*@param firstString The String is taken as input in this method and check whether the input is in numerical format or not
	*@return bool This returns the true or false 
	*/
	public static boolean isNumeric(String firstString){
		boolean bool = true;
		char[] character = firstString.toCharArray();
		for(int loopCounter = 0; loopCounter < character.length; loopCounter++){
			if(character[loopCounter] < '0' || character[loopCounter] > '9')
				bool = false;
			else
				bool = true;
		}
		return bool;
	}

	/**
	*This method is used to perform the login operation for the admin.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	private static void adminLogin() throws Exception{
		Main object = new Main();
		Console console = System.console();
		if(console == null){
			System.out.println("\t\tPlease enter password");
			return;
		}
		System.out.println("\t\t==========================\n\t\tAdmin Login\n\t\t==========================");
		log.info("\t\tEnter the username: ");
		String username = console.readLine();
		log.info("\t\tEnter the password: ");
		char[] characters  = console.readPassword();
		String password = String.valueOf(characters); 
		if(username.equalsIgnoreCase("admin") && password.equals("enhisecure@2020")){
			log.debug("\t\tLogin Successfully\n");
			object.operations();
		}
		else if(isNumeric(username)){
			log.warn("\t\tPlease enter the strings");
			adminLogin();
		}
		else{
			log.warn("\t\tPlease enter correct details to login");
			adminLogin();
		}
	}

	/**
	*This method is used to check the status of the employee and if the status of the employee is inactive then he can't login to the system.
	*@param employeeId This parameter is the ID of the particular employee through which we can check the status.
	*@return count This will check and returns the integer value and with that particular value we can perform the furthur operations.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	private static int employeeStatusCheck(String employeeId) throws Exception{
		boolean flag = false;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		DES des = new DES();
		int count = 0;
		try{
		des.employeeFileDecryption();
		
		String filePath = "../Database/employee_data.xml";
		File file = new File(filePath);
		Document document = documentBuilder.parse(file);
		NodeList list = document.getElementsByTagName("employee");
		for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
			Node node = list.item(loopCounter);	
			Element element = (Element) node;
			if(employeeId.equalsIgnoreCase(element.getAttribute("Id"))){
				String status = element.getElementsByTagName("Status").item(0).getTextContent();
				if(status.equals("Active")){
					//flag = true;
					count = 1;
				}
				else{
					//flag = false;
					count = 2;
					//System.out.println("\t\tYou have been removed from the organization.");
				}	
				break;
			}
			else{
				count = 3;
				
			}
		}
			
		des.employeeFileEncryption();
		}catch(FileNotFoundException e){
			System.out.println("\t\tNo employee is added");
		}
		return count;
	}

	/**
	*This method is used to perform the login operation for the employee with the username and password.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	private static void employeeLogin() throws Exception{
		Console console = System.console();
		if(console == null){
			System.out.println("\t\tPlease enter password");
			return;
		}
		String filePath = "../Login_Database/logindetails.csv";
		System.out.println("\t\t==========================\n\t\tEmployee Login\n\t\t==========================");
		log.info("\t\tEnter the username: ");
		String username = console.readLine();
		log.info("\t\tEnter the password: ");
		char[] characters  = console.readPassword();
		String password = String.valueOf(characters); 
		if(employeeStatusCheck(username) == 1){
			readRecord(username, password, filePath);
		}
		else if(employeeStatusCheck(username) == 2){
			System.out.println("\t\tYou have been removed from the organization");
		}
		else if(employeeStatusCheck(username) == 3){
			System.out.println("\t\tEmployee is not present in the organization");
		}
	
	}

	/**
	*This method is used to check the status of the contractor and if the status of the contractor is inactive then he can't login to the system.
	*@param contarctorId This parameter is the ID of the particular contractor through which we can check the status.
	*@return count This will check and returns the integer value and with that particular value we can perform the furthur operations.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	private static int contractorStatusCheck(String contractorId) throws Exception{
		boolean flag = false;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		DES des = new DES();
		int count = 0;
		try{
		des.contractorFileDecryption();

		String filePath = "../Database/contractor_data.xml";
		File file = new File(filePath);
		Document document = documentBuilder.parse(file);
		NodeList list = document.getElementsByTagName("contractor");
		for(int loopCounter = 0; loopCounter < list.getLength(); loopCounter++){
			Node node = list.item(loopCounter);	
			Element element = (Element) node;
			if(contractorId.equalsIgnoreCase(element.getAttribute("Id"))){
				String status = element.getElementsByTagName("Status").item(0).getTextContent();
				if(status.equals("Active")){
					//flag = true;
					count = 1;
				}
				else{
					//flag = false;
					count = 2;
					//System.out.println("\t\tyou have been removed from the organization");
				}	
				break;
			}
			else{
				//System.out.println("\t\tThere is no contractor with the given username, please try logging in again");
				count = 3;
			}
		}

		des.contractorFileEncryption();
		}catch(FileNotFoundException e){
			System.out.println("\t\tNo contractor is added");
		}
		return count;

	}

	/**
	*This method is used to send the one time password(OTP) for the employee to change the password.
	*@param username This parameter is the ID of the particular employee through which we can check the status.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
    	public static void run(String username) throws Exception{
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
        		boolean flag=true;
		int number = 0;
		String x = "";
        		while(flag){
                		java.util.Random rand =  new java.util.Random();
                		number = rand.nextInt(9999);
                		//System.out.println(String.format("%04d", number));
			x = String.format("%04d", number);
			for(int i = 0; i < list.getLength(); i++){
				Node n = list.item(i);
				Element element = (Element)n;
				if(username.equalsIgnoreCase(element.getAttribute("Id"))){
					String name = element.getElementsByTagName("FirstName").item(0).getTextContent();
					String emailId = element.getElementsByTagName("EmailID").item(0).getTextContent();
					SendEmail e = new SendEmail();
					e.otpMail(name, x);
					e.sendEmailOtp(emailId, name, x);
				}
			}
                		flag = otpCheck(number, username);
				changePassword(username);

        		}
		/*if(flag == false){
			changePassword(username);
			
		}*/
    	}

	/**
	*This method is used to check the OTP is correct or not.
	*@param username This parameter is the ID of the particular employee through which we can check the status.
	*@param otp This parameter is the OTP that is sent to the employee emailID.
	*@return flag This will return the true or false.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
    	public static boolean otpCheck(int otp, String username) throws Exception{
        		boolean flag = false;
        		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		log.info("\t\t\t\t\tEnter the OTP: ");
        		int s = Integer.parseInt(bufferedReaderObject.readLine());
        		if(otp == s){
            			//System.out.println("Login");
            			flag = false;
        		}
        		else{
            			System.out.println("\t\t\t\t\tWrong OTP");
				flag = true;
            			
        		}
        		return flag;
    	}

	/**
	*This method is used to change the password for the employee or the contractor.
	*@param username This parameter is the ID of the particular employee through which we can check the status.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	private static void changePassword(String username) throws Exception{
		int count = 0;
		int loopCounter = 0;
		String csvFile = "../Login_Database/logindetails.csv";
		File file = new File(csvFile);	
		java.io.FileReader fr = new java.io.FileReader(file);
		java.io.BufferedReader br =new java.io.BufferedReader(fr);

		String data = null;
		boolean flag = false;
		Console console = System.console();
		if(console == null){
			System.out.println("Please enter password");
			return;
		}
		EncryptionDecryption ed = new EncryptionDecryption();
		System.out.println("\t\t\t\t\t===============================\n\t\t\t\t\tUpdating Employee Password\n\t\t\t\t\t===============================");
		log.info("\t\t\t\t\tEnter the old password: ");
		char[] characters  = console.readPassword();
		String oldPassword = String.valueOf(characters);
		br.readLine();
		java.util.ArrayList<String[]> li = new java.util.ArrayList<String[]>();
		while((data=br.readLine())!= null){
			String[] arr = data.split(",");
			count++;
			if(arr[0].equals(username) && oldPassword.equals(ed.decrypt(arr[1]))){
				//String newPassword = passwordInput();
				//String passEnc = ed.encrypt(newPassword);
				//saveRecord(username, passEnc, csvFile);
				flag = true;
				break;
			}
			else{
				flag = false;
				//System.out.println("Please enter the correct old password");
			}
		}
		br.close();
		fr.close();

		if(flag){
			String newPassword = passwordInput();
			String passEnc = ed.encrypt(newPassword);
			saveRecord(username, passEnc, csvFile);
						
		}
		else{
			System.out.println("Please enter the correct old password");
		}
				
	}


	/**
	*This method is used to split the data in the file line wise to count the number of rows.
	*@param filePath This parameter is the path for the particular file.
	*@return length This method returns the number of rows present in the data.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static int lines(String filePath) throws Exception{
		File file = new File(filePath);
		java.io.FileInputStream fis = new java.io.FileInputStream(file);
		byte[] byteArray = new byte[(int)file.length()];
      		fis.read(byteArray);
      		String data = new String(byteArray);
      		String[] stringArray = data.split("\r\n");
		return stringArray.length;
	}

	/**
	*This method is used to save the changed password into the file with data encrypted.
	*@param username This parameter is the ID of the particular employee through which we can check the status.
	*@param password This parameter is the new changed password of the employee.
	*@param filePath This parameter is the path for the particular file.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	private static void saveRecord(String username, String password, String filePath) throws Exception{
		File file = new File(filePath);
		java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath));

		Scanner sc = new Scanner(br);
		String[][] myArray = new String[lines(filePath)][2];
		while(sc.hasNextLine()) {
         			for (int i=0; i<myArray.length; i++) {
            				String[] line = sc.nextLine().trim().split(",");
            				for (int j=0; j<line.length; j++) {
               				myArray[i][j] = line[j];
            				}
         			}
      		}

		for(int i = 0; i < myArray.length; i++){
	
			if(myArray[i][0].equals(username)){
				myArray[i][1] = password;
			}

			for(int j = 0; j < 2; j++){
				System.out.println(myArray[i][j]);
				//bw.write(myArray[i][j]);
			}
			//bw.newLine();
		}
		/*FileWriter fw = new FileWriter(filePath);
		for(int i = 0; i < myArray.length; i++){
			for(int j = 0; j < 2; j++){
				fw.write(myArray[i][j]);
			}
		}
		fw.close();*/
		java.io.FileOutputStream fos = new java.io.FileOutputStream(file);
		java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.OutputStreamWriter(fos));
		for(int i = 0; i < myArray.length; i++){
			for(int j = 0; j < 2; j++){
				bw.write(myArray[i][j] + ",");
			}
			bw.newLine();
		}
		bw.close();
	}



	/**
	*This method is used to take the input from the employee to enter password.
	*@return newPassword This returns the string that contains the new password set by the employee.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	private static String passwordInput() throws Exception{
		Console console = System.console();
		String s = "";
		log.info("\t\t\t\t\tEnter the new password: ");
		char[] newChars = console.readPassword();
		String newPassword = String.valueOf(newChars);
		log.info("\t\t\t\t\tre-enter the new password: ");
		char[] reChars = console.readPassword();
		String rePassword = String.valueOf(reChars);
		if(newPassword.equals(rePassword)){
			s = rePassword;	
		}
		return s;
	}

	/**
	*This method is used to perform operations on the employee after getting logged into system.
	*@param username This parameter is the ID of the particular employee through which we can check the status.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	private static void getEmployeeDetails(String username) throws Exception{
		int choice = 0;
		Employee employee = new Employee();
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		while(flag){
			System.out.println("\t\t\t\t======================\n\t\t\t\tSelect any option\n\t\t\t\t======================\n\t\t\t\t1. Read your details\n\t\t\t\t2. Change password\n\t\t\t\t3. Exit");
			log.info("\t\t\t\tEnter option: ");
			choice = Integer.parseInt(bufferedReaderObject.readLine());
			System.out.println();
			if(choice == 1){
				employee.updateDetails(username);
			}
			else if(choice == 2){
				run(username);
				//changePassword(username);
			}
			else{
				System.out.println("\t\t\t\tPlease wait...");
				flag = false;
			}
		
		}
	}

	/**
	*This method is used to fetch the details of the employee who logged into the system.
	*@param username This parameter is the ID of the particular employee through which we can check the status.
	*@param password This parameter is the new changed password of the employee.
	*@param filePath This parameter is the path for the particular file.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/	
	private static void readRecord(String username, String password, String filePath) throws Exception{
		File file = new File(filePath);	
		//Employee employee = new Employee();
		java.io.FileReader fr = new java.io.FileReader(file);
		java.io.BufferedReader br =new java.io.BufferedReader(fr);
		String data = null;
		boolean flag = false;
		EncryptionDecryption ed = new EncryptionDecryption();
		while((data=br.readLine())!= null){
			String[] arr = data.split(",");
			//String pass = ed.decrypt(arr[1]);
			//System.out.println(pass);
			if(arr[0].equals(username) && (ed.decrypt(arr[1]).equals(password))){
				flag = true;
				break;
			}
		}
		br.close();
		fr.close();
		
		if(flag){
			getEmployeeDetails(username);
		}
		else{
			System.out.println("\n\t\t\tPlease enter correct username and password");
		}
		
		
	}


	/**
	*This method is used to perform the login operation for the contractor with the username and password.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	private static void contractorLogin() throws Exception{
		Console console = System.console();
		if(console == null){
			System.out.println("\t\tPlease enter password");
			return;
		}
		String filePath = "../Login_Database/logindetails.csv";
		System.out.println("\t\t==========================\n\t\tContractor Login\n\t\t==========================");
		log.info("\t\tEnter the username: ");
		String username = console.readLine();
		log.info("\t\tEnter the password: ");
		char[] characters  = console.readPassword();
		String password = String.valueOf(characters); 
		//String password = console.readLine(); 
		if(contractorStatusCheck(username) == 1){
			readContractorRecord(username, password, filePath);
		}
		else if(contractorStatusCheck(username) == 2){
			System.out.println("\t\tYou have been removed from the organisation");
		}
		else if(contractorStatusCheck(username) == 3){
			System.out.println("\t\tEmployee is not present in the organisation");
		}
	
	}


	/**
	*This method is used to perform operations on the contractor after getting logged into system.
	*@param username This parameter is the ID of the particular contractor through which we can check the status.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	private static void getContractorDetails(String username) throws Exception{
		int choice = 0;
		Contractor contractor = new Contractor();
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = true;
		while(flag){
			System.out.println("\t\t\t======================\n\t\t\tSelect any option\n\t\t\t======================\n\t\t\t1. Read your details\n\t\t\t2. Change password\n\t\t\t3. Exit");
			log.info("\t\t\tEnter your choice: ");
			choice = Integer.parseInt(bufferedReaderObject.readLine());
			if(choice == 1){
				contractor.updateDetails(username);
				//flag = false;
			}
			else if(choice == 2){
				run(username);
				//changePassword(username);
				//flag = false;
			}
			else{
				System.out.println("Please wait...");
				flag = false;
			}
		
		}
	}


	/**
	*This method is used to fetch the details of the contractor who logged into the system.
	*@param username This parameter is the ID of the particular contractor through which we can check the status.
	*@param password This parameter is the new changed password of the contractor.
	*@param filePath This parameter is the path for the particular file.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/	
	private static void readContractorRecord(String username, String password, String filePath) throws Exception{
		File file = new File(filePath);	
		//Contractor contractor = new Contractor();
		java.io.FileReader fr = new java.io.FileReader(file);
		java.io.BufferedReader br =new java.io.BufferedReader(fr);
		String data = null;
		boolean flag = false;
		EncryptionDecryption ed = new EncryptionDecryption();
		while((data=br.readLine())!= null){
			String[] arr = data.split(",");
			if(arr[0].equals(username) && (ed.decrypt(arr[1]).equals(password))){
				flag = true;
				//System.out.println("Working");
				break;
			}
		}
		if(flag){
			getContractorDetails(username);
		}
		else{
			System.out.println("Please enter correct username and password");
		}
		
		
	}

	/**
	*This method is the main method of the project which is used to perform the login operations for the Master, employee and contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void main(String args[]) throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));
		int choice = 0;
		System.out.println("\t\t================================\n\t\t\tHRMS Application\n\t\t================================");
		try{
		do{
			System.out.println("\t====================\n\tUser Login\n\t====================\n\t1. Admin\n\t2. Employee\n\t3. Contractor\n\t4. Exit");
			log.info("\tEnter option: ");
			choice = Integer.parseInt(bufferedReaderObject.readLine());
			if(choice == 1){
				adminLogin();
			}
			else if(choice == 2){
				employeeLogin();
			}
			else if(choice == 3){
				contractorLogin();
			}
			else if(choice == 4){
				System.out.println("\tProgram Terminated");
				break;
			}
		}while(choice != 4);
		}catch(NumberFormatException e){
			System.out.println("\tPlease enter numbers");
			main(null);
		}
	}
}
