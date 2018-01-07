package com.neil.imagetools;

import java.awt.Color;  
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;  
import java.io.IOException;  
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;  
  
public class BinaryColor {  
    public static void convert(String inBase64String,StringBuilder outBase64String,Color colorBack, Color colorFront,String formateType ) throws IOException { 
		byte[] bytes = Base64.decodeBase64(inBase64String);
 
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);      
        BufferedImage image =ImageIO.read(bais);  
        int w = image.getWidth();  
        int h = image.getHeight();  
        float[] rgb = new float[3];  
        double[][] position = new double[w][h];  
        BufferedImage bi= new BufferedImage(w, h,BufferedImage.TYPE_INT_ARGB);;  
        for (int x = 0; x < w; x++) {  
            for (int y = 0; y < h; y++) {  
                int pixel = image.getRGB(x, y);    
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                float avg = (rgb[0]+rgb[1]+rgb[2])/3;  
                position[x][y] = avg;      
            }  
        }  
        double SW = 250.0d;  //阈值
        for (int x = 0; x < w; x++) {  
            for (int y = 0; y < h; y++) {  
                if (position[x][y] <= SW) {  
                    bi.setRGB(x, y, colorFront.getRGB());  
                }else{  
                    bi.setRGB(x, y, colorBack.getRGB());  
                }  
            } 
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  

        ImageIO.write(bi, "png", baos); 
 //       ImageIO.write(bi, "png", new File("d:/1/neilTest.png"));
        byte[] bytesOut = baos.toByteArray(); 
        outBase64String.append(Base64.encodeBase64String(bytesOut));
//        outBase64String = Base64.encodeBase64String(bytesOut);
  

    }  
}  
