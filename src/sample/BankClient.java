package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BankClient implements Runnable{

    private Socket request;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String command;
    private int port;

    public BankClient(int port){
        this.port = port;
    }

    public void sendCommand(String command){
        try {
            System.out.println("Server: " + command);
            outputStream.writeObject(command);
            outputStream.flush();

            String[] tokens = command.split("\\s+");
            if(tokens[0].equals("quit")) {
                quit();
            }


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void quit(){
        try {

            outputStream.close();
            inputStream.close();
            request.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            System.out.println("Trying to connect to server");

            request = new Socket("localhost", port);
            System.out.println("Connected to localhost in port " + port);
            //BankServer.changePort();
            //System.out.println(BankServer.getPort());

            outputStream = new ObjectOutputStream(request.getOutputStream());
            outputStream.flush();

            inputStream = new ObjectInputStream(request.getInputStream());



        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
