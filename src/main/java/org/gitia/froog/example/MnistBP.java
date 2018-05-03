/*
 * The MIT License
 *
 * Copyright 2017 Matías Roodschild <mroodschild@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.gitia.froog.example;

import java.util.Random;
import org.gitia.jdataanalysis.CSV;
import org.gitia.jdataanalysis.data.stats.FilterConstantColumns;
import org.gitia.jdataanalysis.data.stats.STD;
import org.gitia.froog.Feedforward;
import org.gitia.froog.layer.Layer;
import org.gitia.froog.statistics.Clock;
import org.gitia.froog.trainingalgorithm.Backpropagation;
import org.ejml.simple.SimpleMatrix;
import org.gitia.froog.lossfunction.LossFunction;
import org.gitia.froog.transferfunction.TransferFunction;

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class MnistBP {

    public static void main(String[] args) {
        
        //================== Preparación de los datos ==========================
        SimpleMatrix input = CSV.open("src/main/resources/mnist/mnist_train_in_50000.csv");
        SimpleMatrix output = CSV.open("src/main/resources/mnist/mnist_train_out_50000.csv");

        //ajustamos la desviación standard
        FilterConstantColumns filter = new FilterConstantColumns();
        filter.fit(input);
        System.out.println("Dimensiones iniciales");
        input.printDimensions();
        input = filter.eval(input);
        System.out.println("Dimensiones finales");
        input.printDimensions();

        STD std = new STD();
        std.fit(input);

        //convertimos los datos
        input = std.eval(input);
        //================== /Preparación de los datos =========================

        //=================  configuraciones del ensayo ========================
        //Preparamos el algoritmo de entrenamiento

        int inputSize = input.numCols();
        int outputSize = output.numCols();
        
        input = input.transpose();
        output = output.transpose();

        //==================== Preparamos la RNA =======================
        Random r = new Random(1);
        Feedforward net = new Feedforward();
        net.addLayer(new Layer(inputSize, 300, TransferFunction.TANSIG, r));
        net.addLayer(new Layer(300, outputSize, TransferFunction.SOFTMAX, r));

        //==================== /Preparamos la RNA ======================
        Clock clock = new Clock();
        clock.start();
        
        Backpropagation bp = new Backpropagation();
        bp.setLearningRate(0.01);
        bp.setRegularization(1e-4);
        bp.setEpoch(5);
        bp.setMomentum(0.9);
        bp.setLossFunction(LossFunction.CROSSENTROPY);
        
        bp.train(net, input, output);
        
        clock.stop();
        double time1 = clock.timeSec();
        System.out.println("Tiempo: " + time1);
    }
}