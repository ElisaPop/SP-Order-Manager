package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Company;

public class CompanyView {

    static TableView<Company> table = new TableView<>();
    static GridPane newGrid = new GridPane();
    static Label companyLabel = new Label("Company");
    static Region gridRegionH = new Region();
    static Region gridRegionW = new Region();
    static Region hBoxRegion = new Region();
    static Region wBoxRegion = new Region();

    static Label customersMenuLabel = new Label("Customers");
    static Label ordersMenuLabel =new Label("Orders");
    static Label productsMenuLabel =new Label("Products");
    static Label companyMenuLabel = new Label("Company");
    static Menu menu1 = new Menu("", customersMenuLabel);
    static Menu menu2 = new Menu("", ordersMenuLabel);
    static Menu menu3 = new Menu("", productsMenuLabel);
    static Menu menu4 = new Menu("", companyMenuLabel);

    static MenuBar menuBar = new MenuBar();

    static GridPane editGrid = new GridPane();

    static TableColumn<Company,Integer> idCol = new TableColumn<>("ID");
    static TableColumn<Company,String> firstNameCol = new TableColumn<>("Name");

    static Label idLabel = new Label("ID");
    static Label companyNewNameLabel = new Label("Name");


    static TextField id = new TextField();
    static TextField companyNewName = new TextField();

    static Button addButton = new Button("Add");
    static Button editButton = new Button("Edit");
    static Button deleteButton = new Button("Delete");
    static Button clearButton = new Button("Clear");
    static TextField idSelect = new TextField();

    public BorderPane interfaceUI() {
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

        table.getColumns().addAll(idCol,firstNameCol);
        table.setPrefWidth(200);
        table.setPrefHeight(400);

        gridRegionH.setPrefHeight(100);
        VBox.setVgrow(gridRegionH, Priority.ALWAYS);

        gridRegionW.setPrefWidth(40);
        VBox.setVgrow(gridRegionW, Priority.ALWAYS);

        companyLabel.setScaleX(4);
        companyLabel.setScaleY(4);
        companyLabel.setAlignment(Pos.CENTER);

        newGrid.add(gridRegionH, 0, 0);
        newGrid.add(companyLabel, 1, 1,2,1);
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
        companyNewNameLabel.setAlignment(Pos.CENTER);

        editGrid.add(idSelect,5,0);
        editGrid.add(editButton,6,0);
        editGrid.add(deleteButton,7,0);

        editGrid.add(idLabel,0,1);
        editGrid.add(companyNewNameLabel,1,1);

        id.setMaxWidth(50);
        idSelect.setMaxWidth(50);
        addButton.setAlignment(Pos.CENTER);

        editGrid.add(id,0,2);
        editGrid.add(companyNewName,1,2);
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
