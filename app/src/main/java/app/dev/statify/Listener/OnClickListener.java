package app.dev.statify.Listener;

import android.view.View;
import android.widget.ListView;
import app.dev.statify.Activities.MainActivity;
import app.dev.statify.Activities.ResultActivity;
import app.dev.statify.Handler.SubmitHandler;
import app.dev.statify.R;


public class OnClickListener implements View.OnClickListener{

    private MainActivity mActivity;
    private ResultActivity mResultActivity;

    public OnClickListener(MainActivity activity){
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
            SubmitHandler submitHandler = new SubmitHandler(mActivity);
            submitHandler.handleSubmit(mActivity.getEditText());
        } else if (v instanceof ListView) {
            mActivity.handleItemClick(v.getId());
        } else if (v.getId() == R.id.idCard21) {
            mResultActivity.handleCardClick();
        } else if(v.getId() == R.id.idCard30){
            mResultActivity.goToClassify();
        }
    }
}




