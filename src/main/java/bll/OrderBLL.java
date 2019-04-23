package bll;

import bll.validators.NumbersValidator;
import dao.CompanyDAO;
import dao.CustomersDAO;
import dao.OrdersDAO;
import dao.ProductsDAO;
import model.Company;
import model.Customers;
import model.Orders;
import model.Products;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * This is the business logic class for the Orders model.
 * It is the gateway between database's data and the gui, including the
 * functionality of the class Orders.
 */

public class OrderBLL {


    private OrdersDAO daoCust = new OrdersDAO();

    public OrderBLL() {
    }

    /**
     * Creates a txt file with the details of the order determined by an order id. It connects with the
     * Products and Customers model in order to get the necessary data for the receipt.
     *
     * @param selectId the id of the order that will have a txt file with in-depth details about the order.
     */
    public void actionListenerPrint(String selectId) {

        if (selectId.isEmpty() || !NumbersValidator.validate(selectId))
            utility.MessageBox.alert("ERROR", "One of the required fields is empty or incorrect", "Ok");
        else {
            int id = Integer.parseInt(selectId);
            if (daoCust.exists(id)) {
                Orders printOrder = daoCust.findById(id);
                CustomersDAO daoCust1 = new CustomersDAO();
                Customers printCustomer = daoCust1.findById(printOrder.getCustomerId());
                ProductsDAO daoCust2 = new ProductsDAO();
                Products printProduct = daoCust2.findById(printOrder.getProductId());
                CompanyDAO daoCust3 = new CompanyDAO();
                Company companyPrint = daoCust3.findById(printProduct.getCompid());

                List<String> lines = Arrays.asList("           ORDER #" + printOrder.getId() + "                 ",
                        "#######################",
                        "CUSTOMER ID: " + printOrder.getCustomerId() + "    ",
                        "CUSTOMER NAME: " + printCustomer.getName() + " " + printCustomer.getSurname() + " ",
                        "PHONE: " + printCustomer.getPhone(),
                        "EMAIL: " + printCustomer.getEmail(),
                        "#######################",
                        "PRODUCT ID: " + printProduct.getId(),
                        "PRODUCT NAME: " + printProduct.getName(),
                        "COMPANY: " + companyPrint.getName(),
                        "AMOUNT: " + printOrder.getProductAmount(),
                        "PRICE: " + printProduct.getValue(),
                        "#######################",
                        "TOTAL: " + printProduct.getValue() * printOrder.getProductAmount()
                );
                Path file = Paths.get("Order_#" + printOrder.getId() + ".txt");

                try {
                    Files.write(file, lines, Charset.forName("UTF-8"));
                    utility.MessageBox.alert("SUCCESS", "File created", "Ok");
                } catch (IOException e) {
                    e.printStackTrace();
                    utility.MessageBox.alert("ERROR", "File failed to be created", "Ok");
                }
            }

        }
    }

    /**
     * Adds or updates the values given in the fields of the customer page.
     * The id can only be added by using edit, and without clearing the fields, another
     * item can not be added. Therefore, If the id is present, it will result in a database update.
     * Otherwise, we execute in order to add a new object.
     * <p>
     * Since projectAmount relies on how many of the given product we have in stock, we have to always
     * check for an understock: Both when adding and editing. Therefore, adding an item will decrease the stock,
     * and editing will either increase or decrease the stock based on whether or now the new amount is smaller or
     * greater than the current one.
     *
     * @param id            the id of the order to be edited (or empty if the customer has to be added) asa a String.
     * @param customerId    the customer id string to be added or edited.
     * @param productId     the product id string to be added or edited.
     * @param productAmount the amount of products the customer will buy.
     */
    public void actionListenerAdd(String id, String customerId, String productId, String productAmount) {
        if (customerId.isEmpty() || productId.isEmpty() || productAmount.isEmpty() || !NumbersValidator.validate(customerId) || !NumbersValidator.validate(productId) || !NumbersValidator.validate(productAmount)) {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty or incorrect", "Ok");
        } else {
            int iCustomerId = Integer.parseInt(customerId);
            int iProductId = Integer.parseInt(productId);
            int iProductAmount = Integer.parseInt(productAmount);

            CustomersDAO daoCust1 = new CustomersDAO();
            if (!daoCust1.exists(iCustomerId)) {
                return;
            }
            ProductsDAO daoCust2 = new ProductsDAO();
            if (!daoCust2.exists(iProductId)) {
                return;
            }
            Products prod = daoCust2.findById(iProductId);

            if (id.isEmpty()) {
                Products checkStock = daoCust2.findById(iProductId);
                if (checkStock.getStock() < iProductAmount) {
                    utility.MessageBox.alert("ERROR", "Understock", "Ok");
                    return;
                }

                int currentStock = prod.getStock();
                prod.setStock(currentStock - iProductAmount);
                daoCust2.update(prod);

                Orders newOrder = new Orders(iCustomerId, iProductId, iProductAmount);
                daoCust.insert(newOrder);

            } else {
                int iId = Integer.parseInt(id);
                Orders oldStockOrder = daoCust.findById(iId);
                int oldStock = oldStockOrder.getProductAmount();

                Products checkStock = daoCust2.findById(iProductId);
                if (checkStock.getStock() - (iProductAmount - oldStock) < 0) {
                    utility.MessageBox.alert("ERROR", "Understock. Not enough items of the requested product found.", "Ok");
                    return;
                }

                int currentStock = prod.getStock();
                prod.setStock(currentStock - (iProductAmount - oldStock));
                daoCust2.update(prod);

                Orders newCustomer = new Orders(iId, iCustomerId, iProductId, iProductAmount);
                daoCust.update(newCustomer);

            }
        }
    }

    /**
     * Given a certain id, the database will be searched for orders with the same id as
     * the given one. It returns the found data in an object.
     *
     * @param selectId the id of the item we want to take from the database
     * @return the Orders object from the database with the given id
     */
    public Orders getSelectedId(String selectId) {
        int id = Integer.parseInt(selectId);
        return daoCust.findById(id);
    }

    /**
     * Given a certain id, the database will be searched for orders with the same id as
     * the given one. It returns a boolean indicating if the id is present in the database.
     *
     * @param selectId the id of the item we want to search in the database
     * @return a boolean indicating that the item with the given id was found.
     */
    public Boolean existsId(String selectId) {
        int id = Integer.parseInt(selectId);
        return daoCust.exists(id);
    }

    /**
     * Deletes an entry from the database using a certain id, given as parameter.
     *
     * @param selectId the id of the order to be deleted.
     */
    public void actionListenerDelete(String selectId) {
        if (selectId.isEmpty() || !NumbersValidator.validate(selectId))
            utility.MessageBox.alert("ERROR", "One of the required fields is empty", "Ok");
        else {
            int id = Integer.parseInt(selectId);
            if (daoCust.exists(id)) {
                daoCust.deleteById(id);
            }
        }
    }

    /**
     * Searches for all the entries in a certain database and returns them as a list of the specific objects.
     *
     * @return a list of all the entries in the current table.
     */
    public List<Orders> getAll() {
        return daoCust.findAll();
    }
}