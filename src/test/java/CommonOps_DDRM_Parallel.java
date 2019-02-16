
import java.util.stream.IntStream;
import org.ejml.MatrixDimensionException;
import org.ejml.data.DMatrixD1;

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
public class CommonOps_DDRM_Parallel {

    public static void elementExp_parallel(DMatrixD1 A, DMatrixD1 C) {

        if (A.numCols != C.numCols || A.numRows != C.numRows) {
            throw new MatrixDimensionException("All matrices must be the same shape");
        }

        int size = A.getNumElements();
        IntStream.range(0, size).parallel().forEach(i -> {
            C.data[i] = Math.exp(A.data[i]);
        });
    }

    /**
     * <p>
     * Performs an in-place element by element scalar multiplication.<br>
     * <br>
     * a<sub>ij</sub> = &alpha;*a<sub>ij</sub>
     * </p>
     *
     * @param a The matrix that is to be scaled. Modified.
     * @param alpha the amount each element is multiplied by.
     */
    public static void scale_parallel(double alpha, DMatrixD1 a) {
        // on very small matrices (2 by 2) the call to getNumElements() can slow it down
        // slightly compared to other libraries since it involves an extra multiplication.
        final int size = a.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            a.data[i] *= alpha;
        });
    }

    /**
     * <p>
     * Element-wise power operation  <br>
     * c<sub>ij</sub> = a<sub>ij</sub> ^ b
     * <p>
     *
     * @param A left side
     * @param b right scalar
     * @param C output (modified)
     */
    public static void elementPower(DMatrixD1 A, double b, DMatrixD1 C) {

        if (A.numRows != C.numRows || A.numCols != C.numCols) {
            throw new MatrixDimensionException("All matrices must be the same shape");
        }

        int size = A.getNumElements();
        IntStream.range(0, size).parallel().forEach(i -> {
            C.data[i] = Math.pow(A.data[i], b);
        });
    }

    /**
     * <p>
     * Performs the an element by element multiplication operation:<br>
     * <br>
     * a<sub>ij</sub> = a<sub>ij</sub> * b<sub>ij</sub> <br>
     * </p>
     *
     * @param a The left matrix in the multiplication operation. Modified.
     * @param b The right matrix in the multiplication operation. Not modified.
     */
    public static void elementMult(DMatrixD1 a, DMatrixD1 b) {
        if (a.numCols != b.numCols || a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }

        int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            a.times(i, b.get(i));
        });
    }

    /**
     * <p>
     * Performs the an element by element multiplication operation:<br>
     * <br>
     * c<sub>ij</sub> = a<sub>ij</sub> * b<sub>ij</sub> <br>
     * </p>
     *
     * @param a The left matrix in the multiplication operation. Not modified.
     * @param b The right matrix in the multiplication operation. Not modified.
     * @param c Where the results of the operation are stored. Modified.
     */
    public static void elementMult(DMatrixD1 a, DMatrixD1 b, DMatrixD1 c) {
        if (a.numCols != b.numCols || a.numRows != b.numRows
                || a.numRows != c.numRows || a.numCols != c.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }

        int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            c.set(i, a.get(i) * b.get(i));
        });
    }

    /**
     * <p>
     * Performs the an element by element division operation:<br>
     * <br>
     * a<sub>ij</sub> = a<sub>ij</sub> / b<sub>ij</sub> <br>
     * </p>
     *
     * @param a The left matrix in the division operation. Modified.
     * @param b The right matrix in the division operation. Not modified.
     */
    public static void elementDiv(DMatrixD1 a, DMatrixD1 b) {
        if (a.numCols != b.numCols || a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }

        int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            a.div(i, b.get(i));
        });
    }

    /**
     * <p>
     * Performs the an element by element division operation:<br>
     * <br>
     * c<sub>ij</sub> = a<sub>ij</sub> / b<sub>ij</sub> <br>
     * </p>
     *
     * @param a The left matrix in the division operation. Not modified.
     * @param b The right matrix in the division operation. Not modified.
     * @param c Where the results of the operation are stored. Modified.
     */
    public static void elementDiv(DMatrixD1 a, DMatrixD1 b, DMatrixD1 c) {
        if (a.numCols != b.numCols || a.numRows != b.numRows
                || a.numRows != c.numRows || a.numCols != c.numCols) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }

        int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            c.set(i, a.get(i) / b.get(i));
        });
    }
    
    /**
     * <p>
     * Element-wise power operation  <br>
     * c<sub>ij</sub> = a<sub>ij</sub> ^ b<sub>ij</sub>
     * <p>
     *
     * @param A left side
     * @param B right side
     * @param C output (modified)
     */
    public static void elementPower(DMatrixD1 A, DMatrixD1 B, DMatrixD1 C) {

        if (A.numRows != B.numRows || A.numRows != C.numRows
                || A.numCols != B.numCols || A.numCols != C.numCols) {
            throw new MatrixDimensionException("All matrices must be the same shape");
        }

        int size = A.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            C.data[i] = Math.pow(A.data[i], B.data[i]);
        });
    }

    /**
     * <p>
     * Element-wise power operation  <br>
     * c<sub>ij</sub> = a ^ b<sub>ij</sub>
     * <p>
     *
     * @param a left scalar
     * @param B right side
     * @param C output (modified)
     */
    public static void elementPower(double a, DMatrixD1 B, DMatrixD1 C) {

        if (B.numRows != C.numRows || B.numCols != C.numCols) {
            throw new MatrixDimensionException("All matrices must be the same shape");
        }

        int size = B.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            C.data[i] = Math.pow(a, B.data[i]);
        });
    }

    /**
     * <p>
     * Element-wise log operation  <br>
     * c<sub>ij</sub> = Math.log(a<sub>ij</sub>)
     * <p>
     *
     * @param A input
     * @param C output (modified)
     */
    public static void elementLog(DMatrixD1 A, DMatrixD1 C) {

        if (A.numCols != C.numCols || A.numRows != C.numRows) {
            throw new MatrixDimensionException("All matrices must be the same shape");
        }

        int size = A.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            C.data[i] = Math.log(A.data[i]);
        });

    }
    
    /**
     * <p>
     * Performs the following operation:<br>
     * <br>
     * a = a + b <br>
     * a<sub>ij</sub> = a<sub>ij</sub> + b<sub>ij</sub> <br>
     * </p>
     *
     * @param a A Matrix. Modified.
     * @param b A Matrix. Not modified.
     */
    public static void addEquals(DMatrixD1 a, DMatrixD1 b) {
        if (a.numCols != b.numCols || a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }

        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            a.plus(i, b.get(i));
        });
    }

    /**
     * <p>
     * Performs the following operation:<br>
     * <br>
     * a = a + &beta; * b  <br>
     * a<sub>ij</sub> = a<sub>ij</sub> + &beta; * b<sub>ij</sub>
     * </p>
     *
     * @param beta The number that matrix 'b' is multiplied by.
     * @param a A Matrix. Modified.
     * @param b A Matrix. Not modified.
     */
    public static void addEquals(DMatrixD1 a, double beta, DMatrixD1 b) {
        if (a.numCols != b.numCols || a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }

        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            a.plus(i, beta * b.get(i));
        });
    }

    /**
     * <p>
     * Performs the following operation:<br>
     * <br>
     * c = a + b <br>
     * c<sub>ij</sub> = a<sub>ij</sub> + b<sub>ij</sub> <br>
     * </p>
     *
     * <p>
     * Matrix C can be the same instance as Matrix A and/or B.
     * </p>
     *
     * @param a A Matrix. Not modified.
     * @param b A Matrix. Not modified.
     * @param c A Matrix where the results are stored. Modified.
     */
    public static void add(final DMatrixD1 a, final DMatrixD1 b, final DMatrixD1 c) {
        if (a.numCols != b.numCols || a.numRows != b.numRows) {
            throw new MatrixDimensionException("The matrices are not all the same dimension.");
        }

        c.reshape(a.numRows, a.numCols);

        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            c.set(i, a.get(i) + b.get(i));
        });

    }

    /**
     * <p>
     * Performs the following operation:<br>
     * <br>
     * c = a + &beta; * b <br>
     * c<sub>ij</sub> = a<sub>ij</sub> + &beta; * b<sub>ij</sub> <br>
     * </p>
     *
     * <p>
     * Matrix C can be the same instance as Matrix A and/or B.
     * </p>
     *
     * @param a A Matrix. Not modified.
     * @param beta Scaling factor for matrix b.
     * @param b A Matrix. Not modified.
     * @param c A Matrix where the results are stored. Modified.
     */
    public static void add(DMatrixD1 a, double beta, DMatrixD1 b, DMatrixD1 c) {
        if (a.numCols != b.numCols || a.numRows != b.numRows) {
            throw new MatrixDimensionException("The matrices are not all the same dimension.");
        }

        c.reshape(a.numRows, a.numCols);

        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            c.set(i, a.get(i) + beta * b.get(i));
        });
    }

    /**
     * <p>
     * Performs the following operation:<br>
     * <br>
     * c = &alpha; * a + &beta; * b <br>
     * c<sub>ij</sub> = &alpha; * a<sub>ij</sub> + &beta; * b<sub>ij</sub> <br>
     * </p>
     *
     * <p>
     * Matrix C can be the same instance as Matrix A and/or B.
     * </p>
     *
     * @param alpha A scaling factor for matrix a.
     * @param a A Matrix. Not modified.
     * @param beta A scaling factor for matrix b.
     * @param b A Matrix. Not modified.
     * @param c A Matrix where the results are stored. Modified.
     */
    public static void add(double alpha, DMatrixD1 a, double beta, DMatrixD1 b, DMatrixD1 c) {
        if (a.numCols != b.numCols || a.numRows != b.numRows) {
            throw new MatrixDimensionException("The matrices are not all the same dimension.");
        }

        c.reshape(a.numRows, a.numCols);

        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            c.set(i, alpha * a.get(i) + beta * b.get(i));
        });

    }

    /**
     * <p>
     * Performs the following operation:<br>
     * <br>
     * c = &alpha; * a + b <br>
     * c<sub>ij</sub> = &alpha; * a<sub>ij</sub> + b<sub>ij</sub> <br>
     * </p>
     *
     * <p>
     * Matrix C can be the same instance as Matrix A and/or B.
     * </p>
     *
     * @param alpha A scaling factor for matrix a.
     * @param a A Matrix. Not modified.
     * @param b A Matrix. Not modified.
     * @param c A Matrix where the results are stored. Modified.
     */
    public static void add(double alpha, DMatrixD1 a, DMatrixD1 b, DMatrixD1 c) {
        if (a.numCols != b.numCols || a.numRows != b.numRows) {
            throw new MatrixDimensionException("The matrices are not all the same dimension.");
        }

        c.reshape(a.numRows, a.numCols);
        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            c.set(i, alpha * a.get(i) + b.get(i));
        });
    }

    /**
     * <p>
     * Performs an in-place scalar addition:<br>
     * <br>
     * a = a + val<br>
     * a<sub>ij</sub> = a<sub>ij</sub> + val<br>
     * </p>
     *
     * @param a A matrix. Modified.
     * @param val The value that's added to each element.
     */
    public static void add(DMatrixD1 a, double val) {
        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            a.plus(i, val);
        });

    }

    /**
     * <p>
     * Performs scalar addition:<br>
     * <br>
     * c = a + val<br>
     * c<sub>ij</sub> = a<sub>ij</sub> + val<br>
     * </p>
     *
     * @param a A matrix. Not modified.
     * @param c A matrix. Modified.
     * @param val The value that's added to each element.
     */
    public static void add(DMatrixD1 a, double val, DMatrixD1 c) {
        c.reshape(a.numRows, a.numCols);

        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            c.data[i] = a.data[i] + val;
        });

    }

    /**
     * <p>
     * Performs matrix scalar subtraction:<br>
     * <br>
     * c = a - val<br>
     * c<sub>ij</sub> = a<sub>ij</sub> - val<br>
     * </p>
     *
     * @param a (input) A matrix. Not modified.
     * @param val (input) The value that's subtracted to each element.
     * @param c (Output) A matrix. Modified.
     */
    public static void subtract(DMatrixD1 a, double val, DMatrixD1 c) {
        c.reshape(a.numRows, a.numCols);

        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            c.data[i] = a.data[i] - val;
        });

    }

    /**
     * <p>
     * Performs matrix scalar subtraction:<br>
     * <br>
     * c = val - a<br>
     * c<sub>ij</sub> = val - a<sub>ij</sub><br>
     * </p>
     *
     * @param val (input) The value that's subtracted to each element.
     * @param a (input) A matrix. Not modified.
     * @param c (Output) A matrix. Modified.
     */
    public static void subtract(double val, DMatrixD1 a, DMatrixD1 c) {
        c.reshape(a.numRows, a.numCols);

        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            c.data[i] = val - a.data[i];
        });

    }

    /**
     * <p>
     * Performs the following subtraction operation:<br>
     * <br>
     * a = a - b  <br>
     * a<sub>ij</sub> = a<sub>ij</sub> - b<sub>ij</sub>
     * </p>
     *
     * @param a A Matrix. Modified.
     * @param b A Matrix. Not modified.
     */
    public static void subtractEquals(DMatrixD1 a, DMatrixD1 b) {
        if (a.numCols != b.numCols || a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }

        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            a.data[i] -= b.data[i];
        });

    }

    /**
     * <p>
     * Performs the following subtraction operation:<br>
     * <br>
     * c = a - b  <br>
     * c<sub>ij</sub> = a<sub>ij</sub> - b<sub>ij</sub>
     * </p>
     * <p>
     * Matrix C can be the same instance as Matrix A and/or B.
     * </p>
     *
     * @param a A Matrix. Not modified.
     * @param b A Matrix. Not modified.
     * @param c A Matrix. Modified.
     */
    public static void subtract(DMatrixD1 a, DMatrixD1 b, DMatrixD1 c) {
        if (a.numCols != b.numCols || a.numRows != b.numRows) {
            throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions");
        }
        c.reshape(a.numRows, a.numCols);

        final int length = a.getNumElements();

        IntStream.range(0, length).parallel().forEach(i -> {
            c.data[i] = a.data[i] - b.data[i];
        });

    }

    /**
     * <p>
     * Performs an element by element scalar multiplication.<br>
     * <br>
     * b<sub>ij</sub> = &alpha;*a<sub>ij</sub>
     * </p>
     *
     * @param alpha the amount each element is multiplied by.
     * @param a The matrix that is to be scaled. Not modified.
     * @param b Where the scaled matrix is stored. Modified.
     */
    public static void scale(double alpha, DMatrixD1 a, DMatrixD1 b) {
        b.reshape(a.numRows, a.numCols);

        final int size = a.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            b.data[i] = a.data[i] * alpha;
        });
    }

    /**
     * <p>
     * Performs an in-place element by element scalar division with the scalar
     * on top.<br>
     * <br>
     * a<sub>ij</sub> = &alpha;/a<sub>ij</sub>
     * </p>
     *
     * @param a The matrix whose elements are divide the scalar. Modified.
     * @param alpha top value in division
     */
    public static void divide(double alpha, DMatrixD1 a) {
        final int size = a.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            a.data[i] = alpha / a.data[i];
        });

    }

    /**
     * <p>
     * Performs an in-place element by element scalar division with the scalar
     * on bottom.<br>
     * <br>
     * a<sub>ij</sub> = a<sub>ij</sub>/&alpha;
     * </p>
     *
     * @param a The matrix whose elements are to be divided. Modified.
     * @param alpha the amount each element is divided by.
     */
    public static void divide(DMatrixD1 a, double alpha) {
        final int size = a.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            a.data[i] /= alpha;
        });

    }

    /**
     * <p>
     * Performs an element by element scalar division with the scalar on
     * top.<br>
     * <br>
     * b<sub>ij</sub> = &alpha;/a<sub>ij</sub>
     * </p>
     *
     * @param alpha The numerator.
     * @param a The matrix whose elements are the divisor. Not modified.
     * @param b Where the results are stored. Modified.
     */
    public static void divide(double alpha, DMatrixD1 a, DMatrixD1 b) {
        b.reshape(a.numRows, a.numCols);

        final int size = a.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            b.data[i] = alpha / a.data[i];
        });
    }

    /**
     * <p>
     * Performs an element by element scalar division with the scalar on
     * botton.<br>
     * <br>
     * b<sub>ij</sub> = a<sub>ij</sub> /&alpha;
     * </p>
     *
     * @param a The matrix whose elements are to be divided. Not modified.
     * @param alpha the amount each element is divided by.
     * @param b Where the results are stored. Modified.
     */
    public static void divide(DMatrixD1 a, double alpha, DMatrixD1 b) {
        b.reshape(a.numRows, a.numCols);

        final int size = a.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            b.data[i] = a.data[i] / alpha;
        });
    }

    /**
     * <p>
     * Changes the sign of every element in the matrix.<br>
     * <br>
     * a<sub>ij</sub> = -a<sub>ij</sub>
     * </p>
     *
     * @param a A matrix. Modified.
     */
    public static void changeSign(DMatrixD1 a) {
        final int size = a.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            a.data[i] = -a.data[i];
        });

    }

    /**
     * <p>
     * Changes the sign of every element in the matrix.<br>
     * <br>
     * output<sub>ij</sub> = -input<sub>ij</sub>
     * </p>
     *
     * @param input A matrix. Modified.
     */
    public static void changeSign(DMatrixD1 input, DMatrixD1 output) {
        output.reshape(input.numRows, input.numCols);

        final int size = input.getNumElements();

        IntStream.range(0, size).parallel().forEach(i -> {
            output.data[i] = -input.data[i];
        });

    }
    
