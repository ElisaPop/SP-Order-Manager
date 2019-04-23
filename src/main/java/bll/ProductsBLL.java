package bll;

import bll.validators.DoubleValidator;
import bll.validators.NumbersValidator;
import dao.ProductsDAO;
import model.Products;

import java.util.List;

/**
 * This is the business logic class for the Products model.
 * It is the gateway between database's data and the gui, including the
 * functionality of the class Products.
 */

public class ProductsBLL {

    ProductsDAO daoCust;

    public ProductsBLL() {
        daoCust = new ProductsDAO();
    }

    /**
     * Adds or updates the values given in the fields of the products page.
     * The id can only be added by using edit, and without clearing the fields, another
     * item can not be added. Therefore, If the id is present, it will result in a database update.
     * Otherwise, we execute in order to add a new object.
     * <p>
     * The given parameters are also checked if they are satisfying the requirements of
     * the specific data they represent.
     *
     * @param id        the id of the product to be edited (or empty if the product has to be added) asa a String.
     * @param companyId the id as a String of the company the item is produced by.
     * @param name      the name of the product to be edited or added.
     * @param stock     the amount of available items from this category.
     * @param value     the price each unit of the determined product has.
     */
    public void actionListenerAdd(String id, String companyId, String name, String stock, String value) {
        if (name.isEmpty() || companyId.isEmpty() || stock.isEmpty() || value.isEmpty() || !NumbersValidator.validate(companyId) || !NumbersValidator.validate(stock) || !DoubleValidator.validate(value)) {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty or incorrect", "Ok");
        } else {

            int compid = Integer.parseInt(companyId);
            int stk = Integer.parseInt(stock);
            double val = Double.parseDouble(value);

            if (id.isEmpty()) {
                Products newProducts = new Products(compid, name, stk, val);
                daoCust.insert(newProducts);
            } else {
                int is = Integer.parseInt(id);
                Products newProducts = new Products(is, compid, name, stk, val);
                daoCust.update(newProducts);
            }
        }
    }

    /**
     * Given a certain id, the database will be searched for products with the same id as
     * the given one. It returns the found data in an object.
     *
     * @param selectId the id of the item we want to take from the database
     * @return the Products object from the database with the given id
     */
    public Products getSelectedId(String selectId) {
        int id = Integer.parseInt(selectId);
        return daoCust.findById(id);
    }

    /**
     * Given a certain id, the database will be searched for products with the same id as
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
     * @param selectId the id of the customer to be deleted.
     */
    public void actionListenerDelete(String selectId) {
        if (selectId.isEmpty() || !NumbersValidator.validate(selectId))
            utility.MessageBox.alert("ERROR", "One of the required fields is empty or incorrect.", "Ok");
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
    public List<Products> getAll() {
        return daoCust.findAll();
    }

}
