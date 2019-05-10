//this is the manager class
//all the actions a manager can take are performer here

//import necessary classes to read from xml files
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

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

//this class handles all actions undertaken by the manager
public class manager extends user{
	
	//create a user object to use and re-purpose methods from the 
	//generic parent class
	public static user userObj = new user();
	
	//this is the method used to return the menu
	//this will show the manager menu only
	public static void getMenu() {
		
		//create and initialise the variable to hold the managers
		//selected menu option
		int menuOpt = 0;
	
		// clear the screen of previous commands for added clarity
		for (int i = 0; i < 50; ++i) { 
			System.out.println();
		}
		
		//display the menu type and the menu options for a manager
		System.out.print("MANAGER MENU\n");
		System.out.println("1. View all bookings\n2. Manage a booking\n3. Exit");
		
		menuOpt = scanner.nextInt();
		
		//if menuOpt equals 1 then the manager can view all current bookings
		if(menuOpt == 1) {
			manager.viewBookings();
		}
		
		//if the option is 2 then the manager can manage a booking
		else if(menuOpt == 2) {
			try {
				manager.manageBooking("src/booking_information.xml", "src/booking_confirmation.xml");
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//if the option is 3 then the manager will exit the system
		else if (menuOpt == 3) {
			userObj.exit();
		}
		
		//any other option is invalid and the manager is returned to the 
		//menu screen
		else {
			System.out.println("INVALID MENU OPTION. CHOOSE A VALID MENU OPTION.");
			manager.getMenu();
		}
		
}
	
	
	//this is the method used to view all bookings made
	//only the manager can use this function
	public static void viewBookings() {
		
		System.out.print("All currrent bookings\n");
		
		try {
			//link to the file that contains the relevant information
	         File inputFile = new File("src/booking_confirmation.xml");
	         
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         
	         //search by bookings
	         NodeList nList = doc.getElementsByTagName("booking");
	         
	         //loop through all bookings
	         //and loop through all nodes for each booking
	         for (int temp = 0; temp < nList.getLength();) {
	            Node nNode = nList.item(temp);
	            System.out.println("-----------------------------------\n");
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;
	                
	                	//print the client ID
                		System.out.println("Booking ID : " 
                			+ eElement
                			.getElementsByTagName("bookingID")
                			.item(0)
                			.getTextContent());
	                	
	                	//print the client ID
	                	System.out.println("Client ID : " 
	                        + eElement
	                        .getElementsByTagName("userID")
	                        .item(0)
	                        .getTextContent());
	                	
	                	//print the name of the client
	                     System.out.println("Client Name : " 
	                        + eElement
	                        .getElementsByTagName("name")
	                        .item(0)
	                        .getTextContent());
	                     
	                     //print the number of guests inc. the client
	                     System.out.println("Number of guests : " 
	                        + eElement
	                        .getElementsByTagName("numGuests")
	                        .item(0)
	                        .getTextContent());
	                     
	                     //print the start date of the stay
	                     System.out.println("Start date : " 
	                        + eElement
	                        .getElementsByTagName("startDate")
	                        .item(0)
	                        .getTextContent());
	                    
	                     //print the end date of the stay
	                     System.out.println("End date : " 
	                        + eElement
	                        .getElementsByTagName("endDate")
	                        .item(0)
	                        .getTextContent());
	                     
	                     //print the catering requirements
	                     System.out.println("Catered : " 
	 	                        + eElement
	 	                        .getElementsByTagName("catered")
	 	                        .item(0)
	 	                        .getTextContent());
	                    
	                     temp++;
	                  }
	               }
	            } catch (Exception e) {
	               e.printStackTrace();
	            }
		
		
		System.out.println("Press enter key to continue...");
		//pause to let the manager read the information]
		//then return the manager to the manager menu
		 try
          {
              System.in.read();
          }  
          catch(Exception e)
          {}
		 
		 //return the manager to the menu
		 manager.getMenu();
		
	}
	
	
	//this method will allow the manager to manager/edit
	//any booking can be edited from here
	public static void manageBooking(String info_path, String conf_path) throws ParserConfigurationException, SAXException, IOException {
	String newGuests;
	String newStartDate;
	String newEndDate;
	String newCatered;
	
	
		
		int booking_id = 0;
		
		//the manager can enter a booking id and
		//then edit that booking
		
		System.out.println("Enter the booking ID of the booking you want to edit");
		booking_id = scanner.nextInt();
		
		System.out.println("Enter the new information for this booking...");
		
		//prompt the user for the new information they wish to replace
		//the old information with
		System.out.print("Enter the number of guests for this booking: ");
		newGuests = scanner.next();
		System.out.print("Enter the start date for your stay: ");
		newStartDate = scanner.next();
		System.out.print("Enter the end date for your stay: ");
		newEndDate = scanner.next();
		System.out.print("Do you want this stay to be catered? (y/n)");
		newCatered = scanner.next();
		
		//2 file paths are needed because 2 seperate documents need
		//to be edited (the booking information and the booking confirmation)
		String filepath = info_path;
		String filepath2 = conf_path;
		
		//---------------------------SECTION 1 - AMMEND BOOKING INFORMATION------------------
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    Document doc = docBuilder.parse(filepath);
	    
	    NodeList nList = doc.getElementsByTagName("booking");
	    
	    for (int temp = 0; temp < nList.getLength(); temp++) {
	        Node nNode = nList.item(temp);
	        
	        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            Element eElement = (Element) nNode;
	    
	            //the new id is the booking id taken from the xml file
	            //and converted into a string to be compared to 
	            //the id the user enters
	            int newID = Integer.parseInt(eElement.getElementsByTagName("bookingID")
	            		.item(0).getTextContent());
	            
	            //if the new id matches the id submitted by the user
	            if(newID == booking_id) {
	            	
	            	try {
	            		
	            		//create nodes to replace the ones that are being edited
	            		
	            		Node new_guests = doc.getElementsByTagName("numGuests").item(temp);
	            		Node new_start_date = doc.getElementsByTagName("startDate").item(temp);
	            		Node new_end_date = doc.getElementsByTagName("endDate").item(temp);
	            		Node new_catered = doc.getElementsByTagName("catered").item(temp);
	            		
	            		//assign the new nodes the values submitted by the user
	            		
	            		new_guests.setTextContent(newGuests);
	            		new_start_date.setTextContent(newStartDate);
	            		new_end_date.setTextContent(newEndDate);
	            		new_catered.setTextContent(newCatered);
	            		
	            		//once the new nodes have been created and filled
	            		//the data can be written into the new xml
	            		
	            		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            		 Transformer transformer = transformerFactory.newTransformer();
	     	             DOMSource source = new DOMSource(doc);
	     	             StreamResult result = new StreamResult(new File(filepath));
	     	             transformer.transform(source, result);
	     	            
	            		
	            		
	            	} catch (TransformerException e) {
	    	            // TODO Auto-generated catch block
	    	            e.printStackTrace();
	            }
	            	
	            	//-----------------------END OF SECTION 1----------------------------
	            	
	            	//-----------------SECTION 2 - AMMEND BOOKING CONFIRMATION-------------
	            	
	            	//this section amends the accompanying booking_confirmation section
	            	
	                
	            	 Document doc2 = docBuilder.parse(filepath2);
	            	 
	            	 NodeList nList2 = doc.getElementsByTagName("booking");
	            	 
	            	 for (int temp2 = 0; temp2 < nList.getLength(); temp2++) {
	            	        Node nNode2 = nList.item(temp);
	            	        
	            	        if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
	            	            Element eElement2 = (Element) nNode;
	            	            
	            	            //new_ID for booking confirmation
	            	            //newID for booking information
	            	            int new_ID = Integer.parseInt(eElement2.getElementsByTagName("bookingID")
	            	            		.item(0).getTextContent());
	            	            
	            	            
	            	            //if the new id matches the id submitted by the user
	            	            if(newID == booking_id) {
	            	            	
	            	            	try {
	            	            		
	            	            		//create nodes to replace the ones that are being edited
	            	            		
	            	            		Node conf_new_guests = doc2.getElementsByTagName("numGuests").item(temp2);
	            	            		Node conf_new_start_date = doc2.getElementsByTagName("startDate").item(temp2);
	            	            		Node conf_new_end_date = doc2.getElementsByTagName("endDate").item(temp2);
	            	            		Node conf_new_catered = doc2.getElementsByTagName("catered").item(temp2);
	            	            		
	            	            		//assign the new nodes the values submitted by the user
	            	            		
	            	            		conf_new_guests.setTextContent(newGuests);
	            	            		conf_new_start_date.setTextContent(newStartDate);
	            	            		conf_new_end_date.setTextContent(newEndDate);
	            	            		conf_new_catered.setTextContent(newCatered);
	            	            		
	            	            		//once the new nodes have been created and filled
	            	            		//the data can be written into the new xml
	            	            		
	            	            		 TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            	            		 Transformer transformer = transformerFactory.newTransformer();
	            	     	             DOMSource source = new DOMSource(doc);
	            	     	             StreamResult result2 = new StreamResult(new File(filepath2));
	            	     	             transformer.transform(source, result2);
	            	     	             
	            	     	        	System.out.print("Your booknig has been successfully changed!\n\n\n");
	            	            		
	            	            		System.out.println("Press Enter key to continue...");
	            			            try
	            			            {
	            			                System.in.read();
	            			            }  
	            			            catch(Exception e)
	            			            {}
	            			            
	            			            // once the booking has been altered the information
	            			            //is returned to the manager to see their changes
	            			            
	            			            System.out.println("Below is the refined booking information: \n\n\n");
	            			          //print the client ID
	                            		System.out.println("Booking ID : " 
	                            			+ eElement2
	                            			.getElementsByTagName("bookingID")
	                            			.item(0)
	                            			.getTextContent());
	            	                	
	            	                	//print the client ID
	            	                	System.out.println("Client ID : " 
	            	                        + eElement2
	            	                        .getElementsByTagName("userID")
	            	                        .item(0)
	            	                        .getTextContent());
	            	                	
	            	                	//print the name of the client
	            	                     System.out.println("Client Name : " 
	            	                        + eElement2
	            	                        .getElementsByTagName("name")
	            	                        .item(0)
	            	                        .getTextContent());
	            	                     
	            	                     //print the number of guests inc. the client
	            	                     System.out.println("Number of guests : " 
	            	                        + eElement2
	            	                        .getElementsByTagName("numGuests")
	            	                        .item(0)
	            	                        .getTextContent());
	            	                     
	            	                     //print the start date of the stay
	            	                     System.out.println("Start date : " 
	            	                        + eElement2
	            	                        .getElementsByTagName("startDate")
	            	                        .item(0)
	            	                        .getTextContent());
	            	                    
	            	                     //print the end date of the stay
	            	                     System.out.println("End date : " 
	            	                        + eElement2
	            	                        .getElementsByTagName("endDate")
	            	                        .item(0)
	            	                        .getTextContent());
	            	                     
	            	                     //print the catering requirements
	            	                     System.out.println("Catered : " 
	            	 	                        + eElement2
	            	 	                        .getElementsByTagName("catered")
	            	 	                        .item(0)
	            	 	                        .getTextContent());
	            	                     
	            	                   //print the total price
	            	                     System.out.println("Total Price : " 
	            	 	                        + eElement2
	            	 	                        .getElementsByTagName("price")
	            	 	                        .item(0)
	            	 	                        .getTextContent());
	            			            
	            			            
	            			            
	            	            	
	            	            	
	            	            }catch (TransformerException e) {
	                	            // TODO Auto-generated catch block
	                	            e.printStackTrace();
	                	            
	                	            
	                	            //-------------END OF SECTION 2------------------
	            	 
		
		
	            	            }
	            	            }
	            	        }
	            	 }
	            }
	        }
	    }
	    
	}
	

}
