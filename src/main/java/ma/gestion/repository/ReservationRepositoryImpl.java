package ma.gestion.repository;

import ma.gestion.classes.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-reservations");

    @Override
    public void save(Reservation reservation) {

        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(reservation);

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void update(Reservation reservation) {

        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.merge(reservation);

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void delete(Long id) {

        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Reservation r = em.find(Reservation.class, id);
            if (r != null) {
                em.remove(r);
            }

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Reservation getById(Long id) {

        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            return em.find(Reservation.class, id);

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Reservation> getAll() {

        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            return em.createQuery("SELECT r FROM Reservation r", Reservation.class)
                    .getResultList();

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    // fermeture de l'EntityManagerFactory
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}