//    /**
//     * Converts the columns in a matrix into a set of vectors.
//     *
//     * @param A Matrix.  Not modified.
//     * @param v
//     * @return An array of vectors.
//     */
//    public static DMatrixRMaj[] columnsToVector(DMatrixRMaj A, DMatrixRMaj[] v)
//    {
//        DMatrixRMaj[]ret;
//        if( v == null || v.length < A.numCols ) {
//            ret = new DMatrixRMaj[ A.numCols ];
//        } else {
//            ret = v;
//        }
//
//        for( int i = 0; i < ret.length; i++ ) {
//            if( ret[i] == null ) {
//                ret[i] = new DMatrixRMaj(A.numRows,1);
//            } else {
//                ret[i].reshape(A.numRows,1, false);
//            }
//
//            DMatrixRMaj u = ret[i];
//
//            for( int j = 0; j < A.numRows; j++ ) {
//                u.set(j,0, A.get(j,i));
//            }
//        }
//
//        return ret;
//    }
//    /**
//     * Converts the rows in a matrix into a set of vectors.
//     *
//     * @param A Matrix.  Not modified.
//     * @param v
//     * @return An array of vectors.
//     */
//    public static DMatrixRMaj[] rowsToVector(DMatrixRMaj A, DMatrixRMaj[] v)
//    {
//        DMatrixRMaj[]ret;
//        if( v == null || v.length < A.numRows ) {
//            ret = new DMatrixRMaj[ A.numRows ];
//        } else {
//            ret = v;
//        }
//
//
//        for( int i = 0; i < ret.length; i++ ) {
//            if( ret[i] == null ) {
//                ret[i] = new DMatrixRMaj(A.numCols,1);
//            } else {
//                ret[i].reshape(A.numCols,1, false);
//            }
//
//            DMatrixRMaj u = ret[i];
//
//            for( int j = 0; j < A.numCols; j++ ) {
//                u.set(j,0, A.get(i,j));
//            }
//        }
//
//        return ret;
//    }
//    /**
//     * <p>
//     * The Kronecker product of two matrices is defined as:<br>
//     * C<sub>ij</sub> = a<sub>ij</sub>B<br>
//     * where C<sub>ij</sub> is a sub matrix inside of C &isin; &real; <sup>m*k &times; n*l</sup>,
//     * A &isin; &real; <sup>m &times; n</sup>, and B &isin; &real; <sup>k &times; l</sup>.
//     * </p>
//     *
//     * @param A The left matrix in the operation. Not modified.
//     * @param B The right matrix in the operation. Not modified.
//     * @param C Where the results of the operation are stored. Modified.
//     */
//    public static void kron(DMatrixRMaj A , DMatrixRMaj B , DMatrixRMaj C )
//    {
//        int numColsC = A.numCols*B.numCols;
//        int numRowsC = A.numRows*B.numRows;
//
//        if( C.numCols != numColsC || C.numRows != numRowsC) {
//            throw new MatrixDimensionException("C does not have the expected dimensions");
//        }
//
//        // TODO see comment below
//        // this will work well for small matrices
//        // but an alternative version should be made for large matrices
//        for( int i = 0; i < A.numRows; i++ ) {
//            for( int j = 0; j < A.numCols; j++ ) {
//                double a = A.get(i,j);
//
//                for( int rowB = 0; rowB < B.numRows; rowB++ ) {
//                    for( int colB = 0; colB < B.numCols; colB++ ) {
//                        double val = a*B.get(rowB,colB);
//                        C.set(i*B.numRows+rowB,j*B.numCols+colB,val);
//                    }
//                }
//            }
//        }
//    }
//    /**
//     * <p>
//     * Returns the value of the element in the matrix that has the largest value.<br>
//     * <br>
//     * Max{ a<sub>ij</sub> } for all i and j<br>
//     * </p>
//     *
//     * @param a A matrix. Not modified.
//     * @return The max element value of the matrix.
//     */
//    public static double elementMax( DMatrixD1 a ) {
//        final int size = a.getNumElements();
//
//        double max = a.get(0);
//        for( int i = 1; i < size; i++ ) {
//            double val = a.get(i);
//            if( val >= max ) {
//                max = val;
//            }
//        }
//
//        return max;
//    }
//
//    /**
//     * <p>
//     * Returns the absolute value of the element in the matrix that has the largest absolute value.<br>
//     * <br>
//     * Max{ |a<sub>ij</sub>| } for all i and j<br>
//     * </p>
//     *
//     * @param a A matrix. Not modified.
//     * @return The max abs element value of the matrix.
//     */
//    public static double elementMaxAbs( DMatrixD1 a ) {
//        final int size = a.getNumElements();
//
//        double max = 0;
//        for( int i = 0; i < size; i++ ) {
//            double val = Math.abs(a.get(i));
//            if( val > max ) {
//                max = val;
//            }
//        }
//
//        return max;
//    }
//    /**
//     * <p>
//     * Returns the value of the element in the matrix that has the minimum value.<br>
//     * <br>
//     * Min{ a<sub>ij</sub> } for all i and j<br>
//     * </p>
//     *
//     * @param a A matrix. Not modified.
//     * @return The value of element in the matrix with the minimum value.
//     */
//    public static double elementMin( DMatrixD1 a ) {
//        final int size = a.getNumElements();
//
//        double min = a.get(0);
//        for( int i = 1; i < size; i++ ) {
//            double val = a.get(i);
//            if( val < min ) {
//                min = val;
//            }
//        }
//
//        return min;
//    }
//
//    /**
//     * <p>
//     * Returns the absolute value of the element in the matrix that has the smallest absolute value.<br>
//     * <br>
//     * Min{ |a<sub>ij</sub>| } for all i and j<br>
//     * </p>
//     *
//     * @param a A matrix. Not modified.
//     * @return The max element value of the matrix.
//     */
//    public static double elementMinAbs( DMatrixD1 a ) {
//        final int size = a.getNumElements();
//
//        double min = Double.MAX_VALUE;
//        for( int i = 0; i < size; i++ ) {
//            double val = Math.abs(a.get(i));
//            if( val < min ) {
//                min = val;
//            }
//        }
//
//        return min;
//    }
    

