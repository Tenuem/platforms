

public class Piwo {

    private String name;
    private int content;

    Piwo(String s, int c){
        name = s;
        content = c;
    }
    Piwo(String s){
        name = s;
        content = 5;
    }
    @Override
    public int hashCode(){
        return this.name.hashCode();
    }
    public String getName(){
        return name;
    }
    @Override
    public String toString(){
        String str = "";
        str = name + " " + content;
        return str;
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Piwo){
            Piwo other = (Piwo) obj;
            return this.name.equals(other.name);
        }

        return false;
    }
}
