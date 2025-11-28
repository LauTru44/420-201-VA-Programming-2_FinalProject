import java.util.ArrayList;

abstract class Person implements Payment{
	int id_member;
	String name;
	String email;
	String type;
	
	private ArrayList<Movie> movies = new ArrayList<>();
	
	public Person(int id_member, String name, String email) {
		super();
		this.id_member = id_member;
		this.name = name;
		this.email = email;
	}

	abstract String displayMemberType();

	@Override
	public String toString() {
		return "Person [id_member=" + id_member + ", name=" + name + ", email=" + email + "]";
	}

	public int getId_member() {
		return id_member;
	}

	public void setId_member(int id_member) {
		this.id_member = id_member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}
	
	
}