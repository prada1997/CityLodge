public class SuiteRoom extends Room {
	
	private DateTime lastMaintenanceDate;
	
	protected SuiteRoom(String roomId, String features, String roomType, DateTime lastMaintenanceDate) {
		// TODO Auto-generated constructor stub
		super.setRoomId(roomId);
		super.setFeatures(features);
		super.setRoomType(roomType);
		super.setRoomStatus("Available");
		super.setNumberOfBeds(6);
		this.lastMaintenanceDate = lastMaintenanceDate;		
	}

	public boolean rent(String customerId, DateTime rentDate, int numOfRentDay) {
		
		if ( super.getRoomStatus() == "Maintenance" || super.getRoomStatus() == "Rented") {
			return false;
		}	
			
		DateTime estimatedReturnDate = new DateTime(rentDate, numOfRentDay);
		HiringRecord obj = new HiringRecord(customerId, super.getRoomId(), rentDate, estimatedReturnDate);
		obj.rentalFeeCalculation(super.getNumberOfBeds(), numOfRentDay);
		super.roomRecord(obj);
		super.setRoomStatus("Rented");
		
		return true;
	}
	

	public boolean returnRoom(DateTime returnDate) {
		
		if ( super.getRoomStatus() == "Available" || super.getRoomStatus() == "Maintenance") {
			return false;
		}
				
		super.getRecord().get(super.getEntries()).setActualReturnDate(returnDate); 
		super.getRecord().get(super.getEntries()).lateFeeCalculation(super.getNumberOfBeds());
		super.setRoomStatus("Available");
		super.setEntries(getEntries() + 1);;
		
		return true;
	}
	
	
	public boolean performMaintenance() {
		
		if (super.getRoomStatus() == "Maintenance")
			return true;
		
		else if (super.getRoomStatus() == "Available") {
			super.setRoomStatus("Maintenance");
			return true;
		}
		// for roomStatus == Rented	
		else
			return false;
	}

	
	public boolean completeMaintenance(DateTime completionDate) {
		
			if ( super.getRoomStatus() == "Maintenance") {
				super.setRoomStatus("Available");
				lastMaintenanceDate = completionDate;
				return true;
			}
		
			else if ( super.getRoomStatus() == "Available") {
				return false;		
			}
			//for roomStatus == rented 
			else
				return false;
	}
	
	
	public String toString() {
		
		String toString;
		
		toString = super.getRoomId() + ":" + super.getNumberOfBeds() + ":" + super.getRoomType() 
				 + ":" + super.getRoomStatus() + ":" + super.getFeatures() + ":" + lastMaintenanceDate;
		
		return toString;
	}

	
	public String getDetails() {
		
		String details = "Room ID: " + super.getRoomId() + "\n" + "Number of beds: " + super.getNumberOfBeds() +
						 "\n" + "Type: " + super.getRoomType() + "\n" + "Status: " + super.getRoomStatus() + "\n" +
						 "Last maintenance date: " + lastMaintenanceDate +
						 "\n" +"Feature summary: " + super.getFeatures() + "\n";
		
		
		details = details + "RENTAL RECORD: ";
		
		if (super.getRecord().isEmpty()) {
			details = details + "Empty";
			return details;
		}
		
		else {
			String	keySet = super.getRecord().keySet().toString();
			String [] keySetArray = keySet.substring(1, keySet.length() - 1).split(", ");
	
			int key = keySetArray.length;
			while (key > 0) {
				key--;
				details = details + super.getRecord().get(Integer.valueOf(keySetArray[key])).getDetails();
			}
		}	
		
		return details;
	}
	
	
	public DateTime getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}
}

