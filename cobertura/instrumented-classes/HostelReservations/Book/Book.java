package HostelReservations.Book;

import java.sql.SQLException;

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

	private void MakeBooking(int search_id, int user_id) {
		// TODO Auto-generated method stub
		String booking_sql = "insert into bookings (UserID) values ( " + user_id + " );";
		int booking_id = Utils.executeNonQuery(booking_sql);
		if (booking_id > 0)
		{
			booking_sql = "update bed_prices set BookingID = " + booking_id + " where Search_ID = " + search_id + ";";
			Utils.executeNonQuery(booking_sql);
			
			booking_sql = "select h.City, S.CheckInDate, S.CheckOutDate, S.NumberofBeds, bp.bookingid, ud.First_Name, ud.Last_Name, sum(bp.price) as total_price "
					+ "from bed_prices bp, beds b, hostels h, bookings bk, usersdata ud, Searches S "
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
}
