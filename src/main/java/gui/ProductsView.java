package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Products;

public class ProductsView {

    static TableView<Products> table = new TableView<>();
    static GridPane newGrid = new GridPane();
    static Label productsLabel = new Label("Products");
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

    static TableColumn<Products,Integer> idCol = new TableColumn<>("ID");
    static TableColumn<Products,Integer> compidCol = new TableColumn<>("Company ID");
    static TableColumn<Products,String> nameCol = new TableColumn<>("Name");
    static TableColumn<Products,Integer> stockCol = new TableColumn<>("Stock");
    static TableColumn<Products,Double> valueCol = new TableColumn<>("Value" );

    static Label idLabel = new Label("ID");
    static Label compidLabel = new Label("Company ID");
    static Label nameLabel = new Label("Name");
    static Label stockLabel = new Label("Stock");
    static Label amountLabel = new Label("Value");


    static TextField id = new TextField();
    static TextField compidText = new TextField();
    static TextField nameText = new TextField();
    static TextField stockText = new TextField();
    static TextField valueText = new TextField();

    static Button addButton = new Button("Add");
    static Button editButton = new Button("Edit");
    static Button deleteButton = new Button("Delete");
    static Button clearButton = new Button("Clear");
    static TextField idSelect = new TextField();

    public BorderPane interfaceUI() {

        table.getItems().clear();
        table.getColumns().clear();

        menuBar.getMenus().clear();
        newGrid.getChildren().clear();
        editGrid.getChildren().clear();

        newGrid.setHgap(10);
        newGrid.setVgap(10);
        newGrid.setPadding(new Insets(10, 10, 10, 10));

        menuBar.getMenus().addAll(menu1,menu2,menu3,menu4);

        idCol.setPrefWidth(50);
        compidCol.setPrefWidth(150);
        nameCol.setPrefWidth(150);
        stockCol.setPrefWidth(150);
        valueCol.setPrefWidth(150);

        table.getColumns().addAll(idCol,compidCol, nameCol, stockCol,valueCol);
        table.setPrefWidth(650);
        table.setPrefHeight(400);

        gridRegionH.setPrefHeight(100);
        VBox.setVgrow(gridRegionH, Priority.ALWAYS);

        gridRegionW.setPrefWidth(40);
        VBox.setVgrow(gridRegionW, Priority.ALWAYS);

        productsLabel.setScaleX(4);
        productsLabel.setScaleY(4);
        productsLabel.setAlignment(Pos.CENTER);

        newGrid.add(gridRegionH, 0, 0);
        newGrid.add(productsLabel, 1, 1,2,1);
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
        compidLabel.setAlignment(Pos.CENTER);
        nameLabel.setAlignment(Pos.CENTER);
        stockLabel.setAlignment(Pos.CENTER);
        amountLabel.setAlignment(Pos.CENTER);

        editGrid.add(idSelect,5,0);
        editGrid.add(editButton,6,0);
        editGrid.add(deleteButton,7,0);

        editGrid.add(idLabel,0,1);
        editGrid.add(compidLabel,1,1);
        editGrid.add(nameLabel,2,1);
        editGrid.add(stockLabel,3,1);
        editGrid.add(amountLabel,4,1);

        id.setMaxWidth(50);
        idSelect.setMaxWidth(50);
        addButton.setAlignment(Pos.CENTER);

        editGrid.add(id,0,2);
        editGrid.add(compidText,1,2);
        editGrid.add(nameText,2,2);
        editGrid.add(stockText,3,2);
        editGrid.add(valueText,4,2);
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
