package app.dev.statify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mBtnNewData, mBtnEditData;
    private ListView mListView;
    private ArrayList<ArrayList<Double>> mArrayList;
    private ArrayAdapter<ArrayList<Double>> mAdapter;
    SharedPreferences mStatRowSettings;
    private final String PREFS_NAME = "StatRowPrefs";
    private boolean isEditMode = false;

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

        Intent intent = new Intent(ListViewActivity.this, CalculationsActivity.class);
        startActivity(intent);
        Calculations.calcAbsFreq(mArrayList.get(position));
        mAdapter.notifyDataSetChanged();
    }

    // Convert Insertion into Gson for saving statistical row
    private void saveStatRows() {
        SharedPreferences.Editor editor = mStatRowSettings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mArrayList);
        editor.putString("arrayList_data", json);
        editor.apply();
    }

    // Reconvert Gson to ArrayList Element for loading last statistical rows
    private void loadStatRows() {
        Gson gson = new Gson();
        String json = mStatRowSettings.getString("arrayList_data", null);
        if (json != null) {
            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<ArrayList<ArrayList<Double>>>() {
            }.getType();
            mArrayList = gson.fromJson(json, type);
        } else {
            mArrayList = new ArrayList<>();
        }
    }

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

    private void initializeViews() {
        mEditText = findViewById(R.id.idEditText);
        mListView = findViewById(R.id.idListView);
        mBtnNewData = findViewById(R.id.idBtnNewData);
        mBtnEditData = findViewById(R.id.idBtnEditData);
        mStatRowSettings = getSharedPreferences(PREFS_NAME, 0);
    }

    private void setUpListeners() {

        mBtnNewData.setOnClickListener(v -> changeTextVisibility(true));
        mBtnEditData.setOnClickListener(v -> changeEditMode());

        mEditText.setOnKeyListener((v, keyCode, keyEvent) -> {
            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                handleSubmit();
                return true;
            }
            return false;
        });

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            if (isEditMode) {
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

                if (isEditMode) {
                    view.setBackgroundColor(Color.LTGRAY);
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
        isEditMode = !isEditMode;
        mBtnEditData.setText(isEditMode ? "Fertig" : "Datensatz bearbeiten");
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
