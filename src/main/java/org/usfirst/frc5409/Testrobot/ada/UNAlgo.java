package org.usfirst.frc5409.Testrobot.ada;
//https://www.geogebra.org/geometry/mj6c8auz
import org.usfirst.frc5409.Testrobot.util.Vector2;
import org.usfirst.frc5409.Testrobot.util.Vector3;

public class UNAlgo {
    public double kD;
    public double kR;
    public double db;

    private boolean is_first_iter;
    private double  fl;


    public UNAlgo(double kD, double kR, double db) {
        this.kD = kD;
        this.kR = kR;
        this.db = db;

        is_first_iter = true;
    }

    public void reset() {
        is_first_iter = true;
    }

    public double[] compute(Vector2 targ, Vector2 n1, Vector2 org, Vector2 n2) {
        Vector2 C = computeGravitator(targ, n1, org, n2);
        final Vector2 n3 = C.sub(org).norm();
        if (is_first_iter) {
            is_first_iter = false;
            fl = Math.signum(n3.dot(new Vector2(-n1.y, n1.x)));
        }

        final double df = clamp(fl*Math.acos(n2.dot(n3))*kR);
        final double ddist = clamp(deadband(targ.distance(org)*kD));

        return new double[] {df, ddist};
    }
    // O - target
    // B - Origin
    public Vector2 computeGravitator(Vector2 targ, Vector2 n1, Vector2 org, Vector2 n2) {
        Vector2 opt = computeOptimize(org, n2);
        Vector2 insct = computeIntersect(org, n1, targ, n2);

        final double dot = (targ.sub(insct)).dot(n1);

        if (dot < 1)
            return opt;
        else {
            if (perimeter(targ, org, opt) < perimeter(targ, org, insct))
                return opt;
            else
                return insct;
        }
    }

    public double perimeter(Vector2 A, Vector2 B, Vector2 C) {
        return A.distance(C) + B.distance(C);
    }

    public Vector2 computeOptimize(Vector2 targ, Vector2 n) {
        final double a = targ.dot(targ);
        final double b = -2*targ.dot(n);
    
        return targ.add(n.mult(a/b));
    }

    public Vector2 computeIntersect(Vector2 targ, Vector2 n1, Vector2 org, Vector2 n2) {
        Vector3 pi = new Vector3(n1.x,n1.y, org.x-org.y).cross(
                     new Vector3(n2.x,n2.y, targ.x-targ.y));
        return new Vector2(pi.x/pi.z, pi.y/pi.z);
    }

    private double deadband(double value) {
        if (value > db && value < -db)
            return value*Math.signum(value);
        return value;
    }

    private double clamp(double value) {
        if (value > 1)
            return 1;
        else if (value < -1)
            return -1;
        return value;
    }
}