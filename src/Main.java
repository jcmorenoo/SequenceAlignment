import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Scanner;

public class Main {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String first;
		String second;
		if(args.length == 2){
			first = args[0];
			second = args[1];
			System.out.println("First: " + first+ "\n" + "Second: " + second);
			Alignment a = new Alignment(first,second);
			long startTime = System.currentTimeMillis();
			a.optAlign();
			long timeTaken = System.currentTimeMillis()-startTime;
			System.out.println("Time Taken: " + timeTaken  + "ms");
		}
		else if(args.length == 1){
			System.out.println("Opening Test Data " + args[0]);
			String filename = args[0];
			String path = Main.class.getResource("").getPath()+"/tests/";
			
			
			String pathToFile = path+filename;
			try {
				FileReader file = new FileReader(pathToFile);
				BufferedReader in = new BufferedReader(file);
				Scanner scan = new Scanner(in);
				first = scan.next();
				second = scan.next();
				scan.close();
				System.out.println("First: " + first+ "\n" + "Second: " + second);
				Alignment a = new Alignment(first,second);
				long startTime = System.currentTimeMillis();
				a.optAlign();
				long timeTaken = System.currentTimeMillis()-startTime;
				System.out.println("Time Taken: " + timeTaken  + "ms");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			
			System.out.println("Running default test data");
			
			String filename = "default";
			String path = Main.class.getResource("").getPath()+"/tests/";
			
			
			String pathToFile = path+filename;
			try {
				FileReader file = new FileReader(pathToFile);
				BufferedReader in = new BufferedReader(file);
				Scanner scan = new Scanner(in);
				first = scan.next();
				second = scan.next();
				scan.close();
				System.out.println("First: " + first+ "\n" + "Second: " + second);
				Alignment a = new Alignment(first,second);
				long startTime = System.currentTimeMillis();
				a.optAlign();
				long timeTaken = System.currentTimeMillis()-startTime;
				System.out.println("Time Taken: " + timeTaken  + "ms");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
