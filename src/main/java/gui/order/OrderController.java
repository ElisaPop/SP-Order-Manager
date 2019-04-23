package gui.order;

import bll.validators.NumbersValidator;
import bll.OrderBLL;
import gui.products.ProductsController;
import gui.company.CompanyController;
import gui.customers.CustomersController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Orders;

import java.util.List;

/**
 * The controller classes manage the data that is displayed by the View. This data is taken from the BLL.
 */

public class OrderController {
    private Scene sceneOrders;
    private OrderBLL orderBL;
    private ObservableList tableList;
    private List<Orders> ordersList;

    public OrderController() {
        OrderView nView = new OrderView();
        this.setSceneOrders(new Scene(nView.interfaceUI()));
        this.orderBL = new OrderBLL();
        OrderView.deleteButton.setOnAction(e -> {
            orderBL.actionListenerDelete(OrderView.idSelect.getText());
            tableList.removeAll();
            ordersList = orderBL.getAll();
            tableList.setAll(ordersList);
            refreshTable();
        });
        OrderView.addButton.setOnAction(e -> {
            orderBL.actionListenerAdd(OrderView.id.getText(), OrderView.customerIdText.getText(), OrderView.productIdText.getText(), OrderView.productAmountText.getText());
            tableList.removeAll();
            ordersList = orderBL.getAll();
            tableList.setAll(ordersList);
            refreshTable();
        });
        OrderView.editButton.setOnAction(e -> actionListenerEdit());
        OrderView.clearButton.setOnAction(e -> actionListenerClear());
        OrderView.printButton.setOnAction(e -> orderBL.actionListenerPrint(OrderView.idSelect.getText()));

        OrderView.productsMenuLabel.setOnMouseClicked(event -> {
            ProductsController pCtrl = new ProductsController();
            Scene newScene = pCtrl.getSceneProducts();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        OrderView.companyMenuLabel.setOnMouseClicked(event -> {
            CompanyController pCtrl = new CompanyController();
            Scene newScene = pCtrl.getSceneCompany();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        OrderView.customersMenuLabel.setOnMouseClicked(event -> {
            CustomersController pCtrl = new CustomersController();
            Scene newScene = pCtrl.getSceneCustomers();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        ordersList = orderBL.getAll();
        this.tableList = FXCollections.observableList(ordersList);
        setTable(tableList);
    }

    /**
     * Method for setting the table to be displayed.
     */
    void setTable(ObservableList a) {
        OrderView.idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        OrderView.companyIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCustomerId()));
        OrderView.productIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProductId()));
        OrderView.productAmountCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProductAmount()));
        OrderView.table.setItems(a);
    }

    /**
     * Method for loading the fields with data of the item for which the ID was given.
     */
    private void actionListenerEdit() {
        String id = OrderView.idSelect.getText();

        if (id.isEmpty() || !NumbersValidator.validate(id))
            utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
        else {
            if (orderBL.existsId(id)) {
                Orders newCustomer = orderBL.getSelectedId(id);
                OrderView.id.setText(Integer.toString(newCustomer.getId()));
                OrderView.customerIdText.setText(Integer.toString(newCustomer.getCustomerId()));
                OrderView.productIdText.setText(Integer.toString(newCustomer.getProductId()));
                OrderView.productAmountText.setText(Integer.toString(newCustomer.getProductAmount()));
            }
        }
    }

    /**
     * Method for clearing all the fields.
     */
    private void actionListenerClear() {
        OrderView.id.clear();
        OrderView.customerIdText.clear();
        OrderView.productIdText.clear();
        OrderView.productAmountText.clear();
    }

    /**
     * Method used to get the scene for other packages.
     *
     * @return the scene tied to this controller.
     */
    public Scene getSceneOrders() {
        return this.sceneOrders;
    }

    /**
     * Method used to change between scenes.
     */
    private void setSceneOrders(Scene sceneOrders) {
        this.sceneOrders = sceneOrders;
    }

    /**
     * Method used to refresh the data from the table.
     */
    private void refreshTable() {
        OrderView.table.refresh();
    }
}

