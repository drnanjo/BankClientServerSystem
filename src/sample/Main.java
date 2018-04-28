package sample;

import javafx.application.Application;
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
import javafx.stage.Stage;

//import java.awt.*;

public class Main extends Application {

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
        TextField textBalance = new TextField();

        Label amount = new Label("Amount: ");
        TextField textAmount = new TextField();

        vbox.getChildren().addAll(account, textAccount, balance, textBalance, amount, textAmount);

        return vbox;
    }

    private BorderPane setUpScene(){
        BorderPane pane = new BorderPane();
        HBox hbox = addHBox();
        VBox vbox = addVBox();

        pane.setBottom(hbox);
        pane.setCenter(vbox);

        return pane;
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Parent root = setUpScene();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
