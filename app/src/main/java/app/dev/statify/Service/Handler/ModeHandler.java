package app.dev.statify.Service.Handler;

//region Imports
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import androidx.core.content.ContextCompat;
import app.dev.statify.R;
import app.dev.statify.UI.Activities.MainActivity;
import java.util.ArrayList;
//endregion

/**
 * Handles EditMode / Non EditMode &  New Data Mode for {@link MainActivity}
 */
public class ModeHandler {
    private final MainActivity mActivity;
    private boolean mIsNewDataMode = false, mIsEditMode = false;
    private final Button mBtnNewData, mBtnEditData;
    private final EditText mEditText;
    private ArrayAdapter<ArrayList<Double>> mAdapter;

    public ModeHandler(MainActivity activity){
        this.mActivity = activity;
        this.mBtnNewData = activity.findViewById(R.id.idBtnNewData);
        this.mBtnEditData = activity.findViewById(R.id.idBtnEditData);
        this.mEditText = activity.findViewById(R.id.idEditText);
    }

    public void setAdapter(ArrayAdapter<ArrayList<Double>> adapter){
        this.mAdapter = adapter;
    }

    /**
     * Toggles new Data mode state: Changes UI to indicate NewDataMode. Changes button text, disables "Edit" button and refreshes the adapter.
     * Leaving NewDataMode sets "Edit" button back to enabled and resets the editText.
     */
    public void changeNewDataMode() {
        mIsNewDataMode = !mIsNewDataMode;
        mBtnNewData.setText(mIsNewDataMode ? "Cancel" : "New Data");
        mBtnEditData.setEnabled(false);
        mBtnEditData.setBackgroundColor(Color.LTGRAY);

        changeTextVisibility(mIsNewDataMode);

        if (mIsNewDataMode) {
            mEditText.setText("");
        }

        if (!mIsNewDataMode) {
            mBtnEditData.setEnabled(true);
            mBtnEditData.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.statify_orange));
        }

        if(mAdapter != null){mAdapter.notifyDataSetChanged();}
    }

    /**
     * Toggles edit mode state: Changes UI to indicate EditMode. Changes button text, disables "New Data" button and refreshes the adapter.
     * Leaving EditMode sets "New Data" button back to enabled and hides editing indicators.
     */
    public void changeEditMode() {
        mIsEditMode = !mIsEditMode;
        mBtnEditData.setText(mIsEditMode ? "Done" : "Edit");
        mBtnNewData.setEnabled(false);
        mBtnNewData.setBackgroundColor(Color.LTGRAY);

        if (!mIsEditMode) {
            changeTextVisibility(false);
            mBtnNewData.setEnabled(true);
            mBtnNewData.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.statify_turquise));
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Changes the visibility of the EditText. It will be shown and focused, when visibility is true.
     *
     * @param visible Boolean indicating whether to show or to hide the EditText.
     */
    public void changeTextVisibility(boolean visible) {
        mEditText.setVisibility(visible ? View.VISIBLE : View.GONE);
        if (visible) {
            mEditText.requestFocus();
        }
    }

    //region Getter
    public boolean getIsNewDataMode() {
        return mIsNewDataMode;
    }

    public boolean getIsEditMode() {
        return mIsEditMode;
    }
    //endregion
}
