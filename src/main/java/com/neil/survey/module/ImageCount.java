package com.neil.survey.module;

public class ImageCount implements Comparable<ImageCount> {

	private Image i;
	private int count = 0;

	public void increaseCount() {
		count++;
	}
	@Override
	public int compareTo(ImageCount o) {
		if (count < o.count) 
			return 1;
		if (count > o.count)
			return -1;
		return 0;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Image getI() {
		return i;
	}

	public void setI(Image i) {
		this.i = i;
	}

}