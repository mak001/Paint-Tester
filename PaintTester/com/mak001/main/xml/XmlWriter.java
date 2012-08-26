package com.mak001.main.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class XmlWriter {
	private static final String XML_VERSION = "1.0";
	private String path = null;
	private DocumentBuilderFactory dbfac;
	private DocumentBuilder docBuilder;
	private Document doc;

	private void writeNew() throws ParserConfigurationException, IOException {
		new File(path).createNewFile();
		dbfac = DocumentBuilderFactory.newInstance();
		docBuilder = dbfac.newDocumentBuilder();
		doc = docBuilder.newDocument();
		doc.setXmlVersion(XML_VERSION);
	}

	public XmlWriter(String filePath) throws ParserConfigurationException,
			IOException, SAXException {
		path = filePath;
		try {
			dbfac = DocumentBuilderFactory.newInstance();
			docBuilder = dbfac.newDocumentBuilder();
			doc = docBuilder.parse(filePath);
		} catch (FileNotFoundException e) {
			writeNew();
		}
	}

	public XmlWriter() throws ParserConfigurationException, IOException,
			SAXException {
		dbfac = DocumentBuilderFactory.newInstance();
		docBuilder = dbfac.newDocumentBuilder();
		doc = docBuilder.newDocument();
	}

	public XmlWriter(File file) throws ParserConfigurationException,
			IOException, SAXException {
		this(file.getPath());
	}

	public void setFilePath(String filePath) throws SAXException, IOException {
		path = filePath;
		File f = new File(filePath);
		if (f.exists()) {
			doc = docBuilder.parse(filePath);
		} else {
			f.createNewFile();
		}
	}

	public void setFile(File file) throws SAXException, IOException {
		setFilePath(file.getPath());
	}

	public Document getDocument() {
		return doc;
	}

	public Element getElement(String tag) {
		Element element = doc.createElement(tag);
		return element;
	}

	public void appendRoot(Element root) {
		doc.appendChild(root);
	}

	public void appendChild(Element father, Element son) {
		father.appendChild(son);
	}

	public void addComment(Element element, String text) {
		Comment comment = doc.createComment(text);
		element.appendChild(comment);
	}

	public void addTextNode(Element element, String val) {
		Text text = doc.createTextNode(val);
		element.appendChild(text);
	}

	public void addAttribute(Element element, String key, String value) {
		element.setAttribute(key, value);
	}

	public void commit() throws TransformerException, IOException {
		File file = new File(path);

		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();

		FileWriter writer = new FileWriter(file);
		StreamResult result = new StreamResult(writer);
		DOMSource source = new DOMSource(doc);

		DocumentType dt = doc.getDoctype();
		if (dt != null) {
			String pub = dt.getPublicId();
			if (pub != null) {
				trans.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, pub);
			}
			trans.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dt.getSystemId());
		}
		trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");
		trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
				"4");

		trans.transform(source, result);
		writer.flush();
		writer.close();
	}

	public String getFilePath() {
		return path;
	}
}
