package HostelReservations.Users;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.tools.ant.types.Commandline;

import HostelReservations.Core.Utils;

public class Users {
	
	UserData ud = new UserData();
	
	public void AddUser(String command) {
		// TODO Auto-generated method stub
		InitializeUserData(command);
		SaveUserToDB();
	}
	
	public void InitializeUserData(String command) {
		// TODO Auto-generated method stub
		String[] args = Commandline.translateCommandline(command);
		try {
			for(int i =0; i< args.length; i++)
			{
				if(args[i].contains("user_id"))
				{
					ud.user_id = Integer.parseInt(args[i+1]);
				}
				if(args[i].contains("first_name"))
				{
					ud.first_name = args[i+1];
				}
				if(args[i].contains("last_name"))
				{
					ud.last_name  = args[i+1];
				}
				if(args[i].contains("email"))
				{
					ud.email = args[i+1];
				}
				if(args[i].contains("cc_number"))
				{
					ud.cc_number = args[i+1];
				}
				if(args[i].contains("expiration_date"))
				{
					ud.expiration_date = new SimpleDateFormat("yyyyMMdd").parse(args[i+1]);
					
				}
				if(args[i].contains("security_code"))
				{
					ud.security_code = Integer.parseInt(args[i+1]);
				}
				if(args[i].contains("phone"))
				{
					ud.phone = args[i+1];
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void SaveUserToDB() {
		// TODO Auto-generated method stub
		String sql = "insert into usersdata (First_Name, Last_Name, Email, CC_Number, Expiration_Date, Security_Code, Phone) "
				+ "values ('" + ud.first_name +"', '"+ ud.last_name +"', '" + ud.email + "', '" + ud.cc_number + "', " + ud.expiration_date + ", " + ud.security_code + ", '" + ud.phone + "');";
		
		int new_user_id = Utils.executeNonQuery(sql);
		if(new_user_id > 0)
		{
			System.out.println("user_id: " + new_user_id);
			System.out.println("Name: " + ud.first_name + " " + ud.last_name);
			System.out.println("Email: " + ud.email);
			System.out.println("Date Created: " + Utils.GetTodayDateTime());
		}
		else
			System.out.println("Invalid User information");
	}
	
	public void EditUser(String command) {
		// TODO Auto-generated method stub
		InitializeUserData(command);
		SaveUserChangesToDB();
	}

	public void SaveUserChangesToDB() {
		// TODO Auto-generated method stub
		String sql = "update usersdata "
				+ "set First_Name = '" + ud.first_name +"', "
				+ "Last_Name = '"+ ud.last_name +"', "
				+ "Email = '" + ud.email + "', "
				+ "CC_Number = '" + ud.cc_number + "', "
				+ "Expiration_Date = " + ud.expiration_date + ", "
				+ "Security_Code = " + ud.security_code + ", "
				+ "Phone = '" + ud.phone + "' "
				+ "where user_id = " + ud.user_id + ";";
		Utils.executeNonQuery(sql);
		
		System.out.println("User information changed.");
		System.out.println("user_id: " + ud.user_id);
		System.out.println("Name: " + ud.first_name + " " + ud.last_name);
		System.out.println("Email: " + ud.email);
		
	}

}
