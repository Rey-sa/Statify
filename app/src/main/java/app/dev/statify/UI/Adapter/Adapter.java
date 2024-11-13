package app.dev.statify.UI.Adapter;

//region Imports

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.core.content.ContextCompat;
import app.dev.statify.R;
import app.dev.statify.Service.Handler.ModeHandler;

import java.util.ArrayList;
//endregion

/**
 * Customized ArrayAdapter used for displaying a list of ArrayList<Double> objects.
 * Supports "Edit Mode" where background color of item is changed to indicate that items can be edited.
 */
public class Adapter extends ArrayAdapter<ArrayList<Double>> {

    private final ModeHandler mModehandler;

    /**
     * Constructs new Adapter.
     *
     * @param context    Activity in which the {@code Adapter} ist being used.
     * @param arrList    List of data to be displayed.
     * @param modeHandler xxxx
     */
    public Adapter(Context context, ArrayList<ArrayList<Double>> arrList, ModeHandler modeHandler) {
        super(context, android.R.layout.simple_list_item_1, arrList);

        this.mModehandler = modeHandler;
    }

    /**
     * Overrides getView to return a view for a dataset (statistical row) in the list at the specified position.
     * Appearance is customized based on EditMode/!EditMode.
     *
     * @param position    Position of the dataset within the adapter.
     * @param convertView Recycled View to be reused or Null.
     * @param parent      Parent view that this view will be attached to.
     * @return View for dataset at position with customized background.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (mModehandler !=null && mModehandler.getIsEditMode()) {
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.statify_light_blue));
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        return view;
    }
}


