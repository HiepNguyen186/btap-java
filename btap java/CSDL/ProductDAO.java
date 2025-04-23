package CSDL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//ProductDAO - Quản lý sản phẩm
public class ProductDAO {
 private Connection conn;

 public ProductDAO(Connection conn) {
     this.conn = conn;
 }

 public void addProduct(String name, double price, int stock) throws SQLException {
     String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";
     try (PreparedStatement stmt = conn.prepareStatement(sql)) {
         stmt.setString(1, name);
         stmt.setDouble(2, price);
         stmt.setInt(3, stock);
         stmt.executeUpdate();
     }
 }
}