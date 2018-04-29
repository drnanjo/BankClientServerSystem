package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Controller {

    private Label balance;
    private Bank bank;
    private BankServer bankServer;
    private BankClient bankClient;

    public Controller(Label label, BankServer server, BankClient bankClient){
        balance = label;
        bankServer = BankServer.getInstance(10);
        this.bankClient = bankClient;
        this.bank = bankServer.getBank();
    }

    public void startServer(){
        bankServer.startServer();
    }

    public void startClient(){
        bankClient.run();
    }

    public void sendCommand(String command){
        bankClient.sendCommand(command);
    }

    public int getPort(){
        return bankServer.getPort();
    }

    public void setBankClient(BankClient client){
        this.bankClient = client;
    }

    public void setBankServer(BankServer server){
        bankServer = server;
    }

    public void parseCommand(String[] tokens){
        //parses command string into tokens
        //String[] tokens = command.split("\\s+");
        if(tokens[0].equals("balance")) {
            //balance.setText("Balance: " + bank.getBalance(Integer.valueOf(tokens[1])));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    balance.setText("Balance: " + bank.getBalance(Integer.valueOf(tokens[1])));
                }
            });
        }

        if(tokens[0].equals("deposit")) {
            /*bank.deposit(Integer.valueOf(tokens[1]), Double.valueOf(tokens[2]));
            balance.setText("Balance: " + bank.getBalance(Integer.valueOf(tokens[1])));*/

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    bank.deposit(Integer.valueOf(tokens[1]), Double.valueOf(tokens[2]));
                    balance.setText("Balance: " + bank.getBalance(Integer.valueOf(tokens[1])));
                }
            });
        }

        if(tokens[0].equals("withdraw")){
            /*bank.withdraw(Integer.valueOf(tokens[1]), Double.valueOf(tokens[2]));
            balance.setText("Balance: " + bank.getBalance(Integer.valueOf(tokens[1])));*/
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    bank.withdraw(Integer.valueOf(tokens[1]), Double.valueOf(tokens[2]));
                    balance.setText("Balance: " + bank.getBalance(Integer.valueOf(tokens[1])));
                }
            });
        }
    }

}
