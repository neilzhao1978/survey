package com.neil.batiktools;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
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

import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
public class SvgUtilities {
	private static final Logger logger = LoggerFactory.getLogger(SvgUtilities.class);
	private static final String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	public static List<Point2D> getAllKeyPoits(String uri) throws IOException{
	      String parser = XMLResourceDescriptor.getXMLParserClassName();
	      SVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
	      List<Point2D>  points=new LinkedList<Point2D>();
	      SVGDocument  doc = (SVGDocument)f.createSVGDocument(uri);
	      
	      NodeList paths = doc.getElementsByTagNameNS(svgNS,"path");
	      for(int i=0;i<paths.getLength();i++){
	    	  Element elementPath = (Element)paths.item(i);
	    	  logger.debug("d="+elementPath.getAttribute("d").toString());
		      MyPathParser pathParser = new MyPathParser();
		      points.addAll(pathParser.extractPoints(elementPath.getAttribute("d").toString()));
	      }
	      
	      NodeList polylines = doc.getElementsByTagNameNS(svgNS,"polyline");
	      for(int i=0;i<polylines.getLength();i++){
	    	  Element elementPath = (Element)polylines.item(i);
	    	  logger.debug("points="+elementPath.getAttribute("points").toString());
	    	  MyPointsParser pointsParser = new MyPointsParser();
		      points.addAll(pointsParser.extractPoints(elementPath.getAttribute("points").toString()));
	      }

	      NodeList lines = doc.getElementsByTagNameNS(svgNS,"line");
	      for(int i=0;i<lines.getLength();i++){
	    	  Element elementLine = (Element)lines.item(i);
		      
		      Point2D linePoint1 = new  Point2D.Double(Double.parseDouble(elementLine.getAttribute("x1")),
		    		  Double.parseDouble(elementLine.getAttribute("y1")));
		      Point2D linePoint2 = new  Point2D.Double(Double.parseDouble(elementLine.getAttribute("x2")),
		    		  Double.parseDouble(elementLine.getAttribute("y2")));
		      

		      logger.debug("x1="+elementLine.getAttribute("x1").toString()
		    		  +"y1="+elementLine.getAttribute("y1").toString()
		    		  +"x2="+elementLine.getAttribute("x2").toString()
		    		  +"y2="+elementLine.getAttribute("y2").toString()
		    		  );
		      points.add(linePoint1);
		      points.add(linePoint2);
	      }
	      return points;

	}
	public static void main(String[] args) {

		  try {
		      String uri = "http://oobviza1s.bkt.clouddn.com/1507893295798.svg";
		      String temp = getAllKeyPoits(uri).toString().replaceAll("Point2D.Double", "").
		    		  replaceAll("Point2D.Float", "");
		      
		      logger.debug(temp.substring(1, temp.length()-1));

		  } catch (IOException ex) {
			  System.out.println(ex.getMessage()); 
		      // ...
		  }

	}

}
