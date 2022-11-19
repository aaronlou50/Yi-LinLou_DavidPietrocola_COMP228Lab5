package jdbc;

import java.util.ArrayList;
import java.util.List;

public class Game {
	//variables
	private String gameID;
	private String gameTitle;
	
	private List <String> records = new ArrayList<>();
	private GameData gameData = null;
	
	//constructor with 2 arguments
	public Game(String gameID, String gameTitle) {
	
		this.gameID = gameID;
		this.gameTitle = gameTitle;
	}
	//getters and setters

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public String getGameTitle() {
		return gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}
	//add navigation methods
	


}