//    /**
//     * <p>
//     * Computes the sum of all the elements in the matrix:<br>
//     * <br>
//     * sum(i=1:m , j=1:n ; a<sub>ij</sub>)
//     * <p>
//     *
//     * @param mat An m by n matrix. Not modified.
//     * @return The sum of the elements.
//     */
//    public static double elementSum(DMatrixD1 mat) {
//        double total = 0;
//
//        int size = mat.getNumElements();
//
//        for (int i = 0; i < size; i++) {
//            total += mat.get(i);
//        }
//
//        return total;
//    }
//    /**
//     * <p>
//     * Computes the sum of the absolute value all the elements in the
//     * matrix:<br>
//     * <br>
//     * sum(i=1:m , j=1:n ; |a<sub>ij</sub>|)
//     * <p>
//     *
//     * @param mat An m by n matrix. Not modified.
//     * @return The sum of the absolute value of each element.
//     */
//    public static double elementSumAbs(DMatrixD1 mat) {
//        double total = 0;
//
//        int size = mat.getNumElements();
//
//        for (int i = 0; i < size; i++) {
//            total += Math.abs(mat.get(i));
//        }
//
//        return total;
//    }
    

//    /**
//     * Multiplies every element in row i by value[i].
//     *
//     * @param values array. Not modified.
//     * @param A Matrix. Modified.
//     */
//    public static void multRows(double[] values, DMatrixRMaj A) {
//        if (values.length < A.numRows) {
//            throw new IllegalArgumentException("Not enough elements in values.");
//        }
//
//        int index = 0;
//        
//        for (int row = 0; row < A.numRows; row++) {
//            double v = values[row];
//            for (int col = 0; col < A.numCols; col++, index++) {
//                A.data[index] *= v;
//            }
//        }
//    }
//    /**
//     * Divides every element in row i by value[i].
//     *
//     * @param values array. Not modified.
//     * @param A Matrix. Modified.
//     */
//    public static void divideRows(double[] values, DMatrixRMaj A) {
//        if (values.length < A.numRows) {
//            throw new IllegalArgumentException("Not enough elements in values.");
//        }
//
//        int index = 0;
//        for (int row = 0; row < A.numRows; row++) {
//            double v = values[row];
//            for (int col = 0; col < A.numCols; col++, index++) {
//                A.data[index] /= v;
//            }
//        }
//    }
//    /**
//     * Multiplies every element in column i by value[i].
//     *
//     * @param A Matrix. Modified.
//     * @param values array. Not modified.
//     */
//    public static void multCols(DMatrixRMaj A, double values[]) {
//        if (values.length < A.numCols) {
//            throw new IllegalArgumentException("Not enough elements in values.");
//        }
//
//        int index = 0;
//        for (int row = 0; row < A.numRows; row++) {
//            for (int col = 0; col < A.numCols; col++, index++) {
//                A.data[index] *= values[col];
//            }
//        }
//    }
//    /**
//     * Divides every element in column i by value[i].
//     *
//     * @param A Matrix. Modified.
//     * @param values array. Not modified.
//     */
//    public static void divideCols(DMatrixRMaj A, double values[]) {
//        if (values.length < A.numCols) {
//            throw new IllegalArgumentException("Not enough elements in values.");
//        }
//
//        int index = 0;
//        for (int row = 0; row < A.numRows; row++) {
//            for (int col = 0; col < A.numCols; col++, index++) {
//                A.data[index] /= values[col];
//            }
//        }
//    }
//    /**
//     * <p>
//     * Computes the sum of each row in the input matrix and returns the results
//     * in a vector:<br>
//     * <br>
//     * b<sub>j</sub> = sum(i=1:n ; a<sub>ji</sub>)
//     * </p>
//     *
//     * @param input INput matrix whose rows are summed.
//     * @param output Optional storage for output. Reshaped into a column.
//     * Modified.
//     * @return Vector containing the sum of each row in the input.
//     */
//    public static DMatrixRMaj sumRows(DMatrixRMaj input, DMatrixRMaj output) {
//        if (output == null) {
//            output = new DMatrixRMaj(input.numRows, 1);
//        } else {
//            output.reshape(input.numRows, 1);
//        }
////        int size = input.numRows;
////        IntStream.range(0, size).parallel().forEach(row -> {
////            double total = 0;
////            int end = (row + 1) * input.numCols;
////            for (int index = row * input.numCols; index < end; index++) {
////                total += input.data[index];
////            }
////            output.set(row, total);
////        });
//
//        for (int row = 0; row < input.numRows; row++) {
//            double total = 0;
//
//            int end = (row + 1) * input.numCols;
//            for (int index = row * input.numCols; index < end; index++) {
//                total += input.data[index];
//            }
//
//            output.set(row, total);
//        }
//        return output;
//    }
//    /**
//     * <p>
//     * Finds the element with the minimum value along each row in the input
//     * matrix and returns the results in a vector:<br>
//     * <br>
//     * b<sub>j</sub> = min(i=1:n ; a<sub>ji</sub>)
//     * </p>
//     *
//     * @param input Input matrix
//     * @param output Optional storage for output. Reshaped into a column.
//     * Modified.
//     * @return Vector containing the sum of each row in the input.
//     */
//    public static DMatrixRMaj minRows(DMatrixRMaj input, DMatrixRMaj output) {
//        if (output == null) {
//            output = new DMatrixRMaj(input.numRows, 1);
//        } else {
//            output.reshape(input.numRows, 1);
//        }
//
//        for (int row = 0; row < input.numRows; row++) {
//            double min = Double.MAX_VALUE;
//
//            int end = (row + 1) * input.numCols;
//            for (int index = row * input.numCols; index < end; index++) {
//                double v = input.data[index];
//                if (v < min) {
//                    min = v;
//                }
//            }
//
//            output.set(row, min);
//        }
//        return output;
//    }
//    /**
//     * <p>
//     * Finds the element with the maximum value along each row in the input
//     * matrix and returns the results in a vector:<br>
//     * <br>
//     * b<sub>j</sub> = max(i=1:n ; a<sub>ji</sub>)
//     * </p>
//     *
//     * @param input Input matrix
//     * @param output Optional storage for output. Reshaped into a column.
//     * Modified.
//     * @return Vector containing the sum of each row in the input.
//     */
//    public static DMatrixRMaj maxRows(DMatrixRMaj input, DMatrixRMaj output) {
//        if (output == null) {
//            output = new DMatrixRMaj(input.numRows, 1);
//        } else {
//            output.reshape(input.numRows, 1);
//        }
//
//        for (int row = 0; row < input.numRows; row++) {
//            double max = -Double.MAX_VALUE;
//
//            int end = (row + 1) * input.numCols;
//            for (int index = row * input.numCols; index < end; index++) {
//                double v = input.data[index];
//                if (v > max) {
//                    max = v;
//                }
//            }
//
//            output.set(row, max);
//        }
//        return output;
//    }
//    /**
//     * <p>
//     * Computes the sum of each column in the input matrix and returns the
//     * results in a vector:<br>
//     * <br>
//     * b<sub>j</sub> = min(i=1:m ; a<sub>ij</sub>)
//     * </p>
//     *
//     * @param input Input matrix
//     * @param output Optional storage for output. Reshaped into a row vector.
//     * Modified.
//     * @return Vector containing the sum of each column
//     */
//    public static DMatrixRMaj sumCols(DMatrixRMaj input, DMatrixRMaj output) {
//        if (output == null) {
//            output = new DMatrixRMaj(1, input.numCols);
//        } else {
//            output.reshape(1, input.numCols);
//        }
//
//        for (int cols = 0; cols < input.numCols; cols++) {
//            double total = 0;
//
//            int index = cols;
//            int end = index + input.numCols * input.numRows;
//            for (; index < end; index += input.numCols) {
//                total += input.data[index];
//            }
//
//            output.set(cols, total);
//        }
//        return output;
//    }
//    /**
//     * <p>
//     * Finds the element with the minimum value along column in the input matrix
//     * and returns the results in a vector:<br>
//     * <br>
//     * b<sub>j</sub> = min(i=1:m ; a<sub>ij</sub>)
//     * </p>
//     *
//     * @param input Input matrix
//     * @param output Optional storage for output. Reshaped into a row vector.
//     * Modified.
//     * @return Vector containing the minimum of each column
//     */
//    public static DMatrixRMaj minCols(DMatrixRMaj input, DMatrixRMaj output) {
//        if (output == null) {
//            output = new DMatrixRMaj(1, input.numCols);
//        } else {
//            output.reshape(1, input.numCols);
//        }
//        for (int cols = 0; cols < input.numCols; cols++) {
//            double minimum = Double.MAX_VALUE;
//
//            int index = cols;
//            int end = index + input.numCols * input.numRows;
//            for (; index < end; index += input.numCols) {
//                double v = input.data[index];
//                if (v < minimum) {
//                    minimum = v;
//                }
//            }
//
//            output.set(cols, minimum);
//        }
//        return output;
//    }
//    /**
//     * <p>
//     * Finds the element with the minimum value along column in the input matrix
//     * and returns the results in a vector:<br>
//     * <br>
//     * b<sub>j</sub> = min(i=1:m ; a<sub>ij</sub>)
//     * </p>
//     *
//     * @param input Input matrix
//     * @param output Optional storage for output. Reshaped into a row vector.
//     * Modified.
//     * @return Vector containing the maximum of each column
//     */
//    public static DMatrixRMaj maxCols(DMatrixRMaj input, DMatrixRMaj output) {
//        if (output == null) {
//            output = new DMatrixRMaj(1, input.numCols);
//        } else {
//            output.reshape(1, input.numCols);
//        }
//        for (int cols = 0; cols < input.numCols; cols++) {
//            double maximum = -Double.MAX_VALUE;
//
//            int index = cols;
//            int end = index + input.numCols * input.numRows;
//            for (; index < end; index += input.numCols) {
//                double v = input.data[index];
//                if (v > maximum) {
//                    maximum = v;
//                }
//            }
//
//            output.set(cols, maximum);
//        }
//        return output;
//    }
    

