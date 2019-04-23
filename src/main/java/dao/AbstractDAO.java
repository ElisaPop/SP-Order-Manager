package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.jdbc;

public class AbstractDAO<T> {

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates a sql query that is a prepared statement for selecting a certain item based on the given field.
     *
     * @param field the fiend from the database from which we want to select the item for
     * @return a string with the select query
     */
    private String createSelectQuery(String field) {
        String sb = "SELECT " +
                " * " +
                " FROM " +
                type.getSimpleName() +
                " WHERE " + field + " =?";
        return sb;
    }

    /**
     * Creates a sql query that is a prepared statement for deleting a certain item based on the given field.
     *
     * @param field the fiend from the database from which we want to delete the item
     * @return a string with the delete query
     */
    private String createDeleteQuery(String field) {
        String sb = "DELETE " +
                " FROM " +
                type.getSimpleName() +
                " WHERE " + field + " =?";
        return sb;
    }

    /**
     * Creates a sql query that is a prepared statement for inserting the given object into the database.
     *
     * @param t the object that will be added to the database
     * @return a string with the insert query
     */
    private String createInsertQuery(Object t) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT ");
        sb.append(" INTO ");
        sb.append(type.getSimpleName());
        sb.append(" ( ");
        Field[] field = t.getClass().getDeclaredFields();
        sb.append(field[1].getName());
        for(int i = 2 ; i < field.length; i ++)
        {
            field[i].setAccessible(true);
            sb.append(" , ");
            sb.append(field[i].getName());
        }
        sb.append(" ) VALUES ( " );
        sb.append(" ? ");
        for(int i = 2 ; i < field.length; i++)
        {
            sb.append(" , ? ");
        }
        sb.append(" ) ");
        return sb.toString();
    }

    /**
     * Creates a sql query that is a prepared statement for editing the given object into the database.
     *
     * @param t the object that will be eddited in the database.
     * @return a string with the edit query
     */
    private String createEditQuery(Object t) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        Field[] field = t.getClass().getDeclaredFields();
        sb.append(field[1].getName());
        sb.append(" = ? ");
        for(int i = 2 ; i < field.length; i ++)
        {
            field[i].setAccessible(true);
            sb.append(" , ");
            sb.append(field[i].getName());
            sb.append(" = ? ");
        }
        sb.append(" WHERE id = ?" );
        return sb.toString();
    }

    /**
     * A method that gets all the entries from a table in the database
     * @return the list of objects
     */
    public List<T> findAll() {
        try {
            Connection myConn = jdbc.getConnection();
            ResultSet result;
            String query = new String("SELECT * FROM " + type.getSimpleName());
            PreparedStatement statement = myConn.prepareStatement(query);
            result = statement.executeQuery();
            return createObjects(result);


        } catch (Exception var4) {
            var4.printStackTrace();

        }
        return null;
    }

    /**
     * A method that checks if the given id exists in the current database.
     * @param id the id that will be checked for existance
     * @return a boolean value, indicating if the id exists or not.
     */
    public boolean exists(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = jdbc.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if(!resultSet.next())
            {
                utility.MessageBox.alert("ERROR", "The given ID given doesn't exist", "Ok");
                return false;
            }
            else
            {
                resultSet.beforeFirst();
                return true;
            }


        } catch (SQLException e) {
            System.out.println( type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            jdbc.close(resultSet);
            jdbc.close(statement);
            jdbc.close(connection);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * A method that returns an object with the fields from the given id (from the database).
     * @param id the id of the item whose field information will be extracted from the database.
     * @return the created object
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = jdbc.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if(!resultSet.next())
            {
                utility.MessageBox.alert("ERROR", "The given ID given doesn't exist", "Ok");
                return null;
            }
            else
            {
                resultSet.beforeFirst();
                return createObjects(resultSet).get(0);
            }


        } catch (SQLException e) {
            System.out.println( type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            jdbc.close(resultSet);
            jdbc.close(statement);
            jdbc.close(connection);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * A method that deletes the field with the given id and returns the list of items after the query executed.
     * @param id the id of the item we want to delete from the database.
     * @return the list of items from the database after the query executed
     */
    public List<T> deleteById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet;
        String query = createDeleteQuery("id");
        try {
            connection = jdbc.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeUpdate();

            if(resultSet == 0)
                utility.MessageBox.alert("ERROR", "The given ID given doesn't exist", "Ok");

            return findAll();

        } catch (SQLException e) {
            System.out.println( type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            jdbc.close(statement);
            jdbc.close(connection);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * A method that will create a list of the current class from a ResultSet.
     * @param resultSet the ResultSet we obtain after executing a query.
     * @return a list of the current class containing objects with fields extracted from the database.
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * A method that will create a list of the current class from a ResultSet.
     * @param t the object that contains the fields to be added to the database.
     * @return a list of the current class containing objects with fields extracted from the database.
     */
    public List<T> insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet;
        String query = createInsertQuery(t);
        try {
            connection = jdbc.getConnection();
            statement = connection.prepareStatement(query);
            int cnt = 0;
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(!field.getName().equals("id"))
                {
                    try {
                        if(field.getType().equals(Integer.TYPE))
                            statement.setInt(++cnt, field.getInt(t));
                        if(field.getType().equals(String.class))
                            statement.setString(++cnt, (String) field.get(t));
                        if(field.getType().equals(Double.TYPE))
                            statement.setDouble(++cnt, field.getDouble(t));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
            resultSet = statement.executeUpdate();
            if(resultSet == 0) System.out.println("Failed to add");
        } catch (SQLException e) {
            System.out.println( type.getName() + "DAO:Insert " + e.getMessage());
            utility.MessageBox.alert("ERROR", "One of the IDs does not exist.", "Ok");
        } finally {
            jdbc.close(statement);
            jdbc.close(connection);
        }
        return findAll();
    }

    /**
     * A method that will edit in the database an entry given as a parameter.
     * @param t the object that contains the fields to be edited from the database.
     * @return a list of the current class containing objects with fields extracted from the database.
     */
    public List<T> update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet;
        String query = createEditQuery(t);
        try {
            connection = jdbc.getConnection();
            statement = connection.prepareStatement(query);
            int cnt = 0;
            int id = 0;
            for (Field field : t.getClass().getDeclaredFields()) {

                field.setAccessible(true);
                if(!field.getName().equals("id"))
                {
                    try {
                        if(field.getType().equals(Integer.TYPE))
                            statement.setInt(++cnt, field.getInt(t));
                        if(field.getType().equals(String.class))
                            statement.setString(++cnt, (String) field.get(t));
                        if(field.getType().equals(Double.TYPE))
                            statement.setDouble(++cnt, field.getDouble(t));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else id = field.getInt(t);

            }
            statement.setInt(++cnt, id);
            resultSet = statement.executeUpdate();
            if(resultSet == 0) System.out.println("Failed to add");
        } catch (SQLException e) {
            System.out.println( type.getName() + "DAO:Insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            jdbc.close(statement);
            jdbc.close(connection);
        }
        return findAll();
    }
}
