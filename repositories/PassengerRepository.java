package com.nikhil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.entities.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

}
