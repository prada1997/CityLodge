import java.util.HashMap;
import java.util.Scanner;

public class CityLodge {
	
	private Scanner input = new Scanner (System.in);
	private HashMap<String, Room> allRoom = new HashMap<String, Room>();
	private int roomPointer = 1;
		
	public void menu() {
		int menuInput = 0;
		
		do{
        	System.out.println("\n$$$ City Lodge Menu $$$ \n"
        						+ "1: Add Room \n"
        						+ "2: Rent Room \n"
        						+ "3: Return Room \n"
        						+ "4: Room Maintenance \n"
        						+ "5: Complete Maintenance \n"
        						+ "6: Display All Rooms \n"
        						+ "7. Exit \n"
        						+ "Enter your Choice: ");
        	menuInput = input.nextInt();	
        	
        	if(menuInput == 1) {	
        		        		        		
        		if(addRoom())
        			System.out.println("Room is Created.");
        	}
        	        	
        	else if(menuInput == 2) {        		
        		if(rentRoom())
        			System.out.println("Room is Rented.");
        		else
        			System.out.println("Room is not available at the moment.");
        	}
        	        	
        	else if(menuInput == 3) {
        		if(returnRoom())
        			System.out.println("Room is Returned");
        		else 
        			System.out.println("Room is not Rented.");
        		
        	}
        	
        	else if(menuInput == 4) {	
        		if(roomMaintenance())
        			System.out.println("The room is now under Maintenance.");
        		else
        			System.out.println("The room is Rented.");
        	}
        	        	
        	else if(menuInput == 5) {
        		if(completeRoomMaintenance()) {
        			System.out.println("The Maintenance of room is completed.");
        		}
        		else
        			System.out.println("The room is not in Maintenance.");
        	}
        	        	
        	else if(menuInput == 6) {
        		for (String id: allRoom.keySet()) {
        			System.out.println(allRoom.get(id).getDetails());    
        		}
        	} 
        	               	
        	else if(menuInput == 7) {
        		System.out.println("$$$ Program Terminated $$$");
        		System.exit(0);
        	}
        	
        	else if(menuInput > 7) {
        		System.out.println("Enter Correct Choice.");
        		menu();
        	}
        	
        } while(menuInput != 7);
        input.close();
        
	}
	
	
	//method for adding room. 
	public boolean addRoom() {
		
		String roomId = inputRoomId();
		if(allRoom.containsKey(roomId)) {
			System.out.println("The room ID " + roomId + " already exists.");
			menu();
		}
		
		System.out.println("Enter Feature Summary:");  
		String features = "";
		features = input.next();
		features += input.nextLine();
		
		String [] featuresCount = features.split(" ");
		if (featuresCount.length >= 20) {
			System.out.println("Please enter Feature summary less than 20 words.");
			menu();
		}
		
		System.out.println("Enter Room type:");
		String roomType = input.next().toLowerCase();
	
		if ( roomType.equals("standard") && roomId.substring(0, 2).equals("R_") ) {
			
			System.out.println("Enter number of Beds: ");
			int numberOfBeds = input.nextInt();
			if (numberOfBeds != 1 && numberOfBeds != 2 && numberOfBeds != 4) {
				System.out.println("Enter the values of number of beds equal to 1, 2, 4.");
				menu();
			}
			
			StandardRoom obj = new StandardRoom(roomId, numberOfBeds, features, roomType);
			roomInsertion(roomId, obj);
		}
		
		else if ( roomType.equals("suite") && roomId.substring(0, 2).equals("S_")) {
			
			System.out.println("Enter Last Maintenance Date:");
			DateTime lastMaintenanceDate = inputDateTime();
			
			SuiteRoom obj = new SuiteRoom(roomId,features, roomType, lastMaintenanceDate);
			roomInsertion(roomId, obj);
		}
		
		else {
			System.out.println("Please enter correct room type: Standard or Suite.");
			menu();
		}
		
		return true;
    }
	
	
	//method for renting room.
	public boolean rentRoom() {	
		
		Room obj = allRoom.get(roomExistence(inputRoomId()));
		
		System.out.println("Customer ID: ");
		String customerId = input.next().toUpperCase();
		if(! customerId.substring(0, 3).equals("CUS")) {
			System.out.println("Please Enter Customer ID in Correct way \n"
								+ "For Example: CUS123.");
			menu();
		}
		
		System.out.println("Rent date (dd/mm/yyyy):");
		DateTime rentDate = inputDateTime();
		
	
		System.out.println("How many days? ");
		int numberOfDays = input.nextInt();
		
		if(obj.getRoomType().equals("standard")) {
			roomAvailability(rentDate, numberOfDays);
			return obj.rent(customerId, rentDate, numberOfDays);
		}
		
		else {
			DateTime Date = new DateTime(rentDate, numberOfDays);
			
			if(DateTime.diffDays(Date, ((SuiteRoom) obj).getLastMaintenanceDate()) > 10 ) {
				System.out.println("The room is not available for rent.");
			}
			
		}		
		return obj.rent(customerId, rentDate, numberOfDays);
	}
	
	
	//method for returning room.
	public boolean returnRoom() {
		
		Room obj = allRoom.get(roomExistence(inputRoomId()));
		
		System.out.println("Return Date:");
		DateTime returnDate = inputDateTime();
		
		return obj.returnRoom(returnDate);
	}
	
	
	//method for performing Maintenance
	public boolean roomMaintenance() {
		
		Room obj = allRoom.get(roomExistence(inputRoomId()));
		
		return obj.performMaintenance();
	}
	
	
	//method for completing Maintenance.
	public boolean completeRoomMaintenance() {
		
		Room obj = allRoom.get(roomExistence(inputRoomId()));
		
		if( (obj.getRoomType() )== "suite"){
			
			System.out.println("Maintenance Completion Date (dd/mm/yyyy): ");
			DateTime completeMaintenanceDate =  inputDateTime();
			
			return obj.completeMaintenance(completeMaintenanceDate);
		}
		
		//if room is Standard
		DateTime date = new DateTime();
	
		return obj.completeMaintenance(date);
	}
	
	
	//method for taking input of roomID.
	public String inputRoomId() {
		
		System.out.println("Enter Room ID:");
		String roomId = input.next().toUpperCase();
		
		if (roomId.substring(0, 2).equals("R_") || roomId.substring(0, 2).equals("S_"))  {
			
			return roomId;
		}
		else {
			System.out.println("Enter a valid Room ID \n"
					+ "for Example R_108 for Standard Room"
					+ " or S_559 for Suite Room");
			menu();
		}
		
		return roomId;
	}
	
	
	//method for storing room object in HashMap.
	public void roomInsertion(String roomId, Room object) {
		int roomLimit = 50;
		if (roomPointer > roomLimit) {
			System.out.println("Only 50 rooms can be added.");
			menu();
		}
		
		allRoom.put(roomId , object);
			
		roomPointer++;
	}
	
