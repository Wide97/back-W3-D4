package marcowidesott.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import marcowidesott.entities.Event;

public class EventsDAO {
    private EntityManager em;

    public EventsDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Event evento) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(evento);
            t.commit();
            System.out.println("Evento - " + evento.getTitolo() + " - creato!");
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            System.out.println("Errore durante il salvataggio dell'evento: " + e.getMessage());
        }
    }

    public Event findById(long id) {
        return em.find(Event.class, id);
    }

    public void findByIdAndDelete(long id) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Event found = em.find(Event.class, id);
            if (found != null) {
                em.remove(found);
                t.commit();
                System.out.println("Evento eliminato.");
            } else {
                System.out.println("Evento non trovato.");
                t.rollback();
            }
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            System.out.println("Errore durante la cancellazione dell'evento: " + e.getMessage());
        }
    }
}

