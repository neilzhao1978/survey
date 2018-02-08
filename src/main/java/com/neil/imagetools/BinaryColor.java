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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.neil.survey.controller.SurveyController;
import com.neil.survey.module.ColornId;
import com.neil.survey.module.Image;

public class BinaryColor {
	//剪影
	private static final Logger logger = LoggerFactory.getLogger(BinaryColor.class);
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
    
    public static String combineImage(String bigFileName,String smallFileName, int x,int y) throws IOException{
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
            
            byte[] bytesOut = baos.toByteArray(); 
            return Base64.encodeBase64String(bytesOut);
    	}catch(Exception e){
        	return null;
        }finally{
        	g.dispose();
        }
    }
    
    private static Integer getColorsbyFileName(String fileName,Map<String,ColornId> colorMap){
    	for(Entry<String,ColornId> e:colorMap.entrySet()){
    		if(fileName.contains(e.getValue().fileName)){
    			return e.getValue().color;
    		}
    	}
    	logger.error("cannot find color.");
    	return 0;
    }
    public static String combineWholeFeatureImage(String masterFileName,List<String> fileNames, Map<String,ColornId> colorMap) throws IOException{
    	List<InputStream> s = new ArrayList<InputStream>();
    	List<Integer> colors = new ArrayList<Integer>();
    	
    	Graphics g =  null;
    	InputStream b = new URL(masterFileName).openStream();
    	try{
            try{
            	for(String fileName :fileNames){            		
            		s.add(new URL(fileName).openStream());
            		Integer i=getColorsbyFileName(fileName,colorMap);
            		colors.add(i);
            	}
            }catch(Exception e){
            	s = null;
            }

            BufferedImage bImg = ImageIO.read(b);
            g = bImg.getGraphics();
            if(s!=null){
            	for(int i=0;i<s.size();i++){
                    BufferedImage sImg = ImageIO.read(s.get(i));
                    Integer color = colors.get(i);
    	            color = 0xff000000 | color ;
    	            for (int x = 0; x < sImg.getWidth(); x++) {  
    	                for (int y = 0; y < sImg.getHeight(); y++) {  
    	                    int pixel = sImg.getRGB(x, y);  
    	                    pixel &= 0x00ffffff;
    	                    int alpha = (pixel & 0xff000000) >> 24;
    	                    if (pixel>0x000000) {//not transparent.
    	                    	sImg.setRGB(x, y, color);  
    	                    }
    	                }  
    	            } 
                    g.drawImage(sImg, 0, 0,sImg.getWidth(), sImg.getHeight(), null);
            	}
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();  

            ImageIO.write(bImg, "png", baos); 

//            ImageIO.write(bImg, "png", new File("D:/1/qwe.png"));
            
            byte[] bytesOut = baos.toByteArray(); 
            return Base64.encodeBase64String(bytesOut);
    	}catch(Exception e){
        	return null;
        }finally{
        	g.dispose();
        }
    }
    
    
    public static String combineWholeImage(String masterFileName,List<String> fileNames) throws IOException{
    	List<InputStream> s = new ArrayList<InputStream>();
    	Graphics g =  null;
    	InputStream b = new URL(masterFileName).openStream();
    	try{
            try{
            	for(String fileName :fileNames){            		
            		s.add(new URL(fileName).openStream());
            	}
            }catch(Exception e){
            	s = null;
            }

            BufferedImage bImg = ImageIO.read(b);
            g = bImg.getGraphics();
            if(s!=null){
            	for(InputStream is:s){
                    BufferedImage sImg = ImageIO.read(is);
                    g.drawImage(sImg, 0, 0,sImg.getWidth(), sImg.getHeight(), null);
            	}
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();  

            ImageIO.write(bImg, "png", baos); 
            
            byte[] bytesOut = baos.toByteArray(); 
            return Base64.encodeBase64String(bytesOut);
    	}catch(Exception e){
        	return null;
        }finally{
        	g.dispose();
        }
    }
    
    /**
     * 
     * @param masterFileName
     * @param replaced 用于获取被替换部分的坐标。
     * @param src 源图地址。
     * @return String[0]:图像，String[1]:特征线
     * @throws IOException
     * 
     */
    public  static String[] combineStitchImage(Image baseImage,List<Image> replaced,List<Image> src,Map<String,ColornId> colorMap) throws IOException{

    	String ret[] = new String[2];
    	Map<String,Image> replacedMap = new HashMap<String,Image>();
    	for(Image i:replaced){
    		replacedMap.put(i.getImageName(), i);
    	}
    	Map<String,Image> srcMap = new HashMap<String,Image>();
    	for(Image i:src){
    		srcMap.put(i.getImageName(), i);
    	}    	
        
    	try{
        	Graphics g =  null;
        	InputStream b = new URL(baseImage.getPngImageUrl()).openStream();
            BufferedImage bImg = ImageIO.read(b);
            g = bImg.getGraphics();
	    	for(Entry<String, Image> e :replacedMap.entrySet()){
	    		Image replaceImageDb = e.getValue(); 
	    		Image srcImageDb = srcMap.get(e.getKey());
	        	InputStream temp = new URL(srcImageDb.getImageUrl()).openStream();
	            BufferedImage sImg = ImageIO.read(temp);
	            g.drawImage(sImg, replaceImageDb.getX(), replaceImageDb.getY(),
	            		replaceImageDb.getW(), replaceImageDb.getH(), null);
	    	}

	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    	ImageIO.write(bImg, "png", baos); 
	    	
	    	
            byte[] bytesOut = baos.toByteArray(); 
            ret[0]=Base64.encodeBase64String(bytesOut);
        	g.dispose();
    	}catch(Exception e){
    		e.printStackTrace();
        	return null;
        }
    	
    	try{
        	Graphics g =  null;
        	InputStream b = new URL(baseImage.getFeatureUrl()).openStream();
            BufferedImage bImg = ImageIO.read(b);
            int cMaster = 0xff000000 | colorMap.get("master").color ;
            for (int x = 0; x < bImg.getWidth(); x++) {  
                for (int y = 0; y < bImg.getHeight(); y++) {  
                    int pixel = bImg.getRGB(x, y);  
                    pixel &= 0x00ffffff;
                    int alpha = (pixel & 0xff000000) >> 24;
                    if (pixel>0x000000) {//not transparent.
                    	bImg.setRGB(x, y, cMaster); //
                    }
                }  
            } 
//            ImageIO.write(bImg, "png", new File("D:/1/stitch_master.png"));
            
            g = bImg.getGraphics();
	    	for(Entry<String, Image> e :replacedMap.entrySet()){
	    		Image replaceImageDb = e.getValue(); //被替换的图
	    		Image srcImageDb = srcMap.get(e.getKey());//通过名字找到替换的图。
	        	InputStream temp = new URL(srcImageDb.getFeatureUrl()).openStream();
	            BufferedImage sImg = ImageIO.read(temp);
	            //TODO change sImg's front color.
	            int color = 0;
	            if(srcImageDb.getImageName().contains("司机室")){
	            	color = colorMap.get("driverRoom").color;
	            }else if (srcImageDb.getImageName().contains("钢轮")){
	            	color = colorMap.get("wheel").color;
	            }else if (srcImageDb.getImageName().contains("后罩")){
	            	color = colorMap.get("rearHood").color;
	            }else{
	            	color = 0;//默认
	            }
	            
	            color = 0xff000000 | color ;
	            for (int x = 0; x < sImg.getWidth(); x++) {  
	                for (int y = 0; y < sImg.getHeight(); y++) {  
	                    int pixel = sImg.getRGB(x, y);  
	                    pixel &= 0x00ffffff;
	                    int alpha = (pixel & 0xff000000) >> 24;
	                
	                    if (pixel>0x000000) {//not transparent.
	                    	sImg.setRGB(x, y, color);  
	                    }

	                }  
	            } 
	            g.drawImage(sImg, replaceImageDb.getX(), replaceImageDb.getY(),
	            		replaceImageDb.getW(), replaceImageDb.getH(), null);
	    	}

	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    	ImageIO.write(bImg, "png", baos); 
//	    	ImageIO.write(bImg, "png", new File("D:/1/stitchFeature.png")); 
            byte[] bytesOut = baos.toByteArray(); 
            ret[1]=Base64.encodeBase64String(bytesOut);
        	g.dispose();
    	}catch(Exception e){
    		e.printStackTrace();
        	return null;
        }
    	
    	return ret;
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
    
}  
