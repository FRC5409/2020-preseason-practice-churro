package org.usfirst.frc5409.Testrobot.ada;
//https://www.geogebra.org/geometry/mj6c8auz
import org.usfirst.frc5409.Testrobot.util.Vector2;

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
    }

    public void reset() {
        is_first_iter = true;
    }

    public double[] compute(Vector2 org, Vector2 n1, Vector2 targ, Vector2 n2) {
        if (is_first_iter) {
            final Vector2 n3 = 
            
            is_first_iter = false;

            
            fl = Math.signum(d)
        }
    }

    private double deadband(double value) {
        if (value > db && value < -db)
            return value*Math.signum(value);
        return value;
    }
}