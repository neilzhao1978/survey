package com.neil.batiktools;

public class PlainRect extends Rect {

	private int startX;
	private int startY;

	// 有参的构造方法
	public PlainRect(int startX, int startY, double width, double height) {
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;
	}

	public PlainRect(double startX, double startY) {

		System.out.println("这个点的坐标是x=" + startX + "y=" + startY);
	}

	// 无参的构造方法
	public PlainRect() {
		this.startX = 0;
		this.startY = 0;
		this.width = 0;
		this.height = 0;
	}
	// 成员方法

	// 判断是否在矩形内部
	public boolean isInside(double x, double y) {
		if (x >= startX && x <= (startX + width) && y < startY && y >= (startY - height)) {
			return true;
		} else {
			return false;
		}
	}

}