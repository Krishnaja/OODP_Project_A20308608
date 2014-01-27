package HostelReservations.RoomsBeds;

import org.junit.Test;

import HostelReservations.Book.Book;
import HostelReservations.Search.Search;
import HostelReservations.Users.Users;

public class HostelTest {

@Test
	public void testGetHostelDetails() {
		Search search = new Search();
		String command = "Hostel21-romantic search --city 'San Francisco' --start_date 20140701 --end_date 20140703";
		search.SearchInventory(command);
		
		command = "Hostel21-goldenGate search";
		search.SearchInventory(command);
		
	}
	
	@Test  
	   public void testUser() {
		Users adduser = new Users();
		String command = "Hostel21 user add --first_name 'Mojo' --last_name 'Jojp' --email mojo@example.com";
		adduser.AddUser(command);
		
		String command1 = "Hostel21 user change --user_id 65  --first_name 'Alekya' --last_name 'Thalari' --email alekya.thalari@example.com --CC_Number 12345, --Expiration_Date 20130702, --Security_Code 123, --Phone 332";
		adduser.EditUser(command1);
		
	}
	
	@Test
	public void testBookings() {
		Book book = new Book();
		String command = "Hostel21-GoldenGate book add --search_id 18 --user_id 2";
		book.bookRoom(command);
	}
}
	


