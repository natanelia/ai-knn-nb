package com.ai_learning.data.parser;

/**
 * Created by dancinggrass on 11/13/15.
 */

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.*;
import com.ai_learning.data.*;

public class Parser {
    public ArrayList<Instance> dataset = new ArrayList<>();
    private String relationName;
    private ArrayList<AttributeKnowledge> attributeKnowledges = new ArrayList<>();
    

    public Parser(String filename, String delimiter) {
        boolean isReadingData = false;
        File sourcefile = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(sourcefile))) {
            String text = null;
            while ((text = reader.readLine()) != null) {
                if (isReadingData) {
                    String[] inputFields = text.split(delimiter);
                    Instance instance = new Instance(inputFields);
                    dataset.add(instance);
                } else if (isCommentedLine(text)) {
                    /* do nothing */
                } else if (isRelationLine(text)) {
                    relationName = text.substring(10);
                } else if (isAttributeLine(text)) {
                    text = text.substring(11);
                    String[] s;
                    if (text.startsWith("'")) {
                        text = text.substring(1);
                        s = text.split("' +");
                    } else {
                        s = text.split(" ");
                    }
                    AttributeKnowledge ak;
                    if (!s[1].startsWith("{")) {
                        ak = new AttributeKnowledge(s[0], s[1]);
                    } else {
                        ak = new AttributeKnowledge(s[0], "array");
                        String allowedValStr = s[1].substring(1, s[1].length() - 1);
                        String[] allowedVals = allowedValStr.split(", *");
                        for (String a : allowedVals) {
                            ak.addAllowedValues(a);
                        }
                    }
                    attributeKnowledges.add(ak);
                } else if (isDataStartLine(text)) {
                    isReadingData = true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public DataFrame toDF() {
        return new DataFrame(dataset, relationName, attributeKnowledges);
    }
    public DataFrame toDF(int classIndex) {
        return new DataFrame(dataset, relationName, attributeKnowledges, classIndex);
    }
    
    
    private boolean isCommentedLine(String s) {
        return s.startsWith("%");
    }
    
    private boolean isRelationLine(String s) {
        return s.startsWith("@relation ");
    }
    
    private boolean isAttributeLine(String s) {
        return s.startsWith("@attribute ");
    }
    
    private boolean isDataStartLine(String s) {
        return s.startsWith("@data");
    }
}
