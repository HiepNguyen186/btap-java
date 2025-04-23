package IO2;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import javax.swing.*;

public class ReadXML {
    public static void main(String[] args) {
        try {
            File file = new File("students.xml");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy file XML!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList students = doc.getElementsByTagName("student");
            StringBuilder sb = new StringBuilder("Danh sách sinh viên:\n");

            for (int i = 0; i < students.getLength(); i++) {
                Element student = (Element) students.item(i);
                String id = student.getAttribute("id");
                String name = student.getElementsByTagName("name").item(0).getTextContent();
                String age = student.getElementsByTagName("age").item(0).getTextContent();
                sb.append("ID: ").append(id).append(" - Tên: ").append(name).append(" - Tuổi: ").append(age).append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.toString(), "Danh sách Sinh Viên", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi đọc file XML!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
