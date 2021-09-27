package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TicketDAO {

	private static final Logger logger = LogManager.getLogger("TicketDAO");

	public DataBaseConfig dataBaseConfig = new DataBaseConfig();

	public boolean saveTicket(Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.SAVE_TICKET);
			// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			ps.setInt(1, ticket.getParkingSpot().getId());
			ps.setString(2, ticket.getVehicleRegNumber());
			ps.setDouble(3, ticket.getPrice());
			ps.setTimestamp(4, new Timestamp(ticket.getInTime().getTime()));
			ps.setTimestamp(5, (ticket.getOutTime() == null) ? null : (new Timestamp(ticket.getOutTime().getTime())));
			result = ps.execute();

		} catch (Exception ex) {
			logger.error("Error saving ticket", ex);
		} finally {
			try {
				dataBaseConfig.closePreparedStatement(ps);
			} catch (Exception ex) {
				logger.error("Error closing preparedStatement", ex);
			}

			try {
				dataBaseConfig.closeConnection(con);
			} catch (Exception ex) {
				logger.error("Error closing connection", ex);
			}

		}
		return result;
	}

	public Ticket getTicket(String vehicleRegNumber) {
		Connection con = null;
		Ticket ticket = null;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.GET_TICKET);
			// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			ps.setString(1, vehicleRegNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ticket = new Ticket();
				ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(1), ParkingType.valueOf(rs.getString(6)), false);
				ticket.setParkingSpot(parkingSpot);
				ticket.setId(rs.getInt(2));
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(rs.getDouble(3));
				ticket.setInTime(rs.getTimestamp(4));
				ticket.setOutTime(rs.getTimestamp(5));
			}
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
		} catch (Exception ex) {
			logger.error("Error getting ticket", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
		}
		return ticket;
	}

	// @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("ODR_OPEN_DATABASE_RESOURCE")
	public boolean updateTicket(Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.UPDATE_TICKET);
			ps.setDouble(1, ticket.getPrice());
			ps.setTimestamp(2, new Timestamp(ticket.getOutTime().getTime()));
			ps.setInt(3, ticket.getId());
			result = ps.execute();

		} catch (Exception ex) {
			logger.error("Error saving ticket info", ex);
		} finally {
			try {
				dataBaseConfig.closePreparedStatement(ps);
			} catch (Exception ex) {
				logger.error("Error closing preparedStatement", ex);
			}

			try {
				dataBaseConfig.closeConnection(con);
			} catch (Exception ex) {
				logger.error("Error closing connection", ex);
			}
		}
		return result;
	}

//*************** STORY#2 : -5% discount for recurring users *******************		
	//@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("ODR_OPEN_DATABASE_RESOURCE")
	public boolean isRecurringUser(Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_TICKET_BY_VEHICLE_REG_NUMBER);
			ps.setString(1, ticket.getVehicleRegNumber());
			ps.execute();
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (Exception ex) {
			logger.error("Error fetching next available slot", ex);
		} finally {
			try {
				dataBaseConfig.closeResultSet(rs);
				} catch (Exception ex) {
					logger.error("Error closing resultSet", ex);
				}
			try {
				dataBaseConfig.closePreparedStatement(ps);
			} catch (Exception ex) {
				logger.error("Error closing preparedStatement", ex);
			}

			try {
				dataBaseConfig.closeConnection(con);
			} catch (Exception ex) {
				logger.error("Error closing connection", ex);
			}
		}
		return result;
	}
//******************************************************************************
}
