package com.neil.survey.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.batik.transcoder.TranscoderException;

import com.neil.survey.module.ProfileCombine;

public interface IImageProcessService {
	byte[] getCombinedImage(ProfileCombine profileCombine) throws MalformedURLException, IOException, TranscoderException;
}
