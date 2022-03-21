package app;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import db.BaseQuery;

public class CWReq3 extends BaseQuery{
	
	

	public CWReq3(String configFilePath) throws FileNotFoundException {
		super(configFilePath);
	}
	
	/* -------------------------------------------------------------
	 * TODO: getActual() to be completed as part of the coursework.
	 * --------------------------------------------------------------
	 */
	/* ---------------------------------------------------------------------
	 * The getActual() method returns yours requirement code's output.
	 * In this instance, the return type is a String, you are free to choose
	 * other return types depending on the requirement. You are allowed to 
	 * write additional helper methods.
	 * ---------------------------------------------------------------------
	 */
	
	public String getActual() throws SQLException {
		
		List<FilmActor> filmActorList = getFilmActorList();
		List<Integer> filmID = new ArrayList<Integer>();
		List<Film> filmlist = getFilmList();
		String titleName = "";
		
		
		for(FilmActor filmActor: filmActorList) {
			filmID.add(filmActor.getFilmID());
		}	
		
		for(Film film: filmlist) {
			if (film.getFilmID().equals(getMostActorFilm(filmID))) {
				titleName = film.getTitle();
			}
		}
		return titleName;
}

	public static <T> T getMostActorFilm(List<T> list) {
		Map<T, Integer> map = new HashMap<>();

		for (T t : list) {
        Integer value = map.get(t);
        map.put(t, value == null ? 1 : value + 1);
	   }

		Entry<T, Integer> max = null;

		for (Entry<T, Integer> a : map.entrySet()) {
			if (max == null || a.getValue() > max.getValue())
				max = a;
    }

		return max.getKey();
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
	
		
		while(rs.next()) {
			actorID = rs.getInt("actor_id");
			filmID = rs.getInt("film_id");
			filmActor = new FilmActor(filmID, actorID);
			filmActorList.add(filmActor);
	}
		
		return filmActorList;
}		
	
private List<Film> getFilmList() throws SQLException {
		
		List<Film> filmList = new ArrayList<Film>();	
		
		Film f;
		
		Integer film_id;
		String title;
		String description;
		Double rental_rate;
		
		ResultSet rs = this.getResultSet("Select * from film");
		
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


	
	/* -------------------------------------------------------------
	 * TODO: printOutput() to be completed as part of the coursework.
	 * --------------------------------------------------------------
	 */
	/* ----------------------------------------------------------------------
	 * The printOutput() method prints result of your requirement code 
	 * onto the console for the end-user to view. This method should
	 * rely on the requirement code results obtained through the getActual() 
	 * method, decorate it in a human friendly format and display the results 
	 * on the console. It is possible that this method may need to get additional 
	 * data to make the output human friendly. For example, if the requirement 
	 * code returns only the customer IDs, this method may additionally 
	 * want to fetch the customer names to make the output human-friendly.
	 * You are allowed to write additional helper methods.
	 * ----------------------------------------------------------------------
	 */
	
	public void printOutput() throws SQLException{
		String output = getActual();
			System.out.println(output + " is the film that has the highest number of actors. ");
		 
		
	}

}
