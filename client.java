//import necessary classes to read from xml files
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

//this is the client class
//methods that are germane to the client are used here
public class client extends user{
	
	//create objects necessary to call methods 
	public static user userObj = new user();
	public static bookings bookingObj = new bookings();
	public static client clientObj = new client();
	
	//this method returns the menu for the client only
	public static void getMenu() {
		
		//variable to hold the user's chosen menu option
		int menuOpt = 0;
		
		//clear the console of previous actions for clarity
		for (int i = 0; i < 50; ++i) { 
			System.out.println();
		}
			
		//display the menu options 
		System.out.print("CLIENT MENU\n");
		System.out.println("1. Make a booking\n2. Manage your booking\n3. Exit");
		
		menuOpt = scanner.nextInt();
		
		//if the user selects option 1
		//they can make a booking
		if(menuOpt == 1) {
			apartment.showApartments();
		}
		//if the user selects option 2
		//they can manage any previous booking(s) they've made
		else if(menuOpt == 2) {
			try {
				client.manageBooking("src/booking_information.xml", "src/booking_confirmation.xml");
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
		//if the user selects option 3
		//they exit to the login screen
		else if(menuOpt == 3) {
			userObj.exit();
		}
		//if the client selects anything else it is invalid and
		//they are returned to the menu
		else {
			System.out.print("INVALID MENU OPTION. CHOOSE AGAIN");
			clientObj.getMenu();
		}
		
	}


	

	
	
	//this method allows the client to manage bookings made 
	//only by them
	public static void manageBooking(String info_path, String conf_path) throws ParserConfigurationException, SAXException, IOException {
		
		System.out.println("Enter the booking ID of the booking you wish to edit: \n");
		
		String id_to_change;
		id_to_change = scanner.next();
		
		//the client can cycle through any/all bookings made by them
		
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
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;
	                
	                //if the user id of the booking is the same as the user id 
	                //of the client logged in
	                if(eElement.getElementsByTagName("userID").item(0).getTextContent()
	                		.contentEquals(index.getDetails()[1])) {
	                	 
	                	//if the booking id of the user matches the id entered by the user
	                	//then the user can edit their booking
	                	if(eElement.getElementsByTagName("bookingID").item(0).getTextContent()
	                			.contentEquals(id_to_change)) {
	                		
	                		//create the variables to hold the user responses for the 
	                		//next section
	                		String newGuests;
	                		String newStartDate;
	                		String newEndDate;
	                		String newCatered;
	                		
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
	                	    Document doc3 = docBuilder.parse(filepath);
	                	    
	                	    NodeList nList3 = doc3.getElementsByTagName("booking");
	                	    
	                	    for (int temp3 = 0; temp3 < nList.getLength(); temp3++) {
	                	        Node nNode3 = nList.item(temp);
	                	        
	                	        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                	            Element eElement3 = (Element) nNode;
	                	    
	                	            	try {
	                	            		
	                	            		//create nodes to replace the ones that are being edited
	                	            		
	                	            		Node new_guests = doc3.getElementsByTagName("numGuests").item(temp3);
	                	            		Node new_start_date = doc3.getElementsByTagName("startDate").item(temp3);
	                	            		Node new_end_date = doc3.getElementsByTagName("endDate").item(temp3);
	                	            		Node new_catered = doc3.getElementsByTagName("catered").item(temp3);
	                	            		
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
	                	            			            
	                	            			            client.getMenu();
	                	            	            	
	                	            	            	
	                	            	            }catch (TransformerException e) {
	                	                	            // TODO Auto-generated catch block
	                	                	            e.printStackTrace();
	                	                	            
	                	                	            
	                	                	            //-------------END OF SECTION 2------------------
	                	            	            }
	                	            	        }
	                	            	 }
	                	        }
	                	    }
	                	}else{
	                		//if the IDs do not match the user is returned
	                		//to the menu
	                		System.out.println("Booking ID not attributed to this account");
	                		
	                		System.out.println("Press Enter key to continue...");
    			            try
    			            {
    			                System.in.read();
    			            }  
    			            catch(Exception e)
    			            {}
    			            
	                		client.getMenu();
	                	}
	                }temp++;
	            }
	         }
		}catch (Exception e) {
	        e.printStackTrace();
	}
	}
	}
	
	

	                	



	
	
	
	

	
	
	
	
	
	
