package app.dev.statify.UI.Activities;

//region Imports

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import app.dev.statify.Persistence.SaveLoadHandler;
import app.dev.statify.R;
import app.dev.statify.Service.Handler.*;
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
    private EditItemHandler mEditItemHandler;
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
        mEditItemHandler = new EditItemHandler(this);

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
                mEditItemHandler.editItem(position);
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

