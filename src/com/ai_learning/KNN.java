package com.ai_learning;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.lang.*;
import java.util.*;
import com.ai_learning.data.*;
import com.ai_learning.model.*;

import java.util.Comparator;

public class KNN implements Model{

	private ArrayList<ArrayList<String>> neighbors;
	private DataFrame train;
	private DataFrame test;
	private DataFrame resultTest;
        private ArrayList<String> targetValues;
        private Integer[][] confusionMatrix;
        private int targetColumnNumber;
	private int k;
	String result;

	/*** KONSTRUKTOR ***/

	public KNN(int k, int target) {
		setK(k);
                setTargetColumnNumber(target);
	}

	@Override
	public void make(final DataFrame dataset) {
		train = new DataFrame(dataset); 
                targetValues = new ArrayList<String>();

	}

	@Override
	public void run(final DataFrame dataset) {
		
		test = new DataFrame(dataset);
		resultTest = new DataFrame(dataset);
		for(int i=0; i<resultTest.size(); i++) {
			Instance instance = resultTest.getInstance(i);
			//System.out.println("Cluster =" + knnAlgorithm(instance));
			instance.setField(targetColumnNumber,knnAlgorithm(instance));
		}
                createConfusionMatrix();
                System.out.println("CORRECT: " + correct());
                printConfusionMatrix();
	}

	@Override 
	public int correct() {
		int count =0;
		Instance result = resultTest.col(targetColumnNumber);
		Instance fact = test.col(targetColumnNumber);
		for(int i=0; i <result.size(); i++){
			String tempResult = result.getField(i).replace(" ","");
			String tempFact = fact.getField(i).replace(" ","");
			if(tempResult.equals(tempFact)){
				count++;
			}
		}
		return count;
	}

	/**** FUNCTION *****/
	public String knnAlgorithm(Instance check) {
		//ngambil row dari train
		neighbors = new ArrayList<ArrayList<String>>(k);

		
		int difference = 0;

		//bandingin sama masing masing instance dari train	

        for (Instance instance : train) {
        
                if (!targetValues.contains(instance.getField(targetColumnNumber))) {
                targetValues.add(instance.getField(targetColumnNumber));
                }
                
        	for(int i=0; i<instance.size()-1; i++){
        		String field = instance.getField(i);
        		if(!field.equals(check.getField(i))){
        			difference++;
        		}
        	}

        	//masukkan ke dalam temp
        	ArrayList<String> temp = new ArrayList<String>(2);
			temp.add(0, Integer.toString(difference));
			temp.add(1, instance.getField(targetColumnNumber));

			//masukin dulu ke dalam neighbors
			if(neighbors.size()<k) {
				neighbors.add(temp);
			}
			else {
				int indexMax = searchIndexMax(neighbors);
				int maxDif = Integer.parseInt(neighbors.get(indexMax).get(0));
				if(Integer.parseInt(temp.get(0))<=maxDif) {
					neighbors.set(indexMax, temp);
				}
			}
			
			difference = 0;
        }



        //neighbors sudah terbentuk, cari masing-masing kelas jumlahnya berapa, ambil yang paling banyak
        ArrayList<ArrayList<String>> counter = new ArrayList<ArrayList<String> >();
        for(int i=0; i<neighbors.size(); i++) {
        	ArrayList<String> head = neighbors.get(i);
        	//System.out.println(head);
        	if(searchClass(counter,head.get(1))==-1) {
        		ArrayList<String> temp = new ArrayList<String>(head);
        		temp.set(0,"1");
        		counter.add(temp);
        	}
        	else {
        		int index = searchClass(counter,head.get(1));
        		int count = Integer.parseInt(counter.get(index).get(0))+1;
        		counter.get(index).set(0,Integer.toString(count));
        	}

        }


        //cari kelas yang paling banyak muncul
        int max = Integer.parseInt(counter.get(0).get(0));
		result = counter.get(0).get(1);
		for(int i=0; i<counter.size(); i++) {
			if(Integer.parseInt(counter.get(i).get(0)) > max) {
				max = Integer.parseInt(counter.get(i).get(0));
				result = counter.get(i).get(1);
			}
		}
		
        return result;

	}

	//mencari index class str pada counter
	public int searchClass(ArrayList<ArrayList<String>> counter, String str) {
		boolean found = false;
		int i= 0;
		str = str.replace(" ","");
		while(!found && i<counter.size()) {
			if(str.equals(counter.get(i).get(1))) {
				found = true;

			}
			else {
				i++;
			}
		}
		if(found) {
			return i;
		}
		else
			return -1;
	} 

	//mencari index dari nilai maksimum di input
	public int searchIndexMax(ArrayList<ArrayList<String>> input) {
		int max = Integer.parseInt(input.get(0).get(0));
		int imax = 0;
		for(int i=0; i<input.size(); i++) {
			if(Integer.parseInt(input.get(i).get(0)) > max) {
				max = Integer.parseInt(input.get(i).get(0));
				imax = i;
			}
		}
		return imax;
	}

	/**** GETTER ****/
	public int getK() {
		return k;
	}

	public DataFrame getTrain() {
		return train;
	}

	public DataFrame getTest() {
		return test;
	}

	public Instance getTrainI(int i) {
		return train.getInstance(i);
	}

	public String getResult() {
		return result;
	}
	
	/**** SETTER ****/
	public void setK(int k) {
		this.k = k;
	}

        public void setTargetColumnNumber(int target)
        {
            this.targetColumnNumber = target;
        }
        
	public void setTrain(DataFrame train) {
		this.train = train;
	}

	public void setTest(DataFrame test) {
		this.test = test;
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
                int idxTestResult = targetValues.indexOf(resultTest.getInstance(i).getField(targetColumnNumber));
                confusionMatrix[idxTest][idxTestResult] += 1;
            }
  
    }

    @Override
    public Integer[][] getConfusionMatrix() {
        return confusionMatrix;
    }
    
     public ArrayList<String> getTargetValues() {
         return targetValues;
     }

    public void printConfusionMatrix() {
        for (int i = 0; i < confusionMatrix.length; i++) {
            for (int j = 0; j < confusionMatrix.length; j++) {
                System.out.printf("%4d ", confusionMatrix[i][j]);
            }
            System.out.println(" > should be " + targetValues.get(i));
        }
    }
}
