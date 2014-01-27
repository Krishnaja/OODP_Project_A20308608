package HostelReservations.Book;
import org.junit.Test;
import HostelReservations.Book.Book;
public class BookTest {
	@Test
	public void testExecuteNonQuery() {
		Book book = new Book();
		String command = "Hostel21-Romantic user add --first_name Alekya --last_name Thalari --email alekya@gmail.com";
		book.AddUser(command);
	}
	@Test
	public void testBookroom() {
		Book book = new Book();
		String command = "Hostel21-Romantic book add --search_id 36 --user_id 27";
		book.bookRoom(command);
	}
	@Test
	public void testMakeBooking() {
		Book book = new Book();
		int search_id = 36;
		int user_id= 27;
		book.MakeBooking(search_id, user_id);
	}
}
