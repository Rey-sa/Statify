package app.dev.statify.Service.Handler;

//region Imports
import android.widget.Toast;
import app.dev.statify.Persistence.SaveLoadHandler;
import app.dev.statify.UI.Activities.MainActivity;
//endregion

/**
 * Handles longItemClick events for ListView in MainActivity.
 * Removes ListItem.
 */
public class RemoveItemFromListHandler {

    private final MainActivity mMainActivity;

    /**
     *  Constructs {@link RemoveItemFromListHandler}. Initializes necessary params for handling
     *  removing behavior within ListView in MainActivity.
     * @param activity The MainActivity instance where the handler ist used.
     */
    public RemoveItemFromListHandler(MainActivity activity){
        this.mMainActivity = activity;
    }

    /**
     * Removes the selected dataset from the list and sends a confirmation toast.
     * Adapter is notified of the change.
     *
     * @param position Position of the selected listItem which is to be deleted.
     */
    public void removeItemFromList(int position){
        mMainActivity.getArrayList().remove(position);
        Toast.makeText(mMainActivity, "Dataset removed", Toast.LENGTH_SHORT).show();
        mMainActivity.getAdapter().notifyDataSetChanged();
        SaveLoadHandler mSaveLoadHandler = new SaveLoadHandler(mMainActivity);
        mSaveLoadHandler.saveStatRows(mMainActivity.getArrayList());
    }
}
