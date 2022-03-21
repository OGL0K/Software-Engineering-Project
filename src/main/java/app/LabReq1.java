package app;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.BaseQuery;

/**
 * 
 * Defines the properties and behaviour for Lab Requirement 1: Find the list of
 * actor last names that are common to 3 or more actors
 * 
 * @author Mariam Cirovic
 * @author Template code Santanu Dash
 * 
 */
public class LabReq1 extends BaseQuery {

	/**
	 * Constructor sets the path for the configuration details to connect to Sakila database
	 *
	 * @param configFilePath 
	 * 			Path for the JSON file with configuration details
	 * @throws FileNotFoundException 
	 */
	public LabReq1(String configFilePath) throws FileNotFoundException {
		// Use the super constructor to set the File Path
		super(configFilePath);
	}

	/**
	 * Use the list of actors to determine the last names that are common to 3 or
	 * more actors
	 * 
	 * @return The list of Actor last names
	 * @throws SQLException 
	 */
	public List<String> getActual() throws SQLException {

		// Stores the last names of actors whose last name is shared with
		// at least 2 other actors
		List<String> actorLastNames = new ArrayList<String>();

		// Actor last name is the key and the count of the actors with the
		// same last name is the value: last name -> count
		Map<String, Integer> actorLastnameCount = new HashMap<String, Integer>();

		// Get the list of Actors to determine the shared last names
		List<Actor> actorList = getActorList();

		for (Actor actor : actorList) {
			String lastName = actor.getLastName();
			if (actorLastnameCount.containsKey(lastName)) {
				Integer count = actorLastnameCount.get(lastName);
				actorLastnameCount.put(lastName, count + 1);
			} else {
				actorLastnameCount.put(lastName, 1);
			}
		}

		for (String lastName : actorLastnameCount.keySet()) {
			Integer count = actorLastnameCount.get(lastName);
			if (count > 2) {
				actorLastNames.add(lastName);
			}
		}

		// Sort the list in ascending order
		Collections.sort(actorLastNames);

		return actorLastNames;
	}

	/**
	 * Retrieve all Actors from the database and create the Actor objects
	 * 
	 * @return The list of Actors
	 * @throws SQLException 
	 */
	private List<Actor> getActorList() throws SQLException {

		List<Actor> actorList = new ArrayList<Actor>();

		Actor actor;
		Integer actorID;
		String firstName;
		String lastName;

		ResultSet rs = this.getResultSet("Select * from actor");

		// Iterate over the ResultSet to create an ArrayList of Actor objects
		while (rs.next()) {
			actorID = rs.getInt("actor_id");
			firstName = rs.getString("first_name");
			lastName = rs.getString("last_name");

			actor = new Actor(actorID, firstName, lastName);
			actorList.add(actor);
		}

		return actorList;
	}

	/**
	 * Print the list of actor last names that are shared by 3 or more actors to the console
	 * 
	 * @throws SQLException 
	 */
	public void printOutput() throws SQLException {
		List<String> actorLastNames = getActual();
		System.out.println("List of actor last names that are shared by at least 3 actors");
		for (String lastName : actorLastNames) {
			System.out.println(lastName);
		}
	}

}