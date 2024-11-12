package app.dev.statify.Persistence;
//region Imports
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
//endregion

/**
 * Handles saving and loading of statistical row data set using SharedPreferences and Gson
 */
public class SaveLoadHandler {

    private static SharedPreferences mSharedPreferences;

    /**
     * Constructs a SaveLoaderHandler with given context
     *
     * @param context The context used to get the SharedPreferences
     */
    public SaveLoadHandler(Context context) {
        final String PREFS_NAME = "StatRowPrefs";
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Converts to Gson - Saves the last (up to 10) entries of the given ArrayList to SharedPreferences
     *
     * @param mArrayList The ArrayList of statistical rows datasets to save
     */
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

    /**
     * Reconverts the Gson - Loads saved statistical rows datasets from SharedPreferences
     *
     * @return An ArrayList of statistical rows, or an empty ArrayList if no data is found
     */
    public static ArrayList<ArrayList<Double>> loadStatRows() {
        String json = mSharedPreferences.getString("arrayList_data", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ArrayList<Double>>>() {
            }.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Trims down the given ArrayList to retrieve only up to the last 10 entries
     *
     * @param orgList The original ArrayList of statistical rows
     * @return An ArrayList containing the last (up to 10) entries of the orgList
     */
    private static ArrayList<ArrayList<Double>> getLastEntries(ArrayList<ArrayList<Double>> orgList) {
        int size = orgList.size();
        int index = Math.max(0, size - 10);
        return new ArrayList<>(orgList.subList(index, size));
    }
}
