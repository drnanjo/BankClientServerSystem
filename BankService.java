import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BankService implements Runnable {

    private Socket s;
    private Bank bank;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean read = true;

    public BankService(Socket s, Bank bank){
        this.s = s;
        this.bank = bank;
    }

    public void quit(){
        try{
            read = false;
            outputStream.close();
            inputStream.close();
            s.close();

        } catch(Exception e) { e.printStackTrace(); }
    }

    public void parseCommand(String[] tokens){
        if(tokens[0].equals("quit"))
            quit();

        if(tokens[0].equals("balance")){
            String result = tokens[1] + " " + Double.toString(bank.getBalance(Integer.valueOf(tokens[1])));
            try {

                outputStream.writeObject(result);

            } catch(IOException e) { e.printStackTrace(); }
        }

        if(tokens[0].equals("deposit")){
            bank.deposit(Integer.valueOf(tokens[1]), Double.valueOf(tokens[2]));
            String result = tokens[1] + " " + Double.toString(bank.getBalance(Integer.valueOf(tokens[1])));
            try {

                outputStream.writeObject(result);

            } catch(IOException e) { e.printStackTrace(); }
        }

        if(tokens[0].equals("withdraw")){
            bank.withdraw(Integer.valueOf(tokens[1]), Double.valueOf(tokens[2]));
            String result = tokens[1] + " " + Double.toString(bank.getBalance(Integer.valueOf(tokens[1])));
            try {

                outputStream.writeObject(result);

            } catch(IOException e) { e.printStackTrace(); }
        }
    }


    @Override
    public void run() {
        try {

            outputStream = new ObjectOutputStream(s.getOutputStream());
            inputStream = new ObjectInputStream(s.getInputStream());

            while(read){
                String[] tokens = (String[]) inputStream.readObject();

                if(tokens != null)
                    parseCommand(tokens);
            }

        } catch (Exception e){ System.out.println("Connection closed"); }
    }
}