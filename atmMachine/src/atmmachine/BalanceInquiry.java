/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atmmachine;

/**
 *
 * @author Kyle
 */
public class BalanceInquiry extends Transaction {
    
    public BalanceInquiry(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase){
        super(userAccountNumber, atmScreen, atmBankDatabase);
    }
    
    @Override
    public void execute(){
        
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        
        double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
        
        double totalBalance = bankDatabase.getTotalBalance(getAccountNumber());
        
        screen.displayMessageLine("\nBalance Information");
        screen.displayMessage(" - Available Balance");
        screen.displayDollarAmount(availableBalance);
        screen.displayMessage("\n - Total Balance");
        screen.displayDollarAmount(totalBalance);
        screen.displayMessageLine("");
    }
    
}
