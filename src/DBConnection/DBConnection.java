package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Menu.AccountFunctions;

public class DBConnection {


public static List<AccountFunctions> DBConnection() {
	List<AccountFunctions> accDetails = new ArrayList<AccountFunctions>();
try
{
String url = "jdbc:mysql://localhost:3306/userrecords";
String uname="root";
String password="root";
String query = "select * from userDetails";
try(Connection conn = DriverManager.getConnection(url, "root","root"))
{

System.out.println("Connection Successfull!");

Statement stmt = conn.createStatement();
ResultSet result = stmt.executeQuery(query);
while(result.next())
{
String dataDisplay = "";
AccountFunctions ad = new AccountFunctions();
for(int i=1;i<=6;i++)
{
	dataDisplay += result.getString(i) +" | ";
}
ad.setUserID(Integer.parseInt(result.getString("UserId")));
ad.setLastName(result.getString("Lname"));
ad.setFirstName(result.getString("Fname"));
ad.setAddress(result.getString("Address"));
ad.setBalanceAmount(Long.parseLong(result.getString("BalanceAmount")));
ad.setPassword(Integer.parseInt(result.getString("UPassword")));
System.out.println(dataDisplay);
accDetails.add(ad);
}
}
}
catch(SQLException e)
{
System.out.println("Connection failed!");
e.printStackTrace();
}
	return accDetails;
}

public static void updateTransferDB(int cst_no,double bal)
{
	try
	{
		String query = "Update userDetails set BalanceAmount="+bal+ " where UserId='"+cst_no+"'" ;

		System.out.println("\nTransaction successfull, amount transfer to the " + cst_no + " account holder!");
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userrecords", "root","root"))
			{
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			conn.close();
			}
		catch(SQLException e)
		{
		System.out.println("Connection failed!");
		e.printStackTrace();
		}
		
	}
	catch(Exception e)
	{
	System.out.println("Connection failed!");
	e.printStackTrace();
	}
}

public static void updateDB(AccountFunctions ad)
{
	try
	{
		String query = "Update userDetails set BalanceAmount="+ad.getBalance()+ 
				" where UserId='"+ad.getUserID()+"'" ;
		System.out.println("Amount updated sucessfully!");
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userrecords", "root", "root"))
			{
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			conn.close();
			}
		catch(SQLException e)
		{
		System.out.println("Connection failed!");
		e.printStackTrace();
		}
		
	}
	catch(Exception e)
	{
	System.out.println("Connection failed!");
	e.printStackTrace();
	}
}

public static void updatePin(AccountFunctions ad)
{
	try
	{
		String query = "Update userDetails set UPassword="+ad.getPassword()+ 
				" where UserId='"+ad.getUserID()+"'" ;

		System.out.println("Pin updated sucessfully!");
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userrecords", "root", "root"))
			{
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			conn.close();
			}
		catch(SQLException e)
		{
		System.out.println("Connection failed!");
		e.printStackTrace();
		}
		
	}
	catch(Exception e)
	{
	System.out.println("Connection failed!");
	e.printStackTrace();
	}
}
public static void insertData(AccountFunctions ad)
{
	try
	{
		String query = "Insert into userDetails values('"+ad.getUserID()+"','"+ad.getFirstName()+""
				+ "','"+ad.getLastName()+"','"+ad.getAddress()+"','"+ad.getBalanceAmount()+"','"+ad.getPassword()+"')";
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userrecords", "root","root"))
			{
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			conn.close();
			}
		catch(SQLException e)
		{
		System.out.println("Connection failed!");
		e.printStackTrace();
		}
		
	}
	catch(Exception e)
	{
	System.out.println("Connection failed!");
	e.printStackTrace();
	}
}
}