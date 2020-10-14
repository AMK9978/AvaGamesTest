package Models.Game;

import Models.Request.Response;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private int gameID;
    private ArrayList<Bin> bins = new ArrayList<>();
    private HashMap<Integer, Socket> socketHashMap = new HashMap<>();
    private ArrayList<Bin> freeBins = new ArrayList<>();
    private ArrayList<PlayerData> players = new ArrayList<>();
    private HashMap<Integer, PlayerData> playersID = new HashMap<>();

    // COOL DOWN TIME in millisecond
    public static final int COOL_DOWN_TIME = 4 * 60 * 60 * 10000;

    public int getGameID() {
        return gameID;
    }

    public HashMap<Integer, Socket> getSocketHashMap() {
        return socketHashMap;
    }

    public void addSocket(Integer playerID, Socket socket) {
        socketHashMap.put(playerID, socket);
    }

    public void removeSocket(Integer playerID) {
        socketHashMap.remove(playerID);
    }


    public void setSocketHashMap(HashMap<Integer, Socket> socketHashMap) {
        this.socketHashMap = socketHashMap;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public synchronized Response assignBin(PlayerData player) {
        if (System.currentTimeMillis() -
                player.getGetBinDate() > COOL_DOWN_TIME) {
            if (freeBins.size() > 0) {
                player.getBins().add(freeBins.get(0));
                freeBins.remove(0);
                return Response.successful(null, Response.SUCCESSFUL_ASSIGN);
            } else {
                return Response.failed(null, Response.ERROR_COOL_DOWN);
            }
        } else {
            return Response.failed(null, Response.ERROR_COOL_DOWN);
        }
    }

    public ArrayList<Bin> getBins() {
        return bins;
    }

    public void setBins(ArrayList<Bin> bins) {
        this.bins = bins;
    }

    public ArrayList<Bin> getFreeBins() {
        return freeBins;
    }

    public void setFreeBins(ArrayList<Bin> freeBins) {
        this.freeBins = freeBins;
    }

    public ArrayList<PlayerData> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerData> players) {
        this.players = players;
    }

    public HashMap<Integer, PlayerData> getPlayersID() {
        return playersID;
    }

    public void setPlayersID(HashMap<Integer, PlayerData> playersID) {
        this.playersID = playersID;
    }

    public static int getCoolDownTime() {
        return COOL_DOWN_TIME;
    }
}
