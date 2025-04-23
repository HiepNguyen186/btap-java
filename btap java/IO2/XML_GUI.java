package IO2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class XML_GUI {
    public static void main(String[] args) {
        // Khởi tạo cửa sổ chính
        JFrame frame = new JFrame("Quản lý Sinh viên XML");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Tiêu đề
        JLabel titleLabel = new JLabel("Quản Lý Sinh Viên", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180));
        titleLabel.setPreferredSize(new Dimension(500, 50));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel chứa các nút bấm
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Nút Đọc XML
        JButton readButton = createStyledButton("📖 Đọc XML", new Color(0, 128, 255));
        buttonPanel.add(readButton, gbc);

        // Nút Thêm Sinh Viên
        gbc.gridy++;
        JButton addButton = createStyledButton("➕ Thêm Sinh Viên", new Color(0, 200, 0));
        buttonPanel.add(addButton, gbc);

        // Nút Xóa Sinh Viên
        gbc.gridy++;
        JButton deleteButton = createStyledButton("❌ Xóa Sinh Viên", new Color(255, 0, 0));
        buttonPanel.add(deleteButton, gbc);

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Xử lý sự kiện Đọc XML
        readButton.addActionListener(e -> ReadXML.main(null));

        // Xử lý sự kiện Thêm Sinh Viên
        addButton.addActionListener(e -> showAddStudentDialog());

        // Xử lý sự kiện Xóa Sinh Viên
        deleteButton.addActionListener(e -> DeleteXML.main(null));

        // Hiển thị cửa sổ
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        }

    // Hàm tạo nút với style đẹp
    private static JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFont(new Font("Tahoma", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(200, 50));
        return button;
    }

    // Hiển thị hộp thoại thêm sinh viên
    private static void showAddStudentDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField studentCodeField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Tên:"));
        panel.add(nameField);
        panel.add(new JLabel("Mã sinh viên:"));
        panel.add(studentCodeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Thêm Sinh Viên", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String studentCode = studentCodeField.getText().trim();

            if (!id.isEmpty() && !name.isEmpty() && !studentCode.isEmpty()) {
                WriteXML.addStudentToXML(id, name, studentCode);
                JOptionPane.showMessageDialog(null, "Đã thêm sinh viên thành công!", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
