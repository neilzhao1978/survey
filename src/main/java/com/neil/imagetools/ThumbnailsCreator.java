package com.neil.imagetools;

import java.io.IOException;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailsCreator {
	private static int w=80;
	private static int h=54;
	public static void geneThumbnails(String srcUrl, String filePlace,String httpPlace,String outUrl){
		try {
			String pureFileName = UUID.randomUUID().toString().replaceAll("-", "")+".png";
			String fileName = filePlace+pureFileName;
			Thumbnails.of(srcUrl).
			size(w, h).outputFormat("png").
			toFile(fileName);
			
			outUrl = httpPlace+pureFileName;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
