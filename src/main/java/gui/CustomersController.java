package gui;

import dao.CustomersDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Customers;

import java.util.List;

public class CustomersController {

    private Scene sceneCustomers;
    ObservableList tableList;
    CustomersDAO daoCust = new CustomersDAO();
    List<Customers> customersList;

    public CustomersController()
    {
        CustomersView nView = new CustomersView();
        this.setSceneCustomers(new Scene(nView.interfaceUI()));
        CustomersView.deleteButton.setOnAction(e -> actionListenerDelete());
        CustomersView.addButton.setOnAction(e -> actionListenerAdd());
        CustomersView.editButton.setOnAction(e -> actionListenerEdit());
        CustomersView.clearButton.setOnAction(e -> actionListenerClear());

        CustomersView.productsMenuLabel.setOnMouseClicked(event -> {
            ProductsController pCtrl = new ProductsController();
            Scene newScene =pCtrl.getSceneProducts();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        CustomersView.ordersMenuLabel.setOnMouseClicked(event -> {
            OrderController pCtrl = new OrderController();
            Scene newScene =pCtrl.getSceneOrders();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        CustomersView.companyMenuLabel.setOnMouseClicked(event -> {
            CompanyController pCtrl = new CompanyController();
            Scene newScene = pCtrl.getSceneCompany();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });

        customersList = daoCust.findAll();
        this.tableList = FXCollections.observableList(customersList);
        setTable(tableList);
    }

    void actionListenerEdit()
    {
        if(CustomersView.idSelect.getText().isEmpty())
        {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
        }
        else {
        int id = Integer.parseInt(CustomersView.idSelect.getText());
        Customers newCustomer = daoCust.findById(id);
        CustomersView.id.setText(Integer.toString(newCustomer.getId()));
        CustomersView.customerName.setText(newCustomer.getName());
        CustomersView.customerSurname.setText(newCustomer.getSurname());
        CustomersView.customerPhone.setText(newCustomer.getPhone());
        CustomersView.customerEmail.setText(newCustomer.getEmail());}
    }
    void actionListenerClear()
    {
        CustomersView.id.clear();
        CustomersView.customerName.clear();
        CustomersView.customerSurname.clear();
        CustomersView.customerPhone.clear();
        CustomersView.customerEmail.clear();
    }
    void actionListenerAdd()
    {
        if(CustomersView.id.getText().isEmpty())
        {
            String name = CustomersView.customerName.getText();
            String surname = CustomersView.customerSurname.getText();
            String email = CustomersView.customerEmail.getText();
            String phone = CustomersView.customerPhone.getText();

            if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty())
            {
                utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
            }
            else {

            Customers newCustomer = new Customers(name,surname,email,phone);
            tableList.removeAll();
            customersList =daoCust.insert(newCustomer);
            tableList.setAll(customersList);
            refreshTable();}
        }
        else
        {
            int iId = Integer.parseInt(CustomersView.id.getText());
            String name = CustomersView.customerName.getText();
            String surname = CustomersView.customerSurname.getText();
            String email = CustomersView.customerEmail.getText();
            String phone = CustomersView.customerPhone.getText();

            if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty())
            {
                utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
            }
            else {

            Customers newCustomer = new Customers(iId, name,surname,email,phone);
            tableList.removeAll();
            customersList = daoCust.update(newCustomer);
            tableList.setAll(customersList);
            refreshTable();}
        }

    }
    void actionListenerDelete()
    {
        if(CustomersView.idSelect.getText().isEmpty())
        {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");

        }
        else
        {
        int id = Integer.parseInt(CustomersView.idSelect.getText());

        tableList.removeAll();
        customersList = daoCust.deleteById(id);
        tableList.setAll(customersList);
        refreshTable();
    }}

    void setTable(ObservableList a)
    {
        CustomersView.idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        CustomersView.firstNameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        CustomersView.lastNameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSurname()));
        CustomersView.emailCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));
        CustomersView.telephoneCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhone()));
        CustomersView.table.setItems(a);
    }

    void refreshTable()
    {
        CustomersView.table.refresh();
    }

    public Scene getSceneCustomers() {
        return this.sceneCustomers;
    }

    public void setSceneCustomers(Scene sceneCustomers) {
        this.sceneCustomers = sceneCustomers;
    }
}
