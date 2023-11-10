package bin;

public class Salary{

	/**
	*This method is used to get the spacing between the each and every attribute.
	*@param a 
	*@param b
	*/
	public static void gap(int a, int b){
		for(int i = b; i <= a; i++){
			System.out.println(" ");
		}
	}

	/**
	*This method is used to get the salary structure of the employee.
	*@param sal This parameter is used get the ctc of the employee from the database and check the salary structure in this method.
	*/
	public static void salaryStructure(String sal){
		int salary = Integer.parseInt(sal);
		int monthlySalary = (int)(salary/12);
		int basic = (int)(0.5 * monthlySalary);
		int hRA = (int)(0.5 * basic);
		int cA = 1600;
		int medicalAllowance = 1250;
		int mealCoupons;
		if(salary > 400000){
			mealCoupons = 2200;
		}
		else{
			mealCoupons = 1100;
		}
		int specialAllowance = cA + medicalAllowance + mealCoupons;
		int professionalTax = 200;
		int pF = (int)(0.12 * basic);
		int totalDeduction = professionalTax + pF;
		int inHand = monthlySalary - totalDeduction;
		System.out.format("\t\t\t\t\t%-24s%-2s%-8d\n", "Salary ", "|", monthlySalary);
		System.out.format("\t\t\t\t\t%-24s%-2s%-8d\n", "Basic ", "|", basic);
		System.out.format("\t\t\t\t\t%-24s%-2s%-8d\n", "House Rental Allowance ", "|", hRA);
		System.out.format("\t\t\t\t\t%-24s%-2s%-8d\n", "Conveyance Allowance ", "|", cA);
		System.out.format("\t\t\t\t\t%-24s%-2s%-8d\n", "Medical Allowance ", "|", medicalAllowance);
		System.out.format("\t\t\t\t\t%-24s%-2s%-8d\n", "Meal Coupons ", "|", mealCoupons);
		System.out.format("\t\t\t\t\t%-24s%-2s%-8d\n", "Special Allowance ", "|", specialAllowance);
		System.out.format("\t\t\t\t\t%-24s%-2s%-8d\n", "Professional Tax ", "|", professionalTax);
		System.out.format("\t\t\t\t\t%-24s%-2s%-8d\n", "Provident Fund ", "|", pF);
		System.out.format("\t\t\t\t\t%-24s%-2s%-8d\n\n", "Gross Salary ", "|", inHand);
	}
}	