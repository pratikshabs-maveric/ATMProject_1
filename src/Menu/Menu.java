package Menu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnection.DBConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class Menu {
	private static final int List = 0;
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	
	AccountFunctions accountDetails = new AccountFunctions();
	List<AccountFunctions> accDetails = new ArrayList<AccountFunctions>();
	public void setAccountDetails(List<AccountFunctions> ad)
	{
		accDetails = ad;
	}

	// Login function
	public void login() throws IOException, SQLException {
		boolean end = false;
		int userId = 0;
		int pwd=0;
		
		while (!end) {
			try {
				System.out.print("\nEnter your User ID: ");
				userId = menuInput.nextInt();
				System.out.print("\nEnter your PIN number: ");
				pwd = menuInput.nextInt();
				
				for(int i=0;i<accDetails.size();i++) {
					AccountFunctions ad = accDetails.get(i);
					if (ad.getUserID()==userId && ad.getPassword()==pwd) {
						System.out.println("\nWelcome " + ad.getFirstName() + " !");
						System.out.println("\nPlease select the following option:");
//						getAccountType(ad);
						getSaving(ad);
						end = true;
						accountDetails = ad;
						break;
					}					
				}
				if (!end) {
					System.out.println("\nWrong USER ID or Pin Number");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Character(s). Only Numbers.");
			}
		}
	}	

	public void getSaving(AccountFunctions acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSavings Account: ");
				System.out.println(" Type 1 - Check Balance");
				System.out.println(" Type 2 - Withdraw Amount");
				System.out.println(" Type 3 - Deposit Amount");
				System.out.println(" Type 4 - Transfer Amount");
				System.out.println(" Type 5 - Pin Exchange");
				System.out.println(" Type 6 - Exit");
				System.out.print("Choice: ");
				int selection = menuInput.nextInt();
				switch (selection) {
				case 1:
					System.out.println("\nAccount Balance: " + moneyFormat.format(acc.getBalanceAmount()));
					break;
				case 2:
					acc.getWithdrawInput(acc);
					break;
				case 3:
					acc.getDepositInput(acc);
					break;
				case 4:
					acc.transferInput(acc,accDetails);
					break;
				case 5:
					acc.changePin(acc);
					break;
				case 6:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void createAccount() throws IOException, SQLException {
		int cst_no = 0;
		boolean end = false;
		AccountFunctions adc = new AccountFunctions();
		while (!end) {
			try {
				System.out.println("\nEnter your User Id ");
				cst_no = menuInput.nextInt();
				for(int i=0;i<accDetails.size();i++) {
					AccountFunctions ad = accDetails.get(i);
					if (ad.getUserID()!=cst_no) {
						end = true;
						break;
					}
				}
				if (!end) {
					System.out.println("\nThis customer number is already registered");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		adc.setUserID(cst_no);
		System.out.println("\nEnter PIN to be registered");
		adc.setPassword(menuInput.nextInt());
		System.out.println("\nEnter your First Name");
		adc.setFirstName(menuInput.next());
		System.out.println("\nEnter your Last Name");
		adc.setLastName(menuInput.next());
		System.out.println("\nEnter your Address");
		adc.setAddress(menuInput.next());

		long bal=0;
//		if(accType.equalsIgnoreCase("current"))
//		{
			bal=3000;
//		}
//		
		adc.setBalanceAmount(bal);
		
		DBConnection.insertData(adc);
		
		System.out.println("\nYour new account has been successfuly registered!");
		System.out.println("\nRedirecting to login!");
		login();
	}

	public void mainMenu() throws IOException,SQLException {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\n Type 1 - Login");
				System.out.println("\nType 2 - Create Account");
				System.out.print("\nChoice: ");
				int choice = menuInput.nextInt();
				switch (choice) {
				case 1:
					login();
					end = true;
					break;
				case 2:
					createAccount();
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nThank You!\n");
		menuInput.close();
		System.exit(0);
	}
	
	//===============================================
	// Account type function
		public void getAccountType(AccountFunctions acc) {
			boolean end = false;
			while (!end) {
				try {
					System.out.println("\nSelect the Option: ");
					System.out.println(" Type 1  - Savings Account");
					System.out.println(" Type 2 - Exit");
					System.out.print("\nOptions: ");

					int choice = menuInput.nextInt();

					switch (choice) {
					case 1:
						getSaving(acc);
						break;
					case 2:
						end = true;
						break;
					default:
						System.out.println("\nInvalid Choice.");
					}
				} catch (InputMismatchException e) {
					System.out.println("\nInvalid Choice.");
					menuInput.next();
				}
			}
		}

}