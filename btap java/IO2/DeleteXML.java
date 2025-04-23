package IO2;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.swing.*;
import java.io.File;

public class DeleteXML {
    public static void main(String[] args) {
        try {
            String idToDelete = JOptionPane.showInputDialog("Nhập ID sinh viên cần xóa:");

            if (idToDelete == null) {
                JOptionPane.showMessageDialog(null, "Hủy thao tác!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

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
            boolean found = false;

            for (int i = 0; i < students.getLength(); i++) {
                Element student = (Element) students.item(i);
                if (student.getAttribute("id").equals(idToDelete)) {
                    student.getParentNode().removeChild(student);
                    found = true;
                    break;
                }
            }

            if (found) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);

                JOptionPane.showMessageDialog(null, "Xóa sinh viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy sinh viên có ID: " + idToDelete, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa sinh viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
