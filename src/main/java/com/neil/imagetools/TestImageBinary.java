package com.neil.imagetools;

import java.awt.image.BufferedImage;  
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.IOException;  
import java.util.logging.Logger;  
  
import javax.imageio.ImageIO;  
  
import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  
  
public class TestImageBinary {  
  
    private static Logger logger = Logger.getLogger("TestImageBinary");  
      
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();      
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();      
          
    public static void main(String[] args) {  
        logger.info(getImageBinary());  
        base64StringToImage(getImageBinary());      
    }      
        /** 
         * 将图片转换成二进制 
         * @return 
         */  
    public static String getImageBinary(){      
        File file = new File("C:/Users/limingwang/Desktop/test.png");             
        BufferedImage bi;      
        try {      
            bi = ImageIO.read(file);      
            ByteArrayOutputStream baos = new ByteArrayOutputStream();      
            ImageIO.write(bi, "png", baos);      
            byte[] bytes = baos.toByteArray();      
            return encoder.encodeBuffer(bytes).trim();      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        return null;      
    }      
          
    /** 
     * 将二进制转换为图片 
     * @param base64String 
     */  
    public static void base64StringToImage(String base64String){      
        try {      
            byte[] bytes = decoder.decodeBuffer(base64String);      
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);      
            BufferedImage bi =ImageIO.read(bais);  
            File file = new File("C:/Users/limingwang/Desktop/test1.png");//可以是jpg,png,gif格式      
            logger.info("处理图片开始。。。");  
            ImageIO.write(bi, "png", file);//不管输出什么格式图片，此处不需改动      
            logger.info("处理图片结束。。。");  
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
    }      
}  