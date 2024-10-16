package app.dev.statify;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;
    private ListView mListView;
    private ArrayList<Double> mArrayList;
    private ArrayAdapter<Double> mAdapter;

//    private void handleClick(){
//        String s;
//
//        s = mEditText.getText().toString().trim();
//        if(!s.isEmpty()){
//            mArrayList.add(s);
//            mAdapter.notifyDataSetChanged();
//            mEditText.setText("");
//        }
//
//    }

    private void handleSubmit(){
        String s = mEditText.getText().toString().trim();

        if(!s.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(s.split(" ")));
            for (String ss : arrayList) {
                ss = ss.replace(",", ".");
                mArrayList.add(Double.parseDouble(ss.trim()));
            }
        }

    }

    private void handleItemClick(int position){
        mArrayList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        mEditText = findViewById(R.id.idEditText);
        mButton = findViewById(R.id.idButton);
        mListView = findViewById(R.id.idListView);
        mArrayList = new ArrayList<>();

        mButton.setOnClickListener(v -> handleSubmit()); //handleClick());
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mArrayList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener((parent,view,position,id) -> handleItemClick(position));
    }

}
