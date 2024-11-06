package app.dev.statify.OCL;

import android.view.View;
import android.widget.ListView;
import app.dev.statify.ListViewActivity;
import app.dev.statify.R;
import app.dev.statify.ResultActivity;


public class OnClickListener implements View.OnClickListener{

    private ListViewActivity mActivity;
    private ResultActivity mResultActivity;

    public OnClickListener(ListViewActivity activity){
        this.mActivity = activity;
    }

    public OnClickListener(ResultActivity activity){
        this.mResultActivity = activity;
    }

    @Override
    public void onClick(View v){

        if(v.getId() == R.id.idBtnNewData){
            mActivity.changeNewDataMode();
        } else if (v.getId() == R.id.idBtnEditData) {
            mActivity.changeEditMode();
        } else if (v.getId() == R.id.idEditText) {
            mActivity.handleSubmit();
        } else if (v instanceof ListView) {
            mActivity.handleItemClick(v.getId());
        } else if (v.getId() == R.id.idCard21) {
            mResultActivity.handleCardClick();
        }
    }
}




