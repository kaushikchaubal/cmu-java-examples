public class Circle extends Shape {
	private double radius;

	public Circle(double newRadius) {
		super(3.1415926*newRadius*newRadius);
		radius = newRadius;
	}

	public double getRadius() { return radius; }

	public String toString() {
		return "Circle(radius="+radius+")";
	}
}
