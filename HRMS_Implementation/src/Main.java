package bin;
import java.io.BufferedReader;
import java.io.InputStreamReader;



public class Main{
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
				System.out.println("\t\t\t\t==============================\n\t\t\t\tEnter your choice\n\t\t\t\t==============================\n\t\t\t\t1. Create an User\n\t\t\t\t2. Fetch Details of user\n\t\t\t\t3. Update Details of user\n\t\t\t\t4. Delete User\n\t\t\t\t5. Exit");
				System.out.print("\t\t\t\tEnter option: ");
				input = Integer.parseInt(bufferedReaderObject.readLine());
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
					System.out.println("\t\t\t\tProgram Terminated");
					break;
				}
			}while(input != 5);
		}catch(NumberFormatException e){
			System.out.println("Please enter numbers");
			operations();
		}
	}
}