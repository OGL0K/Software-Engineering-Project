package app;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.BaseQuery;

/**
 * 
 * Defines the properties and behaviour for Lab Requirement 2: 
 * Find the list of actors that acted in the film ‘Karate Moon’.
 * 
 * @author Mariam Cirovic
 * @author Template code Santanu Dash
 * 
 */
public class LabReq2 extends BaseQuery{
	
	/** The name of the film. */
	private static final String filmName = "KARATE MOON";
	
	/**
	 * Constructor sets the path for the configuration details to connect to Sakila database
	 *
	 * @param configFilePath 
	 * 			Path for the JSON file with configuration details
	 * @throws FileNotFoundException 
	 */
	public LabReq2(String configFilePath) throws FileNotFoundException {
		super(configFilePath);
	}
	
	/**
	 * Use the list of actors, films and film-actors to determine the actors that acted in the specified film
	 * 
	 * @return The list of Actor names
	 * @throws SQLException 
	 */	
	public List<String> getActual() throws SQLException {
		
		//Stores the full names of the actors that are in the specified film
		List<String> actorListForFilm = new ArrayList<String>();
		
		List<Actor> actorList = getActorList();
		List<Film> filmList = getFilmList();	
		List<FilmActor> filmActorList = getFilmActorList();
				
		Integer filmID = null;
		
		for(Film film: filmList) {
			if (film.getTitle().equalsIgnoreCase(filmName)) {
				filmID = film.getFilmID();
			}
		}
		
		List<Integer> actorIDs = new ArrayList<Integer>();
		
		for(FilmActor filmActor: filmActorList) {
			if(filmActor.getFilmID().equals(filmID)) {
				actorIDs.add(filmActor.getActorID());
			}
		}
		
		for (Integer actorID: actorIDs) {
			for(Actor actor: actorList) {
				if(actor.getActorID().equals(actorID)) {
					actorListForFilm.add(actor.getFirstName() + " " + actor.getLastName());
				}
			}				
		}
		
		return actorListForFilm;
	}
	
	/**
	 * Retrieve all Actors from the database and create the Actor objects
	 * 
	 * @return The list of Actors
	 * @throws SQLException 
	 */	
	private List<Actor> getActorList() throws SQLException{
		
		List<Actor> actorList = new ArrayList<Actor>();
		
		Actor actor;
		Integer actorID;
		String firstName;
		String lastName;
		
		ResultSet rs = this.getResultSet("Select * from actor");
		
		//iterate over the ResultSet to create an ArrayList of Actor objects
		while(rs.next()) {
			actorID = rs.getInt("actor_id");
			firstName = rs.getString("first_name");
			lastName = rs.getString("last_name");
			
			actor = new Actor(actorID, firstName, lastName);
			actorList.add(actor);
		}
				
		return actorList;
	}

	/**
	 * Retrieve all Films from the database and create the Film objects
	 * 
	 * @return The list of Films
	 * @throws SQLException 
	 */	
	private List<Film> getFilmList() throws SQLException {
		
		List<Film> filmList = new ArrayList<Film>();	
		
		Film f;
		
		Integer film_id;
		String title;
		String description;
		Double rental_rate;
		
		ResultSet rs = this.getResultSet("Select * from film");
		
		//iterate over the ResultSet to create an ArrayList of Film objects
		while(rs.next()) {
			film_id = rs.getInt("film_id");
			title = rs.getString("title");
			description = rs.getString("description");
			rental_rate = rs.getDouble("rental_rate");
			f = new Film(film_id, title, description, rental_rate);
			filmList.add(f);
		}
		
		return filmList;
	}

	/**
	 * Retrieve all Film Actors from the database and create the Film Actor objects
	 * 
	 * @return The list of Film Actors
	 * @throws SQLException 
	 */
	private List<FilmActor> getFilmActorList() throws SQLException{
		
		List<FilmActor> filmActorList = new ArrayList<FilmActor>();
		
		FilmActor filmActor;
		Integer actorID;
		Integer filmID;

		ResultSet rs = this.getResultSet("Select * from film_actor");
		
		//iterate over the ResultSet to create an ArrayList of Actor objects
		while(rs.next()) {
			actorID = rs.getInt("actor_id");
			filmID = rs.getInt("film_id");
			filmActor = new FilmActor(filmID, actorID);
			filmActorList.add(filmActor);
		}
				
		return filmActorList;
	}

	/**
	 * Print the list of actor names that are cast in the specified film
	 * 
	 * @throws SQLException 
	 */
	public void printOutput() throws SQLException{
		List<String> filmActorList = getActual();
		System.out.println("List of actor names that acted in the film " + filmName);
		for(String name: filmActorList) {
			System.out.println(name);
		}
	}
}
