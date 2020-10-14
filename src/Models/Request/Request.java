package Models.Request;

import Models.Game.Game;

public class Request {

    /*
    {
        "playerID": 12,
        "gameID" : 21,
        "message: : "exit"
    }
     */
    private int gameID;
    private int playerID;
    private String message;

    public Request(int gameID, int playerID, String message) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
