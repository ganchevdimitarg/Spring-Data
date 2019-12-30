package softuni.exam.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParserImpl implements XmlParser {
    @Override
    @SuppressWarnings(value = "unchecked")
    public <O> O importXML(Class<O> objectClass, String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (O) unmarshaller.unmarshal(new File(path));
    }
}
