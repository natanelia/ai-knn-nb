/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ai_learning;

import com.ai_learning.data.DataFrame;
import com.ai_learning.data.Instance;
import com.ai_learning.model.Model;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author natanelia
 */
public class NB implements Model {
    private DataFrame train;
    private DataFrame test;
    private DataFrame testResult;
    private int targetColumnNumber;
    
    private final int partition = 5;
    
    private ArrayList<ArrayList<ArrayList<Double>>> attributeFrequencies;
    private ArrayList<ArrayList<String>> attributeValues;
    
    private ArrayList<String> targetValues;
    private ArrayList<Double> targetFrequencies;
    
    private ArrayList<ArrayList<ArrayList<Double>>> attributeProbabilities;
    private ArrayList<Double> targetProbabilities;
    
    private ArrayList<AttrNormalizer> attrNormalizers;
    
    private class AttrNormalizer {
        public int index;
        public double range;
        public double min;
        
        public AttrNormalizer(int index, double min, double range) {
            this.index = index;
            this.range = range;
            this.min = min;
        }
    }
    
    private Integer[][] confusionMatrix;

    public NB (int targetColumnNumber) {
        this.targetColumnNumber = targetColumnNumber;
        attributeFrequencies = new ArrayList<>();
        attributeValues = new ArrayList<>();
        targetValues = new ArrayList<>();
        targetFrequencies = new ArrayList<>();
        
        attributeProbabilities = new ArrayList<>();
        targetProbabilities = new ArrayList<>();
    }

    public DataFrame getTrainData() { return train; }
    
    @Override
    public void make(DataFrame trainSet) {
        train = new DataFrame(trainSet);
       
        /* Force numerical attributes into nominal attributes
         * This feat is done by normalizing attribute values
         * and then partition into some categories (label)
         */
        this.attrNormalizers = new ArrayList<>();
		for (int i = 0; i < train.getInstance(0).size(); ++i) {
			if (train.getAttributes().get(i).isNumeric()) {
				Instance col = train.col(i);
				boolean first = true;
				double max = 0.0, min = 0.0;
				for (String value: col) {
					double dvalue = Double.parseDouble(value);
					if (first) {
						max = dvalue;
                                                min = dvalue;
                                                first = false;
					}
					else {
						max = Math.max(max, dvalue);
						min = Math.min(min, dvalue);
					}
				}
				double range = max - min;
				for (int j = 0; j < col.size(); ++j) {
					double dvalue = Double.parseDouble(col.getField(j));
					// Normalize the value, divide by range
					// normval will always between {0..1}
					double normval = (dvalue - min) / range;
					Integer index = (int)Math.floor(normval * this.partition);
					train.row(j).setField(i, index.toString());
				}
                                this.attrNormalizers.add(new AttrNormalizer(i, min, range));
			}
		}
                
                
		//add empty columns to attributeValues
		for (int i = 0; i < train.getInstance(0).size(); i++) {
			attributeValues.add(new ArrayList<>());
			attributeFrequencies.add(new ArrayList<>());
		}

		for (final Instance instance : train) {
			// TAKE CARE OF TARGET VALUES
			// lazy generate targetValues
			if (!targetValues.contains(instance.getField(targetColumnNumber))) {
				targetValues.add(instance.getField(targetColumnNumber));
				targetFrequencies.add(1.0d);

				// when a new target value is found, add all existing frequencyTab with new column
				for (ArrayList<ArrayList<Double>> valueTab : attributeFrequencies) {
					for (ArrayList<Double> frequencyTab : valueTab) {
						frequencyTab.add(1.0d);
					}
				}
			} else {
				int freqToUpdate = targetValues.indexOf(instance.getField(targetColumnNumber));
				targetFrequencies.set(freqToUpdate, targetFrequencies.get(freqToUpdate) + 1);
			}

			// TAKE CARE OF ATTRIBUTE VALUES
			// lazy generate attributeValues
			int i = 0;
			for (final String columnAttributes : instance) {
				if (!attributeValues.get(i).contains(columnAttributes)) {
					attributeValues.get(i).add(columnAttributes);

					// add an empty frequency row for each attribute values
					ArrayList<Double> frequencyRow = new ArrayList<>();
					for (int j = 0; j < targetValues.size(); j++) {
						frequencyRow.add(1.0d);
					}
					attributeFrequencies.get(i).add(frequencyRow);
				}
				int columnAttributeIndex = attributeValues.get(i).indexOf(columnAttributes);
				ArrayList<Double> frequencyRowToUpdate = attributeFrequencies.get(i).get(columnAttributeIndex);
				int frequencyIndexToUpdate = targetValues.indexOf(instance.getField(targetColumnNumber));

				frequencyRowToUpdate.set(frequencyIndexToUpdate, frequencyRowToUpdate.get(frequencyIndexToUpdate) + 1);
				i++;
			}
		}

		//generate probability model for attributes
		// attributeProbabilities = attributeFrequencies;
		ArrayList<ArrayList<Double>> lv1Temp = new ArrayList<>();
		ArrayList<Double> lv2Temp = new ArrayList<>();
		for (ArrayList<ArrayList<Double>> probabilityCol : attributeFrequencies) {
			for (ArrayList<Double> probabilityRow : probabilityCol) {
				for (Double field : probabilityRow) {
					lv2Temp.add(field);
				}
				lv1Temp.add(lv2Temp);
				lv2Temp = new ArrayList<>();
			}
			attributeProbabilities.add(lv1Temp);
			lv1Temp = new ArrayList<>();
		}

		for (ArrayList<ArrayList<Double>> probabilityCol : attributeProbabilities) {
			//totalFrequency contains total frequencies in a target probabilityColumn
			Double[] totalFrequency = new Double[targetValues.size()];
			for (int i = 0; i < totalFrequency.length; i++) {
				totalFrequency[i] = 0.0d;
			}
			for (ArrayList<Double> probabilityRow : probabilityCol) {
				for (int i = 0; i < probabilityRow.size(); i++) {
					totalFrequency[i] += probabilityRow.get(i).intValue();
				}
			}
			for (ArrayList<Double> probabilityRow : probabilityCol) {
				for (int i = 0; i < probabilityRow.size(); i++) {
					probabilityRow.set(i, probabilityRow.get(i) / totalFrequency[i]);
				}
			}
		}

		//targetProbabilities = targetFrequencies;
		for (Double freq : targetFrequencies) {
			targetProbabilities.add(freq);
		}

		for (int i = 0; i < targetProbabilities.size(); i++) {
			targetProbabilities.set(i, targetProbabilities.get(i) / train.size());
		}

		/*System.out.println(targetValues);
		  System.out.println(targetFrequencies);
		  System.out.println(attributeValues);
		  System.out.println(attributeProbabilities);*/
	}

