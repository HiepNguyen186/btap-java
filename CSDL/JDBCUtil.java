package CSDL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {

    // Hàm lấy kết nối đến MySQL
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/order_management";
        String userName = "root";
        String password = "";

        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Connect successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Log error
        }

        return con;
    }

    // Đóng kết nối
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Đóng PreparedStatement
    public static void closeStatement(PreparedStatement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Đóng ResultSet
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức sử dụng try-with-resources để đảm bảo tài nguyên được đóng tự động
    public static void executeQuery(String query) {
        try (Connection con = getConnection(); 
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            // Xử lý dữ liệu trả về
            while (rs.next()) {
                System.out.println(rs.getString(1)); // Giả sử lấy dữ liệu ở cột 1
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log error
        }
    }
}
