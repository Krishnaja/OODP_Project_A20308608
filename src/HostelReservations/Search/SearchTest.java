package HostelReservations.Search;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
public class SearchTest {
	Search search = new Search();
	@Test
	public void testGetInvontery(){
		search.getInventory("Hostel21-GoldenGate");
	}
	@Test
	public void testSearchInvontery(){
		String command = "Hostel21-GoldenGate search --city 'San Francisco' --start_date 20140701 --end_date 20140703";
		search.SearchInventory(command);	
	}
	@Test
	public void testSearchInvontery2(){
		String hostel_name = "Hostel21-GoldenGate";
		String city ="San Francisco";
		Calendar date = Calendar.getInstance();
	    date.set(Calendar.YEAR, 2014);
	    date.set(Calendar.MONTH, 07);
	    date.set(Calendar.DAY_OF_MONTH, 01);
	    Date start_date = date.getTime();
	    Calendar date1 = Calendar.getInstance();
	    date1.set(Calendar.YEAR, 2014);
	    date1.set(Calendar.MONTH, 07);
	    date1.set(Calendar.DAY_OF_MONTH, 03);
		Date end_date = date1.getTime();
		search.SearchInventory(hostel_name, city, start_date, end_date);
	}
	@Test
	public void testSearchInvonteryBeds(){
		String hostel_name = "Hostel21-GoldenGate";
		String city ="San Francisco";
		Calendar date = Calendar.getInstance();
	    date.set(Calendar.YEAR, 2014);
	    date.set(Calendar.MONTH, 07);
	    date.set(Calendar.DAY_OF_MONTH, 01);
	    Date start_date = date.getTime();
	    Calendar date1 = Calendar.getInstance();
	    date1.set(Calendar.YEAR, 2014);
	    date1.set(Calendar.MONTH, 07);
	    date1.set(Calendar.DAY_OF_MONTH, 03);
		Date end_date = date1.getTime();
		search.SearchInventory(hostel_name, city, start_date, end_date, 2);	
	}
	@Test
	public void testSearchInvonteryNoBeds(){
		String hostel_name = "Hostel21-GoldenGate";
		String city ="San Francisco";
		Calendar date = Calendar.getInstance();
	    date.set(Calendar.YEAR, 2014);
	    date.set(Calendar.MONTH, 07);
	    date.set(Calendar.DAY_OF_MONTH, 03);
	    Date start_date = date.getTime();
	    Calendar date1 = Calendar.getInstance();
	    date1.set(Calendar.YEAR, 2014);
	    date1.set(Calendar.MONTH, 07);
	    date1.set(Calendar.DAY_OF_MONTH, 03);
		Date end_date = date1.getTime();
		search.SearchInventory(hostel_name, city, start_date, end_date, 1);		
	}
}
	