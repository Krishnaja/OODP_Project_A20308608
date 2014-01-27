package HostelReservations.Core;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import HostelReservations.Book.HostelsData;
import HostelReservations.RoomsBeds.RoomBedsData;
import HostelReservations.Search.Search;

public class ReadXMLFile extends DefaultHandler
{
		ArrayList<HostelsData> hostel_data;
		String xml_file_path;
		String tmp_value;
		HostelsData temp_hd;
		RoomBedsData temp_bd;
		SimpleDateFormat sdf= new SimpleDateFormat("yy-MM-dd");
		 
		public ReadXMLFile(String filepath)
		{
			this.xml_file_path = filepath;
			hostel_data = new ArrayList<HostelsData>();
			System.out.println("Parsing XML...");
			parseDocument();
			System.out.println("Saving data...");
			saveData();
			System.out.println("Load completed.");
		}
	 
		private void saveData() {
			// TODO Auto-generated method stub
			for (HostelsData hd : hostel_data)
			{
				insertupdateHostelData(hd);
			}
		}

		private void insertupdateHostelData(HostelsData hd) {
			// TODO Auto-generated method stub
			int hostel_id = Search.getHostelID(hd.getName());
			if (hostel_id > 0)
			{
				UpdateHostelData(hostel_id, hd);
				insertupdateRoomBeds(hostel_id, hd.getRoomBedsData());
			}
			else
			{
				hostel_id = InsertHostelData(hd);
				insertRoomBeds(hostel_id, hd.getRoomBedsData());
			}
		}

