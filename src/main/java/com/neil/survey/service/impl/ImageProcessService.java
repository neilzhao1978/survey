package com.neil.survey.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import com.neil.batiktools.SvgUtilities;
import com.neil.survey.controller.SurveyController;
import com.neil.survey.inputout.ImageReplaceParam;
import com.neil.survey.module.Image;
import com.neil.survey.module.ProfileCombine;
import com.neil.survey.repository.ImageRepository;
import com.neil.survey.service.IImageProcessService;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import  org.apache.batik.transcoder.image.*;
@Service
@Transactional
public class ImageProcessService implements IImageProcessService {
	@Autowired
	private ImageRepository imageRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(ImageProcessService.class);
	
	@Override
	public byte[] getCombinedImage(ProfileCombine profileCombine) throws IOException, TranscoderException {
		
		List<Integer> ids = profileCombine.oraginalImages;
		if(ids.size()==0) return null;
		
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		
		List<SVGDocument> docs= new ArrayList<SVGDocument>();

		for(int i=0;i<ids.size();i++){//get all docs first.
			try{
				List<Image> images = imageRepo.findByImageId(ids.get(i).toString());
				if(images.size()==0) continue;
				Image image = images.get(0);
				String profileUrl = image.getProfileImageUrl();
				URL url = new URL(profileUrl);
				docs.add((SVGDocument) f.createSVGDocument(profileUrl,
						new BufferedInputStream(url.openStream(), 2 * 1024 * 1024)));
			}catch(Exception e){
				continue;
			}
		}
		
		SVGDocument docBase = docs.get(0);
		for(int i=1;i<docs.size();i++){
			SVGDocument temp = docs.get(i);

			Element imageEle = (Element)temp.getElementsByTagName("image").item(0);
			
			Element refEle = (Element)docBase.getElementsByTagName("image").item(0);
			
//			logger.info(SvgUtilities.ele2FormatString(imageEle));
			
			Element x = (Element) docBase.importNode(imageEle, true);
			
			docBase.getElementById("特征线").insertBefore(x, refEle);
//			docBase.getElementsByTagName("svg").item(0).appendChild(x);
		}
		
//		SvgUtilities.saveDoc2SvgFile(docBase,"./test.svg");

		PNGTranscoder pngTranscoder = new PNGTranscoder();


        // Set the transcoder input and output.
        TranscoderInput input = new TranscoderInput(docBase);
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(ostream);

        // Perform the transcoding.
        pngTranscoder.transcode(input, output);
        ostream.flush();
        ostream.close();
        
		
		// TODO Auto-generated method stub
		return ostream.toByteArray();
	}

	@Override
	public List<Image> getCartoonBaseImage(String imageId,List<String> partNames) {
		List<Image> rtn =imageRepo.findByImageId(imageId);
		if( partNames.size()>0){
			for(String partNm:partNames){
				List<Image> temp = imageRepo.findByImageTypeAndParentImageIdAndImageNameLike("PART",imageId,partNm);
				rtn.addAll(temp);	
			}

		}
		return rtn;
	}

	@Override
	public List<Image> getCartoonReplaceImage(String imageId,String partName) {
		List<Image> rtn = imageRepo.findByImageTypeAndParentImageIdAndImageNameLike("PART",imageId,partName);
		return rtn;	
	}

	@Override
	public List<Image> getCartoonBaseImage(List<String> imageIds) {
		return imageRepo.findByImageIdIn(imageIds);
	}

}
