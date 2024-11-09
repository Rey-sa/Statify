package app.dev.statify.Handler;

import android.widget.EditText;
import app.dev.statify.Activities.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class SubmitHandler {

    private MainActivity mActivity;

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
                mActivity.saveStatRows();
            }
        }
    }
}
