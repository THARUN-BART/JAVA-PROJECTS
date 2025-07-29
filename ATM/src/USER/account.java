package USER;

public class account {
    private String accountNumber;
    private String cardNumber;
    private String pin;
    private String holderName;
    private double balance;
    private boolean isActive;

    public account(String accountNumber, String cardNumber, String pin,
                   String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.holderName = holderName;
        this.balance = balance;
        this.isActive = true;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public boolean isActive() { return isActive; }

    public  void setBalance(double balance){
        this.balance=balance;
    }
    public void setActive(boolean active) { this.isActive = active; }
    public void setPin(String pin) { this.pin = pin; }

    public boolean withdraw(double amt){
        if(amt>0 && amt<=balance){
            balance-=amt;
            return true;
        }
        return false;
    }

    public void deposite (float amt){
        if(amt>0){
            balance+=amt;
        }
    }

    @Override

    public String toString(){
        return "Account: " + accountNumber + " | Card: " + cardNumber +
               " | Holder: " + holderName + " | Balance: ₹" +
               String.format("%.2f", balance) + " | Status: " +
               (isActive ? "Active" : "Inactive");
    }

}
