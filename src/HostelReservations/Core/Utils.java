package HostelReservations.Core;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.*;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
public class Utils {
		public static long getDiffInDays(Date start_date, Date end_date)
	{
		long diff = end_date.getTime() - start_date.getTime();	
		return (diff / (24 * 60 * 60 * 1000));
	}
	public static Date AddDays(Date old_date, int number_of_days) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.setTime(old_date);
		cal.add(Calendar.DATE, number_of_days);
		Date new_date = null;
		new_date = cal.getTime();
		return new_date;
	}
	public static String GetTodayDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public static Date ParseDate(String date_str, String format)
	{
		Date return_date = null;
		try {
			return_date = new SimpleDateFormat(format).parse(date_str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return return_date;
	}
	public static String formatDate(Date date, String format)
	{
		String return_date = null;
		return_date = new SimpleDateFormat(format).format(date);
		return return_date;
	}
	public static String getConnectionString()
	{
		String connectionString = "jdbc:mysql://localhost/hostelsdb?user=root&password=root";
		return connectionString;
	}
	public static int executeNonQuery(String sql_statement)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		int id = 0;
		try {
			conn = DriverManager.getConnection(getConnectionString());
			stmt = conn.prepareStatement(sql_statement, Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();		
			ResultSet rs = stmt.getGeneratedKeys();
	        if (rs.next()){
	            id=rs.getInt(1);
	        }
	        rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if (e.getMessage().contains("User_Unique_IX"))
				System.out.println("User exists in the database");
			else
				e.printStackTrace();
		}
		finally
		{
			try {
				if(conn != null)
					conn.close();
				if(stmt != null)
					stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return id;
	}
	public static CachedRowSet executeQuery(String sql_statement)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		RowSetFactory factory = null;
		CachedRowSet crs = null;	
		try {
			conn = DriverManager.getConnection(getConnectionString());
			stmt = conn.prepareStatement(sql_statement);
			rs = stmt.executeQuery();
			if (rs != null)
			{
				factory = RowSetProvider.newFactory();
				crs = factory.createCachedRowSet();
				crs.populate(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
		finally
		{
			try {
				if(conn != null)
					conn.close();
				if(stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return crs;		
	}
}
