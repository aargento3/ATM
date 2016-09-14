import javax.swing.*;

/**
 * File: InsufficientFunds.java
 * Author: Anthony Argento
 * Date: 20160913: 1900
 * Purpose: File defines the custom exception that is thrown when Insufficient
 *      Funds are available in the account user is attempting to manipulate
*/

public class InsufficientFunds extends Exception {

    public InsufficientFunds() {
        JOptionPane frame = new JOptionPane();
        JOptionPane.showMessageDialog(frame, "Insufficient Funds"
                + "\nPlease check balance");
    }
    
}//end InsufficientFunds