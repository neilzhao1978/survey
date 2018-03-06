package com.neil.imagetools;

import java.io.IOException;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailsCreator {
	private static int w=320;
	private static int h=216;
	public static void geneThumbnails(String srcUrl, String filePlace,String httpPlace,
			StringBuilder outUrl){
		try {
			outUrl.delete(0, outUrl.length());
			String pureFileName = UUID.randomUUID().toString().replaceAll("-", "")+".png";
			String fileName = filePlace+pureFileName;
			Thumbnails.of(srcUrl).
			size(w, h).outputFormat("png").
			toFile(fileName);
			
			outUrl.append(httpPlace).append(pureFileName);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
