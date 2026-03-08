package ma.gestion.repository;

import ma.gestion.classes.Salle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SalleRepository {

    List<Salle> getAvailableRooms(LocalDateTime start, LocalDateTime end);

    List<Salle> findByCriteria(Map<String, Object> criteria);

    List<Salle> getAllPaginated(int page, int size);

    long count();


    void save(Salle salle);

    void update(Salle salle);

    void delete(Salle salle);

    Salle getById(Long id);

    List<Salle> getAll();
}
