package IO3;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MergeXML {
    public static void mergeFiles() {
        List<String> filePaths = new ArrayList<>();
        filePaths.add("students1.xml");
        filePaths.add("students2.xml");
        filePaths.add("students3.xml");
        String mergedFileName = "merged_students.xml";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document mergedDoc = builder.newDocument();
            Element rootElement = mergedDoc.createElement("students");
            mergedDoc.appendChild(rootElement);

            for (String filePath : filePaths) {
                File file = new File(filePath);
                if (!file.exists()) continue;

                Document doc = builder.parse(file);
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("student");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node importedNode = mergedDoc.importNode(nodeList.item(i), true);
                    rootElement.appendChild(importedNode);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(mergedDoc);
            StreamResult result = new StreamResult(new File(mergedFileName));
            transformer.transform(source, result);

            JOptionPane.showMessageDialog(null, "Ghép file thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

