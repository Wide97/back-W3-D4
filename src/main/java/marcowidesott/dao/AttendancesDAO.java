package marcowidesott.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import marcowidesott.entities.Attendance;

public class AttendancesDAO {
    private EntityManager em;

    public AttendancesDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Attendance attendance) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(attendance);
            t.commit();
            System.out.println("Partecipazione all'evento " + attendance.getEvento().getTitolo() + " per la persona " + attendance.getPersona().getCognome() + ", creata.");
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            System.out.println("Errore durante il salvataggio della partecipazione: " + e.getMessage());
        }
    }

    public Attendance findById(long id) {
        return em.find(Attendance.class, id);
    }

    public void findByIdAndDelete(long id) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Attendance found = em.find(Attendance.class, id);
            if (found != null) {
                em.remove(found);
                t.commit();
                System.out.println("Partecipazione cancellata.");
            } else {
                System.out.println("Partecipazione non trovata.");
                t.rollback();
            }
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            System.out.println("Errore durante la cancellazione della partecipazione: " + e.getMessage());
        }
    }
}
