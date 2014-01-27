package HostelReservations.Book;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;
import org.apache.tools.ant.types.Commandline;
import HostelReservations.Core.Utils;
import HostelReservations.Users.Users;
public class Book extends Users {
	public void bookRoom(String command) {
		// TODO Auto-generated method stub
		String[] args = Commandline.translateCommandline(command);
		int search_id, user_id;
		search_id = Integer.parseInt(args[4]);
		user_id = Integer.parseInt(args[6]);
		MakeBooking(search_id, user_id);
	}
	
	public void CancelBooking(String command) {
		// TODO Auto-generated method stub
		String[] args = Commandline.translateCommandline(command);
		int booking_id;
		booking_id = Integer.parseInt(args[4]);
		CancelBooking(booking_id);
	}
	
	public void MakeBooking(int search_id, int user_id) {
		// TODO Auto-generated method stub
		String booking_sql = "insert into bookings (UserID) values ( " + user_id + " );";
		int booking_id = Utils.executeNonQuery(booking_sql);
		if (booking_id > 0)
		{
			booking_sql = "update bed_prices set BookingID = " + booking_id + " where Search_ID = " + search_id + ";";
			Utils.executeNonQuery(booking_sql);
			booking_sql = "select h.City, S.CheckInDate, S.CheckOutDate, S.NumberofBeds, bp.bookingid, ud.First_Name, ud.Last_Name, sum(bp.price) as total_price "
					+ "from bed_prices bp, beds b, hostels h, bookings bk, usersdata ud, searches S "
					+ "where bp.Bed_PK_ID = b.Bed_PK_ID and b.Hostel_ID = h.Hostel_ID and bp.bookingid = bk.BookingID and bk.UserID = ud.User_ID and bp.Search_ID = S.SearchID "
					+ "and bk.BookingID = " + booking_id + ";";
			try{
				CachedRowSet rs = Utils.executeQuery(booking_sql);
				while(rs.next())
				{
					System.out.println("Booking successful! Here's the detail of your booking:");
					System.out.println("Hostel #1, " + rs.getString("city"));
					System.out.println("Check-in date:" + Utils.formatDate(rs.getDate("CheckInDate"), "MM/dd/yyyy"));
					System.out.println("Check-out date:" + Utils.formatDate(rs.getDate("CheckOutDate"), "MM/dd/yyyy"));
					System.out.println("Beds:" + rs.getInt("NumberofBeds"));
					System.out.println("Booking ID:" + rs.getInt("bookingid"));
					System.out.println("Name:" + rs.getString("First_Name") + " " + rs.getString("Last_Name"));
					System.out.println("Price:" + rs.getFloat("total_price"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void CancelBooking(int booking_id)
	{
		String sql_statement = "select h.Cancellation_Deadline, h.Cancellation_Penalty, sum(Price) as Total_Price, bp.Price_Date " + 
								"from bed_prices bp, beds b, hostels h " +
								"where bp.BookingID = " + booking_id + " and h.Hostel_ID = b.Hostel_ID and b.Bed_PK_ID = bp.Bed_PK_ID " +
								"order by bp.Price_Date Asc limit 1";
		try{
			CachedRowSet rs = Utils.executeQuery(sql_statement);
			while(rs.next())
			{
				int Cancellation_Deadline = rs.getInt("Cancellation_Deadline");
				Date Checkindate = rs.getDate("Price_Date");
				String Cancellation_Penalty = rs.getString("Cancellation_Penalty");
				float price = rs.getFloat("Total_Price");
				Date date = new Date();
				
				if (Utils.getDiffInDays(date, Checkindate) <= (Cancellation_Deadline / 24))
				{
					System.out.println("Cancellation Deadline reached");
					System.out.println(Cancellation_Penalty + " of total price: " + price + " must be returned to the customer.");
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String update_sql_statement = "update bed_prices set BookingID = null, Search_ID = null where BookingID = " + booking_id;
		Utils.executeNonQuery(update_sql_statement);
		System.out.println("Booking ID: " + booking_id + " cancelled.");		
	}
}
