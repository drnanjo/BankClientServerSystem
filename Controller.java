import javafx.application.Platform;
import javafx.scene.control.Label;

public class Controller {

    private Label balanceLabel;
    private Label accountLabel;
    private BankClient bankClient;


    public Controller(Label account, Label label, BankClient bankClient){
        accountLabel = account;
        balanceLabel = label;
        this.bankClient = bankClient;
    }

    public void startClient(){
        new Thread(bankClient).start();
    }

    public void sendCommand(String command){
        bankClient.sendCommand(command);
    }

    public void update(String result){
        String[] tokens = result.split("\\s+");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                accountLabel.setText("Account Number: " + tokens[0]);
                balanceLabel.setText("Balance: " + tokens[1]);
            }
        });
    }

    public void setLabel(Label label){
        balanceLabel = label;
    }

    public void setBankClient(BankClient bankClient){
        this.bankClient = bankClient;
    }


}
