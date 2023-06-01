import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.factory.LinearSolverFactory_DDRM;
import org.ejml.dense.row.linsol.AdjustableLinearSolver_DDRM;

import java.util.Arrays;

public class PolynomialFit {
    // Polynomial Degree
    int degreePoly;

    // Vandermonde Matrix of x-vals
    DMatrixRMaj A;

    // polynomial coefficients
    DMatrixRMaj coef;

    // observation of y-vals
    DMatrixRMaj y;

    // Linear System Solver
    AdjustableLinearSolver_DDRM solver;


    public PolynomialFit(int degree) {
        degreePoly = degree;
        A = new DMatrixRMaj(1, degreePoly + 1);
        coef = new DMatrixRMaj(degreePoly + 1, 1);
        y = new DMatrixRMaj(1, 1);
        solver = LinearSolverFactory_DDRM.adjustable();
    }

    public void fit (double[] xPoints, double[] yObs) {
        // create a copy of y-vals
        y.reshape(yObs.length, 1, false);

        // copies data from yObs to y.data from array positions 0 to yObs.length-1
        System.arraycopy(yObs, 0, y.data, 0, yObs.length);

        // reshape matrix A based on number of points inputted from yObs
        A.reshape(y.numRows, coef.numRows, false);

        // construct Vandermonde Matrix of x-vals
        for (int i = 0; i < yObs.length; i++) {
            double obs = 1;
            for (int j = 0; j < coef.numRows; j++) {
                A.set(i, j, obs);
                obs *= xPoints[i];
            }
        }

        if (!solver.setA(A))
            throw new RuntimeException("Solver Failed");
        
        // solves for coefficients
        solver.solve(y, coef);
    }

    // returns coefficeints
    public double[] getCoef() {
        return coef.data;
    }

    // checks if the coefficients have been computed
    public boolean coefComputed() {
        double max = Arrays.stream(coef.data).max().getAsDouble();
        if (max > 0.0) 
            return true;
        
        else 
            return false;
        
    }

    // computes the polynomial at the specified x-value 
    // remember to check if coefficients have been computed before using this method
    public double compute(double x) {
            double yVal = coef.data[0];
            double xVal = 1;
            for (int i = 1; i < degreePoly+1; i++) {
                xVal *= x;
                yVal += coef.data[i] * xVal;
            }
            return yVal;
    }

}
