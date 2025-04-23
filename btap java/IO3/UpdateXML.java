package IO3;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.swing.*;
import java.io.File;

public class UpdateXML {
    private static final String FILE_NAME = "students.xml";

    public static void showUpdateDialog() {
        String idToUpdate = JOptionPane.showInputDialog("Nhập ID sinh viên cần cập nhật:");
        if (idToUpdate == null || idToUpdate.trim().isEmpty()) {
            return;
        }

        JTextField nameField = new JTextField();
        JTextField studentCodeField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Tên mới:"));
        panel.add(nameField);
        panel.add(new JLabel("Mã sinh viên mới:"));
        panel.add(studentCodeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Cập nhật Sinh Viên", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            updateStudentInXML(idToUpdate, nameField.getText().trim(), studentCodeField.getText().trim());
        }
    }

    public static void updateStudentInXML(String id, String newName, String newStudentCode) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy file dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("student");
            boolean updated = false;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String studentId = element.getElementsByTagName("id").item(0).getTextContent();
                    if (studentId.equals(id)) {
                        element.getElementsByTagName("name").item(0).setTextContent(newName);
                        element.getElementsByTagName("studentCode").item(0).setTextContent(newStudentCode);
                        updated = true;
                        break;
                    }
                }
            }

            if (updated) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy sinh viên với ID này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
