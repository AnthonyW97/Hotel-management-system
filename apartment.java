//this is the class that handles all apartment based operations

//import necessary classes to read from xml files
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class apartment {
	
	public static client clientObj = new client();
	public static manager managerObj = new manager();
	
	//this class is used to assign an apartment to a client
	// the apartment is assigned after the client has entered their 
	//details
	//the apartment is assigned based on the number of guests the client enters
	
	public static String clientApartment;
	
	//store all necessary apartment information
	public static String numBed;
	public static String livingRoom;
	public static String numBath;
	public static String price;
	
	public static void assignApartment() {
		
		try {
	         File inputFile = new File("src/apartments.xml");
	         
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         
	         NodeList nList = doc.getElementsByTagName("apartment");
	         
	         //loop through all apartments to find one that is appropriate for 
	         //the client's number of guests
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;
	                
	                //convert the maxGuests node and the numGuests string into integers so they
	                //can be compared
	                
	                int apartGuests = Integer.parseInt(eElement.getElementsByTagName("maxGuests").item(0).getTextContent());
	                int newnumGuests = Integer.parseInt(bookings.getBookingDetails()[0]);
	                
	                //during the for loop, if any apartment has exactly the same
	                //or one more capacity as the number of guests, it is
	                //assigned to the client
	                if((apartGuests == newnumGuests)) {
	                	clientApartment = eElement.getElementsByTagName("name").item(0).getTextContent();
	                	
	                	//assign the apartment details to their variables ready for future access
	                	numBed = eElement.getElementsByTagName("bedrooms").item(0).getTextContent();
	                	livingRoom = eElement.getElementsByTagName("livingRoom").item(0).getTextContent();
	                	numBath = eElement.getElementsByTagName("bathrooms").item(0).getTextContent();
	                	price = eElement.getElementsByTagName("price").item(0).getTextContent();
	                	
	                	System.out.println("Booking confirmed. Your booking details: \n\n");
	                	
	                	bookings.getConfirmation();
	                	
	                	break;
	                	
	                	
	                } else if(temp == nList.getLength() -1 ){
	                	System.out.println("No apartments were found matching your criteria."
	                			+ "\nPlease try again with a different number of guests");
	                	System.out.println("Press Enter key to continue...");
	 		            try
	 		            {
	 		                System.in.read();
	 		            }  
	 		            catch(Exception e)
	 		            {}
	                	
	                	client.getMenu();
	                	}
	                }
	            }
		
	} catch (Exception e) {
       e.printStackTrace();
    }
	}
	
	public static void showApartments() {
		//when the client selects the option to make a booking they are shown a list of 
		//apartments including their prices
		
		System.out.print("Below is the list of apartments available.\nApartments are automatically "
				+ "assigned based on the number of guests \n\n");
		
		
		 try {
				
			 //input route to the file containing user information
	         File inputFile = new File("src/apartments.xml");
	         
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         
	         //search by users
	         NodeList nList = doc.getElementsByTagName("apartment");
	         
	         //loop through all users
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	        	 
	            Node nNode = nList.item(temp);
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;
	                
	                System.out.println("-----------------------------------\n");
	                
	                System.out.println("Apartment name : " 
                			+ eElement
                			.getElementsByTagName("name")
                			.item(0)
                			.getTextContent());
	                System.out.println("Price for 7-14 days : " 
                			+ eElement
                			.getElementsByTagName("price")
                			.item(0)
                			.getTextContent());
	                System.out.println("Maximum occupacy : " 
                			+ eElement
                			.getElementsByTagName("maxGuests")
                			.item(0)
                			.getTextContent());
	                System.out.println("Seperate Living Room : " 
                			+ eElement
                			.getElementsByTagName("livingRoom")
                			.item(0)
                			.getTextContent());
	                System.out.println("Number of bedrooms : " 
                			+ eElement
                			.getElementsByTagName("bedrooms")
                			.item(0)
                			.getTextContent());
	                System.out.println("Number of bathrooms : " 
                			+ eElement
                			.getElementsByTagName("bathrooms")
                			.item(0)
                			.getTextContent());
	            }
	             
	            
	         }
	         
	         //the client can press enter to continue once they've 
	         //seen the apartment details
	         System.out.println("\n\n\n Press enter to continue with your booking...");
	         
	         try
	          {
	              System.in.read();
	          }  
	          catch(Exception e)
	          {};
	          
	          bookings.getBookingId();
	         
	         
		 } catch (Exception e) {
		       e.printStackTrace();
		    }
		 
		
		
	}
	
	//this method returns all the necessary apartment details when needed
	public static String[] getApartmentDetails() {
		String[] userDetailsArray = new String[] {clientApartment, numBed, livingRoom, numBath, price};
		
		return userDetailsArray;
	}
}

