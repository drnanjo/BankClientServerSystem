package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

    private Bank bank;
    private Socket connection;
    private BankService service;
    private ServerSocket provider;
    private static BankServer bankServerInstance = null;
    private Controller controller;
    private static int port = 1025;

    public static BankServer getInstance(int num){
        if(bankServerInstance == null)
            bankServerInstance = new BankServer(num);

        return bankServerInstance;
    }

    private BankServer(int num){
        bank = new Bank(num);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public Bank getBank(){
        return bank;
    }

    public void parseCommand(String[] tokens){
        controller.parseCommand(tokens);
    }

    public void sendCommand(String command){
        service.sendCommand(command);
    }

    public static int getPort(){
        return port;
    }

    public void startServer(){
        try {
            //COME BACK HERE TO MAKE IT ACCEPT MULTIPLE CONNECTIONS
                provider = new ServerSocket(port++, 10);
                System.out.println("Waiting for connection");

                connection = provider.accept();
                System.out.println("Connection received from " + connection.getInetAddress().getHostName());

                service = new BankService(connection, bank, this);
                Thread t = new Thread(service);
                t.start();



        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*public BankServer(Socket socket, Bank bank){
        connection = socket;
        this.bank = bank;
    }

    public BankServer(Bank bank){
        connection = null;
        this.bank = bank;
    }

    public BankServer(){
        connection = null;
        bank = new Bank();
    }*/

    /*@Override
    public void run() {
        try{
            ServerSocket provider = new ServerSocket(2004, 10);
            System.out.println("Waiting for client connection");

            connection = provider.accept();
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());

            out = new ObjectOutputStream(connection.getOutputStream());


        } catch (IOException e){
            e.printStackTrace();
        }
    }*/
}
