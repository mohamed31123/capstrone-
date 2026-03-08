package ma.gestion.service;





import ma.gestion.classes.Reservation;
import ma.gestion.repository.ReservationRepository;
import ma.gestion.repository.ReservationRepositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private EntityManager entityManager;
    private ReservationRepository reservationRepository;

    public ReservationServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reservationRepository = new ReservationRepositoryImpl();
    }

    public ReservationServiceImpl(EntityManager em, ReservationRepository reservationRepository) {
        this.entityManager = em;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void saveReservation(Reservation reservation) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            reservationRepository.save(reservation);

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }

    @Override
    public void updateReservation(Reservation reservation) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            reservationRepository.update(reservation);

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }

    @Override
    public void deleteReservation(Long id) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            reservationRepository.delete(id);

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }

    @Override
    public Reservation getReservationById(Long id) {

        return reservationRepository.getById(id);

    }

    @Override
    public List<Reservation> getAllReservations() {

        return reservationRepository.getAll();

    }
}