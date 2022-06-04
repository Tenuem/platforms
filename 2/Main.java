import java.io.*;
import java.util.*;
public class Main {

    public static void main(String args[]) throws InterruptedException {
        Queue<Long> que = new LinkedList<>();
        int numOfThreads = Integer.parseInt(args[0]);

        try {
            Scanner scanner = new Scanner(new File("test 2 watki.txt"));
            while (scanner.hasNextLine()) {
                que.add(Long.parseLong(scanner.nextLine()));
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.println("err");
        }
        /*
        for (long i : que) {
            System.out.println(i);
        }*/


        Thread threads[] = new Thread[numOfThreads];
        Object producer = new Object();
        Object consumer = new Object();

        for (int i=0; i < numOfThreads; i++) {
            threads[i] = new Thread(new ThreadThing(que, producer, consumer));
            threads[i].start();
        }

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        while(!command.equals("exit")){
            try{
                synchronized (producer) {
                    que.add(Long.parseLong(command));
                    producer.notifyAll();
                }
            }
            catch(NumberFormatException ex){
                System.out.println("err");
            }
            command = scanner.nextLine();
        }

        synchronized (producer) {
            que.add((long) -111);
            producer.notifyAll();
        }

        for (int i=0; i<numOfThreads; i++){
            threads[i].join();
        }
    }
}

