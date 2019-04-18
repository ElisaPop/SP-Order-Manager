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
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }


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

            return createObjects(resultSet).get(0);
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
                System.out.println("Failed to delete " + id);

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

    List<T> createObjects(ResultSet resultSet) {
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<T> insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        int resultSet = 0;
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
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
            resultSet = statement.executeUpdate();
            if(resultSet == 0) System.out.println("Failed to add");
        } catch (SQLException e) {
            System.out.println( type.getName() + "DAO:Insert " + e.getMessage());
        } finally {
            jdbc.close(statement);
            jdbc.close(connection);
        }
        return findAll();
    }

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
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
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
