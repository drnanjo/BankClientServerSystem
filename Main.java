
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage pStage;
    private Controller controller;


    private HBox addHBox(){
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing((10));

        Button buttonBalance = new Button("Balance");
        buttonBalance.setPrefSize(100, 20);

        Button buttonDeposit = new Button("Deposit");
        buttonDeposit.setPrefSize(100, 20);

        Button buttonWithdraw = new Button("Withdraw");
        buttonWithdraw.setPrefSize(100, 20);

        Button buttonQuit = new Button("Quit");
        buttonQuit.setPrefSize(100, 20);

        /*buttonBalance.setDisable(true);
        buttonDeposit.setDisable(true);
        buttonWithdraw.setDisable(true);*/

        hbox.getChildren().addAll(buttonBalance, buttonDeposit, buttonWithdraw, buttonQuit);

        return hbox;
    }

    private VBox addVBox(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Label account = new Label("Account # to use");
        TextField textAccount = new TextField();

        Label accountNum = new Label("Account Number: ");
        Label balance = new Label("Balance: ");
        //TextField textBalance = new TextField();

        Label amount = new Label("Amount: ");
        TextField textAmount = new TextField();

        vbox.getChildren().addAll(accountNum, balance, account, textAccount, amount, textAmount);

        return vbox;
    }

    private void showPopup(String msg){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(pStage);
        VBox dialogBox = new VBox(20);
        //dialogBox.getChildren().add(new Text(msg));

        Button close = new Button("Close");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });

        dialogBox.getChildren().addAll(new Text(msg), close);
        Scene dialogScene = new Scene(dialogBox, 300, 100);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void setButtonListeners(HBox hbox, VBox vbox){
        //Can use index 0 - 3 because there are only 3 buttons and they inserted in that order
        Button balance = (Button) hbox.getChildren().get(0);
        Button deposit = (Button) hbox.getChildren().get(1);
        Button withdraw = (Button) hbox.getChildren().get(2);
        Button quit = (Button) hbox.getChildren().get(3);

        balance.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TextField account = (TextField) vbox.getChildren().get(3);
                //Label balance = (Label) vbox.getChildren().get(1);

                if(account.getText().equals(""))
                    showPopup("Must input account number");
                else
                    controller.sendCommand("balance " + account.getText());
            }
        });

        deposit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TextField account = (TextField) vbox.getChildren().get(3);
                //Label balance = (Label) vbox.getChildren().get(1);
                TextField amount = (TextField) vbox.getChildren().get(5);

                if(account.getText().equals("") || amount.getText().equals(""))
                    showPopup("Must input account number and amount");
                else {
                    controller.sendCommand("deposit " + account.getText() + " " + amount.getText());
                }
            }
        });

        withdraw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TextField account = (TextField) vbox.getChildren().get(3);
                //Label balance = (Label) vbox.getChildren().get(1);
                TextField amount = (TextField) vbox.getChildren().get(5);

                if(account.getText().equals("") || amount.getText().equals(""))
                    showPopup("Must input account number and amount");
                else {
                    controller.sendCommand("withdraw " + account.getText() + " " + amount.getText());
                }
            }
        });

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.sendCommand("quit");
                Platform.exit();
            }
        });
    }

    private void setUpController(Label account, Label balance){
        //BankServer bankServer = BankServer.getInstance();
        //BankClient bankClient = new BankClient(bankServer.getPort());
        BankClient bankClient = new BankClient();
        //controller = new Controller(balance, bankServer, bankClient);
        controller = new Controller(account, balance, bankClient);
        bankClient.setController(controller);

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                controller.startServer();
            }
        }).start();*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.startClient();
            }
        }).start();
    }

    private BorderPane setUpScene(){
        BorderPane pane = new BorderPane();
        HBox hbox = addHBox();
        VBox vbox = addVBox();

        setButtonListeners(hbox, vbox);
        setUpController((Label) vbox.getChildren().get(0), (Label) vbox.getChildren().get(1));
        /*BankServer server = BankServer.getInstance(10);
        server.setController(controller);
        setUpController((Label) vbox.getChildren().get(0));
        setUpServer(hbox);*/


        pane.setBottom(hbox);
        pane.setCenter(vbox);

        return pane;
    }

    private void setPrimaryStage(Stage stage){
        pStage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        setPrimaryStage(primaryStage);

        Parent root = setUpScene();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
