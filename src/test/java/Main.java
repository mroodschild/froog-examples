/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;
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
        //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "1");
        Clock c = new Clock();
        Random r;// = new Random();

        int[] dimensiones = {6};//, 20, 30, 40, 50, 70, 100, 150, 200, 300, 400, 600, 800, 1000, 1500, 2000, 2500, 3000, 3500, 4000};//, 5000, 7000, 10000, 12000};//,17000};

        SimpleMatrix A;
        SimpleMatrix B;
        SimpleMatrix C;

//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            B = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
//            c.start();
//            MatrixMatrixMult_DDRM.mult_reorder(A.getDDRM(), B.getDDRM(), C.getDDRM());
//            c.stop();
//            c.printTime("EJML dim:\t" + dim + "\t");
//            //C.print();
//        }
//
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            B = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
//            c.start();
//            Parallel.mult_reorder(A.getDDRM(), B.getDDRM(), C.getDDRM());
//            c.stop();
//            c.printTime("Par dim:\t" + dim + "\t");
//            //C.print();
//        }
//
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim * 2, -200, 200, r);
//            B = SimpleMatrix.random_DDRM(dim * 2, dim, -200, 200, r);
//            C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
//            c.start();
//            MatrixMatrixMult_DDRM.mult_small(A.getDDRM(), B.getDDRM(), C.getDDRM());
//            c.stop();
//            c.printTime("EJML_small dim:\t" + dim + "\t");
//            //C.print();
//        }
//
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim * 2, -200, 200, r);
//            B = SimpleMatrix.random_DDRM(dim * 2, dim, -200, 200, r);
//            C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
//            c.start();
//            Parallel.mult_small(A.getDDRM(), B.getDDRM(), C.getDDRM());
//            c.stop();
//            c.printTime("Par_small dim:\t" + dim + "\t");
//            //C.print();
//        }
//
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim * 2, dim, -200, 200, r);
//            B = SimpleMatrix.random_DDRM(dim * 2, dim, -200, 200, r);
//            C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
//            c.start();
//            MatrixMatrixMult_DDRM.multTransA_reorder(A.getDDRM(), B.getDDRM(), C.getDDRM());
//            c.stop();
//            c.printTime("EJML_AtB dim:\t" + dim + "\t");
//            //C.print();
//        }
//
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim * 2, dim, -200, 200, r);
//            B = SimpleMatrix.random_DDRM(dim * 2, dim, -200, 200, r);
//            C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
//            c.start();
//            Parallel.multTransA_reorder(A.getDDRM(), B.getDDRM(), C.getDDRM());
//            c.stop();
//            c.printTime("Par_AtB dim:\t" + dim + "\t");
//            //C.print();
//        }
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            B = SimpleMatrix.random_DDRM(dim, 1, -200, 200, r);
//            C = new SimpleMatrix(dim, 1, MatrixType.DDRM);
//            c.start();
//            Parallel.MatrixVectorMult_DDRM_mult(A.getDDRM(), B.getDDRM(), C.getDDRM());
//            c.stop();
//            c.printTime("MatrixVectorMult_DDRM_mult dim:\t" + dim + "\t element_sum:\t"+C.elementSum());
////            C.transpose().print();
//        }
//        
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            B = SimpleMatrix.random_DDRM(dim, 1, -200, 200, r);
//            C = new SimpleMatrix(dim, 1, MatrixType.DDRM);
//            c.start();
//            Parallel.MatrixVectorMult_DDRM_mult_orig(A.getDDRM(), B.getDDRM(), C.getDDRM());
//            c.stop();
//            c.printTime("MatrixVectorMult_DDRM_mult_orig dim:\t" + dim + "\t element_sum:\t"+C.elementSum());
////            C.transpose().print();
//        }
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
//            c.start();
//            CommonOps_DDRM_Parallel.elementExp_parallel(A.getDDRM(), C.getDDRM());
//            c.stop();
//            c.printTime("CommonOps_DDRM_elementExp_parallel dim:\t" + dim + "\t element_sum:\t" + C.elementSum());
////            C.transpose().print();
//        }
//
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
//            c.start();
//            CommonOps_DDRM_Parallel.elementExp(A.getDDRM(), C.getDDRM());
//            c.stop();
//            c.printTime("CommonOps_DDRM_elementExp dim:\t" + dim + "\t element_sum:\t" + C.elementSum());
////            C.transpose().print();
//        }
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            c.start();
//            CommonOps_DDRM_Parallel.scale_parallel(5, A.getDDRM());
//            c.stop();
//            c.printTime("CommonOps_DDRM_scale_parallel dim:\t" + dim + "\t element_sum:\t" + A.elementSum());
////            C.transpose().print();
//        }
//
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            c.start();
//            CommonOps_DDRM_Parallel.scale(5, A.getDDRM());
//            c.stop();
//            c.printTime("CommonOps_DDRM_scale dim:\t" + dim + "\t element_sum:\t" + A.elementSum());
////            C.transpose().print();
//        }
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
//            c.start();
//            CommonOps_DDRM_Parallel.elementPower(A.getDDRM(),-2,C.getDDRM());
//            c.stop();
//            c.printTime("CommonOps_DDRM_elementPower_parallel dim:\t" + dim + "\t element_sum:\t" + C.elementSum());
////            C.transpose().print();
//        }
//
//        for (int i = 0; i < dimensiones.length; i++) {
//            r = new Random(1);
//            int dim = dimensiones[i];
//            A = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//            C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
//            c.start();
//            CommonOps_DDRM.elementPower(A.getDDRM(),-2,C.getDDRM());
//            c.stop();
//            c.printTime("CommonOps_DDRM_elementPower dim:\t" + dim + "\t element_sum:\t" + C.elementSum());
////            C.transpose().print();
//        }

        for (int i = 0; i < dimensiones.length; i++) {
            r = new Random(1);
            int dim = dimensiones[i];
            A = SimpleMatrix.random_DDRM(dim, dim+1, -200, 200, r);
            B = SimpleMatrix.random_DDRM(dim+1, dim, -200, 200, r);
            C = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
            //C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
            c.start();
            //C=A.mult(B).plus(C).transpose();
            MatrixMatrixMult_DDRM_Parallel.multAdd_reorder(A.getDDRM(),B.getDDRM(),C.getDDRM());
            c.stop();
            c.printTime("CommonOps_DDRM_multAdd_reorder_parallel dim:\t" + dim + "\t element_sum:\t" + C.elementSum());
//            C.transpose().print();
C.print();
        }
        
        for (int i = 0; i < dimensiones.length; i++) {
        //for (int i = 0; i < 1; i++) {
            r = new Random(1);
            int dim = dimensiones[i];
            A = SimpleMatrix.random_DDRM(dim, dim+1, -200, 200, r);
            B = SimpleMatrix.random_DDRM(dim+1, dim, -200, 200, r);
            C = SimpleMatrix.random_DDRM(dim, dim, -200, 200, r);
//
//            A = new SimpleMatrix(4, 4, true, new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
//            B = new SimpleMatrix(4, 4, true, new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
//            C = new SimpleMatrix(4, 4, true, new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});

            //C = new SimpleMatrix(dim, dim, MatrixType.DDRM);
            c.start();
            MatrixMatrixMult_DDRM.multAdd_reorder(A.getDDRM(), B.getDDRM(), C.getDDRM());
            c.stop();
            c.printTime("CommonOps_DDRM_multAdd_reorder dim:\t" + dim + "\t element_sum:\t" + C.elementSum());
            //C.transpose().print();
            C.print();
        }

    }
}
