import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {

    private Bank bank;
    private int port = 5056;
    //private static BankServer bankServerState = null;
    private ServerSocket ss;

    /*public static BankServer getInstance(){
        if(bankServerState == null)
            bankServerState = new BankServer();

        return bankServerState;
    }*/

    public BankServer(int num){
        bank = new Bank(num);
        try {

            ss = new ServerSocket(port);

        } catch (Exception e){ e.printStackTrace(); }
    }

    public synchronized void startServer(){
        try {

                //ServerSocket ss = new ServerSocket(port);

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

    public synchronized int getPort() {
        return port;
    }

    /*public static synchronized void changePort(){
        port += 1;
    }*/

    public static void main(String[] args){
        BankServer bankServer = new BankServer(10);

        bankServer.startServer();

    }

}