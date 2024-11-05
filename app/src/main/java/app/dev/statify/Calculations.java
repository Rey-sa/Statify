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

    public void calcRelFreq(ArrayList<Double> arr){

    }

    public static String calcMedian(ArrayList<Double> selectedData){

        Collections.sort(selectedData);
        int listSize = selectedData.size();


        if(listSize %2 == 0){
            int middle = listSize / 2;
            double value1 = selectedData.get(middle -1);
            double value2 = selectedData.get(middle);
            return String.format(Locale.US,"%.2f",(value1 + value2) / 2.0 );

        } else {
            return String.format(Locale.US,"%.2f",selectedData.get(listSize/2));

        }

    }

    public static String calcArithmetic(ArrayList<Double> selectedData){

        if(selectedData == null || selectedData.isEmpty()){
            throw new IllegalArgumentException("ArrayList should not be empty");
        }

        double result = 0;
        for(double help : selectedData){
            result += help;
        }


        return String.format(Locale.US,"%.2f",result/ selectedData.size());
    }




}
