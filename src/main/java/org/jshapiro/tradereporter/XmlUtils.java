package org.jshapiro.tradereporter;

import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathFactory;
import java.io.Reader;
import java.io.StringReader;

public class XmlUtils {
    public static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY;
    static {
        DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    }
    public static final XPathFactory XPATH_FACTORY = XPathFactory.newDefaultInstance();

    @SneakyThrows
    public static Document buildDocument(final String xml) {
        DocumentBuilder builder = getDocumentBuilder();
        Reader reader = new StringReader(xml);
        Document result = builder.parse(new InputSource(reader));
        IOUtils.closeQuietly(reader);
        return result;
    }

    public static DocumentBuilder getDocumentBuilder() {
        DocumentBuilder docBuilder;
        try {
            docBuilder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        return docBuilder;
    }
}
