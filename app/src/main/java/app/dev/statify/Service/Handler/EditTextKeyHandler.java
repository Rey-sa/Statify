package app.dev.statify.Service.Handler;

//region Imports
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import app.dev.statify.UI.Activities.MainActivity;
//endregion

/**
 * Handles key events for {@link EditText} input in {@link MainActivity}.
 * Listens for key events (enter) and processes the input.
 * Interacts with {@link SubmitHandler} to handle submission and manages data modes.
 */
public class EditTextKeyHandler{

    private final MainActivity mActivity;
    private final SubmitHandler mSubmitHandler;
    private ModeHandler mModeHandler;

    /**
     * Constructs {@link EditTextKeyHandler}.
     * @param activity {@link MainActivity} instance for interaction.
     * @param handler {@link SubmitHandler} for handling submissions.
     */
    public EditTextKeyHandler(MainActivity activity, SubmitHandler handler, ModeHandler modeHandler){
        this.mActivity = activity;
        this.mSubmitHandler = handler;
        this.mModeHandler = modeHandler;
    }

    /**
     * Handles key events for {@link EditText}.
     * Triggers appropriate action:
     * 1. Submitting Input via {@link SubmitHandler}.
     * 2. Toggling modes if necessary.
     * @param v {@link View} that triggered the event.
     * @param keyCode the key code of the pressed key (enter).
     * @param event {@link KeyEvent} representing the pressed key event.
     * @return {@code true} if key event was handled successfully otherwise {@code false}.
     */
    public boolean handleKey(View v, int keyCode, KeyEvent event){
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            mSubmitHandler.handleSubmit(mActivity.getEditText());
            mModeHandler = new ModeHandler(mActivity);
            if(mModeHandler.getIsNewDataMode()){
                mModeHandler.changeNewDataMode();
            }
            if(mModeHandler.getIsEditMode()){
                mModeHandler.changeEditMode();
            }

            return true;
        }
        return false;
    }

}
