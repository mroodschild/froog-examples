/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.stream.IntStream;
import org.ejml.MatrixDimensionException;
import org.ejml.data.DMatrix1Row;
import org.ejml.dense.row.CommonOps_DDRM;

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class Parallel {

    public static void mult_reorder(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        } else if (a.numCols != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        c.reshape(a.numRows, b.numCols);

        if (a.numCols == 0 || a.numRows == 0) {
            CommonOps_DDRM.fill(c, 0);
            return;
        }
        //double valA;
        //int indexCbase = 0;
        int endOfKLoop = b.numRows * b.numCols;

        IntStream.range(0, a.numRows).parallel()
                .forEach(i -> {
            int indexCbase = i * c.numCols;//???
            double valA;//??

            int indexA = i * a.numCols;

            // need to assign c.data to a value initially
            int indexB = 0;
            int indexC = indexCbase;
            int end = indexB + b.numCols;

            valA = a.get(indexA++);

            while (indexB < end) {
                c.set(indexC++, valA * b.get(indexB++));
            }

            // now add to it
            while (indexB != endOfKLoop) { // k loop
                indexC = indexCbase;
                end = indexB + b.numCols;

                valA = a.get(indexA++);

                while (indexB < end) { // j loop
                    c.plus(indexC++, valA * b.get(indexB++));
                }
            }
        }
        );

//        for (int i = 0; i < a.numRows; i++) {
//            int indexA = i * a.numCols;
//
//            // need to assign c.data to a value initially
//            int indexB = 0;
//            int indexC = indexCbase;
//            int end = indexB + b.numCols;
//
//            valA = a.get(indexA++);
//
//            while (indexB < end) {
//                c.set(indexC++, valA * b.get(indexB++));
//            }
//
//            // now add to it
//            while (indexB != endOfKLoop) { // k loop
//                indexC = indexCbase;
//                end = indexB + b.numCols;
//
//                valA = a.get(indexA++);
//
//                while (indexB < end) { // j loop
//                    c.plus(indexC++, valA * b.get(indexB++));
//                }
//            }
//            indexCbase += c.numCols;
//        }
    }
}
