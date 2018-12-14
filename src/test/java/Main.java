/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.MatrixType;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.mult.MatrixMatrixMult_DDRM;
//import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.simple.SimpleMatrix;
import org.gitia.froog.statistics.Clock;

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class Main {

    public static void main(String[] args) {
        //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");
        Clock c = new Clock();
        Random r = new Random();
        
        int dim = 100;
        int aDim = 500;
        int bDim = 784;
        
        SimpleMatrix A = SimpleMatrix.random_DDRM(aDim, dim, -200, 200, r);
        SimpleMatrix B = SimpleMatrix.random_DDRM(dim, bDim, -200, 200, r);
        SimpleMatrix C = new SimpleMatrix(aDim, bDim, MatrixType.DDRM);
        
        A = SimpleMatrix.random_DDRM(aDim, dim, -200, 200, r);
        B = SimpleMatrix.random_DDRM(dim, bDim, -200, 200, r);
        C = new SimpleMatrix(aDim, bDim, MatrixType.DDRM);

        c.start();
        Parallel.mult_reorder(A.getDDRM(), B.getDDRM(), C.getDDRM());
        c.stop();
        c.printTime("Parallel");
        
        A = SimpleMatrix.random_DDRM(aDim, dim, -200, 200, r);
        B = SimpleMatrix.random_DDRM(dim, bDim, -200, 200, r);
        C = new SimpleMatrix(aDim, bDim, MatrixType.DDRM);

        c.start();
        MatrixMatrixMult_DDRM.mult_reorder(A.getDDRM(), B.getDDRM(), C.getDDRM());
        //CommonOps_DDRM.mult(A.getDDRM(), B.getDDRM(), C.getDDRM());
        c.stop();
        c.printTime("EJML");

        
    }
}
