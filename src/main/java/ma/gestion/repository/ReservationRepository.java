package ma.gestion.repository;




import ma.gestion.classes.Reservation;

import java.util.List;

public interface ReservationRepository {

    void save(Reservation reservation);
    void update(Reservation reservation);
    void delete(Long id);
    Reservation getById(Long id);
    List<Reservation> getAll();

}
