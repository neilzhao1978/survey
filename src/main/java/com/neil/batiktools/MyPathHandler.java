package com.neil.batiktools;

import java.awt.geom.Point2D;
import java.util.List;

import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPathHandler implements PathHandler{
	private static final Logger logger = LoggerFactory.getLogger(MyPathHandler.class);
	private List<Point2D> allKeyPoints = null;
	
	private Point2D currentPoint = new Point2D.Float();
	
	MyPathHandler(List<Point2D> allKeyPoints){
		this.setAllKeyPoints(allKeyPoints);
	}
	
	@Override
	public void startPath() throws ParseException {
		// TODO Auto-generated method stub
		
		logger.debug("startPath");
	}

	@Override
	public void endPath() throws ParseException {
		// TODO Auto-generated method stub
		logger.debug("endPath");
	}

	@Override
	public void movetoRel(float x, float y) throws ParseException {//m	x,y	moveto	同M，但使用相对坐标
		double newX = currentPoint.getX()+x;
		double newY = currentPoint.getY()+y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	public void movetoAbs(float x, float y) throws ParseException {//M	x,y	moveto 移动到	移动虚拟画笔到指定的（x,y）坐标，仅移动不绘制
		currentPoint.setLocation(x, y);
		allKeyPoints.add(new Point2D.Double(x,y));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	public void closePath() throws ParseException {//Z	无	闭合路径	从结束点绘制一条直线到开始点，闭合路径
		// TODO Auto-generated method stub		
		logger.debug("current point is "+ currentPoint.toString());
		
	}

	@Override
	public void linetoRel(float x, float y) throws ParseException {//l	x,y	lineto	同L，但使用相对坐标
		double newX = currentPoint.getX()+x;
		double newY = currentPoint.getY()+y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	public void linetoAbs(float x, float y) throws ParseException {//L	x,y	lineto 连直线到	从当前画笔所在位置绘制一条直线到指定的（x,y）坐标
		currentPoint.setLocation(x, y);
		allKeyPoints.add(new Point2D.Double(x,y));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	public void linetoHorizontalRel(float x) throws ParseException {//h	x	horizontal lineto	同H，但使用相对坐标
		double newX = currentPoint.getX()+x;
		double newY = currentPoint.getY()+0.0d;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	public void linetoHorizontalAbs(float x) throws ParseException {//H	x	horizontal lineto 水平连线到	绘制一条水平直线到参数指定的x坐标点，y坐标为画笔的y坐标
		double newX = x;
		double newY = currentPoint.getY()+0.0d;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	public void linetoVerticalRel(float y) throws ParseException {//v	y	vertical lineto	同V，但使用相对坐标
		double newX = currentPoint.getX();
		double newY = currentPoint.getY()+y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	public void linetoVerticalAbs(float y) throws ParseException {// V	y	vertical lineto 垂直连线到	从当前位置绘制一条垂直直线到参数指定的y坐标
		double newX = currentPoint.getX();
		double newY = y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	//c	x1,y1 x2,y2 x,y	curveto	同C，但使用相对坐标
	public void curvetoCubicRel(float x1, float y1, float x2, float y2, float x, float y) throws ParseException {
		double newX = currentPoint.getX()+x;
		double newY = currentPoint.getY()+y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	
	@Override
	//C	x1,y1 x2,y2 x,y	curveto 三次方贝塞尔曲线	从当前画笔位置绘制一条三次贝兹曲线到参数（x,y）指定的坐标。x1，y1和x2,y2是曲线的开始和结束控制点，用于控制曲线的弧度
	public void curvetoCubicAbs(float x1, float y1, float x2, float y2, float x, float y) throws ParseException {
		double newX = x;
		double newY = y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	//s	x2,y2 x,y	shorthand / 平滑三次方贝塞尔曲线	同S，但使用相对坐标
	public void curvetoCubicSmoothRel(float x2, float y2, float x, float y) throws ParseException {
		double newX = currentPoint.getX()+x;
		double newY = currentPoint.getY()+y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	//S	x2,y2 x,y	shorthand / 平滑三次方贝塞尔曲线	从当前画笔位置绘制一条三次贝塞尔曲线到参数（x,y）指定的坐标。x2,y2是结束控制点。开始控制点和前一条曲线的结束控制点相同
	public void curvetoCubicSmoothAbs(float x2, float y2, float x, float y) throws ParseException {
		double newX = x;
		double newY = y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	//q	x1,y1 x,y	二次方贝塞尔曲线	同Q，但使用相对坐标
	public void curvetoQuadraticRel(float x1, float y1, float x, float y) throws ParseException {
		double newX = currentPoint.getX()+x;
		double newY = currentPoint.getY()+y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	//Q	x1,y1 x,y	二次方贝塞尔曲线	从当前画笔位置绘制一条二次方贝塞尔曲线到参数（x,y）指定的坐标。x1,y1是控制点，用于控制曲线的弧度
	public void curvetoQuadraticAbs(float x1, float y1, float x, float y) throws ParseException {
		double newX = x;
		double newY = y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	//t	x,y	平滑的二次贝塞尔曲线	同T，但使用相对坐标
	public void curvetoQuadraticSmoothRel(float x, float y) throws ParseException {
		double newX = currentPoint.getX()+x;
		double newY = currentPoint.getY()+y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	//T	x,y	平滑的二次贝塞尔曲线	从当前画笔位置绘制一条二次贝塞尔曲线到参数（x,y）指定的坐标。控制点被假定为最后一次使用的控制点
	public void curvetoQuadraticSmoothAbs(float x, float y) throws ParseException {
		double newX = x;
		double newY = y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	//a	rx,ry x-axis-rotation large-arc-flag,sweepflag x,y	椭圆弧线	同A，但使用相对坐标
	public void arcRel(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x,
			float y) throws ParseException {
		double newX = currentPoint.getX()+x;
		double newY = currentPoint.getY()+y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	@Override
	//A	rx,ry x-axis-rotation large-arc-flag,sweepflag x,y	椭圆弧线	
	//从当前画笔位置开始绘制一条椭圆弧线到（x,y）指定的坐标。rx和ry分别为椭圆弧线水平和垂直方向上的半径。
	//x-axis-rotation指定弧线绕x轴旋转的度数。它只在rx和ry的值不相同是有效果。large-arc-flag是大弧标志位，取值0或1，用于决定绘制大弧还是小弧。sweep-flag用于决定弧线绘制的方向
	public void arcAbs(float rx, float ry, float xAxisRotation, boolean largeArcFlag, boolean sweepFlag, float x,
			float y) throws ParseException {
		double newX = x;
		double newY = y;
		currentPoint.setLocation(newX, newY);
		allKeyPoints.add(new Point2D.Double(newX, newY));
		logger.debug("current point is "+ currentPoint.toString());
	}

	public List<Point2D> getAllKeyPoints() {
		return allKeyPoints;
	}

	public void setAllKeyPoints(List<Point2D> allKeyPoints) {
		this.allKeyPoints = allKeyPoints;
	}

}
