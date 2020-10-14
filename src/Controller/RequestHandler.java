package Controller;

import Models.Game.Game;
import Models.Game.GameRepo;
import Models.Request.Request;
import Models.Request.Response;
import com.google.gson.Gson;

class RequestHandler {

    private static Response handle(int connection_id, Request request) {
        Game game = GameRepo.getInstance().getGameHashMap().get(request.getGameID());

        if (request.getMessage().equals("exit")) {
            return logout(request.getPlayerID(), game);
        }
        if (game.getPlayersID().containsKey(request.getPlayerID())) {
            return game.assignBin(game.getPlayersID().get(request.getPlayerID()));
        } else {
            return login(connection_id, request.getPlayerID(), game);
        }
    }

    private static Response logout(int playerID, Game game) {
        game.removeSocket(playerID);
        return Response.successful(null, Response.SUCCESSFUL_LOGOUT);
    }

    private static Response login(int connection_id, int playerID, Game game) {
        game.addSocket(playerID, connection_id);
        return Response.successful(null, Response.SUCCESSFUL_LOGIN);
    }

    static Response proxyRequests(int connection_id, String json) {
        Request request = new Gson().fromJson(json, Request.class);
        return handle(connection_id, request);
    }
}
