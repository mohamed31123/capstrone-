package ma.gestion.service;


import ma.gestion.classes.Reservation;

import java.util.List;

public interface ReservationService {

    void saveReservation(Reservation reservation);

    void updateReservation(Reservation reservation);

    void deleteReservation(Long id);

    Reservation getReservationById(Long id);

    List<Reservation> getAllReservations();
}
