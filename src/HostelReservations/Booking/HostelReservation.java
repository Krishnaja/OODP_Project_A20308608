package HostelReservations.Booking;
import java.io.*;
import HostelReservations.RoomsBeds.Hostel;
public class HostelReservation {
	/**
	 * @param args
	 */
public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean apprunning = true;
		while(apprunning)
		{
			GetCommands();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String command = null;
			try {
				command = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (command.equals("exit"))
			{
				System.out.println("Exiting application. Thank you.");
				apprunning = false;
			}
			else
			{
				ReadCommand(command);
			}
		}
	}
	public static void ReadCommand(String command) {
		Hostel hs = new Hostel();
		if (command.contains("search") && !command.contains("search_id"))
		{
			hs.Search(command);
		}
		else if (command.contains("book"))
		{
			hs.Book(command);
		}
		else if (command.contains("user"))
		{
			hs.Users(command);
		}
		else if (command.contains("loadinventory"))
		{
			hs.LoadInventory(command);
		}
		else
			System.out.println("Invalid command. Please try again");
	}
	public static void GetCommands() {
		String commands = "search, book, user";
		System.out.println("Enter your command");
		System.out.println(commands);
	}
}
