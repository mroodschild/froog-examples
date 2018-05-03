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

import org.gitia.jdataanalysis.CSV;
import org.gitia.jdataanalysis.data.stats.STD;
import java.util.Random;
import org.ejml.simple.SimpleMatrix;
import org.gitia.froog.Feedforward;
import org.gitia.froog.layer.Layer;
import org.gitia.froog.lossfunction.LossFunction;
import org.gitia.froog.statistics.Compite;
import org.gitia.froog.statistics.ConfusionMatrix;
import org.gitia.froog.trainingalgorithm.Backpropagation;
import org.gitia.froog.transferfunction.TransferFunction;

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class IrisBP {

    public static void main(String[] args) {
        //get data
        SimpleMatrix input = CSV.open("src/main/resources/iris/iris-in.csv");
        SimpleMatrix output = CSV.open("src/main/resources/iris/iris-out.csv");

        //Standard Desviation
        STD std = new STD();
        std.fit(input);

        //normalization
        input = std.eval(input);
        
        Random random = new Random(1);
        
        //set data in horizontal format (a column is a register and a row is a feature)
        input = input.transpose();
        output = output.transpose();

        //setting backpropagation
        Backpropagation bp = new Backpropagation();
        bp.setEpoch(1000);
        bp.setMomentum(0.9);
        bp.setClassification(true);
        bp.setLossFunction(LossFunction.CROSSENTROPY);

        //number of neurons
        int Nhl = 2;

        Feedforward net = new Feedforward();

        //add layers to neural network
        net.addLayer(new Layer(input.numRows(), Nhl, TransferFunction.TANSIG, random));
        net.addLayer(new Layer(Nhl, output.numRows(), TransferFunction.SOFTMAX, random));
        
        //train your net
        bp.train(net, input, output);
        
        //show results
        System.out.println("Print all output");
        SimpleMatrix salida = net.output(input);
        ConfusionMatrix confusionMatrix = new ConfusionMatrix();
        confusionMatrix.eval(Compite.eval(salida.transpose()), output.transpose());
        confusionMatrix.printStats();
        
    }
}
