package IO3;


import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class WriteXML {
    private static final String FILE_NAME = "students.xml";

    public static void addStudentToXML(String id, String name, String studentCode) {
        try {
            File file = new File(FILE_NAME);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;

            if (file.exists()) {
                doc = builder.parse(file);
                doc.getDocumentElement().normalize();
            } else {
                doc = builder.newDocument();
                Element rootElement = doc.createElement("students");
                doc.appendChild(rootElement);
            }

            Element root = doc.getDocumentElement();
            Element student = doc.createElement("student");

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(id));

            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(name));

            Element studentCodeElement = doc.createElement("studentCode");
            studentCodeElement.appendChild(doc.createTextNode(studentCode));

            student.appendChild(idElement);
            student.appendChild(nameElement);
            student.appendChild(studentCodeElement);

            root.appendChild(student);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

