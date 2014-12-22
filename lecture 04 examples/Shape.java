public class Shape {
	private double area;

	public Shape(double newArea) {
		area = newArea;
	}

	public double getArea() { return area; }

	public String toString() {
		return "Shape(area="+area+")";
	}
}
