package gui.products;

import bll.validators.NumbersValidator;
import bll.ProductsBLL;
import gui.company.CompanyController;
import gui.customers.CustomersController;
import gui.order.OrderController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Products;

import java.util.List;

/**
 * The controller classes manage the data that is displayed by the View. This data is taken from the BLL.
 */

public class ProductsController {

    private Scene sceneProducts;
    private ObservableList tableList;
    private ProductsBLL newBLL;
    private List<Products> productsList;

    public ProductsController() {
        ProductsView nView = new ProductsView();
        this.setSceneProducts(new Scene(nView.interfaceUI()));
        newBLL = new ProductsBLL();

        ProductsView.deleteButton.setOnAction(e -> {
            newBLL.actionListenerDelete(ProductsView.idSelect.getText());
            tableList.removeAll();
            productsList = newBLL.getAll();
            tableList.setAll(productsList);
            refreshTable();
        });
        ProductsView.addButton.setOnAction(e -> {
            newBLL.actionListenerAdd(ProductsView.id.getText(), ProductsView.compidText.getText(), ProductsView.nameText.getText(), ProductsView.stockText.getText(), ProductsView.valueText.getText());
            tableList.removeAll();
            productsList = newBLL.getAll();
            tableList.setAll(productsList);
            refreshTable();
        });
        ProductsView.editButton.setOnAction(e -> actionListenerEdit());
        ProductsView.clearButton.setOnAction(e -> actionListenerClear());

        ProductsView.companyMenuLabel.setOnMouseClicked(event -> {

            refreshTable();
            CompanyController pCtrl = new CompanyController();
            Scene newScene = pCtrl.getSceneCompany();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        ProductsView.ordersMenuLabel.setOnMouseClicked(event -> {
            OrderController pCtrl = new OrderController();
            Scene newScene = pCtrl.getSceneOrders();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        ProductsView.customersMenuLabel.setOnMouseClicked(event -> {
            CustomersController pCtrl = new CustomersController();
            Scene newScene = pCtrl.getSceneCustomers();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        productsList = newBLL.getAll();
        this.tableList = FXCollections.observableList(productsList);
        setTable(tableList);
    }

    /**
     * Method for loading the fields with data of the item for which the ID was given.
     */
    private void actionListenerEdit() {
        if (ProductsView.idSelect.getText().isEmpty() || !NumbersValidator.validate(ProductsView.idSelect.getText())) {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty or incorrect.", "Ok");
        } else {
            String id = ProductsView.idSelect.getText();
            if (newBLL.existsId(id)) {
                Products newCustomer = newBLL.getSelectedId(id);
                ProductsView.id.setText(Integer.toString(newCustomer.getId()));
                ProductsView.compidText.setText(Integer.toString(newCustomer.getCompid()));
                ProductsView.nameText.setText(newCustomer.getName());
                ProductsView.stockText.setText(Integer.toString(newCustomer.getStock()));
                ProductsView.valueText.setText(Double.toString(newCustomer.getValue()));
            }
        }
    }

    /**
     * Method for clearing all the fields.
     */
    private void actionListenerClear() {
        ProductsView.id.clear();
        ProductsView.compidText.clear();
        ProductsView.nameText.clear();
        ProductsView.stockText.clear();
        ProductsView.valueText.clear();
    }

    /**
     * Method for setting the table to be displayed.
     */
    void setTable(ObservableList a) {
        ProductsView.idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        ProductsView.compidCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCompid()));
        ProductsView.nameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        ProductsView.stockCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStock()));
        ProductsView.valueCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));
        ProductsView.table.setItems(a);
    }

    /**
     * Method used to refresh the data from the table.
     */
    private void refreshTable() {
        ProductsView.table.refresh();
    }


    /**
     * Method used to get the scene for other packages.
     *
     * @return the scene tied to this controller.
     */
    public Scene getSceneProducts() {
        return this.sceneProducts;
    }


    /**
     * Method used to change between scenes.
     */
    private void setSceneProducts(Scene sceneProducts) {
        this.sceneProducts = sceneProducts;
    }
}
