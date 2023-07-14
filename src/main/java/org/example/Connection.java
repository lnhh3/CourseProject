package org.example;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    static Statement statement;

    static {
        statement = getStatement();
    }

    public static ResultSet executeQuery(String command) throws SQLException {
        return statement.executeQuery(command);
    }

    public static int executeNonQuery(String command) throws SQLException {
        return statement.executeUpdate(command);
    }

    private static Statement getStatement() {
        try {
            String connectionUrl = "jdbc:sqlserver://localhost\\SQLExpress:1433;databaseName=FastFoodManagement;" +
                    "user=sa;" +
                    "password=HwangEunbi1998;";
            java.sql.Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
