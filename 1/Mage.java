import java.util.*;

public class Mage implements Comparable<Mage>{
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    public double getPower(){
        return this.power;
    }
    public String getName(){
        return this.name;
    }
    public int getLevel(){
        return this.level;
    }

    public Mage (String name, double power, int level){
        this.name = name;
        this.power = power;
        this.level = level;
        this.apprentices = new HashSet<Mage>();
    }

    public void addTo(Mage newMag){
        apprentices.add(newMag);
    }

    public void printAll(int depth){
        for (Mage i : this.apprentices){
            for (int j = 0; j < depth; j++){
                System.out.print("-");
            }
            System.out.println(i);
            i.printAll(depth+1);
        }
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        if (((Mage) obj).power == this.power && ((Mage) obj).level == this.level && ((Mage) obj).name == this.name)
            return true;
        return false;
    }

    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 67 * result + level;
        return result;
    }

    //@Override
    public int compareTo(Mage other) {

        int ret = name.compareTo(other.name);
        if (ret == 0){
            ret = level - other.level;
            if (ret == 0){
                ret = (int) ((power) - (other.power));
            }
        }
        return ret;
    }

    public int getKids(){
        int result = 0;
        for (Mage i : this.apprentices){
            result += 1 + i.getKids();
        }
        return result;
    }

    @Override
    public String toString(){
        return ("Mage{name= \'"+name+"\', level= "+level+", power= "+power + "}");
    }

    public static Map<Mage, Integer> getMap(Set<Mage> set, int mode){
        Map<Mage, Integer> mapiszon;
        if (mode == 0){
            mapiszon = new HashMap<Mage, Integer>();
        }else if (mode == 2) {
            mapiszon = new TreeMap<Mage, Integer>();
        } else {
            mapiszon = new TreeMap<Mage, Integer>(new MageComparator());
        }
        for (Mage i : set){
            int c = i.getKids();
            Integer count = c;
            mapiszon.put(i, count);
        }

        return mapiszon;
    };

    public static void main(String[] args) {
        Set<Mage> secik;// = new HashSet<Mage>();
        int mode = 1;
        if (args[0].equals("alt")){
            secik = new TreeSet<Mage>(new MageComparator());
        } else if (args[0].equals("norm")) {
            secik = new TreeSet<Mage>();
            mode = 2;
        } else {
            secik = new HashSet<Mage>();
            mode = 0;
        }

        // init
        Mage mag = new Mage("Gandalf", 2.4, 7);
        Mage magi = new Mage("Gandalf", 2.4, 7);
        Mage magik = new Mage("Aadagast", 1.9, 3);
        Mage magiczek = new Mage("MiniMagik", 1.9, 4);
        // add to set
        secik.add(mag);
        secik.add(magi);
        secik.add(magik);
        secik.add(magiczek);
        // padawany
        mag.addTo(magik);
        magik.addTo(magiczek);
        // print
        System.out.println(mag);
        System.out.println("if mag equals magik: " + mag.equals(magik));
        System.out.print("if mag hashCode = magik hashCode: " );
        System.out.println(mag.hashCode() == magik.hashCode());
        System.out.print("if mag hashCode = magi hashCode: " );
        System.out.println(mag.hashCode() == magi.hashCode());
        // compareTo
        System.out.println("compareTo() mag to magik " + mag.compareTo(magik));
        System.out.println("compareTo() magik to mag " + magik.compareTo(mag));
        // comparator
        MageComparator comparator = new MageComparator();
        System.out.println("compare mag to magik by comparator " + comparator.compare(mag, magik));
        System.out.println("compare magik to mag by comparator " + comparator.compare(magik, mag));

        for (Mage i : secik){
            System.out.println("-" + i);
            i.printAll(2);
        }

        Map<Mage,Integer> mapka = getMap(secik, mode);
        for (Mage i : secik){
            System.out.println(i + " : " + mapka.get(i));
        }
    }
}

