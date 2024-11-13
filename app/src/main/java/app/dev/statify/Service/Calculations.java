package app.dev.statify.Service;

//region Imports
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
//endregion

/**
 * CLass for performing different Calculations with the given selectedData.
 */
public class Calculations {

    /**
     * Calculates absolute frequencies of values in the given dataset (statistical row) by
     * checking if value already occurred and add +1 everytime it is found again.
     *
     * @param selectedData ArrayList of Double containing values from chosen statistical row.
     * @return TreeMap with value and count (absolute frequency / TreeMap)
     */
    public static TreeMap<Double, Integer> calcAbsFreq(ArrayList<Double> selectedData) {

        if (selectedData == null || selectedData.isEmpty()) {
            throw new IllegalArgumentException("ArrayList should not be empty");
        }

        HashMap<Double, Integer> frequencyMap = new HashMap<>();

        //Iterating through selectedData + updating frequencyMap by counting occurrences of each value.
        //If element has not been found it is initialized with 1.
        for (Double num : selectedData) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        return new TreeMap<>(frequencyMap);
    }

    /**
     * Calculates Median from given dataset (statistical row) by sorting and checking if the given ArrayList is even.
     * If so: Finds both middle numbers and divide their sum by 2.
     * If uneven: Devides listsize by 2.
     *
     * @param selectedData ArrayList of Double containing values from chosen statistical row.
     * @return calculated Median (double)
     */
    public static double calcMedian(ArrayList<Double> selectedData) {

        sortSelectedData(selectedData);
        int listSize = selectedData.size();


        if (listSize % 2 == 0) {
            int middle = listSize / 2;
            double value1 = selectedData.get(middle - 1);
            double value2 = selectedData.get(middle);
            return (value1 + value2) / 2;

        } else {
            return selectedData.get(listSize / 2);
        }
    }

    /**
     * Calculates Arithmetic Mean from given dataset(statistical row) by sum up all values
     * and dividing it by the ArrayList size.
     *
     * @param selectedData ArrayList of Double containing values from chosen statistical row.
     * @return calculated Arithmetic Mean (double)
     */
    public static double calcArithmetic(ArrayList<Double> selectedData) {

        if (selectedData == null || selectedData.isEmpty()) {
            throw new IllegalArgumentException("ArrayList should not be empty");
        }

        double result = 0;
        for (double help : selectedData) {
            result += help;
        }

        return result / selectedData.size();
    }

    /**
     * Calculates Range from given dataset(statistical row) by sorting the array and then subtract
     * first value from the last value of the list.
     *
     * @param selectedData ArrayList of Double containing values from chosen statistical row.
     * @return calculated Range (double)
     */
    public static double calcRange(ArrayList<Double> selectedData) {
        sortSelectedData(selectedData);
        return selectedData.get(selectedData.size() - 1) - selectedData.get(0);
    }

    /**
     * Calculates Variance of the given dataset (statistical row) by first determining
     * the arithmetic mean and then squaring the difference between current value and arithmetic mean before adding it
     * to the sum. Finally dividing 1 by listSize and multiply with sum.
     *
     * @param selectedData ArrayList of Double containing values from chosen statistical row.
     * @return calculated Variance (double)
     */
    public static double calcVariance(ArrayList<Double> selectedData) {
        double arithmetic = calcArithmetic(selectedData);
        double listSize = selectedData.size();
        double sum = 0;

        for (int i = 0; i < listSize; i++) {
            //Math.pow squares the difference between arithmetic and selectedData(i)
            sum += Math.pow(selectedData.get(i) - arithmetic, 2);
        }

        return (1 / listSize) * sum;
    }

    /**
     * Calculates Deviation of the given dataset (statistical row) by first determining the
     * Variance and square root it afterward.
     *
     * @param selectedData ArrayList of Double containing values from chosen statistical row.
     * @return calculated Deviation (double).
     */
    public static double calcDeviation(ArrayList<Double> selectedData) {
        double variance = calcVariance(selectedData);

        return Math.sqrt(variance);
    }

    /**
     * Help Method to sort given ArrayList of Double.
     *
     * @param selectedData ArrayList of Double containing values from chosen statistical row.
     */
    public static void sortSelectedData(ArrayList<Double> selectedData) {
        Collections.sort(selectedData);
    }

}
