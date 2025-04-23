package IO3;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import javax.swing.JOptionPane;

public class ReadXML {
    private static final String FILE_NAME = "students.xml";

    public static void displayStudents() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("student");
            StringBuilder studentList = new StringBuilder("Danh sách Sinh Viên:\n");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getElementsByTagName("id").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String studentCode = element.getElementsByTagName("studentCode").item(0).getTextContent();
                    studentList.append("ID: ").append(id).append(" | Tên: ").append(name).append(" | Mã SV: ").append(studentCode).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, studentList.toString(), "Danh Sách Sinh Viên", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

