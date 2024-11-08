package app.dev.statify;

import java.util.*;


public class Calculations {
    


    public static TreeMap<Double, Integer> calcAbsFreq(ArrayList<Double> selectedData){

        if(selectedData == null || selectedData.isEmpty()){
            throw new IllegalArgumentException("ArrayList should not be empty");
        }

        HashMap<Double, Integer> frequencyMap = new HashMap<>();

        for(Double num : selectedData) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0)+1);
        }
        return new TreeMap<>(frequencyMap);
    }

    public static double calcMedian(ArrayList<Double> selectedData){

        sortSelectedData(selectedData);
        int listSize = selectedData.size();


        if(listSize %2 == 0){
            int middle = listSize / 2;
            double value1 = selectedData.get(middle -1);
            double value2 = selectedData.get(middle);
            return (value1 + value2) / 2.0 ;

        } else {
            return selectedData.get(listSize/2);

        }

    }

    public static double calcArithmetic(ArrayList<Double> selectedData){

        if(selectedData == null || selectedData.isEmpty()){
            throw new IllegalArgumentException("ArrayList should not be empty");
        }

        double result = 0;
        for(double help : selectedData){
            result += help;
        }

        return result/ selectedData.size();

    }

    public static double calcRange(ArrayList<Double> selectedData){
        sortSelectedData(selectedData);
        return selectedData.get(selectedData.size()-1) - selectedData.get(0);


    }

    public static double calcVariance(ArrayList<Double> selectedData){
        double arithmetic = calcArithmetic(selectedData);
        double listSize = selectedData.size();
        double sum = 0;

        for(int i = 0; i < listSize; i++){
            sum += Math.pow(selectedData.get(i) - arithmetic, 2);
        }

        return  (1 / listSize) * sum;
    }

    public static double calcDeviation(ArrayList<Double> selectedData){
            double variance = calcVariance(selectedData);

            return Math.sqrt(variance);

    }




    public static void sortSelectedData(ArrayList<Double> selectedData) {
        Collections.sort(selectedData);
    }




}
