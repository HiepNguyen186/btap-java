package CSDL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderManagementApp {
    private JFrame frame;
    private JTextField customerIdField, productIdField, productPriceField, quantityField;
    private JTextArea orderHistoryArea;
    private OrderDAO orderDAO;

    public OrderManagementApp() {
        
    	Connection conn = JDBCUtil.getConnection();  
    	if (conn != null) { 
    	    orderDAO = new OrderDAO(conn);  
    
    	} else {
    	    System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
    	}


        // Tạo giao diện Swing
        frame = new JFrame("Quản lý Đơn Hàng");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Nhập ID Khách Hàng:"));
        customerIdField = new JTextField();
        panel.add(customerIdField);

        panel.add(new JLabel("Nhập ID Sản Phẩm:"));
        productIdField = new JTextField();
        panel.add(productIdField);

        panel.add(new JLabel("Nhập Giá Sản Phẩm:"));
        productPriceField = new JTextField();
        panel.add(productPriceField);

        panel.add(new JLabel("Nhập Số Lượng:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        JButton addOrderButton = new JButton("Thêm Đơn Hàng");
        panel.add(addOrderButton);
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewOrder();
            }
        });

        JButton viewOrdersButton = new JButton("Xem Lịch Sử Đơn Hàng");
        panel.add(viewOrdersButton);
        viewOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrderHistory();
            }
        });

        orderHistoryArea = new JTextArea();
        orderHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderHistoryArea);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void addNewOrder() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());

            // Đọc thông tin các sản phẩm từ các field, giả sử bạn có một số sản phẩm cần thêm vào
            List<Integer> productIds = new ArrayList<>();
            List<Integer> quantities = new ArrayList<>();

            productIds.add(Integer.parseInt(productIdField.getText())); // Thêm ID sản phẩm
            quantities.add(Integer.parseInt(quantityField.getText()));  // Thêm số lượng

            // Thêm đơn hàng mới với danh sách các sản phẩm
            orderDAO.addOrder(customerId, productIds, quantities);

            JOptionPane.showMessageDialog(frame, "Đơn hàng đã được thêm!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Vui lòng nhập thông tin hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Lỗi khi thêm đơn hàng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewOrderHistory() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());
            List<String> orders = orderDAO.getOrderHistory(customerId);

            // Xóa các thông tin cũ trong textarea
            orderHistoryArea.setText("");
            for (String order : orders) {
                orderHistoryArea.append(order + "\n");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Vui lòng nhập ID hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Lỗi khi truy xuất dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OrderManagementApp::new);
    }
}
