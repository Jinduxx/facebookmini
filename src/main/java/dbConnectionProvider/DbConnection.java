package dbConnectionProvider;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/week6?serverTimezone=UTC",
                    "root", "Thewipper.");

            if (connection != null) {
                System.out.println("connected successfully");
            } else {
                System.out.println("Connection Doomed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
