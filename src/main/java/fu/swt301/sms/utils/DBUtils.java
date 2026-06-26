package fu.swt301.sms.utils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
    // Connection dành riêng cho môi trường test (H2)
    private static Connection testConnection = null;

    // Dùng trong Integration Test để set H2 connection
    public static void setTestConnection(Connection conn) {
        testConnection = conn;
    }
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // Nếu đang chạy test → dùng H2
        if (testConnection != null) {
            return testConnection;
        }
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=TestDB";
        return DriverManager.getConnection(url, "sa", "sa");
    }
    
    // Hàm tạo connection H2 từ file h2-test.properties
    public static Connection getH2Connection() throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream("src/test/resources/h2-test.properties"));

        Class.forName(props.getProperty("jdbc.driver"));

        return DriverManager.getConnection(
                props.getProperty("jdbc.url"),
                props.getProperty("jdbc.username"),
                props.getProperty("jdbc.password")
        );
    }
}