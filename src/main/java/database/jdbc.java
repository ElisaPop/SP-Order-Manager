package database;


import java.sql.*;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class jdbc {

    private static jdbc singleInstance = new jdbc();

    static public Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection myConn = DriverManager.getConnection(Classified.connection, Classified.user, Classified.password);
            return myConn;

        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }

    }
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    public static void close(Connection connection) {
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
//            }
//        }
    }

    public static void close(Statement statement) {
//        if (statement != null) {
//            try {
//                statement.close();
//            } catch (SQLException e) {
//                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
//            }
//        }
    }

    public static void close(ResultSet resultSet) {
//        if (resultSet != null) {
//            try {
//                resultSet.close();
//            } catch (SQLException e) {
//                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
//            }
//        }
    }

    static public Boolean checkLogin(String username, String password) {
        try {

            Connection myConn = DriverManager.getConnection(Classified.connection, Classified.user, Classified.password);

            PreparedStatement myStmt = myConn.prepareStatement("SELECT account_id FROM `pspdb`.`users` WHERE username=? and password=?");
            myStmt.setString(1, username);
            myStmt.setString(2, password);

            ResultSet result = myStmt.executeQuery();

            if (!result.next()) {
                //there are no rows.
                System.out.println("Incorrect username or password");
                return false;
            } else {
                do {
                    System.out.println("Login successful!");
                   // userID = result.getInt(1);
                    return true;
                } while (result.next());
            }

        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }

    }

    static public void createUser(String username, String name, String surname, String email, String password) {
        try {

            Connection myConn = DriverManager.getConnection(Classified.connection, Classified.user, Classified.password);
            PreparedStatement myStmt = myConn.prepareStatement("INSERT INTO pspdb.users ( username, name, surname, email, password, status) VALUES ( ?, ?, ?, ?, ?, 'default');");
            myStmt.setString(1, username);
            myStmt.setString(2, name);
            myStmt.setString(3, surname);
            myStmt.setString(4, email);
            myStmt.setString(5, password);
            myStmt.executeUpdate();
            //myStmt.execute();
        } catch (Exception var4) {
            var4.printStackTrace();

        }
    }

    static public boolean checkEmail(String email) {
        try {

            Connection myConn = DriverManager.getConnection(Classified.connection, Classified.user, Classified.password);//ResultSet result = myStmt.executeQuery("SELECT account_id FROM `pspdb`.`users` WHERE username=? and password=?");
            PreparedStatement myStmt = myConn.prepareStatement("SELECT email FROM `pspdb`.`users` WHERE email=?");
            myStmt.setString(1, email);

            //Statement myStmt = myConn.createStatement();

            ResultSet result = myStmt.executeQuery();

            if (!result.next()) {
                //there are no rows.

                return false;
            } else {
                do {

                    return true;
                } while (result.next());
            }

        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    static public boolean checkUsername(String username) {
        try {

            Connection myConn = DriverManager.getConnection(Classified.connection, Classified.user, Classified.password);//ResultSet result = myStmt.executeQuery("SELECT account_id FROM `pspdb`.`users` WHERE username=? and password=?");
            PreparedStatement myStmt = myConn.prepareStatement("SELECT username FROM `pspdb`.`users` WHERE username=?");
            myStmt.setString(1, username);

            ResultSet result = myStmt.executeQuery();

            if (!result.next()) {
                //there are no rows.

                return false;
            } else {
                do {

                    return true;
                } while (result.next());
            }

        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }
}
