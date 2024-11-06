package app.dev.statify;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import app.dev.statify.OCL.OnClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class ListViewActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mBtnNewData, mBtnEditData;
    private ListView mListView;

    private ArrayList<ArrayList<Double>> mArrayList;
    private ArrayAdapter<ArrayList<Double>> mAdapter;
    private SaveLoadManager mSaveLoadManager;
    private boolean mIsEditMode = false;
    private boolean mIsNewDataMode = false;
    private ArrayList<Double> selectedData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_listview);

        initializeViews();
        mSaveLoadManager = new SaveLoadManager(this);
        setUpListeners();
        loadStatRows();
        setUpAdapters();
    }

    private void loadStatRows(){
        mArrayList = mSaveLoadManager.loadStatRows();
    }

    private void saveStatRows(){
        mSaveLoadManager.saveStatRows(mArrayList);
    }

    public void handleSubmit() {
        String s = mEditText.getText().toString().trim();

        if (!s.isEmpty()) {

            ArrayList<String> inputArrayList = new ArrayList<>(Arrays.asList(s.split(" ")));
            ArrayList<Double> tempArrayList = new ArrayList<>();

            for (String ss : inputArrayList) {
                ss = ss.replace(",", ".");

                try {
                    tempArrayList.add(Double.parseDouble(ss.trim()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            if (!inputArrayList.isEmpty()) {
                mArrayList.add(tempArrayList);
                mAdapter.notifyDataSetChanged();
                mEditText.setText("");

                SaveLoadManager.saveStatRows(mArrayList);
            }
        }
    }

    public void handleItemClick(int position) {

        selectedData = mArrayList.get(position);
        TreeMap<Double,Integer> frequencyMap = Calculations.calcAbsFreq(selectedData);

        ArrayList<Double> values = new ArrayList<>(frequencyMap.keySet());
        ArrayList<Integer> frequencies = new ArrayList<>(frequencyMap.values());

        Intent intent = new Intent(ListViewActivity.this, ResultActivity.class);
        intent.putExtra("values", values);
        intent.putExtra("frequencies", frequencies);
        intent.putExtra("selected_data", selectedData);
        startActivity(intent);
        mAdapter.notifyDataSetChanged();

    }




    private void initializeViews() {
        mEditText = findViewById(R.id.idEditText);
        mListView = findViewById(R.id.idListView);
        mBtnNewData = findViewById(R.id.idBtnNewData);
        mBtnEditData = findViewById(R.id.idBtnEditData);
    }

    private void setUpListeners() {

        OnClickListener listener = new OnClickListener(this);

        mBtnNewData.setOnClickListener(listener);
        mBtnEditData.setOnClickListener(listener);
        mEditText.setOnClickListener(listener);

        mEditText.setOnKeyListener((v, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                handleSubmit();
                if(mIsNewDataMode){
                changeNewDataMode();
                }
                if(mIsEditMode){
                    changeEditMode();
                }

                return true;
            }
            return false;
        });

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            if (mIsEditMode) {
                editItem(position);
            } else {
                handleItemClick(position);
            }
        });



    }

    private void setUpAdapters() {
        mAdapter = new ArrayAdapter<ArrayList<Double>>(this, android.R.layout.simple_list_item_1, mArrayList) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                if (mIsEditMode) {
                    view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.statify_light_blue));
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                return view;
            }
        };
        mListView.setAdapter(mAdapter);
    }


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

        if(!mIsEditMode){
            changeTextVisibility(false);
            mBtnNewData.setEnabled(true);
            mBtnNewData.setBackgroundColor(ContextCompat.getColor(this, R.color.statify_turquise));
        }
    }

    public void changeNewDataMode(){
        mIsNewDataMode = !mIsNewDataMode;
        mBtnNewData.setText(mIsNewDataMode ? "Cancel" : "New Data");
        mBtnEditData.setEnabled(false);
        mBtnEditData.setBackgroundColor(Color.LTGRAY);

        changeTextVisibility(mIsNewDataMode);

        if(mIsNewDataMode){
            mEditText.setText("");
        }

        if(!mIsNewDataMode){
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



}
