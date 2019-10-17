package com.test.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * https://www.yiibai.com/java_xml/java_xpath_parse_document.html
 */
public class TestXML {
    public static void main(String[] args) throws Exception {
        InputSource inputSource = new InputSource(new InputStreamReader(new FileInputStream("/Users/shenfl/IdeaProjects/test/common/src/main/resources/IKAnalyzer.cfg.xml")));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(false);
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(false);
        factory.setCoalescing(false);
        factory.setExpandEntityReferences(true);

        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void error(SAXParseException exception) throws SAXException {
                throw exception;
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                throw exception;
            }

            @Override
            public void warning(SAXParseException exception) throws SAXException {
            }
        });

        Document document = builder.parse(inputSource);

        XPath xPath =  XPathFactory.newInstance().newXPath();

        NodeList nodeList = (NodeList) xPath.compile("/properties").evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println(nodeList.item(i).getNodeName()); // properties
            System.out.println(nodeList.item(i).getNodeValue()); // null
            System.out.println(nodeList.item(i).toString());
            System.out.println(nodeList.item(i).getAttributes());
            System.out.println("-----------------");
            NodeList childNodes = nodeList.item(i).getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                System.out.println(childNodes.item(j).getTextContent());
            }
        }
        System.out.println("----------------");
        // 没发把整个node下 字符串和标签 的完整字符串拿到
        String value = (String)xPath.compile("/properties").evaluate(document, XPathConstants.STRING);
        System.out.println(value);

        Object evaluate = xPath.compile("/properties/aa").evaluate(document, XPathConstants.NUMBER);
        System.out.println(evaluate);
    }
}
