package app;

public class Actor {
	private Integer actorID;
	private String firstName;
	private String lastName;
	
	public Actor(Integer id, String fname, String lname) {
		this.actorID = id;
		this.firstName = fname;
		this.lastName = lname;
	}

	public Integer getActorID() {
		return actorID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}	

}
