package app.dev.statify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class ListViewActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mBtnNewData, mBtnEditData;
    private ListView mListView;
    private ArrayList<ArrayList<Double>> mArrayList;
    private ArrayAdapter<ArrayList<Double>> mAdapter;
    private SharedPreferences mStatRowSettings;
    private final String PREFS_NAME = "StatRowPrefs";
    private boolean mIsEditMode = false;
    private boolean mIsNewDataMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_listview);

        initializeViews();
        setUpListeners();
        loadStatRows();
        setUpAdapters();
    }

    private void handleSubmit() {
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

                saveStatRows();
            }
        }
    }

    private void handleItemClick(int position) {

        ArrayList<Double> selectedData = mArrayList.get(position);
        TreeMap<Double,Integer> frequencyMap = Calculations.calcAbsFreq(selectedData);
        double mean = Calculations.calcArithmetic(selectedData);
        Log.d("Test", "Ergebnis " + mean);

        ArrayList<Double> values = new ArrayList<>(frequencyMap.keySet());
        ArrayList<Integer> frequencies = new ArrayList<>(frequencyMap.values());

        Intent intent = new Intent(ListViewActivity.this, ResultActivity.class);
        intent.putExtra("values", values);
        intent.putExtra("frequencies", frequencies);
        startActivity(intent);
        mAdapter.notifyDataSetChanged();
    }

    // Convert Insertion into Gson for saving statistical row
    private void saveStatRows() {
        SharedPreferences.Editor editor = mStatRowSettings.edit();
        Gson gson = new Gson();

        ArrayList<ArrayList<Double>> lastEntries = getLastEntries(mArrayList);

        String json = gson.toJson(lastEntries);
        editor.putString("arrayList_data", json);
        editor.apply();

        mArrayList.clear();
        mArrayList.addAll(lastEntries);

    }

    private ArrayList<ArrayList<Double>> getLastEntries(ArrayList<ArrayList<Double>> orgList){
        int size = orgList.size();
        int index = Math.max(0, size - 5);
        return new ArrayList<>(orgList.subList(index,size));
    }

    // Reconvert Gson to ArrayList Element for loading last statistical rows
    private void loadStatRows() {

        String json = mStatRowSettings.getString("arrayList_data", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ArrayList<Double>>>() {}.getType();
            mArrayList = gson.fromJson(json, type);
        } else {
            mArrayList = new ArrayList<>();
        }
    }

    private void initializeViews() {
        mEditText = findViewById(R.id.idEditText);
        mListView = findViewById(R.id.idListView);
        mBtnNewData = findViewById(R.id.idBtnNewData);
        mBtnEditData = findViewById(R.id.idBtnEditData);
        mStatRowSettings = getSharedPreferences(PREFS_NAME, 0);
    }

    private void setUpListeners() {

        mBtnNewData.setOnClickListener(v -> changeNewDataMode());
        mBtnEditData.setOnClickListener(v -> changeEditMode());

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

    private void changeEditMode() {
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

    private void changeNewDataMode(){
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
