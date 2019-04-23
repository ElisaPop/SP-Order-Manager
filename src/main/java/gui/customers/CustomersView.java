package gui.customers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Customers;

class CustomersView {

    static TableView<Customers> table = new TableView<>();
    private static GridPane newGrid = new GridPane();
    private static Label customersLabel = new Label("Customers");
    private static Region gridRegionH = new Region();
    private static Region gridRegionW = new Region();
    private static Region hBoxRegion = new Region();
    private static Region wBoxRegion = new Region();

    private static Label customersMenuLabel = new Label("Customers");
    static Label ordersMenuLabel =new Label("Orders");
    static Label productsMenuLabel =new Label("Products");
    static Label companyMenuLabel = new Label("Company");
    private static Menu menu1 = new Menu("", customersMenuLabel);
    private static Menu menu2 = new Menu("", ordersMenuLabel);
    private static Menu menu3 = new Menu("", productsMenuLabel);
    private static Menu menu4 = new Menu("", companyMenuLabel);

    private static MenuBar menuBar = new MenuBar();

    private static GridPane editGrid = new GridPane();

    static TableColumn<Customers,Integer> idCol = new TableColumn<>("ID");
    static TableColumn<Customers,String> firstNameCol = new TableColumn<>("First Name");
    static TableColumn<Customers,String> lastNameCol = new TableColumn<>("Last Name");
    static TableColumn<Customers,String> emailCol = new TableColumn<>("Email");
    static TableColumn<Customers,String> telephoneCol = new TableColumn<>("Phone" );

    private static Label idLabel = new Label("ID");
    private static Label customerNameLabel = new Label("Name");
    private static Label customerSurnameLabel = new Label("Surname");
    private static Label customerEmailLabel = new Label("Email");
    private static Label customerPhoneLabel = new Label("Phone");


    static TextField id = new TextField();
    static TextField customerName = new TextField();
    static TextField customerSurname = new TextField();
    static TextField customerEmail = new TextField();
    static TextField customerPhone = new TextField();

    static Button addButton = new Button("Add");
    static Button editButton = new Button("Edit");
    static Button deleteButton = new Button("Delete");
    static Button clearButton = new Button("Clear");
    static TextField idSelect = new TextField();

    BorderPane interfaceUI() {
        table.getItems().clear();
        table.getColumns().clear();
        newGrid.getChildren().clear();
        editGrid.getChildren().clear();

        menuBar.getMenus().clear();
        newGrid.setHgap(10);
        newGrid.setVgap(10);
        newGrid.setPadding(new Insets(10, 10, 10, 10));

        menuBar.getMenus().addAll(menu1,menu2,menu3,menu4);

        idCol.setPrefWidth(50);
        firstNameCol.setPrefWidth(150);
        lastNameCol.setPrefWidth(150);
        emailCol.setPrefWidth(150);
        telephoneCol.setPrefWidth(150);


        table.getColumns().addAll(idCol,firstNameCol, lastNameCol, emailCol,telephoneCol);
        table.setPrefWidth(650);
        table.setPrefHeight(400);

        gridRegionH.setPrefHeight(100);
        VBox.setVgrow(gridRegionH, Priority.ALWAYS);

        gridRegionW.setPrefWidth(40);
        VBox.setVgrow(gridRegionW, Priority.ALWAYS);

        customersLabel.setScaleX(4);
        customersLabel.setScaleY(4);
        customersLabel.setAlignment(Pos.CENTER);

        newGrid.add(gridRegionH, 0, 0);
        newGrid.add(customersLabel, 1, 1,2,1);
        newGrid.add(table, 1, 3,4,4);
        newGrid.setAlignment(Pos.CENTER);

        hBoxRegion.setPrefWidth(200);
        HBox.setHgrow(hBoxRegion, Priority.ALWAYS);

        wBoxRegion.setPrefWidth(200);
        HBox.setHgrow(wBoxRegion, Priority.ALWAYS);

        editGrid.setVgap(10);
        editGrid.setPadding(new Insets(10, 10, 10, 10));
        editGrid.setAlignment(Pos.CENTER);

        id.setEditable(false);
        idLabel.setAlignment(Pos.CENTER);
        customerNameLabel.setAlignment(Pos.CENTER);
        customerSurnameLabel.setAlignment(Pos.CENTER);
        customerEmailLabel.setAlignment(Pos.CENTER);
        customerPhoneLabel.setAlignment(Pos.CENTER);

        editGrid.add(idSelect,5,0);
        editGrid.add(editButton,6,0);
        editGrid.add(deleteButton,7,0);

        editGrid.add(idLabel,0,1);
        editGrid.add(customerNameLabel,1,1);
        editGrid.add(customerSurnameLabel,2,1);
        editGrid.add(customerEmailLabel,3,1);
        editGrid.add(customerPhoneLabel,4,1);

        id.setMaxWidth(50);
        idSelect.setMaxWidth(50);
        addButton.setAlignment(Pos.CENTER);

        editGrid.add(id,0,2);
        editGrid.add(customerName,1,2);
        editGrid.add(customerSurname,2,2);
        editGrid.add(customerEmail,3,2);
        editGrid.add(customerPhone,4,2);
        editGrid.add(addButton,5,3,3,3);
        editGrid.add(clearButton,6,3,3,3);

        editGrid.setBackground(new Background(new BackgroundFill(Color.rgb(35, 45, 55), CornerRadii.EMPTY, Insets.EMPTY)));

        BorderPane borderPane = new BorderPane();

        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(newGrid, editGrid);
        borderPane.setCenter(mainBox);
        borderPane.setTop(menuBar);

        borderPane.setPrefSize(1200,800);
        borderPane.setStyle("-fx-base: rgb(80,91,107);" +
                "    -fx-background: rgb(44,51,61);");
        return borderPane;
    }

}
