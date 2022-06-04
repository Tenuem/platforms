package socketCommunication;

import java.util.Random;
import java.io.Serializable;

public class Message implements Serializable {
    private int[] number = new int[10];
    private String content;

    public Message(int n){
        Random rand = new Random();
        for (int i = 0; i < 10; i++){
            number[i] = rand.nextInt(n) + 1;
        }
    }

    public int[] getNumbers(){
        return number;
    }
}