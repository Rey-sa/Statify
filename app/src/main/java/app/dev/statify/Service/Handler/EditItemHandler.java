package app.dev.statify.Service.Handler;

//region Imports

import android.view.View;
import app.dev.statify.UI.Activities.MainActivity;

import java.util.ArrayList;
//endregion

/**
 * Handles edit function in List View in MainActivity.
 */
public class EditItemHandler {

    private final MainActivity mActivity;

    /**
     * Constructs {@link EditItemHandler}. Initializes necessary params for handling
     * editing behavior within ListView in MainActivity.
     *
     * @param activity The MainActivity instance where the handler ist used.
     */
    public EditItemHandler(MainActivity activity) {
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
