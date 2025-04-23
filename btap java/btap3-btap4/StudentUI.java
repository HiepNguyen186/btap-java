package BT_QUAN_LY_SV_VKU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentUI extends JFrame {
    private JTextArea studentListArea;
    private JButton addButton, deleteButton, refreshButton;

    public StudentUI() {
        setTitle("Quản lý Sinh viên");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        studentListArea = new JTextArea();
        studentListArea.setEditable(false);
        add(new JScrollPane(studentListArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Thêm SV");
        deleteButton = new JButton("Xóa SV");
        refreshButton = new JButton("Làm mới");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadStudentList());
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Nhập tên:");
                int age = Integer.parseInt(JOptionPane.showInputDialog("Nhập tuổi:"));
                float gpa = Float.parseFloat(JOptionPane.showInputDialog("Nhập GPA:"));
                String password = JOptionPane.showInputDialog("Nhập mật khẩu:");
                StudentDAO studentDAO = new StudentDAO();
                studentDAO.addStudent(new Student(0, name, age, "", gpa, password));
                loadStudentList();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Nhập ID sinh viên muốn xóa:"));
                StudentDAO studentDAO = new StudentDAO();
                studentDAO.deleteStudent(id);
                loadStudentList();
            }
        });

        loadStudentList();
        setVisible(true);
    }

    private void loadStudentList() {
        StudentDAO studentDAO = new StudentDAO();
        List<Student> students = studentDAO.getAllStudents();
        studentListArea.setText("Danh sách sinh viên:\n");
        for (Student s : students) {
            studentListArea.append(s.getStudentID() + " - " + s.getName() + " - " + s.getEmail() + "\n");
        }
    }

    public static void main(String[] args) {
        new StudentUI();
    }
}

