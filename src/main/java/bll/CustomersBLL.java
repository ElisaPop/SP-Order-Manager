package bll;

import bll.validators.EmailValidator;
import bll.validators.NameValidator;
import bll.validators.NumbersValidator;
import dao.CustomersDAO;
import model.Customers;

import java.util.List;

/**
 * This is the business logic class for the Customer model.
 * It is the gateway between database's data and the gui, including the
 * functionality of the class Customer.
 */

public class CustomersBLL {

    private CustomersDAO daoCust = new CustomersDAO();

    public CustomersBLL() {
    }

    /**
     * Adds or updates the values given in the fields of the customer page.
     * The id can only be added by using edit, and without clearing the fields, another
     * item can not be added. Therefore, If the id is present, it will result in a database update.
     * Otherwise, we execute in order to add a new object.
     *
     * The given parameters are also checked if they are satisfying the requirements of
     * the specific data they represent.
     *
     * @param id      the id of the customer to be edited (or empty if the customer has to be added) asa a String.
     * @param name    the name of the customer to be edited or added.
     * @param surname the surname of the customer to be edited or added.
     * @param email   the email of the customer to be edited or added.
     * @param phone   the phone number of the customer to be edited or added asa a String.
     */
    public void actionListenerAdd(String id, String name, String surname, String email, String phone) {
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || !NameValidator.validate(name) || !NameValidator.validate(surname) || !NumbersValidator.validate(phone) || !EmailValidator.validate(email)) {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty or incorrect", "Ok");
        } else {

            if (id.isEmpty()) {
                Customers newCustomers = new Customers(name, surname, email, phone);
                daoCust.insert(newCustomers);
            } else {
                int is = Integer.parseInt(id);
                Customers newCustomers = new Customers(is, name, surname, email, phone);
                daoCust.update(newCustomers);
            }
        }
    }

    /**
     * Given a certain id, the database will be searched for customers with the same id as
     * the given one. It returns the found data in an object.
     *
     * @param selectId the id of the item we want to take from the database
     * @return the Customer object from the database with the given id
     */
    public Customers getSelectedId(String selectId) {
        int id = Integer.parseInt(selectId);
        return daoCust.findById(id);
    }

    /**
     * Given a certain id, the database will be searched for customers with the same id as
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
    public List<Customers> getAll() {
        return daoCust.findAll();
    }

}
