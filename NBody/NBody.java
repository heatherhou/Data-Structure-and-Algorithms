import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBody {
	
	public static double readRadius(String fname)
	{
		try {
			Scanner scan = new Scanner (new File(fname));
			int number = scan.nextInt();
			double radius = scan.nextDouble();
			scan.close();
			return radius;
		}catch (FileNotFoundException e) {
			//System.exit(0);
			System.out.println("No appropriate found for radius");
			return 0.0;
		}
	}
	
	public static Body[] readBodies(String fname)
	{
		try {
			Scanner scan = new Scanner (new File(fname));
			int number = scan.nextInt();
			Body[] universe = new Body[number];
		
			scan.nextDouble(); //read the radius 
			//Read information from file and assign
			for (int i = 0; i< universe.length; i++)
			{
				universe[i] = new Body();
				universe[i].myXPos = scan.nextDouble();
				universe[i].myYPos = scan.nextDouble();
				universe[i].myXVel = scan.nextDouble();
				universe[i].myYVel = scan.nextDouble();
				universe[i].myMass = scan.nextDouble();
				universe[i].myFileName = scan.next();
			}
			scan.close();
			return universe;
		}catch (FileNotFoundException e) {
			System.out.println("No appropriate found for radius");
			return null;
		}	
	}

	public static void main(String[] args) {
		double totalTime = 157788000.0;
		double dt = 25000.0;
		String pfile = "data/planets.txt";
		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			pfile = args[2];
		}

		// Read simulation data from file
		Body[] planets = readBodies(pfile); // readBodies(pfile);
		double radius = readRadius(pfile); // readRadius(pfile);
		
		// TODO Draw the background
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for (int i= 0; i< planets.length;i++)
		{
			planets[i].draw();
		}
		
//		// TODO Animate the simulation from time 0 until totalTime
	
		double[] xForces = new double[planets.length];
		double[] yForces = new double[planets.length];
		for (double time = 0 ; time < totalTime; time += dt )
		{
			
			//Calculate the net force for each planet
			for (int j = 0; j < planets.length; j++) 
			{
				xForces[j] = planets[j].calcNetForceExertedByX(planets);
				yForces[j] = planets[j].calcNetForceExertedByY(planets);
			}
			
			//update the information for each planet
			for (int m = 0; m < planets.length; m++) 
			{
				planets[m].update(dt, xForces[m], yForces[m]);
			}
			
			//Re-draw the picture 
			StdDraw.setScale(-radius, radius);
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (int i= 0; i< planets.length;i++)
			{
				planets[i].draw();
			}
			
			StdDraw.show(10);
				
		}
		// Print final positions of planets
		System.out.printf("%d\n", planets.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
			System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", 
							  planets[i].myXPos, planets[i].myYPos,
							  planets[i].myXVel, planets[i].myYVel, 
							  planets[i].myMass, planets[i].myFileName);
		}
	}
}
