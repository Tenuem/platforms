import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class Repositorium {
    private Collection<Piwo> rep;

    Repositorium(){
        rep = new HashSet<Piwo>();
    }

    public Collection<Piwo> getRepo(){
        return rep;
    }

    public Optional<Piwo> find(String name) {
        Optional<Piwo> ret = Optional.empty();
        if(rep.contains(new Piwo(name))){
            for (Piwo piwo : rep){
                if(piwo.getName().equals(name)){
                    ret = Optional.of(piwo);
                }
            }
        }
        return ret;
    }

    public void delete(String name) throws IllegalArgumentException {
        if(!rep.remove(new Piwo(name))){
            throw new IllegalArgumentException();
        }
    }

    public void add(Piwo piwo) throws IllegalArgumentException {
        if(!rep.add(piwo)){
            throw new IllegalArgumentException();
        }
    }
}
