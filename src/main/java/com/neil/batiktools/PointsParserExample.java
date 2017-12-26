package com.neil.batiktools;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import org.apache.batik.parser.DefaultPointsHandler;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PointsHandler;
import org.apache.batik.parser.PointsParser;

public class PointsParserExample {

    public List extractPoints(String s) throws ParseException {
        final LinkedList points = new LinkedList();
        PointsParser pp = new PointsParser();
        PointsHandler ph = new DefaultPointsHandler() {
            public void point(float x, float y) throws ParseException {
                Point2D p = new Point2D.Float(x, y);
                points.add(p);
            }
        };
        pp.setPointsHandler(ph);
        pp.parse(s);
        return points;
    }
}