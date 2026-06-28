package fu.swt301.sms.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String url =
                "jdbc:sqlserver://localhost\\SQLEXPRESS;"
                + "databaseName=TestDB;"
                + "encrypt=false;"
                + "trustServerCertificate=true;";

        return DriverManager.getConnection(url, "sa", "sa");
    }
}