	public String decideClass(Instance instance) {
		Double[] probability = new Double[targetValues.size()];
		for (int j = 0; j < probability.length; j++) {
			probability[j] = 1.0d;
			int i = 0;
			for (final String columnAttributes : instance) {
				if (i != targetColumnNumber) {
					probability[j] *= attributeProbabilities.get(i).get(
							attributeValues.get(i).indexOf(columnAttributes)
							).get(j);
				}
				i++;
			}
			probability[j] *= targetProbabilities.get(j);
		}

		Integer idMaxProb = 0;
		Double maxProb = probability[0];
		for (int i = 1; i < probability.length; i++) {
			if (maxProb < probability[i]) {
				maxProb = probability[i];
				idMaxProb = i;
			}
		}
		return targetValues.get(idMaxProb);
	}
        
	@Override
	public void run(DataFrame testSet) {
                for (AttrNormalizer normalizer: this.attrNormalizers) {
                    for (int i = 0; i < testSet.size(); ++i) {
                        int col = normalizer.index;
                        double dvalue = Double.parseDouble(testSet.row(i).getField(col));
                        double normval = (dvalue - normalizer.min) / normalizer.range;
                        Integer index = (int)Math.floor(normval * this.partition);
                        if (index < 0) index = 0;
                        if (index > this.partition) index = partition;
                        testSet.row(i).setField(col, index.toString());
                    }
                }
                
		test = new DataFrame(testSet);
		testResult = new DataFrame(testSet);

		for (final Instance instance : testResult) {
			instance.setField(targetColumnNumber, decideClass(instance));
		}
		createConfusionMatrix();

	}

	@Override
	public void createConfusionMatrix() {
		confusionMatrix = new Integer[targetValues.size()][targetValues.size()];
		for (int i = 0; i < targetValues.size(); i++) {
			for (int j = 0; j < targetValues.size(); j++) {
				confusionMatrix[i][j] = 0;
			}
		}
		for (int i = 0; i < test.size(); i++) {
			int idxTest = targetValues.indexOf(test.getInstance(i).getField(targetColumnNumber));
			int idxTestResult = targetValues.indexOf(testResult.getInstance(i).getField(targetColumnNumber));
			confusionMatrix[idxTest][idxTestResult] += 1;
		}
	}

	@Override
	public Integer[][] getConfusionMatrix() {
		return confusionMatrix;
	}

	@Override
	public int correct() {
		int r = 0;
		for (int i = 0; i < confusionMatrix.length; i++) {
			r += confusionMatrix[i][i];
		}
		return r;
	}

	public void printConfusionMatrix() {
		for (int i = 0; i < targetValues.size(); i++) {
			for (int j = 0; j < targetValues.size(); j++) {
				System.out.print(confusionMatrix[i][j] + "\t");
			}
			System.out.println();
		}
	}
	public ArrayList<String> getTargetValues() {
		return targetValues;
	}

	public void printClassFrequencies() {
		int i, j, k;
		for (i = 0; i < targetValues.size(); i++) {
			System.out.println("Class " + targetValues.get(i));
			j = 0;
			for (ArrayList<String> attributes : attributeValues) {
				k = 0;
				System.out.println();
				for (String attributeValue : attributes) {
					System.out.print("\t" + attributeValue);
					System.out.println("\t\t" + attributeFrequencies.get(j).get(k).get(i)); // attr . attr val . target attr val
					k++;
				}
				System.out.println();
				j++;
			}
			System.out.println();
		}
	}
        
        public void outputModel() throws FileNotFoundException, UnsupportedEncodingException {
		int i, j, k;
                PrintWriter out_file = new PrintWriter("Model.txt", "UTF-8");
		for (i = 0; i < targetValues.size(); i++) {
			out_file.println("Class " + targetValues.get(i));
			j = 0;
			for (ArrayList<String> attributes : attributeValues) {
				k = 0;
				for (String attributeValue : attributes) {
					out_file.print("\t" + attributeValue);
					out_file.println("\t\t" + attributeProbabilities.get(j).get(k).get(i)); // attr . attr val . target attr val
					k++;
				}
				out_file.println();
				j++;
			}
			out_file.println();
		}
                out_file.close();
	}

	public void printAccuracy() {
		int correct = correct();
		int instances = test.size();
		System.out.println(correct/instances);
		System.out.println(correct + " out of " + instances);
	}

	public void printModel() {
		printConfusionMatrix();
		printClassFrequencies();
		printAccuracy();
	}

}
