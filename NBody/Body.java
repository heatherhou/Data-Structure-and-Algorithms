
public class Body {
 double myXPos; //current Position
 double myYPos; //current y Position
 double myXVel; //current Velocity in x direction
 double myYVel; //current Velocity in y direction
 double myMass; //mass of body
 String myFileName; //file name in images folder
 

 public Body(double xp, double yp, double xv, double yv, double mass, String filename) {
    myXPos = xp;
    myYPos = yp;
    myXVel = xv;
    myYVel = yv;
    myMass = mass;
    myFileName = filename;
 }
 public Body(Body p) {
	myXPos = p.myXPos;
	myYPos = p.myYPos;
    myXVel = p.myXVel;
    myYVel = p.myYVel;
    myMass = p.myMass;
    myFileName = p.myFileName;
 }
 public Body()
 {
	
 }
 public double calcDistance(Body otherPlanet)
 {
	 double distance = Math.pow((otherPlanet.myXPos-this.myXPos ),2) + Math.pow((this.myYPos - otherPlanet.myYPos),2);
	 return (Math.sqrt(distance));
 }
 
 public double calcForceExertedBy(Body otherPlanet)
 {
	 double force = 6.67 * Math.pow(10,-11) *this.myMass * otherPlanet.myMass /Math.pow(this.calcDistance(otherPlanet),2);
	 return force;
 }
 
 public double calcForceExertedByX(Body otherPlanet)
 {
	 double forceX = this.calcForceExertedBy(otherPlanet) * ( otherPlanet.myXPos - this.myXPos )/ this.calcDistance(otherPlanet);
		 return forceX;
 }
 
 public double calcForceExertedByY(Body otherPlanet)
 {
	 double forceY = this.calcForceExertedBy(otherPlanet) *(otherPlanet.myYPos - this.myYPos )/ this.calcDistance(otherPlanet);
		 return forceY;
 }
 
 public	double calcNetForceExertedByX(Body[] allPlanets)
 {
	 double force = 0;
	 for (int i = 0; i< allPlanets.length; i++)
	 {
		 if (! this.equals(allPlanets[i]))  //Exclude the own gravity force
		 {
			 force += calcForceExertedByX(allPlanets[i]);
		 }
	 }
	 return force;
 }
 
 public	double calcNetForceExertedByY(Body[] allPlanets)
 {
	 double force = 0;
	 for (int i = 0; i < allPlanets.length; i++)
	 {
		if (! this.equals(allPlanets[i]))// Exclude the own gravity force
		 {
			 force += calcForceExertedByY(allPlanets[i]);
		 }
	 }
	 return force;
 }

 public void update(double seconds, double xforce, double yforce )
 {
	 double accelX = xforce / myMass;
	 double accelY = yforce / myMass;
	 myXVel = myXVel + seconds * accelX;
	 myYVel = myYVel + seconds * accelY; 
	 myXPos += seconds * myXVel; 
	 myYPos += seconds * myYVel; 
 }
 
 public void draw()
 {
	 StdDraw.picture(myXPos, myYPos, "images/"+myFileName);
 }

}



