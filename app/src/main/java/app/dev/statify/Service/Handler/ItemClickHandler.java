package app.dev.statify.Service.Handler;

//region Imports

import android.content.Intent;
import app.dev.statify.UI.Activities.MainActivity;
import app.dev.statify.UI.Activities.ResultActivity;

import java.util.ArrayList;
//endregion

/**
 * Handles ItemClicks within ListView in }MainActivity and starts ResultActivity.
 */
public class ItemClickHandler {

    private final MainActivity mMainActivity;

    /**
     * Constructs {@link ItemClickHandler}. Initializes necessary params for handling
     * item click behavior within ListView in MainActivity.
     *
     * @param activity The MainActivity instance where the handler ist used.
     */
    public ItemClickHandler(MainActivity activity){
        this.mMainActivity = activity;
    }

    /**
     * Handles the click event of an item within the list. Navigates to "ResultActivity".
     * @param position Position of the selected Item within listView in MainActivity.
     */
    public void handleItemClick(int position){
        ArrayList<Double> selectedData = mMainActivity.getArrayList().get(position);

        Intent intent = new Intent(mMainActivity, ResultActivity.class);
        intent.putExtra("selected_data", selectedData);

        mMainActivity.startActivity(intent);
        mMainActivity.getAdapter().notifyDataSetChanged();
    }
}
