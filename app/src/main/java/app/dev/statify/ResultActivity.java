package app.dev.statify;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ResultActivity extends AppCompatActivity {


    private TableLayout mTableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            setContentView(R.layout.activity_results_layout);


            mTableLayout = findViewById(R.id.idTableLayout);

        ArrayList<Double> values = (ArrayList<Double>) getIntent().getSerializableExtra("values");
        ArrayList<Integer> frequencies = (ArrayList<Integer>) getIntent().getSerializableExtra("frequencies");

        TableRow valueRow = (TableRow) mTableLayout.getChildAt(0);
        TableRow freqRow = (TableRow)  mTableLayout.getChildAt(1);


        for(int i = 0; i < values.size(); i++){
            TableRow row = new TableRow(this);
            TextView valueText = new TextView(this);

            valueText.setText(String.valueOf(values.get(i)));
            valueText.setPadding(8, 8, 8, 8);
            valueText.setGravity(Gravity.CENTER);
            valueText.setBackgroundResource(R.drawable.cell_border);

            TextView frequencyText = new TextView(this);
            frequencyText.setText(String.valueOf(frequencies.get(i)));
            frequencyText.setPadding(8, 8, 8, 8);
            frequencyText.setGravity(Gravity.CENTER);
            frequencyText.setBackgroundResource(R.drawable.cell_border);


            valueRow.addView(valueText);
            freqRow.addView(frequencyText);


        }
    }





}