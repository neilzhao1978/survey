package com.neil.batiktools;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import com.neil.survey.controller.SurveyController;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
public class ReadSvg {
	private static final Logger logger = LoggerFactory.getLogger(ReadSvg.class);
	public static void main(String[] args) {

		  try {
			  String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		      String parser = XMLResourceDescriptor.getXMLParserClassName();
		      SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		      String uri = "http://oobviza1s.bkt.clouddn.com/1507893295798.svg";
		      List<Point2D>  points=new LinkedList<Point2D>();
		      SVGDocument  doc = (SVGDocument)f.createDocument(uri);
		      
		      NodeList paths = doc.getElementsByTagNameNS(svgNS,"path");
		      for(int i=0;i<paths.getLength();i++){
		    	  Element elementPath = (Element)doc.getElementsByTagNameNS(svgNS,"path").item(0);
		    	  logger.debug("d="+elementPath.getAttribute("d").toString());
			      MyPathParser pathParser = new MyPathParser();
			      points .addAll(pathParser.extractPoints(elementPath.getAttribute("d").toString()));
		      }
		      
		      

		      
		      Element elementPoint = (Element)doc.getElementsByTagNameNS(svgNS,"polyline").item(0);
		      logger.debug("points="+elementPoint.getAttribute("points").toString());
		      
		      MyPointsParser pointsParser = new MyPointsParser();
		      points.addAll(pointsParser.extractPoints(elementPoint.getAttribute("points").toString()));

		      Element elementLine = (Element)doc.getElementsByTagNameNS(svgNS,"line").item(0);
		      
		      Point2D linePoint1 = new  Point2D.Double(Double.parseDouble(elementLine.getAttribute("x1")),
		    		  Double.parseDouble(elementLine.getAttribute("y1")));
		      Point2D linePoint2 = new  Point2D.Double(Double.parseDouble(elementLine.getAttribute("x2")),
		    		  Double.parseDouble(elementLine.getAttribute("y2")));
		      
		      points.add(linePoint1);
		      points.add(linePoint2);
		      
		      logger.debug("x1="+elementLine.getAttribute("x1").toString()
		    		  +"y1="+elementLine.getAttribute("y1").toString()
		    		  +"x2="+elementLine.getAttribute("x2").toString()
		    		  +"y2="+elementLine.getAttribute("y2").toString()
		    		  );
		      
//		      MyPointsParser elementLine = new MyPointsParser();
//		      points.addAll(pointsParser.extractPoints(elementPoint.getAttribute("points").toString()));
		      
		      
//		      System.out.println("class="+element.getAttribute("class").toString());
//		      System.out.println(doc.getElementsByTagName("polyline").getLength()+"");
//		      System.out.println(doc.getChildNodes().getLength()+"");
//			  Element e = doc.getElementById("特征线");
//				
//			  NodeList xxx = e.getElementsByTagName("g");
//			  System.out.println(xxx.getLength()+"");
//		      NodeList x =doc.getElementsByTagName("path");
//		      Node y =  x.item(0);
//		      String z = y.getNodeName();
//		      String x1 = doc.getBaseURI().toString();
		  } catch (IOException ex) {
			  System.out.println(ex.getMessage()); 
		      // ...
		  }

	}

}
