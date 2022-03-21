package app;

public class FilmActor {
	
	Integer actorID;
	Integer filmID;
	
	public FilmActor(Integer filmID, Integer actorID) {
		this.actorID = actorID;
		this.filmID = filmID;
	}

	public Integer getActorID() {
		return actorID;
	}

	public Integer getFilmID() {
		return filmID;
	}
	
}
