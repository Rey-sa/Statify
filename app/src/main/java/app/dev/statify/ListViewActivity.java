package app.dev.statify;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewActivity extends AppCompatActivity {

    private EditText mEditText;
   // private Button mButton;
    private ListView mListView;
    private ArrayList<ArrayList<Double>> mArrayList;
    private ArrayAdapter<ArrayList<Double>> mAdapter;

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
            }
        }
    }

    private void handleItemClick(int position) {
//        mArrayList.remove(position);
        Intent intent = new Intent(ListViewActivity.this, CalculationsActivity.class);
        startActivity(intent);
        Calculations.calcAbsFreq(mArrayList.get(position));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        //Initialize instance Variables
        mEditText = findViewById(R.id.idEditText);
        // mButton = findViewById(R.id.idButton);
        mListView = findViewById(R.id.idListView);;
        mArrayList = new ArrayList<>();;
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mArrayList);


       //  mButton.setOnClickListener(v -> handleSubmit());

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
