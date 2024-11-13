package app.dev.statify.UI.Activities;

//region Imports

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import app.dev.statify.Persistence.SaveLoadHandler;
import app.dev.statify.R;
import app.dev.statify.Service.Handler.EditTextKeyHandler;
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
    private boolean mIsEditMode = false;
    private boolean mIsNewDataMode = false;
    private SubmitHandler mSubmitHandler;
    private EditTextKeyHandler mEditTextKeyHandler;
    //endregion


    /**
     * Inizializes the views, sets up listeners and loads any previously saved datasets, when activity is being created.
     * @param savedInstanceState Bundle containing the activities previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSaveLoadManager = new SaveLoadHandler(this);
        mSubmitHandler = new SubmitHandler(this);
        mEditTextKeyHandler = new EditTextKeyHandler(this, mSubmitHandler);
        mArrayList = mSaveLoadManager.loadStatRows();

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.main_layout);

        initializeViews();
        setUpListeners();
        setUpAdapters();
    }

    /**
     * Handles the click event of an item wihtin the list. Either opens the clicked item in "edit" mode or navigates to "ResultActivity".
     * @param position Position of the item that was clicked (selected Item).
     */
    public void handleItemClick(int position) {

        ArrayList<Double> selectedData = mArrayList.get(position);

        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("selected_data", selectedData);
        startActivity(intent);
        mAdapter.notifyDataSetChanged();

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
     * Sets up the different listeners for the EditText, buttons and ListView.
     * Calls Handlers to handle user interactions like button clicks etc.
     */
    private void setUpListeners() {

        mEditText.setOnKeyListener((v, keyCode, event) -> mEditTextKeyHandler.handleKey(v, keyCode, event));
        mEditText.setOnClickListener(v -> mSubmitHandler.handleSubmit(this.getEditText()));
        mBtnNewData.setOnClickListener(v -> changeNewDataMode());
        mBtnEditData.setOnClickListener(v -> changeEditMode());

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            if (mIsEditMode) {
                editItem(position);
            } else {
                handleItemClick(position);
            }
        });

        mListView.setOnItemLongClickListener((parent, view, position, id) -> {
            removeItemFromList(position);
            return true;
        });
    }

    /**
     * Sets up the adapter for the ListView by linking the data to the UI.
     */
    private void setUpAdapters() {
        mAdapter = new Adapter(this, mArrayList, mIsEditMode);
        mListView.setAdapter(mAdapter);
    }

    /**
     * Changes the visibility of the EditText. It will be shown and focused, when visibility is true.
     *
     * @param visible Boolean indicating whether to show or to hide the EditText.
     */
    private void changeTextVisibility(boolean visible) {
        mEditText.setVisibility(visible ? View.VISIBLE : View.GONE);
        if (visible) {
            mEditText.requestFocus();
        }
    }

    public void changeEditMode() {
        mIsEditMode = !mIsEditMode;
        mBtnEditData.setText(mIsEditMode ? "Done" : "Edit");
        mAdapter.notifyDataSetChanged();

        mBtnNewData.setEnabled(false);
        mBtnNewData.setBackgroundColor(Color.LTGRAY);

        if (!mIsEditMode) {
            changeTextVisibility(false);
            mBtnNewData.setEnabled(true);
            mBtnNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.statify_turquise));
        }
    }

    public void changeNewDataMode() {
        mIsNewDataMode = !mIsNewDataMode;
        mBtnNewData.setText(mIsNewDataMode ? "Cancel" : "New Data");
        mBtnEditData.setEnabled(false);
        mBtnEditData.setBackgroundColor(Color.LTGRAY);

        changeTextVisibility(mIsNewDataMode);

        if (mIsNewDataMode) {
            mEditText.setText("");
        }

        if (!mIsNewDataMode) {
            mBtnEditData.setEnabled(true);
            mBtnEditData.setBackgroundColor(ContextCompat.getColor(this, R.color.statify_orange));
        }

        mAdapter.notifyDataSetChanged();
    }

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

    private void removeItemFromList(int position) {
        mArrayList.remove(position);
        Toast.makeText(this, "Dataset removed", Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
        mSaveLoadManager.saveStatRows(mArrayList);
    }

    //region Getter
    public EditText getEditText() {
        return mEditText;
    }

    public ArrayList<ArrayList<Double>> getArrayList() {
        return mArrayList;
    }

    public ArrayAdapter<ArrayList<Double>> getAdapter() {
        return mAdapter;
    }

    //endregion
}

