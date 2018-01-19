package com.neil.imagetools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;

  
public class BinaryColor {
	//剪影
	
    private static double SW = 250.0d;  //阈值
	
    public static void convertProfile(String inBase64String,StringBuilder outBase64String,Color colorBack, Color colorFront,String formateType ) throws IOException { 
		byte[] bytes = Base64.decodeBase64(inBase64String);
 
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);      
        BufferedImage image =ImageIO.read(bais);  
        int w = image.getWidth();  
        int h = image.getHeight();  
        float[] rgb = new float[3];  
        double[][] position = new double[w][h];  
        BufferedImage bi= new BufferedImage(w, h,BufferedImage.TYPE_INT_ARGB); 
        
        for (int x = 0; x < w; x++) {  
            for (int y = 0; y < h; y++) {  
                int pixel = image.getRGB(x, y);    
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                float avg = (rgb[0]+rgb[1]+rgb[2])/3;  
                position[x][y] = avg;  
                if (position[x][y] <= SW) {  
                    bi.setRGB(x, y, colorFront.getRGB());  
                }else{  
                    bi.setRGB(x, y, colorBack.getRGB());  
                } 
            }  
        }  
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  

        ImageIO.write(bi, "png", baos); 
        byte[] bytesOut = baos.toByteArray(); 
        outBase64String.append(Base64.encodeBase64String(bytesOut));
    }  
    
	//背景变透明
    public static void convertTransBack(String inBase64String,StringBuilder outBase64String) throws IOException { 
    	byte[] bytes = Base64.decodeBase64(inBase64String);
 
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);      
        BufferedImage image =ImageIO.read(bais);  
        
        
        int w = image.getWidth();  
        int h = image.getHeight(); 
        
        BufferedImage bi= new BufferedImage(w, h,BufferedImage.TYPE_INT_ARGB); 
 
        float[] rgb = new float[3];  
        double[][] position = new double[w][h];  

        for (int x = 0; x < w; x++) {  
            for (int y = 0; y < h; y++) {  
                int pixel = image.getRGB(x, y);    
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                float avg = (rgb[0]+rgb[1]+rgb[2])/3;  
                position[x][y] = avg; 
                if (position[x][y] > SW) {  
                	bi.setRGB(x, y, new Color(255,255,255,0).getRGB());
                }else{
                	bi.setRGB(x, y, pixel);
                }
            }  
        }  

        ByteArrayOutputStream baos = new ByteArrayOutputStream();  

        ImageIO.write(bi, "png", baos);
        
//        ImageIO.write(bi, "png", new File("D:/1/p.png"));
        
        byte[] bytesOut = baos.toByteArray(); 
        outBase64String.append(Base64.encodeBase64String(bytesOut));
    }  
    
    
    public static void convert(String inBase64String,String outFileName,Color colorBack,String formateType ) throws IOException { 
		byte[] bytes = Base64.decodeBase64(inBase64String);
 
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);      
        BufferedImage image =ImageIO.read(bais);  
        int w = image.getWidth();  
        int h = image.getHeight();  
        BufferedImage bi= new BufferedImage(w, h,BufferedImage.TYPE_INT_ARGB); 
        float[] rgb = new float[3];  
        for (int x = 0; x < w; x++) {  
            for (int y = 0; y < h; y++) {  
                int pixel = image.getRGB(x, y);    
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                float avg = (rgb[0]+rgb[1]+rgb[2])/3;
                if(avg>SW){
                	image.setRGB(x, y, colorBack.getRGB());
                }else{
                	bi.setRGB(x, y, pixel);
                }
            }  
        }  
        ImageIO.write(image, "png", new File(outFileName));

    }  

    public static void convertDom2Png(Document document,String outFileName) throws Exception { 
        PNGTranscoder t = new PNGTranscoder();
        TranscoderInput input = new TranscoderInput(document);
        OutputStream ostream = new FileOutputStream(outFileName);
        TranscoderOutput output = new TranscoderOutput(ostream);

        t.transcode(input, output);
        ostream.flush();
        ostream.close();
        
        InputStream inputStream = new FileInputStream(outFileName);   
        
        BufferedImage image =ImageIO.read(inputStream); 

        inputStream.close();
        int w = image.getWidth();  
        int h = image.getHeight();  
        BufferedImage bi= new BufferedImage(w, h,BufferedImage.TYPE_INT_ARGB); 
        float[] rgb = new float[4];  
        for (int x = 0; x < w; x++) {  
            for (int y = 0; y < h; y++) {  
                int pixel = image.getRGB(x, y);    
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                rgb[3] = (pixel & 0xff000000)>>24;  
                float avg = (rgb[0]+rgb[1]+rgb[2])/3;
                if(avg>SW){
                	bi.setRGB(x, y, new Color(255,255,255,0).getRGB());
                }else{
                	bi.setRGB(x, y, pixel);
                }
            }  
        }  
        ImageIO.write(bi, "png", new File(outFileName));

    }  

    //不用
    public static void convertDom2PngSplit(Document document,String outFileName,int x0,int y0,int w0,int h0) throws Exception { 
        // Create a JPEGTranscoder and set its quality hint.
        PNGTranscoder t = new PNGTranscoder();
        TranscoderInput input = new TranscoderInput(document);
        OutputStream ostream = new FileOutputStream(outFileName);
        TranscoderOutput output = new TranscoderOutput(ostream);

        
        t.transcode(input, output);
        ostream.flush();
        ostream.close();
        
        InputStream inputStream = new FileInputStream(outFileName);   
        
        BufferedImage image =ImageIO.read(inputStream); 
        inputStream.close();
        int w = image.getWidth();  
        int h = image.getHeight();  
        BufferedImage bi= new BufferedImage(w, h,BufferedImage.TYPE_INT_ARGB); 
        float[] rgb = new float[4];  
        for (int x = 0; x < w; x++) {  
            for (int y = 0; y < h; y++) {  
                int pixel = image.getRGB(x, y);    
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                rgb[3] = (pixel & 0xff000000)>>24;  
                float avg = (rgb[0]+rgb[1]+rgb[2])/3;
                if(avg>SW){
                	bi.setRGB(x, y, new Color(255,255,255,0).getRGB());
                }else{
                	bi.setRGB(x, y, pixel);
                }
            }  
        }  
        ImageIO.write(bi, "png", new File(outFileName));
    }
    
    public static String conbineImage(String bigFileName,String smallFileName, int x,int y) throws IOException{
    	InputStream s = null;
    	Graphics g =  null;
    	InputStream b = new URL(bigFileName).openStream();
    	try{
            try{
            	s = new URL(smallFileName).openStream();
            }catch(Exception e){
            	s = null;
            }

            BufferedImage bImg = ImageIO.read(b);        
            g = bImg.getGraphics();
            if(s!=null){
                BufferedImage sImg = ImageIO.read(s);
                g.drawImage(sImg, x, y,sImg.getWidth(), sImg.getHeight(), null);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();  

            ImageIO.write(bImg, "png", baos); 
            ImageIO.write(bImg, "png", new File("D:/1/conbine.png")); 
            
            byte[] bytesOut = baos.toByteArray(); 
            return Base64.encodeBase64String(bytesOut);
    	}catch(Exception e){
        	return null;
        }finally{
        	g.dispose();
        }


    	
    }
}  
