import com.github.xt449.pseudorandom.Random;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		Random random;
		int[] counts;

		for(int n = 0; n < 10; n++) {
			random = new Random();
			counts = new int[10];

			for(int i = 0; i < 1000; i++) {
				final int number = random.nextInt(10);
				counts[number]++;
			}

			System.out.println(Arrays.toString(counts));
		}
	}
}
