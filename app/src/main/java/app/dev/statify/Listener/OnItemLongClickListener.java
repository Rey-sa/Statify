package app.dev.statify.Listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import app.dev.statify.Activities.MainActivity;

public class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {

    private MainActivity mActivity;

    public OnItemLongClickListener(MainActivity activity){
        this.mActivity = activity;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
       mActivity.removeItemFromList(position);
        Toast.makeText(mActivity, "Dataset removed", Toast.LENGTH_SHORT).show();
       return true;
    }
}
