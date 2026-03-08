package ma.gestion.service;





import ma.gestion.repository.SalleRepository;
import ma.gestion.classes.* ;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SalleServiceImpl implements SalleService {

    private EntityManager entityManager;
    private SalleRepository salleRepository;

    public SalleServiceImpl(EntityManager entityManager, SalleRepository salleRepository) {
        this.entityManager = entityManager;
        this.salleRepository = salleRepository;
    }

    @Override
    public List<Salle> findAvailableSalles(LocalDateTime start, LocalDateTime end) {
        return salleRepository.getAvailableRooms(start, end);
    }

    @Override
    public List<Salle> searchSalles(Map<String, Object> criteria) {
        return salleRepository.findByCriteria(criteria);
    }

    @Override
    public List<Salle> getPaginatedSalles(int page, int size) {
        if (page < 1) page = 1;
        return salleRepository.getAllPaginated(page, size);
    }

    @Override
    public int getTotalPages(int size) {
        long count = salleRepository.count();
        return (int) Math.ceil((double) count / size);
    }

    @Override
    public Salle getSalleById(Long id) {
        return salleRepository.getById(id);
    }

    @Override
    public List<Salle> getAllSalles() {
        return salleRepository.getAll();
    }

    @Override
    public void saveSalle(Salle salle) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();

            salleRepository.save(salle);

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }

    @Override
    public void updateSalle(Salle salle) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();

            salleRepository.update(salle);

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }

    @Override
    public void deleteSalle(Long id) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();

            Salle salle = salleRepository.getById(id);

            if (salle != null) {
                salleRepository.delete(salle);
            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }

    @Override
    public long countSalles() {

        try {

            return entityManager
                    .createQuery("SELECT COUNT(s) FROM Salle s", Long.class)
                    .getSingleResult();

        } catch (Exception e) {

            e.printStackTrace();
            return 0;
        }
    }
}