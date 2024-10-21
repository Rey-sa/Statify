package app.dev.statify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewActivity extends AppCompatActivity {

    private EditText mEditText;
   // private Button mButton;
    private ListView mListView;
    private ArrayList<ArrayList<Double>> mArrayList;
    private ArrayAdapter<ArrayList<Double>> mAdapter;
    SharedPreferences mStatRowSettings;
    private final String PREFS_NAME = "StatRowPrefs";

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
    private void saveStatRows(){
        SharedPreferences.Editor editor = mStatRowSettings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mArrayList);
        editor.putString("arrayList_data", json);
        editor.apply();
    }

    // Reconvert Gson to ArrayList Element for loading last statistical rows
    private void loadStatRows(){
        Gson gson = new Gson();
        String json = mStatRowSettings.getString("arrayList_data", null);
        if(json != null) {
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
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_listview);

        //Initialize instance Variables
        mEditText = findViewById(R.id.idEditText);
        mListView = findViewById(R.id.idListView);
        mStatRowSettings = getSharedPreferences(PREFS_NAME, 0);

        loadStatRows();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mArrayList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener((parent, view, position, id) -> handleItemClick(position));

        mEditText.setOnKeyListener((v, keyCode, keyEvent) -> {
            if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                handleSubmit();
                return true;
            }
            return false;
        });
    }
}
