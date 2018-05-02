import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

    private Bank bank;
    private int port = 5056;
    private ServerSocket ss;


    public BankServer(int num){
        bank = new Bank(num);
        try {

            ss = new ServerSocket(port);

        } catch (Exception e){ e.printStackTrace(); }
    }

    public synchronized void startServer(){
        try {

            //Infinite loop to accept all incoming connections from clients
            while(true) {
                System.out.println("Waiting for connection");
                Socket server = ss.accept();
                System.out.println("Connection successful");

                BankService service = new BankService(server, bank);
                Thread t = new Thread(service);
                t.start();
            }


        } catch(IOException e){ e.printStackTrace(); }
    }

    public static void main(String[] args){
        BankServer bankServer = new BankServer(10);

        bankServer.startServer();

    }

}