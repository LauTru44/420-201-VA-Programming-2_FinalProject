
public class RentDate {
	int day;
	int month;
	int year;
	
	public void setRentDate(int day,int month,int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	@Override
	public String toString() {
		return "[day=" + day + ", month=" + month + ", year=" + year + "]";
	}
}
	
	
	
