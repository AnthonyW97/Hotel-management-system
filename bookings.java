//this is the class for managing/viewing bookings

//import scanner to read user input
import java.util.Scanner;

//import necessary classes to read from xml files
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import necessary classes to write to xml
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//the booking class contains all relevant methods for making
//and confirming bookings. 
public class bookings extends apartment{
	
	//this class inherits information from apartments as the
	//booking cannot be completed until an apartment has been
	//assigned

	
	//create a global variable to hold the most recent booking id
	public static String booking_id = "";
	
	//create a global int variable to hold the booking ID so it can be manipulated
	public static int bookingID = 0;
	
	//this method will check the 'booking_information.xml'
	//file and see what the most recent booking id is
	//it will then take that number and increment it by 1
	//to apply to the next booking
	public static void getBookingId() {
		
		 try {
			 //connect to the booking information xml file
	         File inputFile = new File("src/booking_information.xml");
	         
	         
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         
	         //search by bookings
	         NodeList nList = doc.getElementsByTagName("booking");
	         
	         //find the most recent booking to find the latest booking id
	         for (int temp = nList.getLength() - 1; temp < nList.getLength();) {
	            Node nNode = nList.item(temp);
	            
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;
	                
	                
	                //assign the current bookingID to  variable
	                booking_id = eElement.getElementsByTagName("bookingID").item(0).getTextContent();
	                
	                //if no previous booking exists
	                //set the booking id to 0
	                if(booking_id == null) {
	                	booking_id = "0";
	                }
	                
	                //convert it to an integer so it can be manipulated as a number
	                bookingID = Integer.parseInt(booking_id);
	                
	                //add 1 to the booking id
	   	         	bookingID++;
	   	         
	   	         	//convert back to a string to be used in the xml file
	   	         	booking_id = Integer.toString(bookingID);
	   	         	
	   	         	//once the new id has been assigned the client can
	   	         	//make their booking
	   	         	bookings.makeBooking();
	            }
	         }
	            
	         }
	 catch (Exception e) {
        e.printStackTrace();
        }
		 
		 
		 }
	
	
	
	//create class objects to call methods as appropriate 
	public static client clientObj = new client();
	public static bookings bookingObj = new bookings();
	
	//create scanner object to read user input
	public static Scanner scanner = new Scanner(System.in);
	
	
	//create public variables to hold user information
	//so that the information can be accessed by any method that needs it
	public static final String xmlFilePath = "src/booking_information.xml";
	public static final String xmlFilePath2 = "src/booking_confirmation.xml";
	

	//create global variables to hold booking information supplied by user
	public static String userName;
	public static String numGuests;
	public static String startDate;
	public static String endDate;
	public static String catered;
	

	//this is the makeBooking method
	//used only by clients to make a booking
	//the client enters the relevant details and then then based on their number of guests 
	//an apartment is assigned to them
	public static void makeBooking() throws TransformerException, ParserConfigurationException, SAXException, IOException{
		
		//create a variable to respond to system questions
		String userResp;
		
		//confirm that the client making the booking is logged in on their own account
		System.out.print("Are you " + index.getDetails()[0] + "? (y/n)");
		
		userResp = scanner.next();
		
		if(userResp.contentEquals("y") || userResp.contentEquals("Y")) {
			
			//if the user responds yes, assign the name to their booking
			userName = index.getDetails()[0];
			
			
			//prompt the user to answer questions applicable to a typical booking
			System.out.print("Total number of guests on your stay: ");
			numGuests = scanner.next();
			//no apartment can hold more than 10 guests, so if
			//the user enters more than 10 guests the booking is denied
			int number_guests = Integer.parseInt(numGuests);
			if(number_guests > 10) {
				System.out.println("You have entered too many guests for your stay. Please begin a new booking");
				client.getBookingId();
			}
			
			System.out.print("Enter the first date of your stay (dd/mm/yy): ");
			startDate = scanner.next();
			System.out.print("Enter the final date of your stay (dd/mm/yy): ");
			endDate = scanner.next();
			System.out.print("Would you like your stay to be catered? (y/n): ");
			catered = scanner.next();
			
			//following the gathering of information the data is written
			//into the booking.xml file
			
			

			File fXmlFile = new File("src/booking_information.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fXmlFile);
            
            Element nList = document.getDocumentElement();
		            
		         // booking element
		            Element booking = document.createElement("booking");
		           
		            
		            //add 1 to each booking 
		            
		            //convert to a string to be used as a text node
		            String booking_id = Integer.toString(bookingID);
		            
		         // booking ID element
		            Element bookingID = document.createElement("bookingID");
		            bookingID.appendChild(document.createTextNode(booking_id));
		            booking.appendChild(bookingID);
		            
		         // name element
		            Element name = document.createElement("name");
		            name.appendChild(document.createTextNode(index.getDetails()[0]));
		            booking.appendChild(name);
		         
		            // user id element
		            Element user_id = document.createElement("userID");
		            user_id.appendChild(document.createTextNode(index.getDetails()[1]));
		            booking.appendChild(user_id);
		            
		         // numGuests element
		            Element num_guests = document.createElement("numGuests");
		            num_guests.appendChild(document.createTextNode(numGuests));
		            booking.appendChild(num_guests);
		            
		         // start date element
		            Element start_date = document.createElement("startDate");
		            start_date.appendChild(document.createTextNode(startDate));
		            booking.appendChild(start_date);
		            
		         // end date element
		            Element end_date = document.createElement("endDate");
		            end_date.appendChild(document.createTextNode(endDate));
		            booking.appendChild(end_date);
		            
		         // catering elements
		            Element catering = document.createElement("catered");
		            catering.appendChild(document.createTextNode(catered));
		            booking.appendChild(catering);
		            
		            nList.appendChild(booking);
	
		            
		            Transformer transformer = TransformerFactory.newInstance().newTransformer();
		              transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		              transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		              
		              StreamResult result = new StreamResult(new File("src/booking_information.xml"));
		              DOMSource source = new DOMSource(document);
		              transformer.transform(source, result);
		              System.out.println("DONE");
		            
		            
		            
		            //comfirm that booking details have been saved
		            System.out.println("Booking details have been saved");
		            
		            
		            //alert the user that the information has been saved and 
		            //give them the opportunity to progress 
		            System.out.println("Press Enter key to continue...");
		            try
		            {
		                System.in.read();
		            }  
		            catch(Exception e)
		            {}
		            
		            //once the user has entered their stay information
		            //an apartment can be booked based on the number of guests
		            apartment.assignApartment();
		           
		            
			 
	            
			 //if the user responds 'no' then they must log in with their own account
		} else {
			System.out.println("login with your own profile to continue...");
			
			//user is brought back to login
			index.login();	
		}
	}
	
	
	
	//this method returns all the client details in an array, ready to
	//be accessed by other methods that may need the information
	public static String[] getBookingDetails() {
		
		String[] userDetailsArray = new String[] {numGuests, startDate, endDate, catered};
		
		return userDetailsArray;
	}
	
	
	
	//this method takes the information given in the makeBooking() method 
	//and uses it to assign an apartment and confirm booking information
	//such as apartment name, total price etc
	//it also adds the necessary data to the booking_confirmation.xml file
	public static void getConfirmation() throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {
		
		//create and initialise a variable to hold the total price of the
		//client's stay
		int total_price = 0;
		
		//if the client has elected to have their stay catered the additional costs 
		//must be appended to the price of the stay
		if(bookings.getBookingDetails()[3].contentEquals("y") || 
			bookings.getBookingDetails()[3].contentEquals("Y")) {
			
			//catering costs an extra £15 so it must be added to the price of the
			//apartment
			total_price = Integer.parseInt(apartment.getApartmentDetails()[4]) + 15;
		} else 
			
			//if no catering, the price remains the same
			total_price = Integer.parseInt(apartment.getApartmentDetails()[4]);
		
		//using the 'get' methods the confirmation method will relay all 
		//necessary information back to the client
		
		System.out.print("YOUR BOOKING ID: " + bookingID + "\nClient: " + index.getDetails()[0]  
				+ "\nNumber of guests: " + 
		bookings.getBookingDetails()[0] + "\nStart date: " + bookings.getBookingDetails()[1] +
		"\nEnd date: " + bookings.getBookingDetails()[2] + "\nApartment: " + 
		apartment.getApartmentDetails()[0] + "\nNumber of bedrooms: " + 
		apartment.getApartmentDetails()[1] + "\nSeperate living room: " +
		apartment.getApartmentDetails()[2] + "\nNumber of bathrooms: " + 
		apartment.getApartmentDetails()[3] + "\nCatering: " + bookings.getBookingDetails()[3] +
		"\nTotal price: " + total_price);
		
		//the user can read and confirm their booking before being prompted
		//to return to the main menu
		System.out.println("\nPress enter key to continue...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}
        
        //once the client hits 'enter' write the details to an xml file
        
        //create an apartment object for calling methods when needed
        apartment apartmentObj = new apartment();
        
        File fXmlFile = new File("src/booking_confirmation.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(fXmlFile);
        
        Element nList = document.getDocumentElement();
        
       
//            // root element
//            Element root = document.createElement("bookingConfirmation");
//            document.appendChild(root);
            
         // booking element
            Element booking = document.createElement("booking");
            
            
         // booking id element
            Element booking_ID = document.createElement("bookingID");
            booking_ID.appendChild(document.createTextNode(booking_id));
            booking.appendChild(booking_ID);
            
        // name element
            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(index.getDetails()[0]));
            booking.appendChild(name);
            
            //user ID element
            
            Element user_id = document.createElement("userID");
            user_id.appendChild(document.createTextNode(index.getDetails()[1]));
            booking.appendChild(user_id);
            
         // number of guests element
            Element numberGuests = document.createElement("numGuests");
            numberGuests.appendChild(document.createTextNode(bookings.getBookingDetails()[0]));
            booking.appendChild(numberGuests);
            
         // start date element
            Element startDate = document.createElement("startDate");
            startDate.appendChild(document.createTextNode(bookings.getBookingDetails()[1]));
            booking.appendChild(startDate);
            
         // end date element
            Element endDate = document.createElement("endDate");
            endDate.appendChild(document.createTextNode(bookings.getBookingDetails()[2]));
            booking.appendChild(endDate);
            
         // apartment element
            Element eapartment = document.createElement("apartment");
           eapartment.appendChild(document.createTextNode(apartment.getApartmentDetails()[0]));
            booking.appendChild(eapartment);
            
         // bedrooms element
            Element bedrooms = document.createElement("bedrooms");
            bedrooms.appendChild(document.createTextNode(apartment.getApartmentDetails()[1]));
            booking.appendChild(bedrooms);
            
         // Separate living room element
            Element livingRoom = document.createElement("livingRoom");
            livingRoom.appendChild(document.createTextNode(apartment.getApartmentDetails()[2]));
            booking.appendChild(livingRoom);
            
         // bathrooms element
            Element bathrooms = document.createElement("bathrooms");
            bathrooms.appendChild(document.createTextNode(apartment.getApartmentDetails()[3]));
            booking.appendChild(bathrooms);
            
         // catering element
            Element catering = document.createElement("catered");
            catering.appendChild(document.createTextNode(bookings.getBookingDetails()[3]));
            booking.appendChild(catering);
            
         //convert the total price integer to a string to be used in the xml file
            String total_price_string = Integer.toString(total_price);
            
         // price element
            Element price = document.createElement("totalPrice");
            price.appendChild(document.createTextNode(total_price_string));
            booking.appendChild(price);
            
         // create the xml file
            
            //transform the DOM Object to an XML File
            
            nList.appendChild(booking);
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            StreamResult result = new StreamResult(new File("src/booking_confirmation.xml"));
            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);
            System.out.println("DONE");
            
		//once the data has been written to the xml file the client is returned
        //to the menu
        
		clientObj.getMenu();
}
	
}
