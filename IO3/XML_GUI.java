package IO3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class XML_GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý Sinh viên XML");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Quản Lý Sinh Viên", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180));
        titleLabel.setPreferredSize(new Dimension(600, 50));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton addButton = createStyledButton("➕ Thêm Sinh Viên", new Color(0, 200, 0));
        buttonPanel.add(addButton, gbc);

        gbc.gridy++;
        JButton viewButton = createStyledButton("📖 Hiển thị danh sách", new Color(0, 128, 255));
        buttonPanel.add(viewButton, gbc);

        gbc.gridy++;
        JButton updateButton = createStyledButton("✏️ Cập nhật Sinh Viên", new Color(255, 165, 0));
        buttonPanel.add(updateButton, gbc);

        gbc.gridy++;
        JButton deleteButton = createStyledButton("❌ Xóa Sinh Viên", new Color(255, 0, 0));
        buttonPanel.add(deleteButton, gbc);

        gbc.gridy++;
        JButton mergeButton = createStyledButton("🔗 Ghép file XML", new Color(128, 0, 128));
        buttonPanel.add(mergeButton, gbc);

        frame.add(buttonPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> showAddStudentDialog());
        viewButton.addActionListener(e -> ReadXML.displayStudents());
        updateButton.addActionListener(e -> UpdateXML.showUpdateDialog());
        deleteButton.addActionListener(e -> DeleteXML.showDeleteDialog());
        mergeButton.addActionListener(e -> MergeXML.mergeFiles());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFont(new Font("Tahoma", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(250, 50));
        return button;
    }

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

        int result = JOptionPane.showConfirmDialog(null, panel, "Thêm Sinh Viên", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String studentCode = studentCodeField.getText().trim();

            if (!id.isEmpty() && !name.isEmpty() && !studentCode.isEmpty()) {
                WriteXML.addStudentToXML(id, name, studentCode);
                JOptionPane.showMessageDialog(null, "Đã thêm sinh viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

