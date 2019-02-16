/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;
import org.ejml.data.MatrixType;
import org.ejml.simple.SimpleMatrix;
import org.gitia.froog.statistics.Clock;

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class BigMatrix {

    public static void main(String[] args) {
        Random r = new Random();
        //for (int i = 0; i < 10; i++) {
        SimpleMatrix dZ2 = new SimpleMatrix(2000, 50000, MatrixType.DDRM);
        SimpleMatrix At2 = new SimpleMatrix(50000, 784, MatrixType.DDRM);
//        try {
//            dZ2.loadCSV("D:\\dZ.csv");
//            At2.loadCSV("D:\\At.csv");
//        } catch (IOException ex) {
//            Logger.getLogger(BigMatrix.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("Finish Load");
        Clock c_prueba = new Clock();
        c_prueba.start();
        SimpleMatrix g = dZ2.mult(At2);
        c_prueba.stop();
        c_prueba.printTime("Tiempo");
        g.printDimensions();
        
        Clock cl = new Clock();
        SimpleMatrix z_prueba = SimpleMatrix.random_DDRM(300, 50000, -200.0, 200, r);
        SimpleMatrix a_prueba = SimpleMatrix.random_DDRM(50000, 784, -200.0, 200, r);
        c_prueba.start();
        z_prueba.mult(a_prueba);
        c_prueba.stop();
        c_prueba.printTime("Calculo Innecesario");
        //}
    }

}
