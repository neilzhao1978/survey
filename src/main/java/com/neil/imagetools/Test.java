package com.neil.imagetools;
  
import java.awt.image.BufferedImage;  
import java.io.ByteArrayInputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
  
import javax.imageio.ImageIO;  
  
import org.dom4j.Document;  
import org.dom4j.DocumentException;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element;  
import org.dom4j.io.SAXReader;  
import org.dom4j.io.XMLWriter;  
  
import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  
  
public class Test {  
    public static void main(String[] args) {  
        Test t = new Test();  
        t.inputToXML();  
//        t.outToImage();  
    }  
  
    // 图片转换成XML  
    public void inputToXML() {  
        BASE64Encoder encoder = new BASE64Encoder();  
        try {  
            File f = new File("D:\\1\\me.jpg");  
            // System.out.println("5555");  
            if (f.exists()) {  
  
                FileInputStream fis = new FileInputStream(f);  
                byte[] buffer = new byte[(int) f.length()];  
                fis.read(buffer);  
                String s_imageData = encoder.encode(buffer);  
                Document doc = DocumentHelper.createDocument();  
                Element root = doc.addElement("ImageList");  
                Element imageID = root.addElement("imageID");  
                Element imageInfo = root.addElement("imageInfo");  
                Element imageSize = root.addElement("imageSize");  
                Element imageData = root.addElement("imageData");  
                imageID.addText("01");  
                imageInfo.addText("图片1");  
                imageSize.addText(String.valueOf(f.length()));  
                imageData.addText(s_imageData);  
                XMLWriter writer = new XMLWriter(new FileOutputStream("D:\\1\\1.xml"));  
                writer.write(doc);  
                System.out.println("22");  
                writer.flush();  
                writer.close();  
            } else {  
                System.out.println("找不到要转换的图片文件！");  
            }  
  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
    }  
  
    // XML转成图片  
    public void outToImage() {  
        File f = new File("D:\\xml\\1.xml");  
        SAXReader reader = new SAXReader();  
        try {  
            Document doc = reader.read(f);  
            byte[] aa = doc.asXML().getBytes() ;  
            System.out.println(new String(aa,"UTF-8"));  
              
            Element root = doc.getRootElement();  
            Element image = (Element) root.selectSingleNode("imageData");  
            String s_data = image.getText();  
            BASE64Decoder decoder = new BASE64Decoder();  
            byte[] data = decoder.decodeBuffer(s_data);  
              
            FileOutputStream fos = new FileOutputStream("D:\\xml\\71198.jpg");  
            fos.write(data);  
            fos.flush();  
            fos.close();  
              
        } catch (DocumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
    }  
}  