
package atmmachine;

/**
 *
 * @author Kyle
 */
public class Withdrawal extends Transaction {
    
    //attributes

    private double amount;
    
    //refs to associated objects
    private Keypad keypad;
    private CashDispenser cashDispenser;
    
    public Withdrawal() {
    }
    
    @Override
    public void execute() {
        
    }
    
}
