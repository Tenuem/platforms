import java.util.Optional;

public class PiwoController {
    private Repositorium repository;

    public PiwoController(Repositorium repository) {
        this.repository = repository;
    }

    public String find(String name) {
        Optional<Piwo> ret = repository.find(name);
        if(ret.isEmpty()){
            return "not found";
        }
        return ret.get().toString();
    }

    public String delete(String name) {
        try{
            repository.delete(name);
        } catch(IllegalArgumentException ex){
            return "not found";
        }

        return "done";
    }

    public String add(String name, int cont) {
        try{
            repository.add(new Piwo(name, cont));
        } catch(IllegalArgumentException ex){
            return "bad request";
        }

        return "done";
    }
}
