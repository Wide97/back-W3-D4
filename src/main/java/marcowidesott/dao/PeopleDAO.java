package marcowidesott.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import marcowidesott.entities.Person;

public class PeopleDAO {
    private EntityManager em;

    public PeopleDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Person p) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(p);
            t.commit();
            System.out.println(p.getNome() + " " + p.getCognome() + " creato!");
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            System.out.println("Errore durante il salvataggio della persona: " + e.getMessage());
        }
    }

    public Person findById(long id) {
        return em.find(Person.class, id);
    }

    public void findByIdAndDelete(long id) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Person found = em.find(Person.class, id);
            if (found != null) {
                em.remove(found);
                System.out.println("Persona eliminata.");
            } else {
                System.out.println("Persona non trovata.");
            }
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            System.out.println("Errore durante la cancellazione della persona: " + e.getMessage());
        }
    }
}


