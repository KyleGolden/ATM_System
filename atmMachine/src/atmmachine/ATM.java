
package atmmachine;

/**
 *
 * @author Kyle
 */
public class ATM {
    
    //constants
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int EXIT = 4;
    
    //attributes
    private boolean userAuthenticated;
    private int currentAccountNumber;
    private Screen screen;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private DepositSlot depositSlot;
    private BankDatabase bankDatabase;
    
    //no arg constructor
    public ATM(){
        
        userAuthenticated = false;
        currentAccountNumber = 0;
        screen = new Screen();
        keypad = new Keypad();
        cashDispenser = new CashDispenser();
        depositSlot = new DepositSlot();
        bankDatabase = new BankDatabase();
    }
    
    public void run(){
        
        while(true){
            while(!userAuthenticated){
                screen.displayMessageLine("\nWelcome");
                authenticateUser();
            }
            
            performTransaction();
            userAuthenticated = false;
            currentAccountNumber = 0;
            screen.displayMessageLine("\nThank You, Goodbye");
            
        }
    }//end run()
    
    
    public void authenticateUser(){
        
        screen.displayMessage("\nEnter your account number: ");
        int accountNumber = keypad.getInput();
        screen.displayMessage("\nEnter your PIN: ");
        int pin = keypad.getInput();
        
        userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);
        
        if(userAuthenticated){
            currentAccountNumber = accountNumber;
        }
        else{
            screen.displayMessageLine("Invalid account number or PIN..try again");
        }
    }
    
    public void performTransaction(){
        
        Transaction currentTransaction = null;
        boolean userExited = false;
        
        while(!userExited){
            
            int mainMenuSelection = displayMainMenu();
            
            switch(mainMenuSelection){
                
                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                case DEPOSIT:
                    currentTransaction = createTransaction(mainMenuSelection);
                    currentTransaction.execute();
                    break;
                case EXIT:
                    screen.displayMessageLine("\nExiting the system");
                    userExited = true;
                    break;
                default:
                    screen.displayMessageLine("\nNo Valid Selection");
                    break;
            }
        }
    }
    
    private int displayMainMenu(){
        
        screen.displayMessageLine("\nMain Menu: ");
        screen.displayMessageLine("\n1 - View Balance");
        screen.displayMessageLine("\n2 - Withdraw Cash");
        screen.displayMessageLine("\n3 - Deposit Funds");
        screen.displayMessageLine("\n4 - Exit");
        screen.displayMessageLine("\nEnter a choice: ");
        
        return keypad.getInput();
    }
    
    private Transaction createTransaction(int type){
        
        Transaction temp = null;
        
        switch(type){
            case BALANCE_INQUIRY:
                temp = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
                break;
            case WITHDRAWAL:
                temp = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
                break;
            case DEPOSIT:
                temp = new Deposit(currentAccountNumber, screen, bankDatabase, keypad, depositSlot);
                break;
        }
        
        return temp;
    }
   
}
