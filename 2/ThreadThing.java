import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Queue;

public class ThreadThing implements Runnable{
    private Queue<Long> que;
    private Object producer;
    private Object consumer;

    public ThreadThing(Queue<Long> queu, Object putIn, Object getOut){
        que = queu;
        producer = putIn;
        consumer = getOut;
    }
    @Override
    public void run(){
        Long num;
        String divs = "1";

        while(true){
            synchronized (producer) {
                while(que.peek() == null) {
                    try {
                        producer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (que.peek() == -111)
                    return;
                num = que.peek();
                que.remove();
            }

            for (long i = 2; i <= num; i++) {
                if (num % i == 0) {
                    divs = divs + ", " + i;
                }
            }
            synchronized (consumer) {
                try {
                    Writer writer = new FileWriter("./wyniki.txt", true);
                    writer.append(num + ": " + divs + "\n");
                    writer.close();
                } catch (IOException e) {
                    System.out.println("err");
                }
            }
            divs = "1";
        }
    }
}

