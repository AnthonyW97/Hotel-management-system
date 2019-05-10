//this is the user super class

public class user extends bookings{
	
	//this is the generic getMenu() method that the client and manager classes
	//will inherit and implement polymorphism to cater it to their
	//requirements
	public static void getMenu() {
		
		//clear the console of previous actions for clarity
		for (int i = 0; i < 50; ++i) { 
			System.out.println();
		}
	
		//print menu output
		System.out.println("This is the menu");
	}
	

	//this method is used to manage bookings
	//this is the default method
	//the manager and client use of this method will be different depending on who uses it
	public static void manageBooking() {
			
			System.out.println("Manage this booking");
			
			}
	
	
	//this is the method used to log a user out of their account
	//it returns the application to the login phase
	public static void exit() {
		
		//clear the console of previous actions
		for (int i = 0; i < 50; ++i) { 
			System.out.println();
		}
		
		//return the system to the login stage
		index.login();
	}
	}
	

