package HostelReservations.Book;

import java.util.ArrayList;

import HostelReservations.RoomsBeds.RoomBedsData;

public class HostelsData {
	private String name;
	private String street;
	private String city;
	private String state;
	private String postal_code;
	private String country;
	private String phone;
	private String email;
	private String facebook;
	private String web;
	private String check_in_time;
	private String check_out_time;
	private boolean smoking;
	private boolean alcohol;
	private int cancellation_deadline;
	private String cancellation_penalty;
	private ArrayList<RoomBedsData> room_beds;
	
	public void setRoomBedsData()
	{
		this.room_beds = new ArrayList<RoomBedsData>(); 
	}
	public ArrayList<RoomBedsData> getRoomBedsData()
	{
		return this.room_beds;
	}
	
	public void setName(String value)
	{
	this.name = value;
	}

	public String getName()
	{
	return this.name;
	}
	public void setStreet(String value)
	{
	this.street = value;
	}

	public String getStreet()
	{
	return this.street;
	}
	public void setCity(String value)
	{
	this.city = value;
	}

	public String getCity()
	{
	return this.city;
	}
	public void setState(String value)
	{
	this.state = value;
	}

	public String getState()
	{
	return this.state;
	}
	public void setPostalCode(String value)
	{
	this.postal_code = value;
	}

	public String getPostalCode()
	{
	return this.postal_code;
	}
	public void setCountry(String value)
	{
	this.country = value;
	}

	public String getCountry()
	{
	return this.country;
	}
	public void setPhone(String value)
	{
	this.phone = value;
	}

	public String getPhone()
	{
	return this.phone;
	}
	public void setEmail(String value)
	{
	this.email = value;
	}

	public String getEmail()
	{
	return this.email;
	}
	public void setFacebook(String value)
	{
	this.facebook = value;
	}

	public String getFacebook()
	{
	return this.facebook;
	}
	public void setWeb(String value)
	{
	this.web = value;
	}

	public String getWeb()
	{
	return this.web;
	}
	public void setCheckInTime(String value)
	{
	this.check_in_time = value;
	}

	public String getCheckInTime()
	{
	return this.check_in_time;
	}
	public void setCheckOutTime(String value)
	{
	this.check_out_time = value;
	}

	public String getCheckOutTime()
	{
	return this.check_out_time;
	}
	public void setSmoking(boolean value)
	{
	this.smoking = value;
	}

	public boolean getSmoking()
	{
	return this.smoking;
	}
	public void setAlcohol(boolean value)
	{
	this.alcohol = value;
	}

	public boolean getAlcohol()
	{
	return this.alcohol;
	}
	public void setCancellationDeadline(int value)
	{
	this.cancellation_deadline = value;
	}

	public int getCancellationDeadline()
	{
	return this.cancellation_deadline;
	}
	public void setCancellationPenalty(String value)
	{
	this.cancellation_penalty = value;
	}

	public String getCancellationPenalty()
	{
	return this.cancellation_penalty;
	}
}
