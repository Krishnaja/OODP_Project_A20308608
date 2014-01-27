package HostelReservations.Booking;
import org.junit.Test;
public class HostelReservationTest {	
	@Test
	public void testGetCommand(){
		HostelReservation.GetCommands();
	}
	@Test
	public void testReadCommand_search(){
		String command = "Hostel21-Romantic search --city 'San Francisco' --start_date 20140701 --end_date 20140703";
		HostelReservation.ReadCommand(command);
	}
	@Test
	public void testReadCommand_user(){
		String command = "user";
		HostelReservation.ReadCommand(command);
	}
	@Test
	public void testReadCommand_book(){
		String command = "Hostel21-GoldenGate book add --search_id 41 --user_id 1";
		HostelReservation.ReadCommand(command);
	}
}


