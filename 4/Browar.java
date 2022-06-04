import lombok.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity

public class Browar {
    @Id
    @Getter
    @Setter
    @GeneratedValue
    private int id;

    @Getter
    @Setter
    private String nazwa;

    @Getter
    @Setter
    private long wartosc;

    @OneToMany
    @Getter
    @Setter
    private List<Piwo> piwa;

    public Browar(String name, long wartoscc){
        nazwa = name;
        wartosc = wartoscc;
        piwa = new ArrayList<Piwo>();
    }

    public void show(){
        System.out.println("Browar: " + nazwa + " produkuje piwa: ");
        if (piwa != null){
            for (Piwo i : piwa){
                System.out.print(i.getNazwa() + ", ");
            }
        }
        System.out.println();
    }

}
