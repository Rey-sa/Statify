package app.dev.statify.Service.Handler;

import android.widget.EditText;
import app.dev.statify.Persistence.SaveLoadHandler;
import app.dev.statify.UI.Activities.MainActivity;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handles onKeyPressed events (Enter).
 */
public class SubmitHandler {

    private final MainActivity mActivity;

    /**
     * Constructs {@code SubmitHandler}.
     *
     * @param activity {@link MainActivity} that is passed to {@code SubmitHandler}.
     */
    public SubmitHandler(MainActivity activity) {
        this.mActivity = activity;
    }

    /**
     * Handles submits from {@link EditText}.
     * Retrieving the text from {@link EditText}, splitting into space-separated values and parsing value as Double.
     * Valid Doubles are added to the list of statistical rows.
     * Updating UI and clear {@link EditText}.
     *
     * @param editText contains the input data to be processed.
     */
    public void handleSubmit(EditText editText) {
        String s = editText.getText().toString().trim();

        if (!s.isEmpty()) {

            ArrayList<String> inputArrayList = new ArrayList<>(Arrays.asList(s.split(" ")));
            ArrayList<Double> tempArrayList = new ArrayList<>();

            for (String ss : inputArrayList) {
                ss = ss.replace(",", ".");

                try {
                    tempArrayList.add(Double.parseDouble(ss.trim()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            if (!tempArrayList.isEmpty()) {
                mActivity.getArrayList().add(tempArrayList);
                mActivity.getAdapter().notifyDataSetChanged();
                mActivity.getEditText().setText("");
                SaveLoadHandler mSaveLoadHandler = new SaveLoadHandler(mActivity);
                mSaveLoadHandler.saveStatRows(mActivity.getArrayList());
            }
        }
    }
}
