package com.ai_learning;

/**
 * Created by dancinggrass on 11/13/15.
 */

import com.ai_learning.data.*;
import com.ai_learning.data.parser.*;
import com.ai_learning.NB;
import com.ai_learning.model.validator.CrossValidator;

public class App {
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
    
     public void run(String algorithm, String trainFile, String testFile, int targetAttributeColumn) {
        Parser trainParser = new Parser(trainFile, ",");
        DataFrame trainDataFrame = trainParser.toDF();
        
        Parser testParser = new Parser(testFile, ",");
        DataFrame testDataFrame = testParser.toDF();
        
        System.out.println("RELATION NAME: " + testDataFrame.getRelationName());
        System.out.println("INSTANCES: " + testDataFrame.size());
        
        switch (algorithm) {
            case "nb": {
                System.out.println("Full Training");
                NB nb = new NB(targetAttributeColumn-1);
                nb.make(trainDataFrame);
                nb.run(testDataFrame);
                //System.out.println("CORRECT = " + (double)nb.correct() / (double)testDataFrame.size());
              
                
                System.out.println("CORRECT = " + nb.correct());
                nb.printModel();
                
                System.out.println();
                System.out.println("10 Fold");
                CrossValidator cv = new CrossValidator(10, trainDataFrame, nb);
                System.out.println(cv.validate());
                
            }
            //KNN
            case "knn": {
                KNN knn = new KNN(targetAttributeColumn);
                
                System.out.println("Full Training");
                knn.make(trainDataFrame);
                knn.run(testDataFrame);
                
                System.out.println();
                System.out.println("10 Fold");
                CrossValidator cv = new CrossValidator(10, trainDataFrame, knn);
                System.out.println(cv.validate());
                
                
            }
        }
    }
}
