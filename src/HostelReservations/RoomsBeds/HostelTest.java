package HostelReservations.RoomsBeds;
import org.junit.Test;
import HostelReservations.Book.Book;
import HostelReservations.Search.Search;
import HostelReservations.Users.Users;
public class HostelTest {
@Test
	public void testGetHostelDetails() {
		Search search = new Search();
		String command = "Hostel21-Romantic search --city 'San Francisco' --start_date 20140701 --end_date 20140703";
		search.SearchInventory(command);
		command = "Hostel21-goldenGate search";
		search.SearchInventory(command);
	}
	@Test  
	   public void testUser() {
		Users adduser = new Users();
		String command = "Hostel21-Romantic user add --first_name 'tej' --last_name 'gadi' --email gadikot@gmail.com";
		adduser.AddUser(command);
		String command1 = "hostel21-romantic user change --user_id 59 --first_name Swetha --last_name Kethireddy--email swetha@gmail.com --cc_number 1234567890123456 --expiration_date 20141201 --security_code 234 --phone 2314566789";
		adduser.EditUser(command1);
	}
	@Test
	public void testBookings() {
		Book book = new Book();
		String command = "Hostel21-Romantic book add --search_id 56 --user_id 2";
		book.bookRoom(command);
	}
}
	


