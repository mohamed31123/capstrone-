package ma.gestion.service;

import ma.gestion.classes.Salle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SalleService {

    List<Salle> findAvailableSalles(LocalDateTime start, LocalDateTime end);

    List<Salle> searchSalles(Map<String, Object> criteria);

    List<Salle> getPaginatedSalles(int page, int size);

    int getTotalPages(int size);

    Salle getSalleById(Long id);

    List<Salle> getAllSalles();

    void saveSalle(Salle salle);

    void updateSalle(Salle salle);

    void deleteSalle(Long id);

    long countSalles();
}