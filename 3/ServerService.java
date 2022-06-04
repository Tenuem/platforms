package socketCommunication;

import java.io.*;
import java.net.*;

public class ServerService implements Runnable {
    private Socket cs;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int clientId; 

    ServerService(Socket client, int cId)
    {
        clientId = cId;
        cs = client;
    }
    public int readMessage() throws ClassNotFoundException {
        try {
            int n; 
            n = (int)in.readObject();
            Message msg;

            System.out.println("przyjalem zlecenie(" + n + ")");
            out.writeObject("gotowy do odbioru");
            out.flush();
            for(int i=0;i<n;i++)          
            {
                msg = (Message)in.readObject();
                System.out.print("paczka numer " + i + ": ");
                for (int j = 0; j < 10; j++){
                    System.out.print(msg.getNumbers()[j] + " ");
                }
                System.out.println();
            }
            out.writeObject("skonczylem");
            System.out.println("zlecenie wykonane");
            out.flush();
        } catch (IOException e) {
            //System.out.println(e.getStackTrace());
            return 99;
        }
        return 0;
    }

    @Override
    public void run()
    {
        try {
            
            out = new ObjectOutputStream( cs.getOutputStream());
            in = new ObjectInputStream(cs.getInputStream());

            out.writeObject("gotow");
            out.flush();
            //System.out.println("gotow");
            while (true) {
                try{
                    if(readMessage() == 99)
                    {
                        System.out.println("Klient " + clientId + " rozlaczony");
                        break;
                    }
                    out.writeObject("gotowy do odbioru");
                    out.flush();
                } catch(ClassNotFoundException e){
                    System.out.println(e.getStackTrace());
                }
            }

            in.close();
            out.close();
            cs.close();
        } catch(IOException e){
            System.out.println(e.getStackTrace());
        }
    }
}
