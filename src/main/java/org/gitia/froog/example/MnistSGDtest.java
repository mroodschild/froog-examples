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
import org.gitia.jdataanalysis.data.Normalize;
import org.gitia.jdataanalysis.data.NormalizeDiv;
import org.gitia.jdataanalysis.data.stats.MapMinMax;

/**
 *
 * @author Matías Roodschild <mroodschild@gmail.com>
 */
public class MnistSGDtest {

    public static void main(String[] args) {

        //================== Preparación de los datos ==========================
        SimpleMatrix input = CSV.open("src/main/resources/mnist/mnist_train_in_50000.csv");
        SimpleMatrix output = CSV.open("src/main/resources/mnist/mnist_train_out_50000.csv");
        SimpleMatrix inputTest = CSV.open("src/main/resources/mnist/mnist_test_in.csv");
        SimpleMatrix outputTest = CSV.open("src/main/resources/mnist/mnist_test_out.csv");
        
        //normalizamos los datos
        input = input.divide(255);
        inputTest = inputTest.divide(255);
        //================== /Preparación de los datos =========================

        //=================  configuraciones del ensayo ========================
        int inputSize = input.numCols();
        int outputSize = output.numCols();

        input = input.transpose();
        inputTest = inputTest.transpose();
        output = output.transpose();
        outputTest = outputTest.transpose();

        //==================== Preparamos la RNA =======================
        Random r = new Random();
        
        Clock clock = new Clock();
        clock.start();

        int ensayos = 3;
        int[] batch = {1, 10, 32, 64, 128, 256, 512, 1024, 60000};
        int[] neuronas = {300, 500, 1000};
        double[] time300 = new double[batch.length];
        double[] time500 = new double[batch.length];
        double[] time1000 = new double[batch.length];

        double[] trainError300 = new double[batch.length];
        double[] trainError500 = new double[batch.length];
        double[] trainError1000 = new double[batch.length];

        double[] testError300 = new double[batch.length];
        double[] testError500 = new double[batch.length];
        double[] testError1000 = new double[batch.length];

        System.out.println("Ensayo 300 Neuronas");
        for (int j = 0; j < batch.length; j++) {
            for (int i = 0; i < ensayos; i++) {
                Feedforward net = new Feedforward();
                net.addLayer(new Dense(inputSize, neuronas[0], TransferFunction.TANSIG, WeightInit.DEFAULT, r));
                net.addLayer(new Dense(neuronas[0], outputSize, TransferFunction.SOFTMAX, r));

                SGD sgd = new SGD();
                sgd.setLearningRate(0.006);
                sgd.setRegularization(1e-4);
                sgd.setEpoch(10);
                sgd.setBatchSize(512);
                sgd.setAcceleration(AccelerateRule.adam(0.9, 0.999, 1e-8, 2));
                sgd.setLossFunction(LossFunction.CROSSENTROPY);

                sgd.train(net, input, output);

            }
        }

        clock.stop();
        double time1 = clock.timeSec();
        System.out.println("Tiempo: " + time1);
    }
}
