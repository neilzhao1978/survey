package com.neil.survey.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.batik.transcoder.TranscoderException;

import com.neil.survey.inputout.ImageReplaceParam;
import com.neil.survey.module.Image;
import com.neil.survey.module.ProfileCombine;

public interface IImageProcessService {
	byte[] getCombinedImage(ProfileCombine profileCombine) throws MalformedURLException, IOException, TranscoderException;
	List<Image> getCartoonBaseImage(List<String> imageIds);
	List<Image> getCartoonReplaceImage(String imageId, String partName);
	List<Image> getCartoonBaseImage(String imageId, List<String> partName);
	Image getCartoonBaseImage(String imageId);
}
