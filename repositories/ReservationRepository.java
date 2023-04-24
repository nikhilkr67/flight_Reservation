package com.nikhil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
