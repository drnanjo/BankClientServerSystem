package sample;

import javafx.application.Application;
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

import java.net.ServerSocket;

//import java.awt.*;

public class Main extends Application {

    private static Stage pStage;
    private Bank bank;
    private BankServer server = null;
    //private VBox vbox;

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

        hbox.getChildren().addAll(buttonBalance, buttonDeposit, buttonWithdraw, buttonQuit);

        return hbox;
    }

    private VBox addVBox(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Label account = new Label("Account #");
        TextField textAccount = new TextField();

        Label balance = new Label("Balance: ");
        //TextField textBalance = new TextField();

        Label amount = new Label("Amount: ");
        TextField textAmount = new TextField();

        //vbox.getChildren().addAll(account, textAccount, balance, textBalance, amount, textAmount);
        vbox.getChildren().addAll(balance, account, textAccount, amount, textAmount);

        return vbox;
    }

    private void showPopup(String msg){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(pStage);
        VBox dialogBox = new VBox(20);
        dialogBox.getChildren().add(new Text(msg));
        Scene dialogScene = new Scene(dialogBox, 300, 50);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void setButtonListeners(HBox hbox, VBox vbox){
        //Can use index 0 - 2 because there are only 3 buttons and they inserted in that order
        Button balance = (Button) hbox.getChildren().get(0);
        Button deposit = (Button) hbox.getChildren().get(1);
        Button withdraw = (Button) hbox.getChildren().get(2);

        balance.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(server == null)
                    showPopup(" Client has not been connected to server yet");
                else {
                    TextField account = (TextField) vbox.getChildren().get(2);
                    Label balance = (Label) vbox.getChildren().get(0);
                    balance.setText("Balance: " + bank.getBalance(Integer.valueOf(account.getText())));
                }
            }
        });

        deposit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(server == null)
                    showPopup(" Client has not been connected to server yet");
                else {
                    TextField account = (TextField) vbox.getChildren().get(2);
                    Label balance = (Label) vbox.getChildren().get(0);
                    TextField amount = (TextField) vbox.getChildren().get(4);

                    bank.deposit(Integer.valueOf(account.getText()), Integer.valueOf(amount.getText()));
                    balance.setText("Balance: " + bank.getBalance(Integer.valueOf(account.getText())));
                }
            }
        });

        withdraw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(server == null)
                    showPopup(" Client has not been connected to server yet");
                else {
                    TextField account = (TextField) vbox.getChildren().get(2);
                    Label balance = (Label) vbox.getChildren().get(0);
                    TextField amount = (TextField) vbox.getChildren().get(4);

                    bank.withdraw(Integer.valueOf(account.getText()), Integer.valueOf(amount.getText()));
                    balance.setText("Balance: " + bank.getBalance(Integer.valueOf(account.getText())));
                }
            }
        });
    }

    private void setUpServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    private BorderPane setUpScene(){
        BorderPane pane = new BorderPane();
        HBox hbox = addHBox();
        //VBox vbox = addVBox();
        VBox vbox = addVBox();

        setButtonListeners(hbox, vbox);

        pane.setBottom(hbox);
        pane.setCenter(vbox);

        return pane;
    }

    private static void setPrimaryStage(Stage stage){
        pStage = stage;
    }

    private static Stage getPrimaryStage(){
        return pStage;
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        setPrimaryStage(primaryStage);

        bank = new Bank();
        for(int i = 0; i < 10; i++)
            bank.addAccount();

        Parent root = setUpScene();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
