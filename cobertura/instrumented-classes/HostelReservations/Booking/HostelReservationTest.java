package HostelReservations.Booking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;


public class HostelReservationTest {
	
	@Test
	public void testGetCommand(){
		//HostelReservation hr =  new  HostelReservation();
		HostelReservation.GetCommands();
	}
	
	@Test
	public void testReadCommand_search(){
		//HostelReservation hr =  new  HostelReservation();
		String command = "Hostel21-GoldenGate search --city 'San Francisco' --start_date 20140701 --end_date 20140703";
		HostelReservation.ReadCommand(command);
	}
	@Test
	public void testReadCommand_user(){
		//HostelReservation hr =  new  HostelReservation();
		String command = "user";
		HostelReservation.ReadCommand(command);
	}
	@Test
	public void testReadCommand_book(){
		//HostelReservation hr =  new  HostelReservation();
		String command = "Hostel21-GoldenGate book add --search_id 21 --user_id 2";
		HostelReservation.ReadCommand(command);
	}
}


