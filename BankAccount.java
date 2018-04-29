
public class BankAccount {
    private double balance;
    private int accountNumber;

    public BankAccount(int accountNumber, double balance){
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public BankAccount(){
        balance = 100;
        accountNumber = -1;
    }

    public void deposit(double amount){
        balance += amount;
    }

    public void withdraw(double amount){
        balance -= amount;
    }

    public double getbalance(){
        return balance;
    }

    public int getAccountNumber(){
        return accountNumber;
    }

    public void setAccountNumber(int num){
        accountNumber = num;
    }
}