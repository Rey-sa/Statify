package app.dev.statify.Handler;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SaveLoadHandler {

        private static SharedPreferences mSharedPreferences;
        private final String PREFS_NAME = "StatRowPrefs";

        public SaveLoadHandler(Context context){
            mSharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }

    // Convert Insertion into Gson for saving statistical row
    public static void saveStatRows(ArrayList<ArrayList<Double>> mArrayList) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();

        ArrayList<ArrayList<Double>> lastEntries = getLastEntries(mArrayList);

        String json = gson.toJson(lastEntries);
        editor.putString("arrayList_data", json);
        editor.apply();

        mArrayList.clear();
        mArrayList.addAll(lastEntries);

    }

    // Reconvert Gson to ArrayList Element for loading last statistical rows
    public static ArrayList<ArrayList<Double>> loadStatRows() {
            String json = mSharedPreferences.getString("arrayList_data", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ArrayList<Double>>>() {}.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }

    private static ArrayList<ArrayList<Double>> getLastEntries(ArrayList<ArrayList<Double>> orgList){
        int size = orgList.size();
        int index = Math.max(0, size - 5);
        return new ArrayList<>(orgList.subList(index,size));
    }
}
