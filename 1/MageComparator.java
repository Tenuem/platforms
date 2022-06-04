import java.util.Comparator;

public class MageComparator implements Comparator<Mage> {
    //@Override
    public int compare(Mage w1, Mage w2) {
        int ret = (w1.getLevel() - w2.getLevel());
        if (ret == 0) ret = (w1.getName().compareTo(w2.getName()));
        if (ret == 0)  ret = (int)(w1.getPower() - w2.getPower());

        return ret;
    }
}
