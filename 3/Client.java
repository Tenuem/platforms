package socketCommunication;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket cs;
    private ObjectOutputStream out;
    private static ObjectInputStream in;
    private static Scanner scanner;

    public Client(String ip, int port) {
        try {
            cs = new Socket(ip, port);
            out = new ObjectOutputStream( cs.getOutputStream() );
            in = new ObjectInputStream(cs.getInputStream());
            scanner = new Scanner(System.in);
        } catch(IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void sendMessage() {
        String response = "";
        int n;
        Message msg;
        n = scanner.nextInt();
      
        try { 
            out.writeObject(n);
            response = (String)in.readObject();
            System.out.println("serwer: " + response);
            //String s = scanner.nextLine();
            if( response.equals("gotowy do odbioru"))
            {
                for (int num = 0; num < n; num++){
                    msg = new Message(n);
                    System.out.println("klient: wysyłam paczke nr " + (num+1));
                    out.writeObject(msg);
                    out.flush();
                }
                response = (String)in.readObject();
                System.out.println("serwer: " + response);
            }
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.getStackTrace());
        }
       
    }
    public static void main(String[] args) {
       Client client = new Client("localhost",8000);
       String str;
       try {
           while(true) {
               str = (String)in.readObject();
               System.out.println("serwer: " + str);
               client.sendMessage();
           }
       } catch (ClassNotFoundException | IOException e) {
           e.printStackTrace();
       }
       scanner.close();
    }
}
