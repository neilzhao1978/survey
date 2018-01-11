package com.neil.batiktools;

import java.awt.geom.AffineTransform;

import org.apache.batik.parser.AWTTransformProducer;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.TransformListParser;

public class TransformParserExample {

    public AffineTransform parseTransformList(String s) throws ParseException {
        TransformListParser p = new TransformListParser();
        AWTTransformProducer tp = new AWTTransformProducer();
        p.setTransformListHandler(tp);
        p.parse(s);
        return tp.getAffineTransform();
    }
}
