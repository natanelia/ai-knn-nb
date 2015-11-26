package com.ai_learning.model.validator;

/**
 * Created by dancinggrass on 11/15/15.
 */

import java.io.StringWriter;
import java.util.*;
import com.ai_learning.model.*;
import com.ai_learning.data.*;

public final class CrossValidator {
    public class Result {
        private ArrayList<Double> accuracies;
        private int size;
        public Result(int size) {
            this.accuracies = new ArrayList<>();
            this.size = size;
        }

        public void addFold(int correct, int size) {
            double accuracy = (double)correct / (double)size;
            this.accuracies.add(accuracy);
        }

        private double getAccuracy() {
            double total = 0;
            int folds = accuracies.size();
            System.out.println("Folds accuracy:");
            for (final Double accuracy : accuracies) {
                total += accuracy;
                System.out.println(accuracy * 100);
            }

            return 100 * (double)total / (double)(folds);
        }

        public String toString() {
            StringWriter stringWriter = new StringWriter();

            double accuracy = this.getAccuracy();

            stringWriter.write("Accuracy: " + Double.toString(accuracy) + "%");
            
            
            return stringWriter.toString();
        }
    }

    public class Fold {
        public DataFrame trainingInstance;
        public DataFrame testInstance;

        public Fold(ArrayList<Instance> trainingInstance, ArrayList<Instance> testInstance) {
            this.trainingInstance = new DataFrame(trainingInstance);
            this.testInstance = new DataFrame(testInstance);
        }

        public Fold(DataFrame trainingInstance, DataFrame testInstance) {
            this.trainingInstance = new DataFrame(trainingInstance);
            this.testInstance = new DataFrame(testInstance);
        }
    }

    private int folds;
    private DataFrame dataset;
    private Model model;
    private Random randomGenerator;

    public CrossValidator(int k, DataFrame dataset, Model model) {
        this.folds = k;
        this.dataset = dataset;
        this.model = model;
        this.randomGenerator = new Random();
    }

    private ArrayList<Integer> shuffledIndices(int size) {
        /* Initialize indices in sorted order */
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            indices.add(i);
        }

        /* Implement Fisher-Yates algorithm to shuffle indices */
        for (int i = size - 1; i > 0; --i) {
            int j = randomGenerator.nextInt(i+1);
            Integer tmp = indices.get(i);
            indices.set(i, indices.get(j));
            indices.set(j, tmp);
        }
        return indices;
    }

    private Fold populateFoldInstances(int testStartIndex, int testEndIndex) {
        int dataSize = this.dataset.size();
        ArrayList<Instance> trainingInstances = new ArrayList<>();
        ArrayList<Instance> testInstances = new ArrayList<>();
        for (int i = 0; i < dataSize; ++i) {
            if (testStartIndex <= i && i < testEndIndex) {
                    /* Test Instances */
                testInstances.add(this.dataset.row(i));
            }
            else {
                    /* Training Instances */
                trainingInstances.add(this.dataset.row(i));
            }
        }

        Fold fold = new Fold(trainingInstances, testInstances);
        return fold;
    }
    public final String validate() {
        int dataSize = dataset.size();

        //ArrayList<Integer> indices = this.shuffledIndices(dataSize);
        /* Create partitions */
        int partitionSize = dataSize / this.folds;
        int remainders = dataSize % this.folds;

        int index = 0;
        Result result = new Result(dataSize);
        while (index < dataSize) {
            int startIndex = index, endIndex = index + partitionSize;
            if (remainders > 0) {
                ++endIndex;
                --remainders;
            }

            Fold foldInstances = this.populateFoldInstances(startIndex, endIndex);

            /* Make model with training dataset */
            this.model.make(new DataFrame(foldInstances.trainingInstance));
            /* Run model with test dataset and then add result to statistic */
            this.model.run(new DataFrame(foldInstances.testInstance));


            result.addFold(this.model.correct(), endIndex - startIndex);
            index = endIndex;
        }

         
        return result.toString();
    }
}
