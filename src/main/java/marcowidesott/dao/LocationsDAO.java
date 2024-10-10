package marcowidesott.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import marcowidesott.entities.Location;

public class LocationsDAO {
    private EntityManager em;

    public LocationsDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Location loc) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(loc);
            t.commit();
            System.out.println("Location - " + loc.getNome() + " - creata!");
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            System.out.println("Errore durante il salvataggio della location: " + e.getMessage());
        }
    }

    public Location findById(long id) {
        return em.find(Location.class, id);
    }

    public void findByIdAndDelete(long id) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Location found = em.find(Location.class, id);
            if (found != null) {
                em.remove(found);
                t.commit();
                System.out.println("Location eliminata.");
            } else {
                System.out.println("Location non trovata.");
                t.rollback();
            }
        } catch (Exception e) {
            if (t.isActive()) {
                t.rollback();
            }
            System.out.println("Errore durante la cancellazione della location: " + e.getMessage());
        }
    }
}
