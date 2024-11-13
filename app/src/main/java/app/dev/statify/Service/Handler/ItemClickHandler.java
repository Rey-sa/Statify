package app.dev.statify.Service.Handler;

//region Imports
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import app.dev.statify.UI.Activities.ResultActivity;
import java.util.ArrayList;
//endregion

/**
 * Handles ItemClicks within ListView in }MainActivity and starts ResultActivity.
 */
public class ItemClickHandler {

    private final Context mContext;
    private final ArrayList<ArrayList<Double>> mArrayList;
    private final ArrayAdapter<ArrayList<Double>> mAdapter;

    /**
     * Constructs {@link ItemClickHandler}. Initializes necessary params for handling
     * item click behavior within ListView in MainActivity.
     *
     * @param context Context of Activity where the handler is used (MainActivity).
     * @param arrayList containing the data that is displayed in ListView in MainActivity.
     * @param adapter associated with ListView in MainActivity.
     */
    public ItemClickHandler(Context context, ArrayList<ArrayList<Double>> arrayList, ArrayAdapter<ArrayList<Double>> adapter){
        this.mContext = context;
        this.mArrayList = arrayList;
        this.mAdapter = adapter;
    }

    /**
     * Handles the click event of an item within the list. Navigates to "ResultActivity".
     * @param position Position of the selected Item within listView in MainActivity.
     */
    public void handleItemClick(int position){
        ArrayList<Double> selectedData = mArrayList.get(position);

        Intent intent = new Intent(mContext, ResultActivity.class);
        intent.putExtra("selected_data", selectedData);

        mContext.startActivity(intent);
        mAdapter.notifyDataSetChanged();
    }
}
