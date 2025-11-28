
public class Movie {
	
	int id_movie;
	String title;
	boolean available=true;
	RentDate rentDate ;
	RentDate returnDate ;
	
	public Movie(int id_movie, String title, boolean available) {
		this.id_movie = id_movie;
		this.title = title;
		this.available = true;
	}
	@Override
	public String toString() {
	    String result = "Movie [id_movie=" + id_movie + ", title=" + title + ", available=" + available;

	    if (rentDate != null) {
	        result += ", rentDate=" + rentDate;
	    }

	    if (returnDate != null) {
	        result += ", returnDate=" + returnDate;
	    }

	    result += "]";
	    return result;
	}
	
	public int getId_movie() {
		return id_movie;
	}
	public void setId_movie(int id_movie) {
		this.id_movie = id_movie;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}

	public RentDate getRentDate() {
		return rentDate;
	}
	public void setRentDate(RentDate rentDate) {
		this.rentDate = rentDate;
	}
	public RentDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(RentDate returnDate) {
		this.returnDate = returnDate;
	}

	public void updateAvailability() {
		if(this.available==false)this.available=true;
		else this.available=false;
	}
}