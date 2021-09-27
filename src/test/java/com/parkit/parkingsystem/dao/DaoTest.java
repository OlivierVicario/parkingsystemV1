package com.parkit.parkingsystem.dao;

import org.junit.jupiter.api.Test;

import java.util.Date;

import org.junit.jupiter.api.Assertions;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;



public class DaoTest {
	private DataBasePrepareService dataBasePrepareService;
	private ParkingSpotDAO parkingSpotDao;
	private TicketDAO ticketDAO;

	@Test
	void testBikeParkingSpotDAO() throws Exception {
		// ARRANGE
		dataBasePrepareService = new DataBasePrepareService();
		dataBasePrepareService.clearDataBaseEntries();

		// ACT
		ParkingSpot parkingSpot = new ParkingSpot(4, ParkingType.BIKE, false);
		parkingSpotDao = new ParkingSpotDAO();
		parkingSpotDao.updateParking(parkingSpot);

		// ASSERT
		Assertions.assertEquals(5, parkingSpotDao.getNextAvailableSlot(ParkingType.BIKE));

	}

	@Test
	void testCarParkingSpotDAO() throws Exception {
		// ARRANGE
		dataBasePrepareService = new DataBasePrepareService();
		dataBasePrepareService.clearDataBaseEntries();

		// ACT
		ParkingSpot parkingSpot = new ParkingSpot(0, ParkingType.CAR, false);
		parkingSpotDao = new ParkingSpotDAO();
		parkingSpotDao.updateParking(parkingSpot);

		// ASSERT
		Assertions.assertEquals(1, parkingSpotDao.getNextAvailableSlot(ParkingType.CAR));

	}

	@Test
	void testCarTicketDAO() throws Exception {
		// ARRANGE
		dataBasePrepareService = new DataBasePrepareService();
		dataBasePrepareService.clearDataBaseEntries();

		// ACT
		Ticket ticket = new Ticket();
		ticket.setId(1);
		ticket.setParkingSpot(new ParkingSpot(0, ParkingType.CAR, false));
		ticket.getParkingSpot().setId(1);
		ticket.setVehicleRegNumber("1234");
		ticket.setInTime(new Date());
		ticketDAO = new TicketDAO();
		ticketDAO.saveTicket(ticket);

		// ASSERT
		Assertions.assertNotNull(ticketDAO.getTicket("1234"));

	}

	@Test
	void testBikeTicketDAO() throws Exception {
		// ARRANGE
		dataBasePrepareService = new DataBasePrepareService();
		dataBasePrepareService.clearDataBaseEntries();

		// ACT
		Ticket ticket = new Ticket();
		ticket.setId(1);
		ticket.setParkingSpot(new ParkingSpot(0, ParkingType.BIKE, false));
		ticket.getParkingSpot().setId(1);
		ticket.setVehicleRegNumber("1234");
		ticket.setInTime(new Date());
		ticketDAO = new TicketDAO();
		ticketDAO.saveTicket(ticket);

		// ASSERT
		Assertions.assertNotNull(ticketDAO.getTicket("1234"));

	}




	@Test
	public void testIsRecurringUser() {
		// ARRANGE

		dataBasePrepareService = new DataBasePrepareService();

		dataBasePrepareService.clearDataBaseEntries();
		Ticket ticket = new Ticket();
		ticket.setId(1);
		ticket.setParkingSpot(new ParkingSpot(0, ParkingType.BIKE, false));
		ticket.getParkingSpot().setId(1);
		ticket.setVehicleRegNumber("1234");
		ticket.setInTime(new Date());
		ticket.setOutTime(new Date());
		ticketDAO = new TicketDAO();
		boolean result = ticketDAO.isRecurringUser(ticket);
		Assertions.assertTrue(result);
	}

	@Test
	public void testgetTicket() {
		// ARRANGE

		dataBasePrepareService = new DataBasePrepareService();

		dataBasePrepareService.clearDataBaseEntries();
		ticketDAO = new TicketDAO();
		Ticket ticket = ticketDAO.getTicket("1234");
		Assertions.assertEquals(ticket.getVehicleRegNumber(), "1234");
	}



	@Test
	public void testgetNextAvailableSlot() {
		// ARRANGE

		dataBasePrepareService = new DataBasePrepareService();

		dataBasePrepareService.clearDataBaseEntries();
		parkingSpotDao = new ParkingSpotDAO();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
		parkingSpot.setId(1);
		int nextAvailabeSlot = parkingSpotDao.getNextAvailableSlot(ParkingType.CAR);
		Assertions.assertEquals(nextAvailabeSlot, 1);
	}

	@Test
	public void testParkingByParkingNumber() {
		// ARRANGE

		dataBasePrepareService = new DataBasePrepareService();

		dataBasePrepareService.clearDataBaseEntries();
		parkingSpotDao = new ParkingSpotDAO();
		ParkingSpot parkingSpot = parkingSpotDao.getParkingByParkingNumber(1);
		Assertions.assertNotNull(parkingSpot);
	}
	
	@Test
	public void testupdateParking() {
		// ARRANGE

		dataBasePrepareService = new DataBasePrepareService();

		dataBasePrepareService.clearDataBaseEntries();
		parkingSpotDao = new ParkingSpotDAO();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
		parkingSpot.setId(1);
		boolean result = parkingSpotDao.updateParking(parkingSpot);
		Assertions.assertTrue(result);
	}
	
	//@Test
	/*public void testUpdateTicket() throws Exception {
		//ARRANGE
		testBikeTicketDAO();
		//ACT
		ticketDAO=new TicketDAO();
		Ticket ticket = new Ticket();
		ticket.setId(1);
		ticket.setParkingSpot(new ParkingSpot(0, ParkingType.BIKE, false));
		ticket.getParkingSpot().setId(1);
		ticket.setVehicleRegNumber("1234");
		ticket.setInTime(new Date());
		ticket.setOutTime(new Date());
		boolean result=ticketDAO.updateTicket(ticket);
		//ASSERT
		Assertions.assertTrue(result);	
    }*/
}
