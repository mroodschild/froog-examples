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
package org.gitia.froog.example;

import java.util.Random;
import org.gitia.jdataanalysis.CSV;
import org.gitia.jdataanalysis.data.stats.FilterConstantColumns;
import org.gitia.jdataanalysis.data.stats.STD;
import org.gitia.froog.Feedforward;
import org.gitia.froog.layer.Dense;
import org.gitia.froog.statistics.Clock;
import org.gitia.froog.optimizer.Backpropagation;
import org.ejml.simple.SimpleMatrix;
import org.gitia.froog.layer.initialization.WeightInit;
import org.gitia.froog.lossfunction.LossFunction;
import org.gitia.froog.optimizer.SGD;
import org.gitia.froog.optimizer.accelerate.AccelerateRule;
import org.gitia.froog.statistics.Compite;
import org.gitia.froog.statistics.ConfusionMatrix;
import org.gitia.froog.transferfunction.TransferFunction;

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class MnistSGD {

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
        Random r = new Random();
        Feedforward net = new Feedforward();
        net.addLayer(new Dense(inputSize, 300, TransferFunction.TANSIG, WeightInit.DEFAULT, r));
       // net.addLayer(new Dense(50, 100, TransferFunction.TANSIG, WeightInit.DEFAULT, 0.5, r));
        net.addLayer(new Dense(300, outputSize, TransferFunction.SOFTMAX, r));

        //==================== /Preparamos la RNA ======================
        Clock clock = new Clock();
        clock.start();

        SGD sgd = new SGD();
        sgd.setLearningRate(0.01);
        //sgd.setBatchSize(30);
        sgd.setRegularization(1e-4);
        //sgd.setDropOut(true);
        sgd.setEpoch(10);
        sgd.setBatchSize(32);
        sgd.setAcceleration(AccelerateRule.adam(0.9, 0.999, 1e-8, 2));
        sgd.setLossFunction(LossFunction.CROSSENTROPY);

        sgd.train(net, input, output);

        clock.stop();
        double time1 = clock.timeSec();
        System.out.println("Tiempo: " + time1);

        //show results
        System.out.println("Print all output");
        SimpleMatrix salida = net.output(input);
        ConfusionMatrix confusionMatrix = new ConfusionMatrix();
        confusionMatrix.eval(Compite.eval(salida.transpose()), output.transpose());
        confusionMatrix.printStats();
    }
}
