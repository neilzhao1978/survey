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
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import java.awt.geom.Point2D;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
public class SvgUtilities {
	private static final Logger logger = LoggerFactory.getLogger(SvgUtilities.class);
	private static final String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	
	
	//      // remove all elements named 'item'
    //removeRecursively(doc, Node.ELEMENT_NODE, "item");

    // remove all comment nodes
    //removeRecursively(doc, Node.COMMENT_NODE, null);
    public static void removeRecursively(Node node, short nodeType, String name) {
        if (node.getNodeType()==nodeType && (name == null || node.getNodeName().equals(name))) {
            node.getParentNode().removeChild(node);
        }
        else {
            // check the children recursively
            NodeList list = node.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                removeRecursively(list.item(i), nodeType, name);
            }
        }
    }

    public static final void prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        System.out.println(out.toString());
    }
	
	public static List<Point2D> getAllKeyPoits(String uri) throws IOException, TransformerException{
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
	
	static public InputStream documentToString(org.w3c.dom.Document doc) throws TransformerException {

	    // Create dom source for the document
	    DOMSource domSource=new DOMSource(doc);

	    // Create a string writer
	    StringWriter stringWriter=new StringWriter();

	    // Create the result stream for the transform
	    StreamResult result = new StreamResult(stringWriter);

	    // Create a Transformer to serialize the document
	    TransformerFactory tFactory =TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer();
	    transformer.setOutputProperty("indent","yes");

	    // Transform the document to the result stream
	    transformer.transform(domSource, result);   
	    String temp = stringWriter.toString();
	    InputStream is = new ByteArrayInputStream(temp.getBytes());
	    return is;
	}
	
	 /** 
     * Document 转换为 String 并且进行了格式化缩进 
     *  
     * @param doc XML的Document对象 
     * @return String 
     */  
    public static String doc2FormatString(Document doc) {         
        StringWriter stringWriter = null;  
        try {  
            stringWriter = new StringWriter();  
            if(doc != null){  
                OutputFormat format = new OutputFormat(doc,"UTF-8",true);  
                //format.setIndenting(true);//设置是否缩进，默认为true  
                //format.setIndent(4);//设置缩进字符数  
                //format.setPreserveSpace(false);//设置是否保持原来的格式,默认为 false  
                //format.setLineWidth(500);//设置行宽度  
                XMLSerializer serializer = new XMLSerializer(stringWriter,format);  

                serializer.asDOMSerializer();  
                serializer.serialize(doc);  
                return stringWriter.toString();  
            } else {  
                return null;  
            }  
        } catch (Exception e) {  
            return null;  
        } finally {  
            if(stringWriter != null){  
                try {  
                    stringWriter.close();  
                } catch (IOException e) {  
                    return null;  
                }  
            }  
        }  
    }  
	
	 /** 
     * Document 转换为 String 并且进行了格式化缩进 
     *  
     * @param doc XML的Document对象 
     * @return String 
     */  
    public static String ele2FormatString(Element element) {         
        StringWriter stringWriter = null;  
        try {  
            stringWriter = new StringWriter();  
            if(element != null){  
                OutputFormat format = new OutputFormat();  
                //format.setIndenting(true);//设置是否缩进，默认为true  
                //format.setIndent(4);//设置缩进字符数  
                //format.setPreserveSpace(false);//设置是否保持原来的格式,默认为 false  
                //format.setLineWidth(500);//设置行宽度  
                XMLSerializer serializer = new XMLSerializer(stringWriter,format);  

                serializer.asDOMSerializer();  
                serializer.serialize(element);  
                return stringWriter.toString();  
            } else {  
                return null;  
            }  
        } catch (Exception e) {  
            return null;  
        } finally {  
            if(stringWriter != null){  
                try {  
                    stringWriter.close();  
                } catch (IOException e) {  
                    return null;  
                }  
            }  
        }  
    }  
    
	public static void saveDoc2SvgFile(SVGDocument doc, String profileFileName) throws IOException, FileNotFoundException {
		String out = SvgUtilities.doc2FormatString(doc);
		File xmlOut = new File(profileFileName);
		if(!xmlOut.exists()){
			xmlOut.createNewFile();
		}
		PrintStream ps = new PrintStream(new FileOutputStream(xmlOut));
		ps.print(out);
		ps.close();
	}
    
	public static void main(String[] args) {

		  try {
		      String uri = "http://oobviza1s.bkt.clouddn.com/1507893295798.svg";
		      String temp = getAllKeyPoits(uri).toString().replaceAll("Point2D.Double", "").
		    		  replaceAll("Point2D.Float", "");
		      logger.debug(temp.substring(1, temp.length()-1));
		  } catch (Exception ex) {
			  System.out.println(ex.getMessage()); 
		      // ...
		  }

	}

}
