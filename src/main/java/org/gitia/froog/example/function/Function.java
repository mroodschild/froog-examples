/*
 * Copyright 2018 Matías Roodschild <mroodschild@gmail.com>.
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
package org.gitia.froog.example.function;

import java.util.Random;
import org.ejml.simple.SimpleMatrix;
import org.gitia.froog.Feedforward;
import org.gitia.froog.layer.Dense;
import org.gitia.froog.lossfunction.LossFunction;
import org.gitia.froog.optimizer.Backpropagation;
import org.gitia.froog.optimizer.SCG;
import org.gitia.froog.optimizer.SGD;
import org.gitia.froog.optimizer.accelerate.AccelerateRule;
import org.gitia.froog.transferfunction.TransferFunction;
import org.gitia.jdataanalysis.CSV;
import org.gitia.jdataanalysis.data.stats.STD;

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class Function {
    
    public static void main(String[] args) {
        SimpleMatrix input = CSV.open("src/main/resources/function/x.csv");
        SimpleMatrix output = CSV.open("src/main/resources/function/y.csv");
        
        //Standard Desviation
        STD stdIn = new STD();
        stdIn.fit(input);
       

        //normalization
        input = stdIn.eval(input);
       
//        STD stdOut = new STD();
//        stdOut.fit(output);
//        output = stdOut.eval(output);
        
        Random random = new Random();
        
        //set data in horizontal format (a column is a register and a row is a feature)
        input = input.transpose();
        output = output.transpose();

        //number of neurons
        int Nhl = 30;

        Feedforward net = new Feedforward();

        //add layers to neural network
        net.addLayer(new Dense(input.numRows(), Nhl, TransferFunction.TANSIG, random));
        //net.addLayer(new Dense(Nhl, Nhl, TransferFunction.TANSIG, random));
        net.addLayer(new Dense(Nhl, output.numRows(), TransferFunction.PURELIM, random));
        
        //setting backpropagation
        Backpropagation alg = new Backpropagation();
        alg.setEpoch(100000);
        alg.setAcceleration(AccelerateRule.momentumRumelhart(0.9));
        alg.setLossFunction(LossFunction.RMSE);
        
//        SCG alg = new SCG();
//        alg.setLossFunction(LossFunction.RMSE);
//        alg.setEpoch(100);
        
        //train your net
        alg.train(net, input, output);
        
        net.output(input).transpose().print();
        
//        stdOut.reverse(net.output(input).transpose()).print();
    }
}
