package Models.Request;

import Models.Game.Game;
import Models.Game.GameRepo;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler {

    public static Response handle(Request request, Socket socket) {
        Game game = GameRepo.getInstance().getGameHashMap().get(request.getGameID());

        if (request.getMessage().equals("exit")) {
            return logout(request.getPlayerID(), game, socket);
        }
        if (game.getPlayersID().containsKey(request.getPlayerID())) {
            return game.assignBin(game.getPlayersID().get(request.getPlayerID()));
        } else {
            login(request.getPlayerID(), game, socket);
        }
        return Response.failed(null, Response.ERROR_HANDLE);
    }

    private static Response logout(int playerID, Game game, Socket socket) {
        game.removeSocket(playerID);
        try {
            socket.close();
            return Response.successful(null, Response.SUCCESSFUL_ASSIGN);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.failed(null, Response.ERROR_LOGOUT);
        }
    }

    public static Response login(int playerID, Game game, Socket socket) {
        game.addSocket(playerID, socket);
        return Response.successful(null, Response.ERROR_LOGIN);
    }

    public static Response proxyRequests(Socket socket, String json) {
        Request request = new Gson().fromJson(json, Request.class);
        return handle(request, socket);
    }

    public static void sendAvailableBinMessage(Socket socket) {
        ObjectOutputStream objectOutputStream;
        try {
            Response response = Response.successful(null, Response.BIN_AVAILABLE);
            objectOutputStream = new
                    ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(response);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
