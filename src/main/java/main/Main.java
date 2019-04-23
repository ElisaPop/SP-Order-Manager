package main;

import gui.customers.CustomersController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        CustomersController mainCtrl = new CustomersController();

        primaryStage.setScene(mainCtrl.getSceneCustomers());
        primaryStage.setTitle("Order Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}