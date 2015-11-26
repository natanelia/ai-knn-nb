package com.ai_learning.model;


/**
 * Created by dancinggrass on 11/15/15.
 */

import com.ai_learning.data.DataFrame;

public interface Model {
    void make(final DataFrame dataset);
    void run(final DataFrame dataset);
    void createConfusionMatrix();
    Integer[][] getConfusionMatrix();
    int correct();
    void printConfusionMatrix();
}
