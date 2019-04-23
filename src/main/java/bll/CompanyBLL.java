package bll;

import bll.validators.NumbersValidator;
import dao.CompanyDAO;
import model.Company;

import java.util.List;

/**
 * This is the business logic class for the Company model.
 * It is the gateway between database's data and the gui, including the
 * functionality of the class Company.
 */

public class CompanyBLL {

    CompanyDAO daoCust;

    public CompanyBLL() {
        daoCust = new CompanyDAO();
    }

    /**
     * Adds or updates the values given in the fields of the company page.
     * The id can only be added by using edit, and without clearing the fields, another
     * item can not be added. Therefore, If the id is present, it will result in a database update.
     * Otherwise, we execute in order to add a new object.
     *
     * @param id   the id of the company to be edited (or empty if the company has to be added)
     * @param name the name of the company to be edited or added.
     */
    public void actionListenerAdd(String id, String name) {
        if (name.isEmpty()) {
            utility.MessageBox.alert("ERROR", "One of the required fields is empty or incorrect", "Ok");
        } else {
            if (id.isEmpty()) {
                Company newCompany = new Company(name);
                daoCust.insert(newCompany);
            } else {
                int is = Integer.parseInt(id);
                Company newCompany = new Company(is, name);
                daoCust.update(newCompany);
            }
        }
    }

    /**
     * Given a certain id, the database will be searched for companies with the same id as
     * the given one. It returns the found data in an object.
     *
     * @param selectId the id of the item we want to take from the database
     * @return the Company object from the database with the given id
     */
    public Company getSelectedId(String selectId) {
        int id = Integer.parseInt(selectId);
        return daoCust.findById(id);
    }

    /**
     * Given a certain id, the database will be searched for companies with the same id as
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
     * @param selectId the id of the company to be deleted.
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
    public List<Company> getAll() {
        return daoCust.findAll();
    }

}
