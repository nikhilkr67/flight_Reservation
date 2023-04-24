package com.nikhil.services;

import com.nikhil.dto.ReservationRequest;
import com.nikhil.entities.Reservation;

public interface ReservationService {
	Reservation bookFlight(ReservationRequest request);

}
