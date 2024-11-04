package app.dev.statify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;


public class Calculations {
    


    public static TreeMap<Double, Integer> calcAbsFreq(ArrayList<Double> arr){

        HashMap<Double, Integer> frequencyMap = new HashMap<>();

        for(Double num : arr) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0)+1);
        }
        return new TreeMap<>(frequencyMap);
    }

    public void calcRelFreq(){

    }

    public void calcMedian(){

    }

    public static double calcArithmetic(ArrayList<Double> arr){

        if(arr == null || arr.size() ==0){
            throw new IllegalArgumentException("ArrayList should not be empty");
        }

        double result = 0;
        for(double help : arr){
            result += help;
        }

        return result/ arr.size();
    }




}
