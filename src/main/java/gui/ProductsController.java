package gui;

import dao.ProductsDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Products;

import java.util.List;

public class ProductsController {

    private Scene sceneProducts;
    ObservableList tableList;
    ProductsDAO daoCust = new ProductsDAO();
    List<Products> productsList;

    public ProductsController()
    {
        ProductsView nView = new ProductsView();
        this.setSceneProducts(new Scene(nView.interfaceUI()));
        ProductsView.deleteButton.setOnAction(e -> actionListenerDelete());
        ProductsView.addButton.setOnAction(e -> actionListenerAdd());
        ProductsView.editButton.setOnAction(e -> actionListenerEdit());
        ProductsView.clearButton.setOnAction(e -> actionListenerClear());

        ProductsView.companyMenuLabel.setOnMouseClicked(event -> {

            refreshTable();
            CompanyController pCtrl = new CompanyController();
            Scene newScene =pCtrl.getSceneCompany();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        ProductsView.ordersMenuLabel.setOnMouseClicked(event -> {
            OrderController pCtrl = new OrderController();
            Scene newScene =pCtrl.getSceneOrders();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        ProductsView.customersMenuLabel.setOnMouseClicked(event -> {
            CustomersController pCtrl = new CustomersController();
            Scene newScene =pCtrl.getSceneCustomers();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        productsList = daoCust.findAll();
        this.tableList = FXCollections.observableList(productsList);
        setTable(tableList);
    }

    void actionListenerEdit()
    {
        int id = Integer.parseInt(ProductsView.idSelect.getText());
        Products newCustomer = daoCust.findById(id);
        ProductsView.id.setText(Integer.toString(newCustomer.getId()));
        ProductsView.compidText.setText(Integer.toString(newCustomer.getCompid()));
        ProductsView.nameText.setText(newCustomer.getName());
        ProductsView.stockText.setText(Integer.toString(newCustomer.getStock()));
        ProductsView.valueText.setText(Double.toString(newCustomer.getValue()));
    }
    void actionListenerClear()
    {
        ProductsView.id.clear();
        ProductsView.compidText.clear();
        ProductsView.nameText.clear();
        ProductsView.stockText.clear();
        ProductsView.valueText.clear();
    }
    void actionListenerAdd()
    {
        if(ProductsView.id.getText().equals(""))
        {
            int compid = Integer.parseInt(ProductsView.compidText.getText());
            String name = ProductsView.nameText.getText();
            int stock = Integer.parseInt(ProductsView.stockText.getText());
            double value = Double.parseDouble(ProductsView.valueText.getText());

            Products newCustomer = new Products(compid,name,stock,value);
            tableList.removeAll();
            productsList =daoCust.insert(newCustomer);
            tableList.setAll(productsList);
            refreshTable();
        }
        else
        {
            int iId = Integer.parseInt(ProductsView.id.getText());
            int compid = Integer.parseInt(ProductsView.compidText.getText());
            String name = ProductsView.nameText.getText();
            int stock = Integer.parseInt(ProductsView.stockText.getText());
            double value = Double.parseDouble(ProductsView.valueText.getText());

            Products newCustomer = new Products(iId, compid,name,stock,value);
            tableList.removeAll();
            productsList = daoCust.update(newCustomer);
            tableList.setAll(productsList);
            refreshTable();
        }

    }
    void actionListenerDelete()
    {
        int id = Integer.parseInt(ProductsView.idSelect.getText());

        tableList.removeAll();
        productsList = daoCust.deleteById(id);
        tableList.setAll(productsList);
        refreshTable();
    }

    void setTable(ObservableList a)
    {
        ProductsView.idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        ProductsView.compidCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCompid()));
        ProductsView.nameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        ProductsView.stockCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStock()));
        ProductsView.valueCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));
        ProductsView.table.setItems(a);
    }

    void refreshTable()
    {
        ProductsView.table.refresh();
    }

    public Scene getSceneProducts() {
        return this.sceneProducts;
    }

    public void setSceneProducts(Scene sceneProducts) {
        this.sceneProducts = sceneProducts;
    }
}
