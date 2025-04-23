package CSDL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//OrderDAO - Quản lý đơn hàng
public class OrderDAO {
    private Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    public void createOrder(int customerId, List<Integer> productIds, List<Integer> quantities) throws SQLException {
        String orderSql = "INSERT INTO orders (customer_id, total_price) VALUES (?, 0)";
        String itemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        String updateTotalSql = "UPDATE orders SET total_price = ? WHERE id = ?";

        conn.setAutoCommit(false);
        try (PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
            orderStmt.setInt(1, customerId);
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                double totalPrice = 0;

                try (PreparedStatement itemStmt = conn.prepareStatement(itemSql)) {
                    for (int i = 0; i < productIds.size(); i++) {
                        int productId = productIds.get(i);
                        int quantity = quantities.get(i);
                        double price = getProductPrice(productId);
                        double totalItemPrice = price * quantity;

                        itemStmt.setInt(1, orderId);
                        itemStmt.setInt(2, productId);
                        itemStmt.setInt(3, quantity);
                        itemStmt.setDouble(4, totalItemPrice);
                        itemStmt.executeUpdate();
                        
                        totalPrice += totalItemPrice;
                    }
                }
                
                try (PreparedStatement updateStmt = conn.prepareStatement(updateTotalSql)) {
                    updateStmt.setDouble(1, totalPrice);
                    updateStmt.setInt(2, orderId);
                    updateStmt.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private double getProductPrice(int productId) throws SQLException {
        String sql = "SELECT price FROM products WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        }
        return 0;
    }

    public List<String> getOrderHistory(int customerId) throws SQLException {
        List<String> orders = new ArrayList<>();
        String sql = "SELECT id, order_date, total_price FROM orders WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add("Order ID: " + rs.getInt("id") + ", Date: " + rs.getTimestamp("order_date") + ", Total: " + rs.getDouble("total_price"));
            }
        }
        return orders;
    }

    public double getTotalOrderAmount(int orderId) throws SQLException {
        String sql = "SELECT total_price FROM orders WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_price");
            }
        }
        return 0;
    }
    public void addOrder(int customerId, List<Integer> productIds, List<Integer> quantities) throws SQLException {
        String orderSql = "INSERT INTO orders (customer_id, total_price) VALUES (?, 0)";
        String itemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        String updateTotalSql = "UPDATE orders SET total_price = ? WHERE id = ?";

        conn.setAutoCommit(false);
        try (PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
            orderStmt.setInt(1, customerId);
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                double totalPrice = 0;

                try (PreparedStatement itemStmt = conn.prepareStatement(itemSql)) {
                    for (int i = 0; i < productIds.size(); i++) {
                        int productId = productIds.get(i);
                        int quantity = quantities.get(i);
                        double price = getProductPrice(productId);
                        double totalItemPrice = price * quantity;

                        itemStmt.setInt(1, orderId);
                        itemStmt.setInt(2, productId);
                        itemStmt.setInt(3, quantity);
                        itemStmt.setDouble(4, totalItemPrice);
                        itemStmt.executeUpdate();
                        
                        totalPrice += totalItemPrice;
                    }
                }
                
                try (PreparedStatement updateStmt = conn.prepareStatement(updateTotalSql)) {
                    updateStmt.setDouble(1, totalPrice);
                    updateStmt.setInt(2, orderId);
                    updateStmt.executeUpdate();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

}

