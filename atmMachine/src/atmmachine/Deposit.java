
package atmmachine;

/**
 *
 * @author Kyle
 */
public class Deposit extends Transaction {
    
    private double amount;
    private Keypad keypad;
    private DepositSlot depositSlot;
    private final static int CANCELLED = 0;
    
    public Deposit(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,
            Keypad atmKeypad, DepositSlot atmDepositSlot){
        
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        depositSlot = atmDepositSlot;
    }
    
    @Override
    public void execute(){
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        
        amount = promptForDepositAmount();
        
        if(amount != CANCELLED){
            
            screen.displayMessage("\nPlease insert deposit envelope containing: ");
            screen.displayDollarAmount(amount);
            screen.displayMessageLine(".");
            
            boolean envelopeReceived = depositSlot.isEnvelopeReceived();
            
            if(envelopeReceived){
                
                screen.displayMessageLine("\nYour Envelope has been received");
                bankDatabase.credit(getAccountNumber(), amount);
                
            }
            else{
                screen.displayMessageLine("\nYou did not insert an envelope\nTransaction cancelled");
            }
            
        }
        else{
            screen.displayMessageLine("\nCancelling transaction...");
        }
    }
    
    private double promptForDepositAmount(){
        
        Screen screen = getScreen();
        screen.displayMessage("Please enter a dollar amount in CENTS: ");
        int input = keypad.getInput();
        
        if(input == CANCELLED){
            return CANCELLED;
        }
        else{
            return (double)input / 100;
        }
    }
    
    
}
