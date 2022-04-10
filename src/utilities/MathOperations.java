package utilities;

public class MathOperations {

	// calculates the dx and dy according to speed and angle
	public static int[] speedCalculator(double speed, double rad) {
		int tempArray[] = new int[2];
		tempArray[0] = (int) (Math.sin(rad) * speed);
		tempArray[1] = (int) (Math.cos(rad) * speed);
		return tempArray;
	}

}
