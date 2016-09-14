import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

/**
 * File: ATM.java
 * Author:Anthony Argento
 * Date: 20160913:1700
 * Purpose: ATM.java defines all required variables and methods required for 
 *      GUI, it also contains the main method. 
*/

public class ATM extends JFrame {

    /**
    * Variables and GUI elements.
    */
       
    //define static
    private static final double SERVICE_CHARGE = 1.50;
    
    //define counter
    private int counter = 1;
    
    //define text field
    private final JTextField userInput;

    //define radio group
    private final ButtonGroup radios;
    
    //define panels
    private final JPanel buttonPanel;
    private final JPanel textEntry;
    
    //define frame
    private final JOptionPane frame;

    //define buttons
    private final JButton withdraw;
    private final JButton deposit;
    private final JButton transfer;
    private final JButton balance;
    
    //define radio buttons
    private final JRadioButton checkRadio;
    private final JRadioButton saveRadio;
    
    //instantiate check and savings accounts
    private static final Account checking = new Account().new Checking();
    private static final Account savings = new Account().new Savings();

    //define number format
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
   

    /**
    * Listeners.
    * Following four classes listen for action on the buttons of the GUI
    */
    
    //withdraw listener
    class WithdrawListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            //perform steps if less than 5 withdraws have been completed
            if (counter <= 4){
                try {
                    //check for valid input and withdrawn in increments of 20
                    if (getValue() > 0 && getValue() % 20 == 0) {
                        //checking or savings account
                        if (checkRadio.isSelected()) {
                            checking.withdraw(getValue());
                            JOptionPane.showMessageDialog(frame, 
                                    currencyFormatter.format(getValue()) +
                                    " withdrawn from Checking.");
                            counter ++;
                        } else if (saveRadio.isSelected()) {
                            savings.withdraw(getValue());
                            JOptionPane.showMessageDialog(frame, 
                                    currencyFormatter.format(getValue()) +
                                    " withdrawn from Savings.");
                            counter ++;
                        }
                        invalidEntry();
                    } else invalidNumberWithdraw();
                        invalidEntry();
                }//end try
                catch (InsufficientFunds insufficientFunds) {
                }//end catch
            //perform steps if 5 or more withdraws have been completed
            } else if (counter > 4){
                try {
                    //check for valid input and withdrawn in increments of 20
                    if (getValue() > 0 && getValue() % 20 == 0) {
                        //checking or savings account
                        if (checkRadio.isSelected()) {
                            checking.withdrawCharge(getValue());
                            JOptionPane.showMessageDialog(frame, 
                                    currencyFormatter.format(getValue() + SERVICE_CHARGE) +
                                    " withdrawn from Checking.");
                        } else if (saveRadio.isSelected()) {
                            savings.withdrawCharge(getValue());
                            JOptionPane.showMessageDialog(frame, 
                                    currencyFormatter.format(getValue() + SERVICE_CHARGE) +
                                    " withdrawn from Savings.");
                        }
                        invalidEntry();
                    } else invalidNumberWithdraw();
                        invalidEntry();
                }//end try
                catch (InsufficientFunds insufficientFunds) {
                }//end catch                
            }
        }//end actionPerformed
    }//end WithdrawListener

    //deposit listener
    class DepositListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            


            //check for valid input
            if (getValue() > 0) {
                //checking or savings account
                if (checkRadio.isSelected()) {
                    checking.deposit(getValue());
                    JOptionPane.showMessageDialog(frame, currencyFormatter.format(getValue()) +
                            " deposited into Checking.");
                } else if (saveRadio.isSelected()) {
                    savings.deposit(getValue());
                    JOptionPane.showMessageDialog(frame, currencyFormatter.format(getValue()) +
                            " deposited into Savings.");
                }//end if
                invalidEntry();
            } else invalidNumber();
                invalidEntry();
        }//end actionPerformed
    }//end DepositListener

    //transfer listener
    class TransferListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //check for valid input
                if (getValue() > 0) {
                    //checking or savings account
                    if (checkRadio.isSelected()) {
                        savings.transferSubtract(getValue());
                        checking.transferAdd(getValue());
                        JOptionPane.showMessageDialog(frame, 
                                currencyFormatter.format(getValue()) +
                                " transferred from Savings to Checking.");
                    } else if (saveRadio.isSelected()) {
                        checking.transferSubtract(getValue());
                        savings.transferAdd(getValue());
                        JOptionPane.showMessageDialog(frame, 
                                currencyFormatter.format(getValue()) +
                                " transferred from Checking to Savings.");
                    }//end if
                    invalidEntry();
                } else invalidNumber();
                invalidEntry();
            }//end try
            catch (InsufficientFunds insufficientFunds) {
            }//end catch
        }//end actionPerformed
    }//end TransferListener

    //balance listener
    class BalanceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //checking or savings account
            if (checkRadio.isSelected()) {
                JOptionPane.showMessageDialog(frame,
                        "Your checking account balance is: \n" +
                                currencyFormatter.format(checking.getBalance()));
            } else if (saveRadio.isSelected()) {
                JOptionPane.showMessageDialog(frame,
                        "Your savings account balance is: \n" +
                                currencyFormatter.format(savings.getBalance()));
            } else invalidNumber();
            invalidEntry();
        }//end actionPerformed
    }//end BalanceListener

    /**
    * ATM constructor to build GUI.
    */
    
    public ATM(double checkingStartingBalance, double savingsStartingBalance) {

        //set particulars of frame
        super("Bank of Argento -- ATM");
        super.setSize(300, 160);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setResizable(false);
        
        //layout
        super.setLayout(new GridBagLayout());
        GridBagConstraints layout = new GridBagConstraints();
        layout.gridy = 3;
        layout.gridx = 0;
        
        //set labels of buttons
        this.withdraw = new JButton("Withdraw");
        this.deposit = new JButton("Deposit");
        this.transfer = new JButton("Transfer To");
        this.balance = new JButton("Balance");
        
        //set labels of radio buttons
        this.checkRadio = new JRadioButton("Checking");
        this.saveRadio = new JRadioButton("Savings");
        
        //define text field
        this.userInput = new JTextField(" Enter Dollar Amount");
        
        //clear userInput text field on mouse click
        userInput.addMouseListener(new MouseAdapter(){
            @Override
                public void mouseClicked(MouseEvent e){
                    userInput.setText("");
                }
            });
        
        //define frame
        this.frame = new JOptionPane();
        
        //define panel and add buttons
        this.buttonPanel = new JPanel();
        add(buttonPanel);
        buttonPanel.setLayout(new GridLayout(3, 2, 2, 2));
        buttonPanel.add(withdraw);
        buttonPanel.add(deposit);
        buttonPanel.add(transfer);
        buttonPanel.add(balance);
        buttonPanel.add(checkRadio);
        buttonPanel.add(saveRadio);
        
        //define radio button group
        this.radios = new ButtonGroup();
        radios.add(checkRadio);
        radios.add(saveRadio);
        
        //set checking account to default
        checkRadio.setSelected(true);
        
        //set text field
        this.textEntry = new JPanel();
        add(textEntry, layout);
        textEntry.setLayout(new GridLayout(1, 1));
        textEntry.add(userInput);
        userInput.setPreferredSize(new Dimension(150, 25));
        
        //open checking and savings account
        openAccounts(checkingStartingBalance, savingsStartingBalance);

        //listeners
        withdraw.addActionListener(new WithdrawListener());
        deposit.addActionListener(new DepositListener());
        transfer.addActionListener(new TransferListener());
        balance.addActionListener(new BalanceListener());
        
    }//end constructor

    /**
     * Methods.
     * Six methods including Main. Other 5 are error handling and get values
     * from GUI
     * 
     * @param openingCheckBalance
     * @param openingSaveBalance
     */

    //set balance of checking and savings based on input from main
    public static void openAccounts(double openingCheckBalance, 
            double openingSaveBalance) {
        checking.setBalance(openingCheckBalance);
        savings.setBalance(openingSaveBalance);
    }//end openAccounts
    
    //parse text entry to double
    public double getValue() {
        try {
            return Double.parseDouble(userInput.getText());
        } catch (NumberFormatException e) {
            System.out.println("Error: \n" + e);
            invalidEntry();
            return 0;
        }
    }//end getValue

    //clears invalid entrys
    public void invalidEntry() {
        userInput.setText("");
    }//end invalidEntry

    //error handler for withdraw 
     public void invalidNumberWithdraw() {
        JOptionPane.showMessageDialog(frame, "The amount entered is invalid or "
                + "amount is not in increment of 20. \nEntry will be cleared. "
                + "Please try again.");
    }//end invalidNumberWithdraw   
    
    //error handler for deposit, transfer and balance
    public void invalidNumber() {
        JOptionPane.showMessageDialog(frame, "The amount entered is invalid. "
                + "\nEntry will be cleared. Please try again.");
    }//end invalidNumber
    
    
    /*
    * main method
    */
    
    //main class loads 25 dollars in to both accounts and verifies login info
    public static void main(String[] args) {
        
        //define variables for login
        String userName1 = "cmis242";
        String userName2 = "";
        int pw1 = 12345;
        int pw2;
     
        System.out.println("WARNING \nIf you enter incorrect information 3 "
            + "times program will exit.");
        
        //for loop and if statement for login verification
        for (int i = 0; i < 3; i++){            
            //scan in user input
            Scanner scannerIn = new Scanner(System.in);
            System.out.print("User Name: ");
            userName2 = scannerIn.nextLine();
            System.out.print("Password: ");
            pw2 = scannerIn.nextInt();
            
            //verify login information
            if ((userName1.equals(userName2) && (pw1 == pw2))){
                ATM myATM = new ATM(25, 25);
                myATM.setVisible(true);
                break;
            } else {
                System.out.println("Incorrect. \nYou have " + (2 - i) +
                        " attempts remaining");
            }//end if
            
        }//end for loop
        
    }//end main
    
}//end ATM class
