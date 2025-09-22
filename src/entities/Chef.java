package entities;

public class Chef 
{
	private int id;
	private String name;
	private String surname;
	private String username;
	private String password;
	
	
	public Chef(int id, String name, String surname, String username, String password) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
	}
	
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	
}
