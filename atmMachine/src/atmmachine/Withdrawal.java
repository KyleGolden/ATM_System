
package atmmachine;

/**
 *
 * @author Kyle
 */
public class Withdrawal extends Transaction {
    
    //attributes

    private int amount;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    
    private final static int CANCELLED = 6;
    
    public Withdrawal(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase,
            Keypad atmKeypad, CashDispenser atmCashDispenser){
        
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
        
    }
    
    @Override
    public void execute(){
        boolean cashDispensed = false;
        double availableBalance;
        
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        
        do{
            
            amount = displayMenuOfAmounts();
            
            if(amount != CANCELLED){
                availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
                
                if(amount <= availableBalance){
                    
                    if(cashDispenser.isSufficientCashAvailable(amount)){
                        bankDatabase.debit(getAccountNumber(), amount);
                        
                        cashDispenser.dispenseCash(amount);
                        cashDispensed = true;
                        
                        screen.displayMessageLine("\nYou cash has been dispensed, Please take your cash");
                        
                    }
                    else{
                        screen.displayMessageLine(
                                "\nInsufficient cash in ATM" +
                                "\n\nPlease choose a smaller amount");
                    }
                }
                else{
                        screen.displayMessageLine(
                                "\nInsufficient cash in ATM" +
                                "\n\nPlease choose a smaller amount");
                }
            }
            else{
                screen.displayMessageLine("\nCancelling transaction...");
                return;
            }
        }while(!cashDispensed);
    }
    
    private int displayMenuOfAmounts(){
        int userChoice = 0;
        Screen screen = getScreen();
        
        int[] amounts = {0, 20, 40, 60, 100, 200};
        
        while(userChoice == 0)
        {
            screen.displayMessageLine("\nWithdrawal Menu");
            screen.displayMessageLine("1 - $20");
            screen.displayMessageLine("2 - $40");
            screen.displayMessageLine("3 - $60");
            screen.displayMessageLine("4 - $100");
            screen.displayMessageLine("5 - $200");
            screen.displayMessageLine("6 - Cancel Transaction");
            screen.displayMessage("\nChoose Withdrawal Amount: ");
            
            int input = keypad.getInput();
            
            switch(input){
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    userChoice = amounts[input];
                    break;
                case CANCELLED:
                    userChoice = CANCELLED;
                    break;
                default:
                    screen.displayMessageLine("\nInvalid Selection. Try Again");
                                
            }
        }
        
        return userChoice;
    }
    
}
