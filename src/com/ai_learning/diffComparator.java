package com.ai_learning;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class diffComparator implements Comparator<ArrayList<Double>>
{
	@Override
	public int compare(ArrayList<Double> x, ArrayList<Double> y){
		if(x.get(0) >= y.get(0)){
			return -1;
		}
		else {
			return 1;
		}
		
	}
}