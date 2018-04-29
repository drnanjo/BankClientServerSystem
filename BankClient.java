import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BankClient implements Runnable {

    private int port = 5056;
    private Socket s;
    private Controller controller;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean read = true;

    public BankClient(int port) { this.port = port; }

    public BankClient() {}

    public void setController(Controller controller){
        this.controller = controller;
    }

    public synchronized void sendCommand(String command){
        String[] tokens = command.split("\\s+");

        if(tokens[0].equals("quit"))
            quit();

        try {
            outputStream.writeObject(tokens);
        } catch(IOException e) { System.out.println("Connection closed"); }
    }

    public void quit(){
        try {
            read = false;
            outputStream.close();
            inputStream.close();
            s.close();

        } catch(IOException e){ e.printStackTrace(); }
    }

    @Override
    public void run() {
        try {

            System.out.println("client: " + port);

            while(s == null)
                s = new Socket("localhost", port);

            System.out.println("Connected to localhost in port " + port);

            outputStream = new ObjectOutputStream(s.getOutputStream());
            inputStream = new ObjectInputStream(s.getInputStream());

            while(read){
                String result = (String) inputStream.readObject();

                if(!result.equals(""))
                    controller.update(result);
            }


        }
          catch (Exception e) { System.out.println("Socket closed"); }
          //catch (ClassNotFoundException m) { m.printStackTrace(); }
    }
}
