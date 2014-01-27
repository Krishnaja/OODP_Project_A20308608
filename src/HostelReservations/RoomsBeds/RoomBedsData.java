package HostelReservations.RoomsBeds;

import java.util.Date;

public class RoomBedsData {
	public Date availability_date;
	public String room;
	public int bed_id;
	public float price;
	private int bed_price_id;
	private int booking_id;
	
	public int getBookingID()
	{
		return this.booking_id;
	}
	public void setBookingID(int value)
	{
		this.booking_id = value;
	}
	
	public int getBedPriceID()
	{
		return this.bed_price_id;
	}
	public void setBedPriceID(int value)
	{
		this.bed_price_id = value;
	}
	
	public void setAvailabilityDate(Date value)
	{
	this.availability_date = value;
	}

	public Date getAvailabilityDate()
	{
	return this.availability_date;
	}
	
	public void setRoom(String value)
	{
	this.room = value;
	}

	public String getRoom()
	{
	return this.room;
	}
	public void setBedId(int value)
	{
	this.bed_id = value;
	}

	public int getBedId()
	{
	return this.bed_id;
	}
	public void setPrice(Float value)
	{
	this.price = value;
	}

	public float getPrice()
	{
	return this.price;
	}
}
