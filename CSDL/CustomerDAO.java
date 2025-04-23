package CSDL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//CustomerDAO - Quản lý khách hàng
public class CustomerDAO {
 private Connection conn;

 public CustomerDAO(Connection conn) {
     this.conn = conn;
 }

 public void addCustomer(String name, String email, String phone) throws SQLException {
     String sql = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";
     try (PreparedStatement stmt = conn.prepareStatement(sql)) {
         stmt.setString(1, name);
         stmt.setString(2, email);
         stmt.setString(3, phone);
         stmt.executeUpdate();
     }
 }

 public List<String> getAllCustomers() throws SQLException {
     List<String> customers = new ArrayList<>();
     String sql = "SELECT * FROM customers";
     try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
         while (rs.next()) {
             customers.add(rs.getInt("id") + " - " + rs.getString("name"));
         }
     }
     return customers;
 }
}
