package BT_QUAN_LY_SV_VKU;

import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    // Đăng nhập
    public boolean login(String email, String password) {
        String query = "SELECT * FROM students WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Thêm sinh viên
    public void addStudent(Student student) {
        String email = generateEmail(student.getName());
        String query = "INSERT INTO students (name, age, email, gpa, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setString(3, email);
            stmt.setFloat(4, student.getGpa());
            stmt.setString(5, student.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách sinh viên
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                students.add(new Student(rs.getInt("studentID"), rs.getString("name"),
                        rs.getInt("age"), rs.getString("email"), rs.getFloat("gpa"), rs.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Xóa sinh viên theo ID
    public void deleteStudent(int studentID) {
        String query = "DELETE FROM students WHERE studentID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private String generateEmail(String name) {
        String[] nameParts = name.split(" ");
        StringBuilder email = new StringBuilder();

        if (nameParts.length >= 2) {
            email.append(nameParts[nameParts.length - 1].charAt(0));
            email.append(nameParts[1].charAt(0));
            email.append(nameParts[0].charAt(0));
        } else if (nameParts.length == 1) {
            email.append(nameParts[0].charAt(0));
        }

        return email.toString().toLowerCase() + "@vku.udn.vn";
    }

}
