package gui.company;

import bll.CompanyBLL;
import bll.validators.NumbersValidator;
import gui.customers.CustomersController;
import gui.order.OrderController;
import gui.products.ProductsController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Company;

import java.util.List;

/**
 * The controller classes manage the data that is displayed by the View. This data is taken from the BLL.
 */

public class CompanyController {

    private Scene sceneCompany;
    private ObservableList tableList;
    private CompanyBLL companyBL;
    private List<Company> companyNewList;

    public CompanyController()
    {
        CompanyView nView = new CompanyView();
        this.setSceneCompany(new Scene(nView.interfaceUI()));
        this.companyBL = new CompanyBLL();
        CompanyView.deleteButton.setOnAction(e -> {
            companyBL.actionListenerDelete(CompanyView.idSelect.getText());
            tableList.removeAll();
            companyNewList = companyBL.getAll();
            tableList.setAll(companyNewList);
            refreshTable();
        });
        CompanyView.addButton.setOnAction(e ->{
            companyBL.actionListenerAdd(CompanyView.id.getText(),CompanyView.companyNewName.getText());
            tableList.removeAll();
            companyNewList = companyBL.getAll();
            tableList.setAll(companyNewList);
            refreshTable();
        });
        CompanyView.editButton.setOnAction(e -> actionListenerEdit());
        CompanyView.clearButton.setOnAction(e -> actionListenerClear());
        CompanyView.productsMenuLabel.setOnMouseClicked(event -> {
            refreshTable();
            ProductsController pCtrl = new ProductsController();
            Scene newScene =pCtrl.getSceneProducts();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        CompanyView.ordersMenuLabel.setOnMouseClicked(event -> {
            OrderController pCtrl = new OrderController();
            Scene newScene =pCtrl.getSceneOrders();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        CompanyView.customersMenuLabel.setOnMouseClicked(event -> {
            CustomersController pCtrl = new CustomersController();
            Scene newScene =pCtrl.getSceneCustomers();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newScene);
        });
        companyNewList = companyBL.getAll();
        this.tableList = FXCollections.observableList(companyNewList);
        setTable(tableList);
    }

    /**
     * Method for loading the fields with data of the item for which the ID was given.
     */
    private void actionListenerEdit()
    {
        if(CompanyView.idSelect.getText().isEmpty() || !NumbersValidator.validate(CompanyView.idSelect.getText()))
        {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty or incorrect", "Ok");
        }
        else {
            if(companyBL.existsId(CompanyView.idSelect.getText())) {
                String id = CompanyView.idSelect.getText();
                Company newCompany = companyBL.getSelectedId(id);
                CompanyView.id.setText(Integer.toString(newCompany.getId()));
                CompanyView.companyNewName.setText(newCompany.getName());
            }
        }
    }
    /**
     * Method for clearing all the fields.
     */
    private void actionListenerClear()
    {
        CompanyView.id.clear();
        CompanyView.companyNewName.clear();
    }
    /**
     * Method for setting the table to be displayed.
     */
    void setTable(ObservableList a)
    {
        CompanyView.idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        CompanyView.firstNameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        CompanyView.table.setItems(a);
    }

    /**
     * Method used to refresh the data from the table.
     */
    private void refreshTable()
    {
        CompanyView.table.refresh();
    }

    /**
     * Method used to get the scene for other packages.
     *
     * @return the scene tied to this controller.
     */
    public Scene getSceneCompany() {
        return this.sceneCompany;
    }

    /**
     * Method used to change between scenes.
     */
    private void setSceneCompany(Scene sceneCompany) {
        this.sceneCompany = sceneCompany;
    }
}
