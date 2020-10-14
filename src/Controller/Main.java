package Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int MAX_CLIENTS = 3000;
    static final SubServer[] m_clientConnections = new SubServer[MAX_CLIENTS];


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
}
