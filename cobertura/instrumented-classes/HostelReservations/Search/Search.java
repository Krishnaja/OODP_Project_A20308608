package HostelReservations.Search;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;

import org.apache.tools.ant.types.Commandline;

import HostelReservations.Core.Utils;

public class Search {

	public void SearchInventory(String command) {
		// TODO Auto-generated method stub
		String[] args = Commandline.translateCommandline(command);
		String city, hostel_name;
		Date start_date = null, end_date = null;
		int number_of_beds = 0;
		
		hostel_name = args[0];
		if (args.length == 2)
			getInventory(hostel_name);
		else
		{
			for(String arg: args)
			{
				if (arg.contains("beds"))
					number_of_beds = Integer.parseInt(args[9]);
			}
			
			city = args[3];
			try {
				start_date = new SimpleDateFormat("yyyyMMdd").parse(args[5]);
				end_date = new SimpleDateFormat("yyyyMMdd").parse(args[7]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(number_of_beds == 0)
			{
				SearchInventory(hostel_name, city, start_date, end_date);
			}
			else
				SearchInventory(hostel_name, city, start_date, end_date, number_of_beds);
		}
	}
	
	private void getInventory(String hostel_name) {
		// TODO Auto-generated method stub
		String sql_statement = "select b.RoomNumber, b.Bed_ID, bp.Price_Date, bp.Price, bp.IsAvailable "
				+ "from hostels h, beds b, bed_prices bp "
				+ "where h.Hostel_ID = b.Hostel_ID and b.Bed_PK_ID = bp.Bed_PK_ID and h.Hostel_Name = '" + hostel_name + "';";
		try {
			CachedRowSet rs = Utils.executeQuery(sql_statement);
			while(rs.next())
			{
				System.out.println("Room #" + rs.getInt("RoomNumber") + " Bed #" + rs.getInt("Bed_ID") + " Date: " + new SimpleDateFormat("MM-dd-yyyy").format(rs.getDate("Price_Date")) + " Price: " + rs.getFloat("Price"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void SearchInventory(String hostel_name, String city, Date start_date, Date end_date) {
		// TODO Auto-generated method stub
		String sql_statement = "select bp.price_date, Count(b.Bed_ID) AS Number_Of_Beds, Min(bp.price) AS Min_Price, MAX(bp.Price) AS Max_Price "
				+ "from hostels h, beds b, bed_prices bp "
				+ "where h.Hostel_ID = b.Hostel_ID and b.Bed_PK_ID = bp.Bed_PK_ID and h.Hostel_Name = '" + hostel_name + "' and bp.bookingid is null and h.City = '" + city +"' "
				+ "and bp.price_date between '" + Utils.formatDate(start_date, "yyyy-MM-dd") + "' and '" + Utils.formatDate(Utils.AddDays(end_date, -1), "yyyy-MM-dd") + "' "
				+ "group by bp.Price_Date;";
		
		Date st_date, en_date, date_counter;
		
		try {
			CachedRowSet rs = Utils.executeQuery(sql_statement);
			if(rs.size() > 0)
			{
				date_counter = start_date;
				while(rs.next())
				{
					boolean in_loop = true;
					st_date = rs.getDate("price_date");
					en_date = Utils.AddDays(st_date, 1);
					while(in_loop)
					{
						if (Utils.formatDate(date_counter, "MM/dd/yyyy").equals(Utils.formatDate(st_date, "MM/dd/yyyy")))
						{
							if(rs.getInt("Number_Of_Beds") > 1)
								System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(st_date) + " to " + new SimpleDateFormat("MM/dd/yyyy").format(en_date) + ": " + rs.getInt("Number_Of_Beds") + " beds available between $" + rs.getFloat("Min_Price") + " and $" + rs.getFloat("Max_Price"));
							else if(rs.getInt("Number_Of_Beds") == 1)
								System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(st_date) + " to " + new SimpleDateFormat("MM/dd/yyyy").format(en_date) + ": " + rs.getInt("Number_Of_Beds") + " bed available at $" + rs.getFloat("Min_Price"));
							in_loop = false;
						}
						else
							System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(date_counter) + " to " + new SimpleDateFormat("MM/dd/yyyy").format(Utils.AddDays(date_counter, 1)) + ": no beds available.");
						date_counter = Utils.AddDays(date_counter, 1);
					}
				}
			}
			else
			{
				System.out.println("Sorry no beds available. ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void SearchInventory(String hostel_name, String city, Date start_date, Date end_date, int number_of_beds) 
	{
		float bed_price = 0;
		// TODO Auto-generated method stub
		String room_number = "";
		String sql_statement = "select b.Bed_PK_ID, sum(bp.price) as bed_price, b.RoomNumber "
				+ "from hostels h, beds b, bed_prices bp "
				+ "where h.Hostel_ID = b.Hostel_ID and b.Bed_PK_ID = bp.Bed_PK_ID and h.Hostel_Name = '" + hostel_name + "' and bp.bookingid is null and h.City = '" + city +"' "
				+ "and bp.price_date between '" + Utils.formatDate(start_date, "yyyy-MM-dd") + "' and '" + Utils.formatDate(Utils.AddDays(end_date, -1), "yyyy-MM-dd") + "' "
				//+ "and not exists (select * from bed_prices bp1 where bp1.Bed_PK_ID = bp.Bed_PK_ID and bp1.BookingID is not null) "
				+ "group by bp.Bed_PK_ID "
				+ "order by bed_price limit 0, " + number_of_beds  + ";";
		String max_searchid_sql_statement = "insert into searches(CheckInDate, CheckOutDate, NumberOfBeds) values ('" + Utils.formatDate(start_date, "yyyy-MM-dd") + "', '" + Utils.formatDate(end_date, "yyyy-MM-dd") + "', " + number_of_beds + " );";
		System.out.println("Hostel #1, " + city);
		try {
			CachedRowSet rs = Utils.executeQuery(sql_statement);
			int search_id = Utils.executeNonQuery(max_searchid_sql_statement);
			if(rs.size() > 0 && rs.size() >= number_of_beds)
			{
				while(rs.next())
				{
					String update_sql = "update bed_prices "
							+ "set Search_ID = " + search_id 
							+ " where Bed_PK_ID = " + rs.getInt("Bed_PK_ID") 
							+ " and price_date between '" + Utils.formatDate(start_date, "yyyy-MM-dd") + "' and '" + Utils.formatDate(Utils.AddDays(end_date, -1), "yyyy-MM-dd") + "'; ";
					Utils.executeNonQuery(update_sql);
					
					bed_price = bed_price + rs.getFloat("bed_price");
					if(!room_number.contains("room #" + rs.getInt("RoomNumber")))
					{
						room_number = room_number + ", room #" + rs.getInt("RoomNumber");
					}
				}
				System.out.println("search_id: " + search_id + ", $" + bed_price + room_number);
			}
			else
			{
				String sql_statement1 = "select bp.price_date, Count(b.Bed_PK_ID) AS Number_Of_Beds, Min(bp.price) AS Min_Price, MAX(bp.Price) AS Max_Price "
						+ "from hostels h, beds b, bed_prices bp "
						+ "where h.Hostel_ID = b.Hostel_ID and b.Bed_PK_ID = bp.Bed_PK_ID and h.Hostel_Name = '" + hostel_name + "' and bp.bookingid is null and h.City = '" + city +"' "
						+ "and bp.price_date between '" + Utils.formatDate(start_date, "yyyy-MM-dd") + "' and '" + Utils.formatDate(Utils.AddDays(end_date, -1), "yyyy-MM-dd") + "' "
						+ "group by bp.Price_Date;";
				try {
					CachedRowSet rs2 = Utils.executeQuery(sql_statement1);
					if(rs2.size() > 0)
					{
						while(rs2.next())
						{
							if(rs2.getInt("Number_Of_Beds") != number_of_beds)
								System.out.println("Sorry, not enoug beds available on " + rs2.getDate("price_date"));
						}
					}
					else
						System.out.println("Sorry, not enoug beds available on " + Utils.formatDate(start_date, "MM/dd/yyyy"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
