package bank.account;

/**
 * Bank abstract class with the properties bankName and routing number
 * @author noaha
 */
public abstract class Bank {
    private String bankName;
    private int routingNumber;

    public Bank(String bankName, int routingNumber) {
        this.bankName = bankName;
        this.routingNumber = routingNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(int routingNumber) {
        this.routingNumber = routingNumber;
    }

    @Override
    public String toString() {
        return "Bank Name: " + getBankName() + " Routing Number: " + getRoutingNumber();
    } 
    
    
}
