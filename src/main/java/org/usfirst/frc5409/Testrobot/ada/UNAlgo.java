package org.usfirst.frc5409.Testrobot.ada;
//https://www.geogebra.org/geometry/mj6c8auz
import org.usfirst.frc5409.Testrobot.util.Vector2;

public class UNAlgo {
    public double  kD;
    public double  kR;
    public double  db;
    public double  mo;
    private double fl;

    private boolean is_first_iter;

    public UNAlgo(double kD, double kR, double db, double mo) {
        this.kD = kD;
        this.kR = kR;
        this.db = db;
        this.mo = mo;

        is_first_iter = true;
    }

    public void reset() {
        is_first_iter = true;
    }

    public double[] compute(Vector2 T, Vector2 nT, Vector2 O, Vector2 nO) {
        Vector2 Gp; //Gets initialized later      
        Vector2 nG = new Vector2();
        double  dg;

        if (is_first_iter) {
            is_first_iter = false;
            fl = sgn((O.y-T.y)*nT.x - (O.x-T.x)*nT.y);
        }

        Vector2 Pi = computePi(T, nT, O, nO);

        //Determine which optimization to use
        if (Pi == null) {
            Gp = computePo(T, O, nT);
            dg = computeD(T, O, Gp);
        } else {
            final double dot = (Pi.x-O.x)*nO.x + (Pi.y-O.y)*nO.y;
            if (dot < 0) {
                Gp = computePo(T, O, nT);
                dg = computeD(T, O, Gp);
            } else {
                Vector2 Po = computePo(T, O, nT);

                double d_i = computeD(T, O, Pi);
                double d_o = computeD(T, O, Po);

                if (d_i < d_o) { //Whichever one has shorter distances
                    Gp = Pi;
                    dg = d_i;
                } else {
                    Gp = Po;
                    dg = d_o;
                }
            }
        }

        nG.x = O.x-Gp.x;
        nG.y = O.y-Gp.y;
        
        //normalize
        final double m = Math.sqrt(nG.x*nG.x + nG.y*nG.y);
        nG.x /= m;
        nG.y /= m;

        //Calculate motor output modifiers
        final double mr = clamp(Math.acos(clamp(nO.x*nG.x + nO.y*nG.y)) * kR * fl);
        final double md = clamp(deadband(dg/2 * kD));

        //Calculate raw motor outputs
        final double Rr = md + mr;
        final double Rl = md - mr;

        double s = 1;
        if (Rr > mo || Rr < -mo) 
            s = Math.abs(Rr);
        else if (Rl > mo || Rl < -mo)
            s = Math.abs(Rl);

        return new double[] {Rl/s, Rr/s};
    }

    public double computeD(Vector2 A, Vector2 B, Vector2 C) {
        return Math.sqrt((A.x-C.x)*(A.x-C.x) + (A.y-C.y)*(A.y-C.y))
                + Math.sqrt((B.x-C.x)*(B.x-C.x) + (B.y-C.y)*(B.y-C.y));
    }       

    private Vector2 computePo(Vector2 T, Vector2 O, Vector2 nT) {
        final double px = T.x - O.x;
        final double py = T.y - O.y;

        final double oi = (px*px + py*py) / (-2*(px*nT.x + py*nT.y));
        return new Vector2(T.x + nT.x*oi, T.y + nT.y*oi);
    }

    private Vector2 computePi(Vector2 T, Vector2 nT, Vector2 O, Vector2 nO) {
        final double c1 = nT.y*T.x - nT.x*T.y;
        final double c2 = nO.y*O.x - nO.x*O.y;
        final double w  = nT.y*nO.x - nT.x*nO.y;

        if (w == 0)
            return null;

        return new Vector2((-nT.x*c2 + nO.x*c1)/w, (nO.y*c1 - nT.y*c2)/w);
    }

    private double deadband(double x) {
        if (x > db && x < -db)
            return x*Math.signum(x);
        return x;
    }

    private double clamp(double x) {
        if (x > mo)
            return mo;
        else if (x < -mo)
            return -mo;
        return x;
    }

    private double sgn(double x) {
        if (x > 0)
            return 1;
        else if (x < 0)
            return -1;
        return 0;
    }
}