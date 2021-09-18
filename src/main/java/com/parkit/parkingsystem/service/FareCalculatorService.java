package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}
//*************** TODO#1 : Fix the code to make the unit tests pass ************
		// int inHour = ticket.getInTime().getHours();
		// int outHour = ticket.getOutTime().getHours();

		long inTime = ticket.getInTime().getTime();
		long outTime = ticket.getOutTime().getTime();

		// TODO: Some tests are failing here. Need to check if this logic is correct
		// int duration = outHour - inHour;
		float duration = ((outTime - inTime) / (1000.0f * 3600.0f));
//******************************************************************************
		
//*************** STORY#1 : Free 30-min parking ********************************
		if (duration <= 0.5f)
			duration = 0.0f;
//******************************************************************************		
		
		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
			break;
		}
		case BIKE: {
			ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
		
//*************** STORY#2 : -5% discount for recurring users *******************		
        TicketDAO ticketDAO = new TicketDAO();
		if (ticketDAO.isRecurringUser(ticket)) {
        	ticket.setPrice(ticket.getPrice()*0.95);
        }
//****************************************************************************** 		
	}
}