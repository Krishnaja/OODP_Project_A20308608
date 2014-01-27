package HostelReservations.Core;

import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testGetDiffInDays() throws ParseException {
		Date start_date = new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2013");
		Date end_date = new SimpleDateFormat("MM/dd/yyyy").parse("11/10/2013");
		assertEquals(9, Utils.getDiffInDays(start_date, end_date));
	}

	@Test
	public void testAddDays() throws ParseException {
		Date start_date = new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2013");
		Date end_date = new SimpleDateFormat("MM/dd/yyyy").parse("11/10/2013");
		assertEquals(end_date, Utils.AddDays(start_date, 9));
	}

	@Test
	public void testGetTodayDateTime() {
		Date date = new Date();
		assertEquals(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date), Utils.GetTodayDateTime());
	}

	@Test
	public void testParseDate() throws ParseException {
		String date = "11/10/2013";
		Date end_date = new SimpleDateFormat("dd/MM/yyyy").parse("11/10/2013");
		assertEquals(end_date, Utils.ParseDate(date, "dd/MM/yyyy"));
	}

	@Test
	public void testFormatDate() throws ParseException {
		String date = "11/10/2013";
		Date end_date = new SimpleDateFormat("dd/MM/yyyy").parse("11/10/2013");
		assertEquals(date, Utils.formatDate(end_date, "dd/MM/yyyy"));
	}

	@Test
	public void testExecuteNonQuery() {
		String sql = "INSERT into usersdata (First_Name, Last_Name, Email) Values('John', 'Doe', 'john.doe@example.com')";
		assertEquals(0, Utils.executeNonQuery(sql));
		
	    sql = "Update usersdata set First_Name = 'John', Last_Name = 'Doe', Email = 'john.doe@example.com' where user_ID = 1";
		assertEquals(0, Utils.executeNonQuery(sql));
	}

	@Test
	public void testExecuteQuery() {
		String sql = "SELECT * FROM hostelsdb.usersdata;";
		CachedRowSet rs = Utils.executeQuery(sql);
		assertEquals("testing userdata", 3, rs.size());
		
		sql = "SELECT * FROM hostelsdb.bed_prices;";
		rs = Utils.executeQuery(sql);
		assertEquals("testing bed_prices", 26, rs.size());
		
		sql = "SELECT * FROM hostelsdb.beds;";
		rs = Utils.executeQuery(sql);
		assertEquals("testing beds", 6, rs.size());
		
		sql = "SELECT * FROM hostelsdb.bookings;";
		rs = Utils.executeQuery(sql);
		assertEquals("testing bookings", 3, rs.size());
		
		sql = "SELECT * FROM hostelsdb.hostels;";
		rs = Utils.executeQuery(sql);
		assertEquals("testing hostels", 2, rs.size());
		
		sql = "SELECT * FROM hostelsdb.searches;";
		rs = Utils.executeQuery(sql);
		assertEquals("testing searches", 5, rs.size());
		
		
	}

}
