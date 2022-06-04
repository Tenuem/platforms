import lombok.*;
import javax.persistence.*;


@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Piwo {
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
    private double cena;

    @ManyToOne
    @Getter
    @Setter
    private Browar browar;

    public Piwo(String name, double price, Browar browarek){
        nazwa = name;
        cena = price;
        browar = browarek;
    }

}