		private void insertupdateRoomBeds(int hostel_id, ArrayList<RoomBedsData> roomBedsData) {
			// TODO Auto-generated method stub
			for(RoomBedsData rb : roomBedsData)
			{
				int bed_pk_id = getBedPKID(hostel_id, rb.getBedId(), rb.getRoom());
				int bed_price_id = 0, booking_id = 0;
				String sql_statement = "select Bed_PriceID, BookingID from bed_prices where Bed_PK_ID = " + bed_pk_id + " and Price_Date = '" + Utils.formatDate(rb.getAvailabilityDate(), "yyyy-MM-dd") + "'";
				try {
					CachedRowSet rs = Utils.executeQuery(sql_statement);
					while(rs.next())
					{
						bed_price_id = rs.getInt("Bed_PriceID");
						booking_id = rs.getInt("BookingID");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(bed_price_id > 0)
				{
					if (booking_id > 0)
					{
						System.out.println("Cannot update bed prices for Room: " + rb.getRoom() + ", Bed: " + rb.getBedId() + ", Date: " + rb.getAvailabilityDate());
					}
					else
					{
						updateBedPrice(bed_price_id, rb.getPrice());
					}
				}
				else
				{
					insertBedPrice(bed_pk_id, rb.getPrice(), rb.getAvailabilityDate());
				}
			}
		}

		private void updateBedPrice(int bed_price_id, float price) {
			// TODO Auto-generated method stub
			String sql_statement = "update bed_prices set Price = " +  price + " where Bed_PriceID = " + bed_price_id;
			try
			{
				Utils.executeNonQuery(sql_statement);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void insertRoomBeds(int hostel_id, ArrayList<RoomBedsData> roomBedsData) {
			// TODO Auto-generated method stub
			for(RoomBedsData rb : roomBedsData)
			{
				int bed_pk_id = getBedPKID(hostel_id, rb.getBedId(), rb.getRoom());
				insertBedPrice(bed_pk_id, rb.getPrice(), rb.getAvailabilityDate());
			}
		}

		private void insertBedPrice(int bed_pk_id, float price, Date availabilityDate) {
			// TODO Auto-generated method stub
			try
			{
				String bed_prices_sql_statement = "insert into hostelsdb.bed_prices(Bed_PK_ID, Price, Price_Date) " +
													"values (" + bed_pk_id + ", " + price + ", '" + Utils.formatDate(availabilityDate, "yyyy-MM-dd") + "')";
				Utils.executeNonQuery(bed_prices_sql_statement);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private int getBedPKID(int hostel_id, int bedId, String room) {
			// TODO Auto-generated method stub
			int bed_pk_id = 0;
			String bed_select_sql_statement = "SELECT Bed_PK_ID FROM hostelsdb.beds where Hostel_ID = " + hostel_id + " and Bed_Id = " + bedId + " and RoomNumber = '" + room + "';";
			try {
				CachedRowSet rs = Utils.executeQuery(bed_select_sql_statement);
				while(rs.next())
				{
					bed_pk_id = rs.getInt("Bed_PK_ID");
				}
				
				if (bed_pk_id == 0)
				{
					String beds_sql_statement = "insert into hostelsdb.beds (Hostel_ID, Bed_ID, RoomNumber) " + 
												"values (" + hostel_id + ", " + bedId + ", '" + room+ "');";
					bed_pk_id = Utils.executeNonQuery(beds_sql_statement);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return bed_pk_id;
		}

		private int InsertHostelData(HostelsData hd) {
			// TODO Auto-generated method stub
			int hostel_id = 0;
			String sql_statement = "INSERT into hostelsdb.hostels (Hostel_Name, City, State, Postal_Code, Country, Phone, Email, Facebook, Web, Check_In_Time, " +
																	"Check_Out_Time, Smoking, Alcohol, Cancellation_Deadline, Cancellation_Penalty) " +
									"Values ('" + hd.getName() + "', '" + hd.getCity() + "', '" + hd.getState() +"', '" + hd.getPostalCode() + "', '" + hd.getCountry() + "', '" + hd.getPhone() + "', '" + hd.getEmail() + "', '" + 
											hd.getFacebook() + "', '" + hd.getWeb() + "', '" + hd.getCheckInTime() + "', '" + hd.getCheckOutTime() + "', '" + hd.getSmoking() + "', '" +
											hd.getAlcohol() + "', " + hd.getCancellationDeadline() + ", '" + hd.getCancellationPenalty() + "');";
			try{
				hostel_id = Utils.executeNonQuery(sql_statement);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return hostel_id;
		}

		private void UpdateHostelData(int hostel_id, HostelsData hd) {
			// TODO Auto-generated method stub
			String sql_statement = "update hostelsdb.hostels set Hostel_Name = '" + hd.getName() + "', City = '" + hd.getCity() + "', State = '" + hd.getState() + "', " + 
									"Postal_Code = '" + hd.getPostalCode() + "', Country = '" + hd.getCountry() + "', " +
									"Phone = '" + hd.getPhone() + "', Email = '" + hd.getEmail() + "', Facebook = '" + hd.getFacebook() + "', Web = '" + hd.getWeb() + "', " +
									"Check_In_Time = '" + hd.getCheckInTime() + "', Check_Out_Time = '" + hd.getCheckOutTime() + "', Smoking = '" + hd.getSmoking() + "', " + 
									"Alcohol = '" + hd.getAlcohol() + "', Cancellation_Deadline = " + hd.getCancellationDeadline() + ", Cancellation_Penalty = '" + hd.getCancellationPenalty() + "'" +
									"where Hostel_Id = " + hostel_id;
			try{
				Utils.executeNonQuery(sql_statement);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void parseDocument() 
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			try{
				SAXParser parser = factory.newSAXParser();
				parser.parse(xml_file_path, this);

			} catch (ParserConfigurationException e) {
				System.out.println("ParserConfig error");
	        } catch (SAXException e) {
	            System.out.println("SAXException : xml not well formed");
	        } catch (IOException e) {
	            System.out.println("IO error");
	        }
		}

		public void startElement(String uri, String localName, String element, Attributes attributes) throws SAXException {
			//System.out.println("Start Element :" + element); 
			if(element.equalsIgnoreCase("HOSTEL"))
			{
				temp_hd = new HostelsData();
				temp_hd.setRoomBedsData();
			}
			
			if(element.equalsIgnoreCase("AVAILABILITY"))
				temp_bd = new RoomBedsData();
		}
 
		public void endElement(String uri, String localName, String element) throws SAXException 
		{
			//System.out.println("End Element :" + element);
			if(element.equalsIgnoreCase("HOSTEL"))
				hostel_data.add(temp_hd);
			if(element.equalsIgnoreCase("NAME"))
				temp_hd.setName(tmp_value);
			if(element.equalsIgnoreCase("STREET"))
				temp_hd.setStreet(tmp_value);
			if(element.equalsIgnoreCase("CITY"))
				temp_hd.setCity(tmp_value);
			if(element.equalsIgnoreCase("STATE"))
				temp_hd.setState(tmp_value);
			if(element.equalsIgnoreCase("POSTAL_CODE"))
				temp_hd.setPostalCode(tmp_value);
			if(element.equalsIgnoreCase("COUNTRY"))
				temp_hd.setCountry(tmp_value);
			if(element.equalsIgnoreCase("PHONE"))
				temp_hd.setPhone(tmp_value);
			if(element.equalsIgnoreCase("EMAIL"))
				temp_hd.setEmail(tmp_value);
			if(element.equalsIgnoreCase("FACEBOOK"))
				temp_hd.setFacebook(tmp_value);
			if(element.equalsIgnoreCase("WEB"))
				temp_hd.setWeb(tmp_value);
			if(element.equalsIgnoreCase("CHECK_IN_TIME"))
				temp_hd.setCheckInTime(tmp_value);
			if(element.equalsIgnoreCase("CHECK_OUT_TIME"))
				temp_hd.setCheckOutTime(tmp_value);
			if(element.equalsIgnoreCase("SMOKING"))
			{
				boolean smoking = Boolean.parseBoolean(tmp_value); 
				temp_hd.setSmoking(smoking);
			}
			if(element.equalsIgnoreCase("ALCOHOL"))
			{
				boolean alcohol = Boolean.parseBoolean(tmp_value);
				temp_hd.setAlcohol(alcohol);
			}
			if(element.equalsIgnoreCase("CANCELLATION_DEADLINE"))
			{
				int deadline = Integer.parseInt(tmp_value);
				temp_hd.setCancellationDeadline(deadline);
			}
			if(element.equalsIgnoreCase("CANCELLATION_PENALTY"))
			{
				temp_hd.setCancellationPenalty(tmp_value);
			}
			if(element.equalsIgnoreCase("AVAILABILITY"))
			{
				temp_hd.getRoomBedsData().add(temp_bd);
			}
			if(element.equalsIgnoreCase("DATE"))
			{
				Date dt = Utils.ParseDate(tmp_value, "yyyyMMdd");
				temp_bd.availability_date = dt;				
			}
			if(element.equalsIgnoreCase("ROOM"))
			{
				temp_bd.room = tmp_value;				
			}
			if(element.equalsIgnoreCase("BED"))
			{
				int bed_id = Integer.parseInt(tmp_value);
				temp_bd.bed_id = bed_id;				
			}
			if(element.equalsIgnoreCase("PRICE"))
			{
				float price = Float.parseFloat(tmp_value);
				temp_bd.price = price;				
			}
		}
 
		public void characters(char ch[], int start, int length) throws SAXException {
			 tmp_value = new String(ch, start, length);
		}
	 
	}
