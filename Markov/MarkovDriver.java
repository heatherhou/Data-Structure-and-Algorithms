import java.io.File;
import java.util.Scanner;
import java.util.*;

/**
 * Driver for Markov Model classes
 * @author ola
 *
 */

public class MarkovDriver {
	public static void main(String[] args) {
		//String filename = "data/trump-un-sept19-17.txt";
		String filename = "data/testfile.txt";
//		if (args.length > 0) {
//			filename = args[1];
//		}
		File f = new File(filename);
		String text = TextSource.textFromFile(f);
	
		double start = System.nanoTime();
		int textLength = 0;
				
		for(int k=1; k <= 10; k++) {
			MarkovInterface<String> markov = new EfficientMarkov(5); 
			markov.setTraining(text);
			String random = markov.getRandomText(5000);
			textLength += random.length();
			System.out.printf("%d markov model with %d chars\n", 5, random.length());
			printNicely(random,60);
		}
		double end = System.nanoTime();
		System.out.printf("total time = %2.3f\n", (end-start)/1e9);
		System.out.println("average length is "+ textLength/10);
	}

	private static void printNicely(String random, int screenWidth) {
		String[] words = random.split("\\s+");
		int psize = 0;
		System.out.println("----------------------------------");
		for(int k=0; k < words.length; k++){
			System.out.print(words[k]+ " ");
			psize += words[k].length() + 1;
			if (psize > screenWidth) {
				System.out.println();
				psize = 0;
			}
		}
		System.out.println("\n----------------------------------");
	}
}
