//this is the main class for the hotel management system
//the application will be started from this class

//install scanner to read user input
import java.util.Scanner;

//import necessary classes to read from xml files
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

//this index class is where the application begins
//it contains the login() method and directs the user to the appropriate
//method following logging in
public class index{
	
	//create objects to call relevant methods
	public static manager managerObj = new manager();
	public static client clientObj = new client();
	public static user userObj = new user();
			
			
	//global variables to hold the user information
	public static String userID = null;
	public static String name = null;
	public static String isManager = null;
			
	//this method is for logging into the system
	//it will be used by both managers and clients 
	//once the details have been entered the user will be directed to the appropriate menu screen
	//based on their credentials
	
	public static void login() {
		
		//create a scanner object so that login details can be taken
		Scanner scanner = new Scanner(System.in);
		
		//create variables to hold the user input to compare with xml data
		String username;
		String password;
		
		//create variables to hold the actual values of the login details
		String x = null;
		String y = null;
		
		
		//prompt the user to enter their details
		System.out.print("Username: ");
		
		//the next thing the user enters will be saved as their user name attempt
		username = scanner.next();
		
		//prompt the user to enter password
		System.out.print("Password: ");
		
		//the next thing the user enters following the password prompt will
		//be saved as their password attempt
		password = scanner.next();
		
		//once the user has entered their information the users.xml file must be read 
		//to extract user details and confirm if the details are correct
		 try {
			
			 //input route to the file containing user information
	         File inputFile = new File("src/users.xml");
	         
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         
	         //search by users
	         NodeList nList = doc.getElementsByTagName("user");
	         
	         //loop through all users
	         for (int temp = 0; temp < nList.getLength();) {
	            Node nNode = nList.item(temp);
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;
	                
	                //throughout the for loop, assign the username and password of
	                //each account to the 'x' and 'y' variables
	                x = eElement.getElementsByTagName("username").item(0).getTextContent();
	                y = eElement.getElementsByTagName("password").item(0).getTextContent();  
	              
	                //for each iteration store the user details to the relevant variables
	                isManager = eElement.getElementsByTagName("manager").item(0).getTextContent();
	                userID = eElement.getElementsByTagName("userID").item(0).getTextContent();
	                name = eElement.getElementsByTagName("name").item(0).getTextContent();
	                
	         }
	            
	            //if the user name AND password supplied matches those in the xml file
	            //then the details are accepted
	            if(x.contentEquals(username) && y.contentEquals(password)) {
	            	
	            	//once details have been verified
	            	//the user needs to be sorted into manager or client
	            	if(isManager.contentEquals("y")) {
	            		
	            		//if the user profile states the user is a manager
	            		//call the getMenu() method from a manager object
	            		managerObj.getMenu();
	            		
	            		//once the menu has been called the loop should stop
	            		break;
	            		
	            	} else {
	            		
	            		//if the user profile states the user is a client
	            		//call the getMenu() method from a client object
	            		clientObj.getMenu();
	            		
	            		//once the menu has been called the loop should stop
	            		break;
	            	}
	            	
	            } else {
	            	
	            	//if the username and password doesn't match the user information in the xml
	            	//file, cycle through to the next one until the list 
	            	//has been fully looped through
	            	 temp++;
	            	 
	            	 //if the loop gets to the end of the file and no matches are
	            	 //found then the details were incorrect
	            	 if(temp == nList.getLength()) {
	            		 System.out.println("Incorrect username or password");
	            		 
	            		 //if the details are incorrect the user is returned 
	            		 //to the log in stage
	            		 userObj.exit();
	            	 }
	            }
		}
	         
	         } catch (Exception e) {
	         e.printStackTrace();
	      }

}
	
	//this method gathers all of the relevant user information
	//that other methods/classes may need to access and stores them in an array
	//so that they can be called in the future
	public static String[] getDetails() {
		
		String[] userArray = new String[] {name, userID};
		return userArray;
	}
	

	//this is the main method in the index class
	//this commences the application by bringing the user to the login stage
	public static void main(String args[]) {
		login();
	}
}


	
