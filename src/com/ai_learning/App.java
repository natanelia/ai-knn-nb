package com.ai_learning;

/**
 * Created by dancinggrass on 11/13/15.
 */

import com.ai_learning.data.*;
import com.ai_learning.data.parser.*;
import com.ai_learning.NB;
import com.ai_learning.model.validator.CrossValidator;
import java.util.ArrayList;

public class App {
    private String relation;
    private int instances;
    private Integer[][] confusionMatrix;
    private int correct;
    private ArrayList<String> targetValues;

    
    public void run() {
        Parser parser = new Parser("car.data", ",");
        DataFrame dataframe = parser.toDF();
        for (Instance instance : dataframe) {
            for (String field : instance) {
                System.out.print(field);
                System.out.print(',');
            }
            System.out.println();
        }
    }
    
     public void run(String algorithm, String trainFile, String testFile, int targetAttributeColumn, String method, int k) {
        Parser trainParser = new Parser(trainFile, ",");
        DataFrame trainDataFrame = trainParser.toDF(targetAttributeColumn);
        
        Parser testParser = new Parser(testFile, ",");
        DataFrame testDataFrame = testParser.toDF(targetAttributeColumn);
        
        relation = testDataFrame.getRelationName();
        instances = testDataFrame.size();
        
        switch (algorithm) {
            case "nb": {
                if(method.equals("Full Training")) {
                    NB nb = new NB(targetAttributeColumn-1);
                    nb.make(trainDataFrame);
                    nb.run(testDataFrame);
                    correct = nb.correct();
                    confusionMatrix = nb.getConfusionMatrix();
                    targetValues = nb.getTargetValues();
                    nb.printModel();
                }
                else {
                    System.out.println("10 Fold");
                    NB nb = new NB(targetAttributeColumn-1);
                    CrossValidator cv = new CrossValidator(10, trainDataFrame, nb);
                    System.out.println(cv.validate());
                    confusionMatrix = cv.getConfusionMatrix();
                    targetValues = cv.getTargetValues();
                    correct = cv.getCorrect();
                }   
            }
            break;
            //KNN
            case "knn": {
                KNN knn = new KNN(k, targetAttributeColumn-1);
                
                if(method.equals("Full Training")) {
                    knn.make(trainDataFrame);
                    knn.run(testDataFrame);
                    correct = knn.correct();
                    confusionMatrix = knn.getConfusionMatrix();
                    targetValues = knn.getTargetValues();
                }
                else {
                    System.out.println();
                    System.out.println("10 Fold");
                    CrossValidator cv = new CrossValidator(10, trainDataFrame, knn);
                    System.out.println(cv.validate());
                   
                }      
            }
        }
    }
     
     public String getRelationName() {
         return relation;
     }
     
     public String getInstances() {
         return String.valueOf(instances);
     }
     
     public Integer[][] getConfusionMatrix() {
         return confusionMatrix;
     }
     
     public int getCorrect() {
         return correct;
     }
     
     public ArrayList<String> getTargetValues() {
         return targetValues;
     }
}
