package com.neil.batiktools;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import java.io.IOException;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;

import org.w3c.dom.Document;
public class ReadSvg {

	public static void main(String[] args) {

		  try {
			  String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		      String parser = XMLResourceDescriptor.getXMLParserClassName();
		      SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		      String uri = "http://oobviza1s.bkt.clouddn.com/7539420920675.svg";
		      SVGDocument  doc = (SVGDocument)f.createDocument(uri);
		   

		      System.out.println(doc.getElementsByTagName("path").getLength()+"");
		      System.out.println(doc.getChildNodes().getLength()+"");
				Element e = doc.getElementById("特征线");
				
				NodeList xxx = e.getElementsByTagName("g");
				System.out.println(xxx.getLength()+"");
		      NodeList x =doc.getElementsByTagName("path");
		      Node y =  x.item(0);
		      String z = y.getNodeName();
		      String x1 = doc.getBaseURI().toString();
		  } catch (IOException ex) {
			  System.out.println(ex.getMessage()); 
		      // ...
		  }

	}

}
