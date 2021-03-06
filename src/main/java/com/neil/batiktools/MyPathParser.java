package com.neil.batiktools;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathHandler;
import org.apache.batik.parser.PathParser;

public class MyPathParser {
    public List<Point2D> extractPoints(String s) throws ParseException {
        final LinkedList<Point2D> points = new LinkedList<Point2D>();
        final LinkedList<Point2D> rtn = new LinkedList<Point2D>();
        PathParser pp = new PathParser();
        PathHandler ph = new MyPathHandler(points);
        pp.setPathHandler(ph);
        pp.parse(s);
        for(Point2D p:points){
        	Point2D temp = new Point2D.Double(SvgUtilities.scale(2,p.getX()), SvgUtilities.scale(2,p.getY()));
        	rtn.add(temp);
        }
        
        return rtn;
    }
}
