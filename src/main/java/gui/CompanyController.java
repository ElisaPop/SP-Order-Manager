package gui;

import dao.CompanyDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Company;

import java.util.List;

public class CompanyController {

    private Scene sceneCompany;
    ObservableList tableList;
    CompanyDAO daoCust = new CompanyDAO();
    List<Company> companyNewList;

    public CompanyController()
    {
        CompanyView nView = new CompanyView();
        this.setSceneCompany(new Scene(nView.interfaceUI()));
        CompanyView.deleteButton.setOnAction(e -> actionListenerDelete());
        CompanyView.addButton.setOnAction(e -> actionListenerAdd());
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
        companyNewList = daoCust.findAll();
        this.tableList = FXCollections.observableList(companyNewList);
        setTable(tableList);
    }

    void actionListenerEdit()
    {
        if(CompanyView.idSelect.getText().isEmpty())
        {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
        }
        else {
            int id = Integer.parseInt(CompanyView.idSelect.getText());
            Company newCompany = daoCust.findById(id);
            CompanyView.id.setText(Integer.toString(newCompany.getId()));
            CompanyView.companyNewName.setText(newCompany.getName());
        }
        }
    void actionListenerClear()
    {
        CompanyView.id.clear();
        CompanyView.companyNewName.clear();
    }
    void actionListenerAdd()
    {
        if(CompanyView.id.getText().isEmpty())
        {
            if(CompanyView.companyNewName.getText().isEmpty())
                utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
            else{
                String name = CompanyView.companyNewName.getText();
                Company newCompany = new Company(name);
                tableList.removeAll();
                companyNewList =daoCust.insert(newCompany);
                tableList.setAll(companyNewList);
                refreshTable();
            }
        }
        else
        {
            int iId = Integer.parseInt(CompanyView.id.getText());
            String name = CompanyView.companyNewName.getText();

            if(name.isEmpty())
            {
                utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
            }
            else {
                Company newCompany = new Company(iId, name);
                tableList.removeAll();
                companyNewList = daoCust.update(newCompany);
                tableList.setAll(companyNewList);
                refreshTable();
            }
        }

    }
    void actionListenerDelete()
    {
        if(CompanyView.idSelect.getText().isEmpty())
        {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
        }
        else {
            int id = Integer.parseInt(CompanyView.idSelect.getText());

            tableList.removeAll();
            companyNewList = daoCust.deleteById(id);
            tableList.setAll(companyNewList);
            refreshTable();
        }
    }


    void setTable(ObservableList a)
    {
        CompanyView.idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        CompanyView.firstNameCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        CompanyView.table.setItems(a);
    }

    void refreshTable()
    {
        CompanyView.table.refresh();
    }

    public Scene getSceneCompany() {
        return this.sceneCompany;
    }

    public void setSceneCompany(Scene sceneCompany) {
        this.sceneCompany = sceneCompany;
    }
}
