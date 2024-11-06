package app.dev.statify.SetupItems;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.core.content.ContextCompat;
import app.dev.statify.R;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<ArrayList<Double>> {

    private Context mContext;
    private boolean mIsEditMode;

    public Adapter(Context context, ArrayList<ArrayList<Double>> arrList, boolean isEditMode) {
        super(context, android.R.layout.simple_list_item_1, arrList);

        this.mContext = context;
        this.mIsEditMode = isEditMode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (mIsEditMode) {
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.statify_light_blue));
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        return view;
    }
}


