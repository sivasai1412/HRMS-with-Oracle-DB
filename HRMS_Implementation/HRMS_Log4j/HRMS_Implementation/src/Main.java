package bin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator; 


public class Main{

	static Logger log = Logger.getLogger(Main.class);
	/**
	*This method is used to perform the CRUD(create read update and delete) operations on the employee and contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void operations() throws Exception{
		BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(System.in));		
		int input = 0;
		try{
			do{
				log.info("\t\tEnter your choice\n\t\t\t\t\t\t\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n\t\t\t\t\t\t\t\t1. Create an User\n\t\t\t\t\t\t\t\t2. Fetch Details of user\n\t\t\t\t\t\t\t\t3. Update Details of user\n\t\t\t\t\t\t\t\t4. Delete User\n\t\t\t\t\t\t\t\t5. Exit\n");
				System.out.print("\t\t\t\t\t\t\tEnter option: ");
				input = Integer.parseInt(bufferedReaderObject.readLine());
				System.out.println();
				CreateUser creatingUser = new CreateUser();
				FetchUser fetchUser = new FetchUser();
				UpdateUser updateUser = new UpdateUser();
				DeleteUser  deleteUser = new DeleteUser();
				if(input == 1){
					creatingUser.addDetails();
				}
				else if(input == 2){
					fetchUser.fetchDetails();
				}
				else if(input == 3){
					updateUser.updateDetails();

				}
				else if(input == 4){
					deleteUser.inactiveDetails();
				}
				else{
					log.debug("\t\tProgram Terminated\n");
					break;
				}
			}while(input != 5);
		}catch(NumberFormatException e){
			log.error("\t\tPlease enter numbers\n");
			operations();
		}
	}
}