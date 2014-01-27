package HostelReservations.RoomsBeds;
import org.apache.tools.ant.types.Commandline;

import HostelReservations.Book.Book;
import HostelReservations.Core.ReadXMLFile;
import HostelReservations.Search.*;
import HostelReservations.Users.*;
public class Hostel {
	public void Search(String command) {
		// TODO Auto-generated method stub
		Search s = new Search();
		s.SearchInventory(command);
	}
	public void Users(String command) {
		Users u = new Users();
		if (command.contains("add"))
		{
			u.AddUser(command);

		}
		else if (command.contains("change"))
		{
			u.EditUser(command);
		}
	}
	public void Book(String command) {
		// TODO Auto-generated method stub
		Book b = new Book();
		if (command.contains("cancel"))
		{
			b.CancelBooking(command);

		}
		else if (command.contains("add"))
		{
			b.bookRoom(command);
		}
		
	}
	public void LoadInventory(String command) {
		String[] args = Commandline.translateCommandline(command);
		new ReadXMLFile(args[1]);
		
	}
}
