/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.account;


/**
 *
 * @author noaha
 */
public enum TransactionType {
    Withdraw("Withdraw"),Deposit("Deposit");

    private TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
     
    public static String[] getArrayTransaction(){
        final String transactionTypes[] = {Withdraw.getDescription(), Deposit.getDescription()};
        return transactionTypes;
    }
    private final String description;
    
    
}
