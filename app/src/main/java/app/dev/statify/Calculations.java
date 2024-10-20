package app.dev.statify;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Calculations {

    public static void calcAbsFreq(ArrayList<Double> arr){

        boolean[] visited = new boolean[arr.size()];
        Arrays.fill(visited, false);

        for(int i = 0; i < arr.size(); i++) {
            if (visited[i] == true) continue;

            int count = 1;
            for (int j = i + 1; j < arr.size(); j++) {
                if (Objects.equals(arr.get(i), arr.get(j))) {
                    visited[j] = true;
                    count++;
                }
            }
            Log.d("COUNTER", +arr.get(i) + " occurs " + count);
        }
    }

    public void calcRelFreq(){

    }

    public void calcMedian(){

    }

    public void calcArithmetic(){

    }




}
