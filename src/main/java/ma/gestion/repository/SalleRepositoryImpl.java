package ma.gestion.repository;

import ma.gestion.classes.Salle;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalleRepositoryImpl implements SalleRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-reservations");

    @Override
    public List<Salle> getAvailableRooms(LocalDateTime start, LocalDateTime end) {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();

            String jpql = "SELECT DISTINCT s FROM Salle s WHERE s.id NOT IN " +
                    "(SELECT r.salle.id FROM Reservation r " +
                    "WHERE (r.dateDebut <= :end AND r.dateFin >= :start))";

            return em.createQuery(jpql, Salle.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Salle> findByCriteria(Map<String, Object> criteria) {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Salle> query = cb.createQuery(Salle.class);
            Root<Salle> salle = query.from(Salle.class);

            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : criteria.entrySet()) {

                String key = entry.getKey();
                Object value = entry.getValue();

                switch (key) {

                    case "nom":
                        predicates.add(cb.like(salle.get("nom"), "%" + value + "%"));
                        break;

                    case "capaciteMin":
                        predicates.add(cb.greaterThanOrEqualTo(salle.get("capacite"), (Integer) value));
                        break;

                    case "capaciteMax":
                        predicates.add(cb.lessThanOrEqualTo(salle.get("capacite"), (Integer) value));
                        break;

                    case "batiment":
                        predicates.add(cb.equal(salle.get("batiment"), value));
                        break;

                    case "etage":
                        predicates.add(cb.equal(salle.get("etage"), value));
                        break;

                    case "equipement":
                        Join<Object, Object> equipements = salle.join("equipements");
                        predicates.add(cb.equal(equipements.get("id"), value));
                        break;
                }
            }

            if (!predicates.isEmpty()) {
                query.where(cb.and(predicates.toArray(new Predicate[0])));
            }

            return em.createQuery(query).getResultList();

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Salle> getAllPaginated(int page, int size) {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();

            return em.createQuery("SELECT s FROM Salle s ORDER BY s.id", Salle.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public long count() {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();

            return em.createQuery("SELECT COUNT(s) FROM Salle s", Long.class)
                    .getSingleResult();

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Salle getById(Long id) {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();
            return em.find(Salle.class, id);

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Salle> getAll() {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();
            return em.createQuery("SELECT s FROM Salle s", Salle.class).getResultList();

        } finally {

            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void save(Salle salle) {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(salle);

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
    public void update(Salle salle) {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.merge(salle);

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
    public void delete(Salle salle) {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            Salle s = em.merge(salle);
            em.remove(s);

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
}