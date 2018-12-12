/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;
import org.ejml.simple.SimpleMatrix;
import org.gitia.froog.statistics.Clock;

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class BigMatrix {

    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            SimpleMatrix A = SimpleMatrix.random_DDRM(300, 50000, -20, 600, r);
            SimpleMatrix B = SimpleMatrix.random_DDRM(50000, 717, -20, 600, r);
            Clock c = new Clock();
            c.start();
            A.printDimensions();
            B.printDimensions();
            System.out.println("A: " + A.getType() + " B: " + B.getType());
            A.mult(B);
            c.stop();
            c.printTime("Tiempo");
        }
    }

}
