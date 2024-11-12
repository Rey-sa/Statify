package app.dev.statify.Service.Handler;

import android.widget.EditText;
import app.dev.statify.Persistence.SaveLoadHandler;
import app.dev.statify.UI.Activities.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class SubmitHandler {

    private final MainActivity mActivity;

    public SubmitHandler(MainActivity activity){
        this.mActivity = activity;
    }

    public void handleSubmit(EditText editText){
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
