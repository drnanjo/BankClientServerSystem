package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer implements Runnable {

    private Bank bank;
    private Socket s;

    public BankServer(Socket socket, Bank bank){
        s = socket;
        this.bank = bank;
    }

    public BankServer(Bank bank){
        s = null;
        this.bank = bank;
    }

    public BankServer(){
        s = null;
        bank = new Bank();
    }

    @Override
    public void run() {
        try{
            ServerSocket provider = new ServerSocket(2004, 10);
            System.out.println("Waiting for client connection");


        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
