import java.text.DecimalFormat;

public class HiringRecord {

	private String recordId;
	private DateTime rentDate;
	private DateTime estimatedReturnDate;
	private DateTime actualReturnDate;
	private double rentalFee;
	private double lateFee;
	private final int oneBedRental = 59;
	private final int twoBedRental = 99;
	private final int fourBedRental = 199;
	private final int sixBedRental = 999;
	private final int sixBedLateFee = 1099;
	
	protected HiringRecord(String customerID, String roomId, DateTime rentDate, DateTime estimatedReturnDate) {
		recordIdConcatination(roomId, customerID, rentDate);
		this.rentDate = rentDate;
		this.estimatedReturnDate = estimatedReturnDate;
	}
	
	public void rentalFeeCalculation(int numberOfBeds, int numOfRentDays) {
				
				if ( numberOfBeds == 1 ) {
					rentalFee = oneBedRental * numOfRentDays;
				}
				else if( numberOfBeds == 2) {
					rentalFee = twoBedRental * numOfRentDays;
				}
				else if ( numberOfBeds == 4) {
					rentalFee = fourBedRental * numOfRentDays;
				}
				else if( numberOfBeds == 6) {
					rentalFee = sixBedRental * numOfRentDays;
				}	
	}
	
	public void lateFeeCalculation (int numberOfBeds) {
				
		if (numberOfBeds == 1) {
			lateFee = (135/100) * oneBedRental;
			lateFee = lateFee * DateTime.diffDays(actualReturnDate, estimatedReturnDate);
		}
		
		else if( numberOfBeds == 2) {
			lateFee = (135/100) * twoBedRental;
			lateFee = lateFee * DateTime.diffDays(actualReturnDate, estimatedReturnDate);
		}
		
		else if( numberOfBeds == 4) {
			lateFee = (135/100) * fourBedRental;
			lateFee = lateFee * DateTime.diffDays(actualReturnDate, estimatedReturnDate);
		}
		
		else if( numberOfBeds == 6) {
			lateFee = sixBedLateFee * DateTime.diffDays(actualReturnDate, estimatedReturnDate);
		}
		
	}
	

	public String toString() {
		String toString;
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		
		if ( numberFormat.format(lateFee).equals(".00") ) {
			toString = recordId + ":" + rentDate + ":" + estimatedReturnDate + ":none:none:none";  
		}
		else
			toString = recordId + ":" + rentDate + ":" + estimatedReturnDate + ":" + actualReturnDate 
					 + ":" + numberFormat.format(rentalFee) + ":" + numberFormat.format(lateFee);
		
		return toString;
	}
	

	public String getDetails() {
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		
		if(actualReturnDate == null) {
			String details = "\n" +  "Record ID: " + recordId + "\n"
    						+ "Rent Date: " + rentDate + "\n"
    						+ "Estimated Return Date: " + estimatedReturnDate + "\n";
			
			return details;
		}
		String details = "\n" + "------------------------------------" + "\n" 
						+ "Record ID: " + recordId + "\n"
        				+ "Rent Date: " + rentDate + "\n"
        				+ "Estimated Return Date: " + estimatedReturnDate + "\n"
        				+ "Actual Return Date: " + actualReturnDate + "\n"
        				+ "Rental Fee: " + numberFormat.format(rentalFee) + "\n"
        				+ "Late Fee: " + numberFormat.format(lateFee) + "\n";
		return details;
	}
	
	public void recordIdConcatination(String roomId, String customerId, DateTime rentDate) {
	this.recordId = roomId + "_"	+ customerId + "_" + rentDate.toString().replace("/", "");
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public DateTime getRentDate() {
		return rentDate;
	}

	public void setRentDate(DateTime rentDate) {
		this.rentDate = rentDate;
	}

	public DateTime getEstimatedReturnDate() {
		return estimatedReturnDate;
	}

	public void setEstimatedReturnDate(DateTime estimatedReturnDate) {
		this.estimatedReturnDate = estimatedReturnDate;
	}

	public DateTime getActualReturnDate() {
		return actualReturnDate;
	}

	public void setActualReturnDate(DateTime actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}

	public double getRentalFee() {
		return rentalFee;
	}

	public void setRentalFee(double rentalFee) {
		this.rentalFee = rentalFee;
	}

	public double getLateFee() {
		return lateFee;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

}
