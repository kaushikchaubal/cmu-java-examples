public class Rectangle extends Shape {
	private double width;
	private double length;

	public Rectangle(double newWidth, double newLength) {
		super(newWidth * newLength);
		width  = newWidth;
		length = newLength;
	}

	public double getLength() { return length; }
	public double getWidth()  { return width;  }

	public String toString() {
		return "Rectangle(width="+width+",length="+length+")";
	}
}
