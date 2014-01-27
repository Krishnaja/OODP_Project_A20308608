package HostelReservations.RoomsBeds;

import HostelReservations.Book.Book;
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
		b.bookRoom(command);
	}
}