//    /**
//     * Applies the &gt; operator to each element in A. Results are stored in a
//     * boolean matrix.
//     *
//     * @param A Input matrx
//     * @param value value each element is compared against
//     * @param output (Optional) Storage for results. Can be null. Is reshaped.
//     * @return Boolean matrix with results
//     */
//    public static BMatrixRMaj elementLessThan(DMatrixRMaj A, double value, BMatrixRMaj output) {
//        if (output == null) {
//            output = new BMatrixRMaj(A.numRows, A.numCols);
//        }
//
//        output.reshape(A.numRows, A.numCols);
//
//        int N = A.getNumElements();
//
//        for (int i = 0; i < N; i++) {
//            output.data[i] = A.data[i] < value;
//        }
//
//        return output;
//    }

//    /**
//     * Applies the &ge; operator to each element in A. Results are stored in a
//     * boolean matrix.
//     *
//     * @param A Input matrix
//     * @param value value each element is compared against
//     * @param output (Optional) Storage for results. Can be null. Is reshaped.
//     * @return Boolean matrix with results
//     */
//    public static BMatrixRMaj elementLessThanOrEqual(DMatrixRMaj A, double value, BMatrixRMaj output) {
//        if (output == null) {
//            output = new BMatrixRMaj(A.numRows, A.numCols);
//        }
//
//        output.reshape(A.numRows, A.numCols);
//
//        int N = A.getNumElements();
//
//        for (int i = 0; i < N; i++) {
//            output.data[i] = A.data[i] <= value;
//        }
//
//        return output;
//    }
//
//    /**
//     * Applies the &gt; operator to each element in A. Results are stored in a
//     * boolean matrix.
//     *
//     * @param A Input matrix
//     * @param value value each element is compared against
//     * @param output (Optional) Storage for results. Can be null. Is reshaped.
//     * @return Boolean matrix with results
//     */
//    public static BMatrixRMaj elementMoreThan(DMatrixRMaj A, double value, BMatrixRMaj output) {
//        if (output == null) {
//            output = new BMatrixRMaj(A.numRows, A.numCols);
//        }
//
//        output.reshape(A.numRows, A.numCols);
//
//        int N = A.getNumElements();
//
//        for (int i = 0; i < N; i++) {
//            output.data[i] = A.data[i] > value;
//        }
//
//        return output;
//    }
//
//    /**
//     * Applies the &ge; operator to each element in A. Results are stored in a
//     * boolean matrix.
//     *
//     * @param A Input matrix
//     * @param value value each element is compared against
//     * @param output (Optional) Storage for results. Can be null. Is reshaped.
//     * @return Boolean matrix with results
//     */
//    public static BMatrixRMaj elementMoreThanOrEqual(DMatrixRMaj A, double value, BMatrixRMaj output) {
//        if (output == null) {
//            output = new BMatrixRMaj(A.numRows, A.numCols);
//        }
//
//        output.reshape(A.numRows, A.numCols);
//
//        int N = A.getNumElements();
//
//        for (int i = 0; i < N; i++) {
//            output.data[i] = A.data[i] >= value;
//        }
//
//        return output;
//    }
//
//    /**
//     * Applies the &lt; operator to each element in A. Results are stored in a
//     * boolean matrix.
//     *
//     * @param A Input matrix
//     * @param B Input matrix
//     * @param output (Optional) Storage for results. Can be null. Is reshaped.
//     * @return Boolean matrix with results
//     */
//    public static BMatrixRMaj elementLessThan(DMatrixRMaj A, DMatrixRMaj B, BMatrixRMaj output) {
//        if (output == null) {
//            output = new BMatrixRMaj(A.numRows, A.numCols);
//        }
//
//        output.reshape(A.numRows, A.numCols);
//
//        int N = A.getNumElements();
//
//        for (int i = 0; i < N; i++) {
//            output.data[i] = A.data[i] < B.data[i];
//        }
//
//        return output;
//    }
//
//    /**
//     * Applies the A &le; B operator to each element. Results are stored in a
//     * boolean matrix.
//     *
//     * @param A Input matrix
//     * @param B Input matrix
//     * @param output (Optional) Storage for results. Can be null. Is reshaped.
//     * @return Boolean matrix with results
//     */
//    public static BMatrixRMaj elementLessThanOrEqual(DMatrixRMaj A, DMatrixRMaj B, BMatrixRMaj output) {
//        if (output == null) {
//            output = new BMatrixRMaj(A.numRows, A.numCols);
//        }
//
//        output.reshape(A.numRows, A.numCols);
//
//        int N = A.getNumElements();
//
//        for (int i = 0; i < N; i++) {
//            output.data[i] = A.data[i] <= B.data[i];
//        }
//
//        return output;
//    }

