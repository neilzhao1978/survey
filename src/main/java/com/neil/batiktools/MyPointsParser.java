package com.neil.batiktools;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import org.apache.batik.parser.DefaultPointsHandler;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PointsHandler;
import org.apache.batik.parser.PointsParser;

public class MyPointsParser {

    public List<Point2D> extractPoints(String s) throws ParseException {
        final LinkedList<Point2D> points = new LinkedList<Point2D>();
        PointsParser pp = new PointsParser();
        PointsHandler ph = new DefaultPointsHandler() {
            public void point(float x, float y) throws ParseException {
            	
            	float nx=(float) SvgUtilities.scale(2,x);
            	float ny=(float) SvgUtilities.scale(2,y);
                Point2D p = new Point2D.Float(nx, ny);
                points.add(p);
            }
        };
        pp.setPointsHandler(ph);
        pp.parse(s);
        return points;
    }
}