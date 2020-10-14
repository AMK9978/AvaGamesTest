package Controller;

import Models.Game.Game;
import Models.Request.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubServer extends Thread {
    private final int connection_id;
    final Socket socket;
    private ExecutorService executors;

    SubServer(Socket connection, int id) {
        this.connection_id = id;
        this.socket = connection;
        this.executors = Executors.newFixedThreadPool(1);
        start();
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        try {
            while (!interrupted()) {
                //process a client request
                //this is for you to implement
                objectInputStream = new
                        ObjectInputStream(socket.getInputStream());
                String message = (String) objectInputStream.readObject();
                System.out.println("Message:" + message);
                Response response = RequestHandler.proxyRequests(connection_id, message);
                if (response.getMessage() == Response.SUCCESSFUL_LOGOUT) {
                    closeConnection();
                    break;
                } else if (response.getMessage() == Response.SUCCESSFUL_ASSIGN) {
                    startTimer(connection_id);
                }
                writeResponse(response);
            }
            closeConnection();
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.executors.shutdown();
        Main.m_clientConnections[connection_id] = null;
    }

    private void startTimer(int connection_id) {
        Runnable runnable = () -> {
            try {
                Thread.sleep(Game.getCoolDownTime());
                if (Main.m_clientConnections[connection_id] != null) {
                    writeResponse(Response.successful(null, Response.COOL_DOWN_TIME_OVER));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        this.executors.submit(runnable);
    }

    private void writeResponse(Response response) {
        try {
            ObjectOutputStream objectOutputStream = new
                    ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(response);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}