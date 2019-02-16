
import java.util.stream.IntStream;
import org.ejml.MatrixDimensionException;
import org.ejml.data.DMatrix1Row;
import org.ejml.data.DMatrixD1;
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
public class MatrixVectorMult_DDRM_Parallel {
    
    public static void mult(DMatrix1Row A, DMatrixD1 B, DMatrixD1 C) {

        if (B.numRows == 1) {
            if (A.numCols != B.numCols) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else if (B.numCols == 1) {
            if (A.numCols != B.numRows) {
                throw new MatrixDimensionException("A and B are not compatible");
            }
        } else {
            throw new MatrixDimensionException("B is not a vector");
        }
        C.reshape(A.numRows, 1);
        if (A.numCols == 0) {
            CommonOps_DDRM.fill(C, 0);
            return;
        }
        IntStream.range(0, A.numRows).parallel().forEach(i -> {
            double total = 0;
            int idxA = i * A.numCols;
            for (int j = 0; j < A.numCols; j++) {
                total += A.get(idxA++) * B.get(j);
            }
            C.set(i, total);

        });
    }
}
