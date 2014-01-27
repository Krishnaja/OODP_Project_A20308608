package HostelReservations.RoomsBeds;

import java.util.Date;

public class RoomBeds {
	private String city;
	private int room_number;
	private int bed_number;
	private Date price_date;
	private float price;
	private boolean isavailable;
	
	public RoomBeds(String city, int room_number, int bed_number, Date price_date, float price, boolean isavailable)
	{
		this.city = city;
		this.room_number = room_number;
		this.bed_number = bed_number;
		this.price_date = price_date;
		this.price = price;
		this.isavailable = isavailable;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public int getRoomNumber()
	{
		return room_number;
	}
	
	public int getBedNumber()
	{
		return bed_number;
	}
	
	public Date getDate()
	{
		return price_date;
	}
	
	public float getPrice()
	{
		return price;
	}
	
	public boolean IsAvailable()
	{
		return isavailable;
	}
	
	public String IsAvailableYesNo()
	{
		if(isavailable)
			return "Yes";
		else
			return "No";
	}
}
