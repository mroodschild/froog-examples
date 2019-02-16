
import java.util.stream.IntStream;
import org.ejml.MatrixDimensionException;
import org.ejml.data.DMatrix1Row;
import org.ejml.dense.row.CommonOps_DDRM;

/*
 * Copyright 2019 Matías Roodschild <mroodschild@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class MatrixMatrixMult_DDRM_Parallel {
    
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

        IntStream.range(0, a.numRows).parallel().forEach(i -> {
            int indexCbase = i * c.numCols;
            double valA;
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
        });
    }

    /**
     * @see CommonOps_DDRM#mult( org.ejml.data.DMatrix1Row,
     * org.ejml.data.DMatrix1Row, org.ejml.data.DMatrix1Row)
     */
    public static void mult_small(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        } else if (a.numCols != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        c.reshape(a.numRows, b.numCols);

        IntStream.range(0, a.numRows).parallel().forEach(i -> {
            int index = i * b.numRows;
            int cIndex = i * b.numCols;
            for (int j = 0; j < b.numCols; j++) {
                int indexA = index;
                double total = 0;
                int indexB = j;
                int end = indexA + b.numRows;
                while (indexA < end) {
                    //System.out.println("indexA\t" + indexA + "\tindexB\t" + indexB + "\tindexC\t" + cIndex + "\tTotal:\t" + total);
                    total += a.get(indexA++) * b.get(indexB);
                    indexB += b.numCols;
                }
                c.set(cIndex++, total);
            }
        });

    }

    /**
     * @see CommonOps_DDRM#multTransA( org.ejml.data.DMatrix1Row,
     * org.ejml.data.DMatrix1Row, org.ejml.data.DMatrix1Row)
     */
    public static void multTransA_reorder(DMatrix1Row a, DMatrix1Row b, DMatrix1Row c) {
        if (a == c || b == c) {
            throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'");
        } else if (a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        c.reshape(a.numCols, b.numCols);

        if (a.numCols == 0 || a.numRows == 0) {
            CommonOps_DDRM.fill(c, 0);
            return;
        }

        IntStream.range(0, a.numCols).parallel().forEach(i -> {
            int indexC_start = i * c.numCols;

            // first assign R
            double valA = a.get(i);
            int indexB = 0;
            int end = indexB + b.numCols;
            int indexC = indexC_start;
            while (indexB < end) {
                c.set(indexC++, valA * b.get(indexB++));
            }
            // now increment it
            for (int k = 1; k < a.numRows; k++) {
                valA = a.unsafe_get(k, i);
                end = indexB + b.numCols;
                indexC = indexC_start;
                // this is the loop for j
                while (indexB < end) {
                    c.plus(indexC++, valA * b.get(indexB++));
                }
            }
        });
    }
    
}
