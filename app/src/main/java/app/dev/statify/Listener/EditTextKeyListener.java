package app.dev.statify.Listener;

import android.view.KeyEvent;
import android.view.View;
import app.dev.statify.Handler.EditTextKeyHandler;

public class EditTextKeyListener implements View.OnKeyListener {

    private EditTextKeyHandler mKeyHandler;

    public EditTextKeyListener(EditTextKeyHandler keyHandler){
        this.mKeyHandler = keyHandler;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event){
        return mKeyHandler.handleKey(v, keyCode, event);
    }

}
