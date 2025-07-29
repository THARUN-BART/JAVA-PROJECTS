package USER;
import java.util.ArrayList;
import java.util.List;
public class account_manager {
    private static List<account> accounts=new ArrayList<>();

    public static  account findAccByCard(String cardNumber){
        for(account acc:accounts){
            if(acc.getCardNumber().equals(cardNumber)){
                return acc;
            }
        }
        return null;
    }

    public static boolean authenticate(String cardNumber,String pin){
        account account=findAccByCard(cardNumber);
        return account !=null && account.getPin().equals(pin);
    }
    public static void addAccount(account acc) {
        accounts.add(acc);
    }

    public static boolean removeAcc(String cardNumber){
        account acc=findAccByCard(cardNumber);
        if(acc!=null){
            acc.setActive(false);
            return true;
        }
        return false;
    }

    public static List<account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }

    public static int getTotalAccount(){
        return (int) accounts.stream().filter(account::isActive).count();
    }
    public static double getTotalBalance() {
        return accounts.stream()
                      .filter(account::isActive)
                      .mapToDouble(account::getBalance)
                      .sum();
    }
}
