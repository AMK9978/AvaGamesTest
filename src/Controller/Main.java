package Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int MAX_CLIENTS = 3000;
    static final private SubServer[] m_clientConnections = new SubServer[MAX_CLIENTS];


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9876);
            while (true) {
                Socket socket = serverSocket.accept();
                assignConnectionToSubServer(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void assignConnectionToSubServer(Socket connection) {
        for (int i = 0; i < MAX_CLIENTS; i++) {
            //find an unassigned subServer (waiter)
            if (m_clientConnections[i] == null) {
                m_clientConnections[i] = new SubServer(connection, i);
                break;
            }
        }
    }

    static class SubServer extends Thread {
        final private int m_id;
        final private Socket m_connection;

        //you can store additional client properties here if you want, for example:
        private int m_gameRating = 1500;

        SubServer(Socket connection, int id) {
            this.m_id = id;
            this.m_connection = connection;
            start();
        }

        @Override
        public void run() {
            while (!interrupted()) {
                //process a client request
                //this is for you to implement
            }
        }

        //as an example, if you read String messages from your client,
        //just call this method from the run() method to process the client request
        public void process(String message) {

        }

        /**
         * terminates the connection with this client (i.e. stops serving him)
         */
        public void close() {
            try {
                this.m_connection.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }

//    public static void sendMessage(Object message,)
}
