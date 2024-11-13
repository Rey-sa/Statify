package app.dev.statify.Service.Handler;

import android.view.View;
import app.dev.statify.UI.Activities.MainActivity;

import java.util.ArrayList;

public class EditItemHandler {

    MainActivity mActivity;

    public EditItemHandler(MainActivity activity){
        this.mActivity = activity;
    }

    /**
     * Edits the selected dataset at the specified position of the list.
     * Dataset is converted into a string and displayed in the EditText view, allowing the user to change/remove values.
     * Once the dataset is displayed, it is removed from the original list and the adapter is notified of the change.
     *
     * @param position Position of the selected listItem which is to be edited.
     */
    public void editItem(int position) {
        ArrayList<Double> item = mActivity.getArrayList().get(position);
        StringBuilder sb = new StringBuilder();
        for (Double d : item) {
            sb.append(d).append(" ");
        }
        mActivity.getEditText().setText(sb.toString().trim());
        mActivity.getEditText().setVisibility(View.VISIBLE);
        mActivity.getEditText().requestFocus();
        mActivity.getArrayList().remove(position);
        mActivity.getAdapter().notifyDataSetChanged();
    }
}
