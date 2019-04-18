package gui;

import dao.CustomersDAO;
import dao.OrdersDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Orders;

import java.util.List;

public class OrderController {
    private Scene sceneOrders;
    ObservableList tableList;
    OrdersDAO daoCust = new OrdersDAO();
    List<Orders> ordersList;

    public OrderController()
    {
        OrderView nView = new OrderView();
        this.setSceneOrders(new Scene(nView.interfaceUI()));
        OrderView.deleteButton.setOnAction(e -> actionListenerDelete());
        OrderView.addButton.setOnAction(e -> actionListenerAdd());
        OrderView.editButton.setOnAction(e -> actionListenerEdit());
        OrderView.clearButton.setOnAction(e -> actionListenerClear());

        OrderView.productsMenuLabel.setOnMouseClicked(event -> {
            ProductsController pCtrl = new ProductsController();
            Scene newScene =pCtrl.getSceneProducts();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        OrderView.companyMenuLabel.setOnMouseClicked(event -> {
            CompanyController pCtrl = new CompanyController();
            Scene newScene =pCtrl.getSceneCompany();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        OrderView.customersMenuLabel.setOnMouseClicked(event -> {
            CustomersController pCtrl = new CustomersController();
            Scene newScene =pCtrl.getSceneCustomers();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });

        ordersList = daoCust.findAll();
        this.tableList = FXCollections.observableList(ordersList);
        setTable(tableList);
    }

    void actionListenerAdd()
    {

        if(OrderView.customerIdText.getText().isEmpty() || OrderView.productIdText.getText().isEmpty() || OrderView.productAmountText.getText().isEmpty())
        {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
        }
        else{
        int iCustomerId = Integer.parseInt(OrderView.customerIdText.getText());
        int iProductId = Integer.parseInt(OrderView.productIdText.getText());
        int iProductAmount = Integer.parseInt(OrderView.productAmountText.getText());


        if(OrderView.id.getText().equals("")) {
            Orders newOrder = new Orders(iCustomerId, iProductId, iProductAmount);
            tableList.removeAll();
            ordersList = daoCust.insert(newOrder);
            tableList.setAll(ordersList);
            refreshTable();
        }
        else
        {
            int iId = Integer.parseInt(OrderView.id.getText());
            Orders newCustomer = new Orders(iId, iCustomerId, iProductId, iProductAmount);
            tableList.removeAll();
            ordersList = daoCust.update(newCustomer);
            tableList.setAll(ordersList);
            refreshTable();
        }}
    }

    void actionListenerEdit()
    {
        if(OrderView.idSelect.getText().isEmpty())
            utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
        else
        {
        int id = Integer.parseInt(OrderView.idSelect.getText());
        Orders newCustomer = daoCust.findById(id);
        OrderView.id.setText(Integer.toString(newCustomer.getId()));
        OrderView.customerIdText.setText(Integer.toString(newCustomer.getCustomerId()));
        OrderView.productIdText.setText(Integer.toString(newCustomer.getProductId()));
        OrderView.productAmountText.setText(Integer.toString(newCustomer.getProductAmount()));


        }
        }

    void actionListenerClear()
    {
        OrderView.id.clear();
        OrderView.customerIdText.clear();
        OrderView.productIdText.clear();
        OrderView.productAmountText.clear();
    }
    void actionListenerDelete()
    {
        if(OrderView.idSelect.getText().isEmpty())
            utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
        else
        {
        int id = Integer.parseInt(OrderView.idSelect.getText());

        tableList.removeAll();
        ordersList = daoCust.deleteById(id);
        tableList.setAll(ordersList);
        refreshTable();}
    }

    void setTable(ObservableList a)
    {
        OrderView.idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        OrderView.companyIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCustomerId()));
        OrderView.productIdCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProductId()));
        OrderView.productAmountCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProductAmount()));
        OrderView.table.setItems(a);
    }

    void refreshTable()
    {
        OrderView.table.refresh();
    }

    public Scene getSceneOrders() {
        return this.sceneOrders;
    }

    public void setSceneOrders(Scene sceneOrders) {
        this.sceneOrders = sceneOrders;
    }
}

