package gui.company;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Company;

public class CompanyView {

    public static TableView<Company> table = new TableView<>();
    private static GridPane newGrid = new GridPane();
    private static Label companyLabel = new Label("Company");
    private static Region gridRegionH = new Region();
    private static Region gridRegionW = new Region();
    private static Region hBoxRegion = new Region();
    private static Region wBoxRegion = new Region();

    static Label customersMenuLabel = new Label("Customers");
    static Label ordersMenuLabel =new Label("Orders");
    static Label productsMenuLabel =new Label("Products");
    private static Label companyMenuLabel = new Label("Company");
    private static Menu menu1 = new Menu("", customersMenuLabel);
    private static Menu menu2 = new Menu("", ordersMenuLabel);
    private static Menu menu3 = new Menu("", productsMenuLabel);
    private static Menu menu4 = new Menu("", companyMenuLabel);

    private static MenuBar menuBar = new MenuBar();

    private static GridPane editGrid = new GridPane();

    static TableColumn<Company,Integer> idCol = new TableColumn<>("ID");
    static TableColumn<Company,String> firstNameCol = new TableColumn<>("Name");

    private static Label idLabel = new Label("ID");
    private static Label companyNewNameLabel = new Label("Name");


    public static TextField id = new TextField();
    static TextField companyNewName = new TextField();

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
