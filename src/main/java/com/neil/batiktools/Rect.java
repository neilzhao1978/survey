package com.neil.batiktools;

public class Rect {
	// 属性
	double width;
	double height;

	// 有参的构造方法
	public Rect(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public Rect() {
		this.width = 10;
		this.height = 10;
	}

	// 自定义成员方法 面积
	public double area() {
		double area;
		area = width * height;
		return area;
	}

	// 周长
	public double perimeter() {
		double perimeter;
		perimeter = (width + height) * 2;
		return perimeter;
	}

	// 通过属性生成的get，set成员方法
	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

}