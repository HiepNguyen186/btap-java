package IO3;
z

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.swing.*;
import java.io.File;

public class DeleteXML {
    private static final String FILE_NAME = "students.xml";

    public static void showDeleteDialog() {
        String idToDelete = JOptionPane.showInputDialog("Nhập ID sinh viên cần xóa:");
        if (idToDelete == null || idToDelete.trim().isEmpty()) {
            return;
        }
        deleteStudentFromXML(idToDelete);
    }

    public static void deleteStudentFromXML(String id) {
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
            boolean deleted = false;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String studentId = element.getElementsByTagName("id").item(0).getTextContent();
                    if (studentId.equals(id)) {
                        element.getParentNode().removeChild(element);
                        deleted = true;
                        break;
                    }
                }
            }

            if (deleted) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
                JOptionPane.showMessageDialog(null, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy sinh viên với ID này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

