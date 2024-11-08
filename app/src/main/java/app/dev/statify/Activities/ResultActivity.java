package app.dev.statify.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import app.dev.statify.Calculations;
import app.dev.statify.Listener.OnClickListener;
import app.dev.statify.R;

import java.util.ArrayList;
import java.util.Locale;


public class ResultActivity extends AppCompatActivity {

    private CardView mCard21;
    private ArrayList<Double> selectedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            setContentView(R.layout.activity_results_layout);

            selectedData = (ArrayList<Double>) getIntent().getSerializableExtra("selected_data");

        if(selectedData == null){
            throw new IllegalArgumentException("Data is missing");
        }
        setUpCardContent(selectedData);


        mCard21 = findViewById(R.id.idCard21);
        OnClickListener listener = new OnClickListener(this);
        mCard21.setOnClickListener(listener);
    }


    private void setUpCardContent(ArrayList<Double> selectedData){
        TextView Card00, Card01, Card10, Card11, Card20, Card21;


        //Find Views
        Card00 =findViewById(R.id.idTextCard00);
        Card01=findViewById(R.id.idTextCard01);
        Card10 =findViewById(R.id.idTextCard10);
        Card11 =findViewById(R.id.idTextCard11);
        Card20 =findViewById(R.id.idTextCard20);
        Card21 =findViewById(R.id.idTextCard21);


        // Perform needed calculations
        double arithmetic = Calculations.calcArithmetic(selectedData);
        double median = Calculations.calcMedian(selectedData);
        double range = Calculations.calcRange(selectedData);
        double variance = Calculations.calcVariance(selectedData);
        double deviation = Calculations.calcDeviation(selectedData);

        // Setup text for Cards
        Card00.setText(String.format(Locale.US,"%.2f",arithmetic));
        Card01.setText(String.format(Locale.US,"%.2f",median));
        Card10.setText(String.format(Locale.US,"%.2f",range));
        Card11.setText(String.format(Locale.US,"%.2f",variance));
        Card20.setText(String.format(Locale.US,"%.2f",deviation));

    }


    public void handleCardClick(){

        Intent intent = new Intent(ResultActivity.this, FrequenciesActivity.class);
        intent.putExtra("selected_data", selectedData);

        startActivity(intent);
    }

}