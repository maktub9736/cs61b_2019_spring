public class NBody {
    public static double readRadius(String filename) {
        In in = new In(filename);
        in.readInt();  // skip the first data
        double radius = in.readDouble();
        return radius;
    }

    public static Body[] readBodies(String filename) {
        In in = new In(filename);
        int N = in.readInt();
        in.readDouble();
        int i = 0;
        Body[] bodies = new Body[N];
        // Actually, we are given the number of planets in data.
        // So the size of the array is set. This is the hardest part of the program.
        while (i < N) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            bodies[i] = new Body(xP, yP, xV, yV, m, img);
            i += 1;
        }
        return bodies;
    }

    public static void main(String[] args) {
        /** Store the 0th and 1st command line arguments as doubles named T and dt. */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        /** Store the 2nd command line argument as a String named filename. */
        String filename = args[2];

        /** Read in the bodies and the universe radius from the file described by filename. */
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);

        /** Drawing
         * First, set the scale so that it matches the radius of the universe.
         * */

        StdDraw.setScale(-radius, radius);
        StdDraw.clear();

        /** Take Home Lesson:
         * 
         * int r = (int) Math.round(radius);
         * System.out.println(radius); // 2.5E11
         * System.out.println(r);      // 891896832
         * System.out.print(radius == r); // false
         *
         * Java cannot deal with integers whose absolute value are larger than 2147483647.
         * Hence rounded value is way different than the original (double) one.
         * */

        /** Load the background picture. */
        StdDraw.picture(0,0,"images/starfield.jpg");
//        StdDraw.show();
//        StdDraw.pause(2000);

        /** Add bodies. */
        for (Body b: bodies) {
            b.draw();
        }

        /** Animation */
        StdDraw.enableDoubleBuffering(); // Prevent flickering in the animation;
        for (double t = 0; t <= T; t = t + dt) {
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];

            // calculate net forces for each body and store them separately in arrays (xForces and yForces)
            for (int i = 0; i < bodies.length; i++) {
                Body b = bodies[i];
                xForces[i] = b.calcNetForceExertedByX(bodies);
                yForces[i] = b.calcNetForceExertedByY(bodies);
            }

            // update bodies
            for (int i = 0; i < bodies.length; i++) {
                Body b = bodies[i];
                double Fx = xForces[i];
                double Fy = yForces[i];
                b.update(dt, Fx, Fy);
            }
            /** Draw background picture. */
            StdDraw.picture(0,0,"images/starfield.jpg");

            /** Add bodies. */
            for (Body b: bodies) {
                b.draw();
            }

            /** Show the offscreen buffer. */
            StdDraw.show();

            /** Pause the animation for 10 milliseconds. */
            StdDraw.pause(10);
        }
   }
}

