/*
 * Copyright 2018 
 *   Matías Roodschild <mroodschild@gmail.com>.
 *   Jorge Gotay Sardiñas <jgotay57@gmail.com>.
 *   Adrian Will <adrian.will.01@gmail.com>.
 *   Sebastián Rodriguez <sebastian.rodriguez@gitia.org>.
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
package org.gitia.froog.example.scg;

import org.gitia.jdataanalysis.CSV;
import org.gitia.jdataanalysis.data.stats.STD;
import java.util.Random;
import org.ejml.simple.SimpleMatrix;
import org.gitia.froog.Feedforward;
import org.gitia.froog.layer.Dense;
import org.gitia.froog.lossfunction.LossFunction;
import org.gitia.froog.statistics.Compite;
import org.gitia.froog.statistics.ConfusionMatrix;
import org.gitia.froog.optimizer.SCG;
import org.gitia.froog.transferfunction.TransferFunction;

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class IrisSCG {

    public static void main(String[] args) {
        //get data
        SimpleMatrix input = CSV.open("src/main/resources/iris/iris-in.csv");
        SimpleMatrix output = CSV.open("src/main/resources/iris/iris-out.csv");

//        //Standard Desviation
//        STD std = new STD();
//        std.fit(input);
//
//        //normalization
//        input = std.eval(input);
        
        Random random = new Random(3);
        
        //set data in horizontal format (a column is a register and a row is a feature)
        input = input.transpose();
        output = output.transpose();

        //setting backpropagation
        SCG scg = new SCG();
        scg.setEpoch(50);
        scg.setClassification(true);
        scg.setLossFunction(LossFunction.CROSSENTROPY);

        //number of neurons
        int Nhl = 5;

        Feedforward net = new Feedforward();

        //add layers to neural network
        net.addLayer(new Dense(input.numRows(), Nhl, TransferFunction.TANSIG, random));
        net.addLayer(new Dense(Nhl, output.numRows(), TransferFunction.SOFTMAX, random));
        
        //train your net
        scg.train(net, input, output);
        
        //show results
        System.out.println("Print all output");
        SimpleMatrix salida = net.output(input);
        ConfusionMatrix confusionMatrix = new ConfusionMatrix();
        confusionMatrix.eval(Compite.eval(salida.transpose()), output.transpose());
        confusionMatrix.printStats();
        
    }
}
