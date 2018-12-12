/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        SimpleMatrix a = SimpleMatrix.random_DDRM(300, 50000, -20, 600, r);
        SimpleMatrix b = SimpleMatrix.random_DDRM(50000, 717, -20, 600, r);
        SimpleMatrix dZ2 = new SimpleMatrix(300, 50000, MatrixType.DDRM);
        SimpleMatrix At2 = new SimpleMatrix(50000, 784, MatrixType.DDRM);
        try {
            dZ2.loadCSV("D:\\dZ.csv");
            At2.loadCSV("D:\\At.csv");
        } catch (IOException ex) {
            Logger.getLogger(BigMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Finish Load");
        Clock c_prueba = new Clock();
        c_prueba.start();
        //A.printDimensions();
        //B.printDimensions();
        //System.out.println("A: " + A.getType() + " B: " + B.getType());
        //a.mult(b);
        SimpleMatrix g = dZ2.mult(At2);
        c_prueba.stop();
        c_prueba.printTime("Tiempo");
        g.printDimensions();
        //}
    }

}
