package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BankService implements Runnable {

    private Socket connection;
    private Bank bank;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String command;
    private Controller controller;
    private BankServer bankServer;

    public BankService(Socket s, Bank bank, BankServer server){
        connection = s;
        this.bank = bank;
        bankServer = server;
    }

    public void sendCommand(String command){
        try {

            output.writeObject(command);
            output.flush();
            System.out.println("Server: " + command);

        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {

            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();

            input = new ObjectInputStream(connection.getInputStream());

            while(true){
                try {

                    command = (String) input.readObject();
                    String[] tokens = command.split("\\s+");

                    if(tokens[0].equals("quit")) {
                        output.close();
                        input.close();
                        connection.close();
                        break;
                    }

                    if(!command.equals(""))
                        bankServer.parseCommand(tokens);


                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
