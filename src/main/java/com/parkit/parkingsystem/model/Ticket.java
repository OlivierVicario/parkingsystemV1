package com.parkit.parkingsystem.model;

import java.util.Calendar;
import java.util.Date;

public class Ticket {
	private int id;
	private ParkingSpot parkingSpot;
	private String vehicleRegNumber;
	private double price;
	private Date inTime;
	private Date outTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	public void setParkingSpot(ParkingSpot parkingSpot) {
		this.parkingSpot = parkingSpot;
	}

	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = Math.round(price * 100.0) / 100.0;
	}

	@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP")
	public Date getInTime() {
		return inTime;
	}
	@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP")
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP")
	public Date getOutTime() {
		return outTime;
	}
	@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP")
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
}
