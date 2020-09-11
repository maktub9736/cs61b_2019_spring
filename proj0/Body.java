
import javax.print.attribute.standard.Sides;
import java.lang.Math;

class Gfg {

    // driver code
    public static void main(String args[])
    {
        double a = 30;

        System.out.println(Math.sqrt(a));

        a = 45;

        System.out.println(Math.sqrt(a));

        a = 60;

        System.out.println(Math.sqrt(a));

        a = 90;

        System.out.println(Math.sqrt(a));
    }
}

public class Body {

    /**
     * Its current x position
     */
    public double xxPos;

    /**
     * Its current y position
     */
    public double yyPos;

    /**
     * Its current velocity in the x direction
     */
    public double xxVel;

    /**
     * Its current velocity in the y direction
     */
    public double yyVel;

    /**
     * Its mass
     */
    public double mass;

    /**
     * The name of the file that corresponds to the image that depicts the body (for example, jupiter.gif)
     */
    public String imgFileName;

    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    /**
     * Calculate the distance between two Bodys
     */
    public double calcDistance(Body b) {
        double dx = b.xxPos - xxPos;
        double dy = b.yyPos - yyPos;
        double r = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return r;
    }

    /**
     * Calculate the force exerted on this body by the given body
     */
    public double calcForceExertedBy(Body b) {
        double r = calcDistance(b);
        double G = 6.67 * Math.pow(10, -11);
        double F = (G * mass * b.mass) / Math.pow(r, 2);
        return F;
    }

    /**
     * Calculate the force in x direction and y direction
     */
    public double calcForceExertedByX(Body b) {
        double dx = b.xxPos - xxPos;
        double F = calcForceExertedBy(b);
        double r = calcDistance(b);
        double F1 = F * dx / r;
        return F1;
    }

    public double calcForceExertedByY(Body b) {
        double dy = b.yyPos - yyPos;
        double F = calcForceExertedBy(b);
        double r = calcDistance(b);
        double F2 = F * dy / r;
        return F2;
    }

    /**
     * Calcualte the net force in x direction and y direction
     */
    // Notice: cannot measure Force between a body and itself because distance is zero and would appear in the denominator.
    public double calcNetForceExertedByX(Body[] group) {
        double NetForceX = 0;
        for (int i = 0; i < group.length; i++) {
            if (!this.equals(group[i])) {
                NetForceX += calcForceExertedByX(group[i]);
            }
        }
        return NetForceX;
    }


    public double calcNetForceExertedByY(Body[] group) {
        double NetForceY = 0;
        for (int i = 0; i < group.length; i++) {
            if (!this.equals(group[i])) {
                NetForceY += calcForceExertedByY(group[i]);
            }
        }
        return NetForceY;
    }

    /**
     * Update the velocity and position of the Body under the effect of force
     */
    public void update(double dt, double fX, double fY) {
        double ax = fX / mass;
        double ay = fY / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    /**
     * Draw the picture of the Body according to its position
     */
    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}