//    /**
//     * Returns a row matrix which contains all the elements in A which are
//     * flagged as true in 'marked'
//     *
//     * @param A Input matrix
//     * @param marked Input matrix marking elements in A
//     * @param output Storage for output row vector. Can be null. Will be
//     * reshaped.
//     * @return Row vector with marked elements
//     */
//    public static DMatrixRMaj elements(DMatrixRMaj A, BMatrixRMaj marked, DMatrixRMaj output) {
//        if (A.numRows != marked.numRows || A.numCols != marked.numCols) {
//            throw new MatrixDimensionException("Input matrices must have the same shape");
//        }
//        if (output == null) {
//            output = new DMatrixRMaj(1, 1);
//        }
//
//        output.reshape(countTrue(marked), 1);
//
//        int N = A.getNumElements();
//
//        int index = 0;
//        for (int i = 0; i < N; i++) {
//            if (marked.data[i]) {
//                output.data[index++] = A.data[i];
//            }
//        }
//
//        return output;
//    }

//    /**
//     * Counts the number of elements in A which are true
//     *
//     * @param A input matrix
//     * @return number of true elements
//     */
//    public static int countTrue(BMatrixRMaj A) {
//        int total = 0;
//
//        int N = A.getNumElements();
//
//        for (int i = 0; i < N; i++) {
//            if (A.data[i]) {
//                total++;
//            }
//        }
//
//        return total;
//    }

}
