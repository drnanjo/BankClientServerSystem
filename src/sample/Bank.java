package sample;

import java.util.ArrayList;

public class Bank {

    private ArrayList<BankAccount> accounts;    //List of accounts in bank
    private static int count = 0;               //Used to assign unique id to each account

    public Bank(){
        accounts = new ArrayList<>();
    }

    public Bank(int num){
        accounts = new ArrayList<>();

        for(int i = 0; i < num; i++)
            accounts.add(new BankAccount());
    }

    public int addAccount(){
        BankAccount account = new BankAccount();
        accounts.add(account);
        return account.id;
    }

    public void deposit(int accountNumber, double amount){
        accounts.get(accountNumber).deposit(amount);
    }

    public void withdraw(int accountNumber, double amount){
        accounts.get(accountNumber).withdraw(amount);
    }

    public double getBalance(int accountNumber){
        return accounts.get(accountNumber).getBalance();
    }



    private class BankAccount {

        private double balance;
        private int id;

        private BankAccount(){
            balance = 100;
            id = count;
            count++;
        }

        private void deposit(double amount){
            balance += amount;
        }

        private void withdraw(double amount){
            balance -= amount;
            if(balance < 0)
                balance = 0;
        }

        private double getBalance(){
            return balance;
        }

        private int getId(){
            return id;
        }
    }

}