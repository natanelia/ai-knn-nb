package com.ai_learning.model.validator;

/**
 * Created by dancinggrass on 11/15/15.
 */

import java.io.StringWriter;
import java.util.*;
import com.ai_learning.model.*;
import com.ai_learning.data.*;

public final class CrossValidator {

    public int correct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public class Result {
        private ArrayList<Double> accuracies;
        private ArrayList<String> targetValues;
        private Integer[][] confMatrix;
        private int size;
        private int attrSize;
        public int correct;
        
        public Result(int size, int attrSize) {
            this.accuracies = new ArrayList<>();
            this.confMatrix = new Integer[attrSize][attrSize];
            for(int i = 0; i < attrSize; i++) {
                for(int j = 0; j < attrSize; j++) {
                    confMatrix[i][j] = 0;
                }
            }
            this.size = size;
            this.attrSize = attrSize;
            this.targetValues = new ArrayList<String>();
        }
        public void setTargetValues(DataFrame dataset) {
            
            
            for (final Instance instance : dataset) {
            // TAKE CARE OF TARGET VALUES
            // lazy generate targetValues
                if (!targetValues.contains(instance.getField(dataset.getClassIndex() - 1))) {
                    
                    targetValues.add(instance.getField(dataset.getClassIndex() - 1));
                }
               
            }
            attrSize = targetValues.size();
        }
        public void addFold(int correct, int size) {
            double accuracy = (double)correct / (double)size;
            this.accuracies.add(accuracy);
            
        }
        
        public void addConfusionMatrix(Integer[][] confMatrix) {
            System.out.println(attrSize);
            for (int i = 0; i < attrSize; ++i) {
                for (int j = 0; j < attrSize; ++j) {
                    System.out.println(this.confMatrix[i][j]);
                    System.out.println(confMatrix[i][j]);
                    this.confMatrix[i][j] += confMatrix[i][j];
                }
            }
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
            
            for (int i = 0; i < attrSize; i++) {
                for (int j = 0; j < attrSize; j++) {
                    stringWriter.write(confMatrix[i][j]);
                }
                //System.out.println(" > should be " + this.targetValues.get(i));
            }
            
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
    private Result result;

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
        
        DataFrame trainDF = new DataFrame(trainingInstances, this.dataset.getRelationName(), this.dataset.getAttributes(), this.dataset.getClassIndex());
        DataFrame testDF = new DataFrame(testInstances, this.dataset.getRelationName(), this.dataset.getAttributes(), this.dataset.getClassIndex());
        Fold fold = new Fold(trainDF, testDF);
        return fold;
    }
    public final String validate() {
        int dataSize = dataset.size();

        //ArrayList<Integer> indices = this.shuffledIndices(dataSize);
        /* Create partitions */
        int partitionSize = dataSize / this.folds;
        int remainders = dataSize % this.folds;

        int index = 0;
        int attributeSize = dataset.getAttributes().size();
        System.out.println(dataset.getRelationName());
        result = new Result(dataSize, attributeSize);
        result.setTargetValues(dataset);
        result.correct=0;
        //Integer[][] confMatrix = new Integer[attributeSize][attributeSize];
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
            result.correct += this.model.correct();
            result.addConfusionMatrix(this.model.getConfusionMatrix());
            result.addFold(this.model.correct(), endIndex - startIndex);
            
            index = endIndex;
        }

         
        return result.toString();
    }
    
    public Integer[][] getConfusionMatrix() {
        return result.confMatrix;
    }
    
    public ArrayList<String> getTargetValues() {
        return result.targetValues;
    }
    
    public int getCorrect() {
        return result.correct;
    }
}
