package app.dev.statify.Listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import app.dev.statify.Activities.ListViewActivity;

public class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {

    private ListViewActivity mActivity;

    public OnItemLongClickListener(ListViewActivity activity){
        this.mActivity = activity;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
       mActivity.removeItemFromList(position);
        Toast.makeText(mActivity, "Dataset removed", Toast.LENGTH_SHORT).show();
       return true;
    }
}
