package Models.Game;

import java.util.HashMap;

public class GameRepo {
    private HashMap<Integer, Game> gameHashMap = new HashMap<>();

    private GameRepo() {
    }

    public synchronized static GameRepo getInstance(){
        return new GameRepo();
    }

    public HashMap<Integer, Game> getGameHashMap() {
        return gameHashMap;
    }

    public void setGameHashMap(HashMap<Integer, Game> gameHashMap) {
        this.gameHashMap = gameHashMap;
    }

    public void createGame(Game game) {
        if (!this.getGameHashMap().containsKey(game.getGameID())) {
            this.gameHashMap.put(game.getGameID(), game);
        }
    }
}
