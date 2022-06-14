package Menu;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import DBConnection.DBConnection;

public class Main {

	public static void main(String[] args) throws IOException, SQLException {

		List<AccountFunctions> ad = DBConnection.DBConnection();
		Menu optionMenu = new Menu();
		greeting();
		optionMenu.setAccountDetails(ad);
		optionMenu.mainMenu();
	}

	public static void greeting() {
		System.out.println("\nWelcome to the ATM Project!");
	}
}