import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in =new Scanner(System.in);
		boolean running=true;
		Rental rental_system = new Rental();
		
		while(running) {
		showMainMenu();
		try {
			
		int choice=in.nextInt();	
		switch (choice) {
		case 1:

		    boolean valid = false;

		    while (valid==false) {
		        try {
		            showMemberChoiceMenu();
		            int memberChoice = in.nextInt();
		            in.nextLine(); // clean buffer

		            // ----- MEMBER ID -----
		            System.out.println("Enter the member's ID (2 digits):");
		            int memberId = in.nextInt();
		            in.nextLine(); // clean buffer
		            rental_system.ValidateId(memberId, "member");

		            // ----- NAME -----
		            System.out.println("Enter your name:");
		            String name = in.nextLine();
		            rental_system.validateAlphabets(name);

		            // ----- EMAIL -----
		            System.out.println("Enter your email:");
		            String email = in.next();
		            in.nextLine(); // clean buffer after next()
		            rental_system.validateEmail(email);

		            if (memberChoice == 1) {
		                // ----- STUDENT -----

		                System.out.println("Enter your school's name:");
		                String schoolName = in.nextLine();
		                rental_system.validateAlphabets(schoolName);

		                System.out.println("Enter your grade:");
		                int grade = in.nextInt();
		                in.nextLine(); // clean buffer
		                rental_system.verifyGrade(grade);

		                rental_system.newMember(new Student(memberId, name, email, schoolName, grade));
		            }
		            else if (memberChoice == 2) {
		                // ----- EXTERNAL MEMBER -----

		                System.out.println("Enter your job's title:");
		                String job = in.nextLine();
		                rental_system.validateAlphabets(job);

		                System.out.println("Enter your organization's name:");
		                String org = in.nextLine();
		                rental_system.validateAlphabets(org);

		                rental_system.newMember(new External_Member(memberId, name, email, job, org));
		            }
		            else {
		                throw new Exception("Please enter either 1 or 2 only!");
		            }

		            System.out.println("New member added and saved successfully!");
		            valid = true; 
		        	}
			        catch (InputMismatchException e) {
			            System.out.println("Invalid input. Please enter the correct format.");
			            in.nextLine(); 
			            valid = false; 
			        }
			        catch (Exception e) {
			            System.out.println(e.getMessage());
			            valid = false; 
			        }
		    }
		    		break;
				
			case 2: int movieId =0;
					boolean validMovie=false;
					
					while (validMovie==false) {
						try { 
							System.out.println("Enter the movies's id (2 digits):");
							movieId = in.nextInt();
							in.nextLine(); //Avoids the empty string left by nextInt								
							rental_system.ValidateId(movieId,"movie");
							
							System.out.println("Enter the movie's name:");
							String title = in.nextLine();
							rental_system.validateEmptyString(title);
							
							rental_system.newMovie(new Movie(movieId,title,true));
							
							System.out.println("New movie added and saved successfully!");
							validMovie=true;
						}
				        catch (InputMismatchException e) {
				            System.out.println("Invalid input. Please enter the correct format.");
				            in.nextLine(); 
				            validMovie = false; 
				        }
						catch (Exception e) {
							System.out.println(e.getMessage());
							validMovie = false; 
							}
						}					
					break;
				
			case 3: try {
						rental_system.emptyLists();//checks if there are members or movies available
						rental_system.possibilityOfRenting();
						System.out.print("Enter the ID of the member renting the movie: ");
						int memberRenting = in.nextInt();	
						rental_system.checkIdMemberExistence(memberRenting);
							
						System.out.println("Enter the title of the movie you want to rent:");
						String movieToRent = in.next();
						rental_system.checkMovieExistence(movieToRent);
						rental_system.checkMovieAvailability(movieToRent);
						
				  		System.out.print("Enter the rent date (dd-mm-yyyy): ");
				  		String date = in.next();
				  		rental_system.rentingMovie(memberRenting,movieToRent,date);
						}
						catch(Exception e){
							System.out.println(e.getMessage());
							in.nextLine();
						}
					break;
				
			case 4: try {
					rental_system.emptyLists(); //checks if there are members or movies available
					rental_system.possibilityOfReturning();
					System.out.print("Enter the ID of the member returning the movie: ");
					int memberRenting = in.nextInt();	
					rental_system.checkIdMemberExistence(memberRenting);
				
					System.out.println("Enter the title of the movie you want to return:");
					String movieToReturn = in.next();
					rental_system.checkMovieExistence(movieToReturn);
					System.out.println("Enter the date when you're returning the movie (dd-mm-yyyy):");
					String dateReturn = in.next();
					System.out.println("The payable amount is "+rental_system.returningMovie(memberRenting, movieToReturn, dateReturn)+"$");
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
					break;

			case 5: showInfoMenu(); int infoChoice=in.nextInt();
			
					switch (infoChoice) {
					case 1: rental_system.getMembers();
						break;
					case 2: rental_system.getMovies();
						break;
					case 3: rental_system.getMembers();
							rental_system.getMovies();						
						break;
					case 4:
						break;
					}			
					break;
				
			case 6: running = false;
					System.out.println("Thank your for using our movie rental service!!");
				break;
			default: 
				System.out.println("Invalid answer...Please try again!");
				}
		
            } catch (InputMismatchException e) {
            System.out.println("Invalid input...Please enter a number!");
            in.nextLine(); // Avoids an infinite loop
            } catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
    public static void showMainMenu() {
        System.out.print("""
        =======================================
                  MOVIE RENTAL SYSTEM
        =======================================
        1. Add a Member
        2. Add a Movie
        3. Rent a Movie
        4. Return a Movie
        5. List Information
        6. Exit
        ---------------------------------------
        Enter your choice:
        """);
    }
    
    public static void showInfoMenu() {
    	System.out.print("""
		    	---------------------------------------
		    		LIST INFORMATION MENU
		    	---------------------------------------
		    	1. List All Members
		    	2. List All Movies
		    	3. List Everything
		    	4. Back to Main Menu
		    	---------------------------------------
		    	Enter your choice: 
		    	""");
    }
    
    public static void showMemberChoiceMenu() {
    	System.out.print("""
    			=======================================
    			             Add New Member
    			=======================================
    			1. Student
		    	2. External member
    			""");
    }
}