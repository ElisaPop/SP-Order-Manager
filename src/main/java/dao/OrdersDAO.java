package dao;

import database.jdbc;
import model.Orders;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrdersDAO extends AbstractDAO<Orders> {
    public OrdersDAO()
    {
        super();
    }

}
