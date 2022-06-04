import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);

    public static void initializeEM(EntityManager em) {
        em.getTransaction().begin();

        Browar b = new Browar("Chopin", 140000);
        Piwo p = new Piwo("Guiness", 5, b);
        em.persist(p);
        b.getPiwa().add(p);
        p = new Piwo("Huncwot", 7.99, b);
        em.persist(p);
        b.getPiwa().add(p);
        p = new Piwo("Kapral", 2, b);
        em.persist(p);
        b.getPiwa().add(p);
        em.persist(b);

        b = new Browar("Berlioz", 125000);
        p = new Piwo("Tupac", 12, b);
        em.persist(p);
        b.getPiwa().add(p);
        p = new Piwo("Biggie", 11, b);
        em.persist(p);
        b.getPiwa().add(p);
        p = new Piwo("K-Dot", 9, b);
        em.persist(p);
        b.getPiwa().add(p);
        em.persist(b);

        em.getTransaction().commit();
        //em.close();
    }

    public static void dodajPiwo(EntityManager em){
        Piwo piwo = new Piwo();
        System.out.print("nazwa: ");
        piwo.setNazwa(input.next());
        System.out.println();
        System.out.print("cena: ");
        piwo.setCena(Float.parseFloat(input.next()));
        System.out.println();
        String queryString = "SELECT p FROM Browar p where p.nazwa LIKE :nazwa";
        Query query = em.createQuery(queryString, Browar.class);
        System.out.print("nazwa browaru: ");
        query.setParameter("nazwa", input.next());
        Browar browar = (Browar) query.getSingleResult();
        piwo.setBrowar(browar);
        browar.getPiwa().add(piwo);
        em.getTransaction().begin();
        em.persist(piwo);
        em.getTransaction().commit();
    }
    public static void dodajBrowar(EntityManager em){
        System.out.print("nazwa i wartosc: ");
        Browar browar = new Browar(input.next(), Integer.parseInt(input.next()));
        em.getTransaction().begin();
        em.persist(browar);
        em.getTransaction().commit();
    }
    public static void usunPiwo(EntityManager em){
        System.out.println("nazwa: ");
        String nazwa = input.next();
        em.getTransaction().begin();

        String queryString = "SELECT p from Piwo p where p.nazwa LIKE :nazwa";
        Query query = em.createQuery(queryString, Piwo.class);
        query.setParameter("nazwa", nazwa);
        Piwo piwo = (Piwo) query.getSingleResult();
        piwo.getBrowar().getPiwa().remove(piwo);

        queryString = "DELETE from Browar p where p.name LIKE :name";
        query = em.createQuery(queryString);
        query.setParameter("nazwa", piwo.getBrowar().getNazwa());
        em.persist(piwo.getBrowar());

        queryString = "DELETE from Piwo p where p.name LIKE :name";
        query = em.createQuery(queryString);
        query.setParameter("nazwa", nazwa);
        query.executeUpdate();

        em.getTransaction().commit();
    }
    public static void usunBrowar(EntityManager em){
        System.out.println("nazwa: ");
        String nazwa = input.next();

        em.getTransaction().begin();

        String queryString = "DELETE from Piwo p where p.browar.nazwa LIKE :nazwa";
        Query query = em.createQuery(queryString);
        query.setParameter("nazwa", nazwa);
        query.executeUpdate();

        queryString = "DELETE from Browar p where p.name LIKE :name";
        query = em.createQuery(queryString);
        query.setParameter("nazwa", nazwa);
        query.executeUpdate();

        em.getTransaction().commit();
    }

    public static void pokazWszystko(EntityManager em){
        String queryString = "SELECT p FROM Browar p";
        Query query = em.createQuery(queryString, Browar.class);

        List<Browar> browary = query.getResultList();
        queryString = "SELECT p FROM Piwo p";
        query = em.createQuery(queryString, Piwo.class);
        List<Piwo> piwa = query.getResultList();
        System.out.println("Browary: ");
        for (Browar i : browary) {
            System.out.println(i.getNazwa());
        }
        System.out.println("\nPiwa: ");
        for (Piwo i : piwa) {
            System.out.println(i.getNazwa());
        }
    }

    public static void piwaBrowara(EntityManager em){
        System.out.println("cena: ");
        double cena = Double.parseDouble(input.next());
        String queryString = "SELECT p from Piwo p where p.cena < :cena";
        Query query = em.createQuery(queryString, Piwo.class);
        query.setParameter("cena", cena);
        List<Piwo> piwa= query.getResultList();
        System.out.println("Piwa tańsze niż " + cena + ": ");
        for (Piwo i: piwa){
            System.out.println(i.getNazwa());
        }
    }

    public static void tansze(EntityManager em){
        System.out.print("browar: ");
        String nazwa = input.next();
        System.out.println();
        System.out.println("Drozsze niz: ");
        double cena = Double.parseDouble(input.next());
        System.out.println();
        String queryString = "SELECT p from Piwo p where p.cena > :cena AND p.browar.nazwa LIKE :nazwa";
        Query query = em.createQuery(queryString, Piwo.class);
        query.setParameter("cena", cena);
        query.setParameter("nazwa", nazwa);
        List<Piwo> piwa= query.getResultList();
        for (Piwo i: piwa){
            System.out.println(i.getNazwa());
        }
    }

    @PersistenceContext
    static public void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ZakladPiwny");
        EntityManager em = emf.createEntityManager();
        initializeEM(em);

        String command = input.next();
        while (!command.equals("quit")) {
            switch (command) {
                case "all": {
                    pokazWszystko(em);
                    break;
                }
                case "dp": {
                    dodajPiwo(em);
                    break;
                }
                case "db": {
                    dodajBrowar(em);
                    break;
                }
                case "up": {
                    usunPiwo(em);
                    break;
                }
                case "ub": {
                    usunBrowar(em);
                    break;
                }
                case "tansze":{
                    tansze(em);
                    break;
                }
                case "wBrowarze":{
                    piwaBrowara(em);
                    break;
                }
            }
            command = input.next();
        }
        input.close();
        em.close();
        emf.close();
    }
}