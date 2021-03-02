package nl.michielgraat.adventofcode2019;

public class Day4 {
	
	private boolean isIncreasing(int number) {
		int digit6 = number % 10;
		number /= 10;
		int digit5 = number % 10;
		number /= 10;
		int digit4 = number % 10;
		number /= 10;
		int digit3 = number % 10;
		number /= 10;
		int digit2 = number % 10;
		number /= 10;
		int digit1 = number % 10;
		
		return digit1 <= digit2 && digit2 <= digit3 && digit3 <= digit4 && digit4 <= digit5 && digit5 <= digit6;
	}
	
	private boolean hasDouble(int number) {
		int digit6 = number % 10;
		number /= 10;
		int digit5 = number % 10;
		number /= 10;
		int digit4 = number % 10;
		number /= 10;
		int digit3 = number % 10;
		number /= 10;
		int digit2 = number % 10;
		number /= 10;
		int digit1 = number % 10;
		
		return digit1 == digit2 || digit2 == digit3 || digit3 == digit4 || digit4 == digit5 || digit5 == digit6;
	}
	
	private boolean hasDistinctDouble(int number) {
		int digit6 = number % 10;
		number /= 10;
		int digit5 = number % 10;
		number /= 10;
		int digit4 = number % 10;
		number /= 10;
		int digit3 = number % 10;
		number /= 10;
		int digit2 = number % 10;
		number /= 10;
		int digit1 = number % 10;
		return (digit1 == digit2 && digit1 != digit3) ||
				(digit2 == digit3 && digit2 != digit4 && digit2 != digit1) ||
				(digit3 == digit4 && digit3 != digit5 && digit3 != digit2) ||
				(digit4 == digit5 && digit4 != digit6 && digit4 != digit3) ||
				(digit5 == digit6 && digit5 != digit4);
	}
	
	public void run1() {
		int total = 0;
		for (int i=356261; i <= 846303; i++) {
			if (isIncreasing(i) && hasDouble(i)) {
				total++;
			}
		}
		System.out.println("Number of passwords: " + total);
	}
	
	private boolean isValid(int i) {
		return isIncreasing(i) && hasDistinctDouble(i);
	}
	
	public void run2() {
		int total = 0;
		for (int i=356261; i <= 846303; i++) {
			if (isValid(i)) {
				//System.out.println("Found valid number: " + i);
				total++;
			}
		}
		System.out.println("Valid: " + isValid(111122)); 
		System.out.println("Number of passwords: " + total);
		//System.out.println("Has distinct double: " + hasDistinctDouble(356667));
	}

	public static void main(String[] args) {
		new Day4().run1();
		new Day4().run2();
	}

}
