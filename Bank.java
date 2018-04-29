import java.util.ArrayList;

public class Bank {
    
    private ArrayList<BankAccount> banks;

    public Bank(){
        banks = new ArrayList<>();
    }

    public Bank(int num){
        banks = new ArrayList<BankAccount>();

        for(int i = 0; i < num; i++){
            banks.add(new BankAccount(i, 100));
        }
    }

    public void deposit(int accountNumber, double amount){
        banks.get(accountNumber).deposit(amount);
    }

    public void withdraw(int accountNumber, double amount) { banks.get(accountNumber).withdraw(amount);}

    public double getBalance(int accountNumber) {return banks.get(accountNumber).getbalance();}


}