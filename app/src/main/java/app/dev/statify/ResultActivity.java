package app.dev.statify;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import app.dev.statify.OCL.OnClickListener;

import java.util.ArrayList;
import java.util.Locale;


public class ResultActivity extends AppCompatActivity {

    private TableLayout mTableLayout;
    private CardView mCard21;

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
        ArrayList<Double> selectedData = (ArrayList<Double>) getIntent().getSerializableExtra("selected_data");

        if(values == null || frequencies == null || selectedData == null){
            throw new IllegalArgumentException("Values,freq ore data are missing");
        }

        TableRow valueRow = (TableRow) mTableLayout.getChildAt(0);
        TableRow freqRow = (TableRow)  mTableLayout.getChildAt(1);


        for(int i = 0; i < values.size(); i++){

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
        setUpCardContent(selectedData);

        mCard21 = findViewById(R.id.idCard21);
        OnClickListener listener = new OnClickListener(this);
        mCard21.setOnClickListener(listener);
    }


    private void setUpCardContent(ArrayList<Double> selectedData){
        TextView Card00, Card01, Card10, Card11, Card20, Card21;


        Card00 =findViewById(R.id.idTextCard00);
        Card01=findViewById(R.id.idTextCard01);
        Card10 =findViewById(R.id.idTextCard10);
        Card11 =findViewById(R.id.idTextCard11);
        Card20 =findViewById(R.id.idTextCard20);
        Card21 =findViewById(R.id.idTextCard21);


        double arithmetic = Calculations.calcArithmetic(selectedData);
        double median = Calculations.calcMedian(selectedData);
        double range = Calculations.calcRange(selectedData);
        double variance = Calculations.calcVariance(selectedData);
        double deviation = Calculations.calcDeviation(selectedData);


        Card00.setText(String.format(Locale.US,"%.2f",arithmetic));
        Card01.setText(String.format(Locale.US,"%.2f",median));
        Card10.setText(String.format(Locale.US,"%.2f",range));
        Card11.setText(String.format(Locale.US,"%.2f",variance));
        Card20.setText(String.format(Locale.US,"%.2f",deviation));

    }


    public void handleCardClick(){
        Toast.makeText(this, "Card21 wurde geklickt", Toast.LENGTH_SHORT).show();
    }

}