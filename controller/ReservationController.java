package com.nikhil.controller;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nikhil.dto.ReservationRequest;
import com.nikhil.entities.Flight;
import com.nikhil.entities.Reservation;
import com.nikhil.repositories.FlightRepository;
import com.nikhil.services.ReservationService;
import com.nikhil.utilities.EmailUtil;
import com.nikhil.utilities.PdfGenerator;

@Controller
public class ReservationController {
	
	private static String filePath="G:\\FlightReservation\\flight_reservation_app_6\\src\\tickets\\booking";
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private FlightRepository flightRepo;
	
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping("/showCompleteReservation")
	public String completeReservation(@RequestParam("flightId") Long flightId,ModelMap modelMap) {
		System.out.println(flightId);
		Optional<Flight> findById = flightRepo.findById(flightId);
		Flight flight = findById.get();
		modelMap.addAttribute("flights", flight);
		return "showCompleteReservation";
	}
	
	@RequestMapping("/confirmRegistration")
	public String confirmRegistration(ReservationRequest request,ModelMap modelMap) {
		Reservation reservation = reservationService.bookFlight(request);
		PdfGenerator pdf = new PdfGenerator();
		pdf.generatePDF(filePath+reservation.getId()+".pdf", 
				reservation.getPassenger().getFirstName(), 
				reservation.getPassenger().getEmail(), 
				reservation.getPassenger().getPhone(), 
				reservation.getFlight().getOperatingAirlines(), 
				reservation.getFlight().getDateOfDeparture(), 
				reservation.getFlight().getDepartureCity(), 
				reservation.getFlight().getArrivalCity());
		
		modelMap.addAttribute("reservationId", reservation.getId());
		EmailUtil util = new EmailUtil();
		String attachment = filePath+reservation.getId()+".pdf";
		emailUtil.sendItinerary(request.getEmail(), attachment);
		return "finalConfirmation";
	}
}