	//method for checking does roomID exist or not.
	public String roomExistence(String roomId) {
		
			if(!allRoom.containsKey(roomId)) {
				System.out.println("The room ID " + roomId + " does not exist.");
				menu();
			}
		
		return roomId;
	}
	
	
	//method for taking input of Date. 
	public DateTime inputDateTime() {
		int day;
		int month;
		int year;
		String date = input.next();
		
		if (date.length() != 10) {
			
			System.out.println("Date should be in dd/mm/yyyy format.");
			menu();
			
		}
		
		if (date.substring(2, 3).compareTo("/") != 0) {
			System.out.println("Date should be in dd/mm/yyyy format, / seperation missing.");
			menu();
		}
		else if (date.substring(5, 6).compareTo("/") != 0) {
			System.out.println("Date should be in dd/mm/yyyy format, / seperation missing.");
			menu();
		}
		
		else if(Integer.valueOf(date.substring(3, 5)) <= 0 || Integer.valueOf(date.substring(3, 5)) >12) {
			System.out.println("Date should be in dd/mm/yyyy format, month should be from 1 to 12.");
			menu();
		}
		
		day = Integer.valueOf(date.substring(0, 2));
		month = Integer.valueOf(date.substring(3, 5));
		year = Integer.valueOf(date.substring(6, 10));
		
		DateTime returnDate = new DateTime();
		returnDate.setDate(day, month, year);
		
		if (DateTime.diffDays(returnDate, new DateTime()) <= -2) {
			System.out.println("The enter todays date or later than today");
			menu();
		}
		
		return returnDate;
	}
	
	
	//method for checking availabilty of room for renting.
	public void roomAvailability(DateTime rentDate, int numberOfDays) {
		// TODO Auto-generated method stub
		
		if (rentDate.getNameOfDay().equals("Saturday") || rentDate.getNameOfDay().equals("Sunday")) {
			
			if (numberOfDays < 2 || numberOfDays >= 10) {
				System.out.println("On weekends room can only be book for Minimum 2 Days and Maximum 10 days");
				menu();
			}
		}
		else {
			
			if (numberOfDays < 3 || numberOfDays >= 10) {
				System.out.println("On week days room can only be book for Minimum 3 Days and Maximum 10 days");
				menu();
			}
		}
	}
	
}
