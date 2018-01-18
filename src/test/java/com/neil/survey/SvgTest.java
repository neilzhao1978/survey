package com.neil.survey;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import com.neil.batiktools.SvgUtilities;
import com.neil.imagetools.BinaryColor;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SvgTest {
	private static final Logger logger = LoggerFactory.getLogger(SvgTest.class);

	
	@Before
    public void setUp() throws Exception {
    }
	
	@Test
	public void testSetBrand() throws Exception  {
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		SVGDocument doc = null;
		try {
			String t = "file:C:/Users/neil/git/survey/src/main/resources/"+"10532902250458.svg";
			doc = (SVGDocument) f.createSVGDocument(t);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Element featureLine = doc.getElementById("特征线");
		Element productImage = doc.getElementById("产品图片");
		
		Element n1 = (Element)doc.getElementsByTagName("svg").item(0);
		String viewBox = n1.getAttribute("viewBox");
		logger.info(viewBox);

		Element eleProductImage = (Element) (featureLine.getElementsByTagName("image").item(0));
		eleProductImage.setAttribute("display", "none");
		
		Element eleImage = (Element) (productImage.getElementsByTagName("image").item(0));
		eleImage.setAttribute("display", "none");	
		
		SvgUtilities.saveDoc2SvgFile(doc, "d:/1/1.svg");
		BinaryColor.convertDom2Png(doc,"d:/1/1.png");
	}
}



















