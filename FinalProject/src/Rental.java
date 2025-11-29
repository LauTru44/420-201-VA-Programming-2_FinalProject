import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Rental {
    private ArrayList<Person> members = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();
    private final String filePath = "rental.json";
    private Gson gson;

    private final int[] monthDays = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //To avoid confusions, the first month goes in index 1

    // Constructor
    public Rental() {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Person.class, new MemberTypeAdapter());
        gson = gsonBuilder.create(); //Creates the gson file 
        loadFromFile(); //Reads from the file at the beggining
    }

    // Load data from JSON
    private void loadFromFile() {
        File file = new File(filePath);
        if (file.exists() && file.length() != 0) //If the file exists and its not empty, it can be read
            try (FileReader reader = new FileReader(file)) {
            	
            	JsonElement tree = JsonParser.parseReader(reader);
                JsonObject jsonObject = tree.getAsJsonObject();
            	
            	Type memberListType = new TypeToken<List<Person>>() {}.getType();
                Type movieListType = new TypeToken<List<Movie>>() {}.getType();
                
                if (jsonObject.has("members")) {
                    members = gson.fromJson(jsonObject.getAsJsonArray("members"), memberListType);
                }

                if (jsonObject.has("movies")) {
                    movies = gson.fromJson(jsonObject.getAsJsonArray("movies"), movieListType);
                }
                
            } catch (Exception e) {
                System.out.println("Error reading members JSON: " + e.getMessage());
            }
        }
	
	public void saveToFile() { //saves and updates the information into the file
	    try (FileWriter writer = new FileWriter(filePath)) {
	    	
	        JsonObject tree = new JsonObject();
	        tree.add("members", gson.toJsonTree(members));
	        tree.add("movies", gson.toJsonTree(movies));

	        gson.toJson(tree, writer);
	        System.out.println("All data is saved successfully!");
	    } catch (IOException e) {
	        System.out.println("Error writing JSON: " + e.getMessage());
	    }
	}
	
	//Displays all movies from the rental array
    public void getMovies() {
    	for (Movie m : movies) {
    	    System.out.println(m);
    	}
    }
    
    //Displays all members from the rental array
    public void getMembers() {
       	for (Person m : members) {
    	    System.out.println(m);
    	}
    }
    
    //Adds a new member to the array of members + it saves it in the json file
    public void newMember(Person p) {
    	members.add(p);
    	saveToFile();
    }
    
    //Adds a new member to the array of members + It sets the movie as available + it saves it in the json file
    public void newMovie(Movie m) {
    	m.available=true;
    	movies.add(m);
    	saveToFile();
    }
    
    //It makes sure that the id is not longer than 2 ids + The id cannot exist already 
    public void ValidateId(int id, String typeOfId) throws Exception{
    	String str = String.valueOf(id); //changes the id to string
    	
    	if (str.length()>2)throw new Exception("Enter an id with only 2 digits, Please Retry!");
    	
    	if (typeOfId.equals("member")) {
    		for(Person p : members) {
    			if(p.id_member==id ) throw new Exception("This id already exists, Please Retry!");
    		}
    	}
    	if (typeOfId.equals("movie")) {
    		for(Movie m : movies) {
    			if(m.id_movie==id )throw new Exception("This id already exists, Please Retry!");
    		}
    	}
    }
    
    //It validates if the selected string is empty
    public void validateEmptyString (String str) throws Exception {
        if (str == null || str.isEmpty()) {
            throw new Exception("This field cannot be empty, Please Retry!");
        }
    }
    
    //It validates the email's parsing
    public void validateEmail(String email) throws Exception {

        if (email == null || email.isEmpty()) {
            throw new Exception("Email cannot be empty, Please Retry!");
        }

        // Checks if it ends with .com
        if (!email.endsWith(".com")) {
            throw new Exception("Email must end with '.com'. Please Retry!");
        }

        //Only one '@' is permitted
        int atCount = 0;
        for (char c : email.toCharArray()) {
            if (c == '@') atCount++;
        }

        if (atCount != 1) {
            throw new Exception("Email must contain exactly one '@' symbol.  Please Retry!");
        }

        // '@' comes before '.com' using the index comparaison
        int atIndex = email.indexOf('@');
        int dotIndex = email.lastIndexOf(".com");

        if (atIndex > dotIndex) {
            throw new Exception("❌ '@' must appear before '.com'.");
        }

        // The email has to have the name
        if (atIndex == 0) {
            throw new Exception("Email must be valid,  Please Retry!");
        }

        // The email has to have a domain name
        if (atIndex == dotIndex - 1) { //if they are next to each other
            throw new Exception("Email must have a domain name after '@'.");
        }
    }
    
    //It validates that only alphabet chars were entered
    public void validateAlphabets(String input) throws Exception {
        boolean valid = true;

        //Checks if it's empty
        if (input == null || input.isEmpty()) {
            valid = false;
        }
        //Checks for alphabets
        else if (!input.matches("[a-zA-Z ]+")) {
            valid = false;
        }

        //If not valid, throws the exception
        if (valid==false) {
            throw new Exception("This field must contain alphabetic characters only. Please retry!");
        }
    }
    
    //Checks that valid school grades were entered
    public void verifyGrade(int grade) throws Exception {

        if (grade < 1 || grade > 12) {
        	 throw new Exception("School grade must be between 1 and 12, Please retry!");
        }
        
    }
    
    //Checks that the id of the member entered exists in the "members" array
    public void checkIdMemberExistence(int id) throws Exception {
    	boolean exists=false;
    	
		for(Person p : members) {
			if(p.id_member==id) exists=true;
		}
		if (exists==false) throw new Exception("This member is not in our system, Please Retry!");
    }
    
    //Checks that the movie entered exists in the "movies" array
    public void checkMovieExistence(String movie) throws Exception {
    	boolean exists=false;  	
		for(Movie m : movies) {
			if(m.title.equalsIgnoreCase(movie)) exists=true;
		}
		if (exists==false) throw new Exception("This movie is not in our catalog, Please Retry!");
    }
    
    public void checkMovieAvailability(String movie)throws Exception {
		for(Movie m : movies) {
			if(m.title.equalsIgnoreCase(movie)) {
				if (m.available==false)throw new Exception("This movie is not available at the moment, Please Retry!");
			};
		}
    }
    
    //It validates if there is at least a movie available to rent
    public void possibilityOfRenting()throws Exception {
       	boolean canBeRented=false;  	
    		for(Movie m : movies) {
    			if(m.available==true) canBeRented=true;
    		}
    		if (canBeRented==false) throw new Exception("All movies are rented already, Please Retry!");
    }
    
    //It validates if there is at least a movie that was already rented to be returned
    public void possibilityOfReturning()throws Exception {
        boolean rentedExists = false;

        for (Movie m : movies) {
            if (m.available==false) {
                rentedExists = true;
            }
        }

        if (rentedExists==false) {
            throw new Exception("All movies are available, no rented movies exist to return.");
        }
    }
      
    // Rents a movie: Accepts the date that is divided into three parts + Sets the date of rent + Verifies the entered movie is in the catalog
    public void rentingMovie(int memberId, String movieToRent, String date) throws Exception {
    	// Validate and parse date
    	String[] parts = date.split("-");
    	if (parts.length != 3) throw new Exception("Incorrect date format. Use dd-mm-yyyy.");

    	int day, month, year;
    	try {
	    	day = Integer.parseInt(parts[0]);
	    	month = Integer.parseInt(parts[1]);
	    	year = Integer.parseInt(parts[2]);
    	} catch (NumberFormatException e) {
    		throw new Exception("Date contains invalid numbers, please Retry!");
    	}

    	boolean memberFound = false;
    	boolean movieFound = false;

    	for (Person p : members) {
    		
    	    if (p.getId_member() == memberId) {
    	        memberFound = true;

    	        for (Movie m : movies) {
    	            if (m.title.equalsIgnoreCase(movieToRent)) {
    	                movieFound = true;

    	                if (m.available==false) {
    	                    throw new Exception("Movie is already rented, please choose another one!");
    	                }

    	                m.rentDate = new RentDate();
    	                m.rentDate.setRentDate(day, month, year);
    	                m.updateAvailability(); //Sets available = false

    	                p.getMovies().add(m); //Adds to member's rented list

    	                System.out.println("Movie rented on: " + date);
    	                saveToFile(); //Updates the changes in the file
    	            }
    	        }
    	    }
    	}

    	// After loops, checks if either was not found
    	if (memberFound==false) {
    	    throw new Exception("Member not found, please choose another one!");
    	}
    	if (movieFound==false) {
    	    throw new Exception("Movie not found, please choose another one!");
    		}
    	}
    
    public double returningMovie(int memberId, String movieTitle, String returnDateStr) throws Exception {
        // Find the member
        Person member = null;
        for (Person p : members) {
            if (p.getId_member() == memberId) {
                member = p;
                break;
            }
        }       
        if (member == null) throw new Exception("Member ID not found, Please Retry!");

        // Find the movie
        Movie movie = null;
        for (Movie m : member.getMovies()) {
            if (m.title.equalsIgnoreCase(movieTitle)) {
                movie = m;
                break;
            }
        }
        if (movie == null) throw new Exception("Movie not found in this member’s list, Please Retry!");

        // Validate to check that it returns date string  in the right format(dd-mm-yyyy)
        String regex = "\\d{1,2}-\\d{1,2}-\\d{4}";
        if (!returnDateStr.matches(regex)) {
            throw new Exception("Invalid return date format. Use dd-mm-yyyy.");
        }

        String[] parts = returnDateStr.split("-");
        int returnDay = Integer.parseInt(parts[0]);
        int returnMonth = Integer.parseInt(parts[1]);
        int returnYear = Integer.parseInt(parts[2]);

        // Set the returnDate in the movie object
        movie.returnDate = new RentDate();
        movie.returnDate.setRentDate(returnDay, returnMonth, returnYear);
        
        int days = daysBetweenDates(movie.rentDate,movie.returnDate);
        
        movie.updateAvailability(); // Set the copy as available

        member.getMovies().remove(movie);
        movie.rentDate = null;
        movie.returnDate = null;
        saveToFile();
        System.out.println("The movie was rented for "+days+" days.");
        return member.calculate(days);
        
    }
    
    public int daysBetweenDates(RentDate rentDate, RentDate returnDate) {
    	 int days1 = getDaysSince2025(rentDate);
    	 int days2 = getDaysSince2025(returnDate);

    	 if (days1 > days2) {
    	   throw new IllegalArgumentException("Rent date must be before return date.");
    	    }

    	 return days2 - days1;
    	}
    public int getDaysSince2025(RentDate date) {
        int day = date.day;
        int month = date.month;
        int year = date.year;
        int days = 0;

        for (int y = 2025; y < year; y++) {
            days += isLeapYear(y) ? 366 : 365;
        }

        for (int m = 1; m < month; m++) {
            days += monthDays[m];
            if (m == 2 && isLeapYear(year)) days++;
        }

        days += day;
        return days;
    }

    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    public void emptyLists() throws Exception{
    	if (members.isEmpty())throw new IllegalArgumentException("Add a member first before continuing please");
    	if (movies.isEmpty())throw new IllegalArgumentException("Add a movie first before continuing please");
    }
		
}