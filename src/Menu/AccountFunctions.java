package Menu;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import DBConnection.DBConnection;

public class AccountFunctions {
		
	private int UserID;
	private String LastName;
	private String FirstName;
	private String Address;
	private double BalanceAmount;
	private int UPassword;
	private double savingBalance;

	Scanner input = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");


	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getLastName() {
		return LastName;
	}
	
	public double getBalance() {
		return BalanceAmount;
	}
	public void setBalance(double balance) {
		BalanceAmount = balance;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	public double getBalanceAmount() {
		return BalanceAmount;
	}
	public void setBalanceAmount(long balanceAmount) {
		BalanceAmount = balanceAmount;
	}
	public int getPassword() {
		return UPassword;
	}
	public void setPassword(int password) {
		UPassword = password;
	}
	
	public double calcSavingWithdraw(double amount) {
		savingBalance = (savingBalance - amount);
		return savingBalance;
	}
	
	public double calcSavingDeposit(double amount) {
		savingBalance = (savingBalance + amount);
		return savingBalance;
	}
	
	// Change pin
	public void changePin(AccountFunctions acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nEnter your current account pin: ");

				int verifyPin = input.nextInt();
				
				if( verifyPin == acc.getPassword()) {
					System.out.println("\nEnter the new pin you want to update: ");
					int newpin = input.nextInt();
					acc.setPassword(newpin);
					DBConnection.updatePin(acc);
					end = true;
				}else {
					System.out.println("\nPin is incorrect!.");
				}
			}catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}	
		}
	}
	
	// Withdraw input
	public void getWithdrawInput(AccountFunctions acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(acc.getBalance()));
				savingBalance = acc.getBalance();
				System.out.print("\nEnter the amount you want to withdraw: ");
				double amount = input.nextDouble();
				if ((acc.getBalance() - amount) >= 0 && amount >= 0) {
					calcSavingWithdraw(amount);
					System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
					acc.setBalance(savingBalance);
					DBConnection.updateDB(acc);
					end = true;
				} else {
					System.out.println("\nBalance Cannot Be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		} 
	} 
	
	// Get deposit input
	public void getDepositInput(AccountFunctions acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Account Balance: " + moneyFormat.format(acc.getBalance()));
				savingBalance = acc.getBalance();
				System.out.print("\nEnter the amount you want to deposit: ");
				double amount = input.nextDouble();

				if ((savingBalance + amount) >= 0 && amount >= 0) {
					calcSavingDeposit(amount);
					System.out.println("\nCurrent Account Balance: " + moneyFormat.format(savingBalance));
					acc.setBalance(savingBalance);
					DBConnection.updateDB(acc);
					end = true;
				} else {
					System.out.println("\nBalance Cannot Be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}
	
	// Transfer input
	public void transferInput(AccountFunctions acc,List<AccountFunctions> accDetails) {
		boolean end = false;
		boolean flag=false;
		AccountFunctions transferacc = new AccountFunctions();
		while (!end) {
			try {
					System.out.println("\nEnter the Customer Number to whom you need to transfer the amount : ");
					int cust_no = input.nextInt();
					for(int i=0;i<accDetails.size();i++) {
						AccountFunctions ad = accDetails.get(i);
						if (ad.getUserID()==cust_no) {
							flag = true;
							transferacc=ad;
							break;
						}
					if (flag) {

						System.out.println("\nYour Current Savings Account Balance: " + moneyFormat.format(acc.getBalance()));
						savingBalance = acc.getBalance();
						System.out.print("\nEnter the Amount you want to transfer : ");
						double amount = input.nextDouble();
						if ((savingBalance - amount) >= 0 && amount >= 0) {
							calcSavingWithdraw(amount);
							System.out.println("\nYour Current Savings Account Balance: " + moneyFormat.format(savingBalance));
							acc.setBalance(savingBalance);
							DBConnection.updateDB(acc);
							amount = amount+ transferacc.getBalance();
							DBConnection.updateTransferDB(cust_no,amount);
							end = true;
						} else {
							System.out.println("\nBalance Cannot Be Negative.");
						}
				    }
						else
						{
							System.out.println("\nThe Customer Number you have entered is Invalid!");
						}
					
				}

			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}
}
