package socketCommunication;

import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket ss;
    //private Socket cs;
    //private static PrintWriter out;
    //private static BufferedReader in;
    private final int maxClients = 8;

    Server() {
        try {
            this.ss = new ServerSocket(8000);
            System.out.println("Serwer sprawny i gotowy do działania!");
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void stop() {
        try {
            //cs.close();
            ss.close();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }



    public static void main(String[] args) {
        Server server = new Server();
        Thread clients[] = new Thread[server.maxClients];
        int numOfClients = 0;

        while (numOfClients < server.maxClients) {
            try {
                clients[numOfClients] = new Thread(new ServerService(server.ss.accept(), numOfClients));
                clients[numOfClients].start();
                numOfClients++;
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        for (int i=0; i < numOfClients; i++)
        {
            try {
                clients[i].join();
            } catch(InterruptedException e) {
                System.out.println(e.getStackTrace());
            }
        }
        System.out.println("zamykam serwer");
    }
}
