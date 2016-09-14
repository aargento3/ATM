/**
 * File: Account.java   
 * Author: Anthony Argento
 * Date: 20160913:1800
 * Purpose: File defines the account class and checking/savings subclasses
 *      It also contains the methods for dealing w/ the money in those accounts
*/

public class Account {

    //define static
    private static final double SERVICE_CHARGE = 1.50;
    
    //define variable
    private double balance;

    //default constructor
    public Account() {
    }//end constructor

    //method to set balance of account
    public void setBalance(double balance) {
        this.balance = balance;
    }//end setBalance

    //method to return balance of account
    public double getBalance() {
        return this.balance;
    }//end getBalance

    //define Checking subclass of Account
    public class Checking extends Account {
        
        //default constructor
        public Checking() {
        }//end constructor
        
    }//end Checking

    //define Savings subclass of Account
    public class Savings extends Account {
        
        //default constructor
        public Savings() {
        }//end constructor
        
    }//end Savings

    //method for withdraws
    public void withdraw(double withdraw) throws InsufficientFunds {
        balanceCheck(withdraw);
        this.balance = this.balance - withdraw;
    }//end 

    //method for withdraws with service charge
    public void withdrawCharge(double withdraw) throws InsufficientFunds {
        balanceCheck(withdraw + SERVICE_CHARGE);
        this.balance = this.balance - (withdraw + SERVICE_CHARGE);
    }//end 
    
    //method for deposits
    public void deposit(double deposit) {
        this.balance = this.balance + deposit;
    }//end deposit

    //method for transfers adding to balance
    public void transferAdd(double transfer) {
        this.balance = this.balance + transfer;
    }//end transferAdd

    //method for transfers subtracting from balance
    public void transferSubtract(double transfer) throws InsufficientFunds {

        balanceCheck(transfer);
        
        this.balance = this.balance - transfer;
    }//end transferSubtract
    
    public void balanceCheck(double check) throws InsufficientFunds{
        
        if (this.balance - check < 0) {
            throw new InsufficientFunds();
        }//end if statement
    }//end balanceCheck
    
}//end Account