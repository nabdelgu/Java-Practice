package bank.account;


/**
 *
 * Enum created to define static values for transaction types
 * 
 * @author noaha
 * @05/12/2019
 */
public enum TransactionType {
    Withdraw("Withdraw"),Deposit("Deposit");

    private TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
     /**
      * Returns an array list of transactionTypes
      * @return String[] 
      */
    public static String[] getArrayTransaction(){
        final String transactionTypes[] = {Withdraw.getDescription(), Deposit.getDescription()};
        return transactionTypes;
    }
    private final String description;
    
    
}
