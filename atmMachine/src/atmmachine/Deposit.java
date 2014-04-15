
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
    
    public Deposit(){
        
    }
    
    
}
