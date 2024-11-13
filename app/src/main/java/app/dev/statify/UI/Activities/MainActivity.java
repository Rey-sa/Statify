package app.dev.statify.UI.Activities;

//region Imports

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import app.dev.statify.Persistence.SaveLoadHandler;
import app.dev.statify.R;
import app.dev.statify.Service.Handler.EditTextKeyHandler;
import app.dev.statify.Service.Handler.ItemClickHandler;
import app.dev.statify.Service.Handler.ModeHandler;
import app.dev.statify.Service.Handler.SubmitHandler;
import app.dev.statify.UI.Adapter.Adapter;

import java.util.ArrayList;
//endregion


/**
 * Primary activity for managing and displaying a list of datasets (statistical rows).
 * Allows the user to add, edit and remove datasets from the list.
 * It supports switching between "edit" and "new data" Mode.
 */
public class MainActivity extends AppCompatActivity {

    //region Declaration instance variables
    private EditText mEditText;
    private Button mBtnNewData, mBtnEditData;
    private ListView mListView;
    private ArrayList<ArrayList<Double>> mArrayList;
    private ArrayAdapter<ArrayList<Double>> mAdapter;
    private SaveLoadHandler mSaveLoadManager;
    private SubmitHandler mSubmitHandler;
    private ModeHandler mModeHandler;
    private EditTextKeyHandler mEditTextKeyHandler;
    private ItemClickHandler mItemClickHandler;
    //endregion


    /**
     * Initializes the views, sets up listeners, sets up handlers and loads any previously saved datasets, when activity is being created.
     *
     * @param savedInstanceState Bundle containing the activities previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.main_layout);

        initializeViews();

        mModeHandler = new ModeHandler(this);
        mSaveLoadManager = new SaveLoadHandler(this);
        mArrayList = mSaveLoadManager.loadStatRows();

        setUpAdapters();

        mSubmitHandler = new SubmitHandler(this);
        mEditTextKeyHandler = new EditTextKeyHandler(this, mSubmitHandler, mModeHandler);
        mItemClickHandler = new ItemClickHandler(this, mArrayList, mAdapter);

        setUpListeners();
    }


    /**
     * Initializes the views by finding the right layouts for them.
     * Views are EditText, ListView, Buttons.
     */
    private void initializeViews() {
        mEditText = findViewById(R.id.idEditText);
        mListView = findViewById(R.id.idListView);
        mBtnNewData = findViewById(R.id.idBtnNewData);
        mBtnEditData = findViewById(R.id.idBtnEditData);
    }

    /**
     * Sets up the adapter for the ListView by linking the data to the UI.
     */
    private void setUpAdapters() {
        mAdapter = new Adapter(this, mArrayList, mModeHandler);
        mModeHandler.setAdapter(mAdapter);
        mListView.setAdapter(mAdapter);
    }

    /**
     * Sets up the different listeners for the EditText, buttons and ListView.
     * Calls Handlers to handle user interactions like button clicks etc.
     */
    private void setUpListeners() {

        mEditText.setOnKeyListener((v, keyCode, event) -> mEditTextKeyHandler.handleKey(v, keyCode, event));
        mEditText.setOnClickListener(v -> mSubmitHandler.handleSubmit(this.getEditText()));
        mBtnNewData.setOnClickListener(v -> mModeHandler.changeNewDataMode());
        mBtnEditData.setOnClickListener(v -> mModeHandler.changeEditMode());

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            if (mModeHandler.getIsEditMode()) {
                editItem(position);
            } else {
                mItemClickHandler.handleItemClick(position);
            }
        });

        mListView.setOnItemLongClickListener((parent, view, position, id) -> {
            removeItemFromList(position);
            return true;
        });
    }

    /**
     * Edits the selected dataset at the specified position of the list.
     * Dataset is converted into a string and displayed in the EditText view, allowing the user to change/remove values.
     * Once the dataset is displayed, it is removed from the original list and the adapter is notified of the change.
     *
     * @param position Position of the selected listItem which is to be edited.
     */
    private void editItem(int position) {
        ArrayList<Double> item = mArrayList.get(position);
        StringBuilder sb = new StringBuilder();
        for (Double d : item) {
            sb.append(d).append(" ");
        }
        mEditText.setText(sb.toString().trim());
        mEditText.setVisibility(View.VISIBLE);
        mEditText.requestFocus();
        mArrayList.remove(position);
        mAdapter.notifyDataSetChanged();
    }


    /**
     * Removes the selected dataset from the list and sends a confirmation toast.
     * Adapter is notified of the change.
     *
     * @param position Position of the selected listItem which is to be deleted.
     */
    private void removeItemFromList(int position) {
        mArrayList.remove(position);
        Toast.makeText(this, "Dataset removed", Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
        mSaveLoadManager.saveStatRows(mArrayList);
    }

    //region Getter

    /**
     * Gets the current EditText.
     *
     * @return The EditText (EditText)
     */
    public EditText getEditText() {
        return mEditText;
    }

    /**
     * Gets the current ArrayList.
     *
     * @return The list of datasets (ArrayList of ArrayList of Double)
     */
    public ArrayList<ArrayList<Double>> getArrayList() {
        return mArrayList;
    }

    /**
     * Gets the current ArrayAdapter.
     *
     * @return The Array of adapters (ArrayAdapter of ArrayList of Double)
     */
    public ArrayAdapter<ArrayList<Double>> getAdapter() {
        return mAdapter;
    }
    //endregion
}

