package database;


import java.sql.*;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class jdbc {

    /**
     * Method for creating a connection to the database that has information stored in the Classified class.
     * @return the new connection if everything is ok, null otherwise.
     */
    private static Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(Classified.connection, Classified.user, Classified.password);

        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }

    }

    /**
     * Method for getting a connection to the database that has information stored in the Classified class.
     * @return the connection if everything is ok, null otherwise.
     */
    public static Connection getConnection() {
        return createConnection();
    }

    /**
     * Method for closing the connection given as parameter
     * @param connection the connection that will be closed
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }

    /**
     * Method for closing the statement given as parameter
     * @param statement the statement that will be closed
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }

    /**
     * Method for closing the resultSet given as parameter
     * @param resultSet the resultSet that will be closed
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }


}
