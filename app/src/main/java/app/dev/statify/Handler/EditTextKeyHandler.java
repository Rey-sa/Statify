package app.dev.statify.Handler;

import android.view.KeyEvent;
import android.view.View;
import app.dev.statify.Activities.ListViewActivity;

public class EditTextKeyHandler{

    private ListViewActivity mActivity;
    private SubmitHandler mSubmitHandler;
    private boolean mIsNewDataMode;
    private boolean mIsEditMode;

    public EditTextKeyHandler(ListViewActivity activity, SubmitHandler handler){
        this.mActivity = activity;
        this.mSubmitHandler = handler;
    }


    public boolean handleKey(View v, int keyCode, KeyEvent event){
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            mSubmitHandler.handleSubmit(mActivity.getEditText());
            if(mIsNewDataMode){
                mActivity.changeNewDataMode();
            }
            if(mIsEditMode){
                mActivity.changeEditMode();
            }

            return true;
        }
        return false;
    }

}
