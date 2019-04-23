package gui.customers;

import bll.CustomersBLL;
import bll.validators.NumbersValidator;
import gui.order.OrderController;
import gui.products.ProductsController;
import gui.company.CompanyController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Customers;

import java.util.List;

/**
 * The controller classes manage the data that is displayed by the View. This data is taken from the BLL.
 */

public class CustomersController {

    private Scene sceneCustomers;
    private ObservableList tableList;
    private CustomersBLL newBLL;
    private List<Customers> customersList;

    public CustomersController()
    {
        CustomersView nView = new CustomersView();
        this.setSceneCustomers(new Scene(nView.interfaceUI()));
        newBLL = new CustomersBLL();
        CustomersView.deleteButton.setOnAction(e -> {
            newBLL.actionListenerDelete(CustomersView.idSelect.getText());
            tableList.removeAll();
            customersList = newBLL.getAll();
            tableList.setAll(customersList);
            refreshTable();
        });
        CustomersView.addButton.setOnAction(e -> {
            newBLL.actionListenerAdd(CustomersView.id.getText(),CustomersView.customerName.getText(), CustomersView.customerSurname.getText(), CustomersView.customerEmail.getText(),CustomersView.customerPhone.getText());
            tableList.removeAll();
            customersList = newBLL.getAll();
            tableList.setAll(customersList);
            refreshTable();
        });
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

        customersList = newBLL.getAll();
        this.tableList = FXCollections.observableList(customersList);
        setTable(tableList);
    }

    /**
     * Method for loading the fields with data of the item for which the ID was given.
     */
    private void actionListenerEdit()
    {
        if(CustomersView.idSelect.getText().isEmpty() || !NumbersValidator.validate(CustomersView.idSelect.getText()))
        {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
        }
        else {
        String id =CustomersView.idSelect.getText();
        if(newBLL.existsId(id))
        {
            Customers newCustomer = newBLL.getSelectedId(id);
            CustomersView.id.setText(Integer.toString(newCustomer.getId()));
            CustomersView.customerName.setText(newCustomer.getName());
            CustomersView.customerSurname.setText(newCustomer.getSurname());
            CustomersView.customerPhone.setText(newCustomer.getPhone());
            CustomersView.customerEmail.setText(newCustomer.getEmail());}
        }
    }

    /**
     * Method for clearing all the fields.
     */
    private void actionListenerClear()
    {
        CustomersView.id.clear();
        CustomersView.customerName.clear();
        CustomersView.customerSurname.clear();
        CustomersView.customerPhone.clear();
        CustomersView.customerEmail.clear();
    }

    /**
     * Method for setting the table to be displayed.
     */
    void setTable(ObservableList a)
    {
        CustomersView.idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        CustomersView.firstNameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        CustomersView.lastNameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSurname()));
        CustomersView.emailCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));
        CustomersView.telephoneCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhone()));
        CustomersView.table.setItems(a);
    }

    /**
     * Method used to refresh the data from the table.
     */

    private void refreshTable()
    {
        CustomersView.table.refresh();
    }


    /**
     * Method used to get the scene for other packages.
     *
     * @return the scene tied to this controller.
     */
    public Scene getSceneCustomers() {
        return this.sceneCustomers;
    }

    /**
     * Method used to change between scenes.
     */
    private void setSceneCustomers(Scene sceneCustomers) {
        this.sceneCustomers = sceneCustomers;
    }
}
