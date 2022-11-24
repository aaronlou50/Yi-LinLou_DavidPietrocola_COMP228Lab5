package jdbc;

import java.util.ArrayList;
import java.util.List;

public class Game {
	//variables
	private String gameID;
	private String gameTitle;
	private String score;
	private String playerID;
	private GameData gameData = null;
	
	//constructor with 2 arguments
	public Game (String gameID, String gameTitle, String playerID, String score) {

		this.gameID = gameID;
		this.gameTitle = gameTitle;
		this.score = score;
		this.playerID = playerID;
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
	public void saveGame(String playerId,String gameId, String gameTitle,String score) throws Exception{
		gameData = new GameData();
		gameData.saveRow(playerId, gameId, gameTitle, score);
	}



}
