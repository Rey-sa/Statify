package app.dev.statify.UI.Activities;

//region Imports

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import app.dev.statify.R;
import app.dev.statify.Service.Calculations;

import java.util.ArrayList;
import java.util.Locale;
//endregion

/**
 * Activity for displaying GridView of results (statistical rows).
 * Allows the user to navigate to Frequencies and Classification.
 */
public class ResultActivity extends AppCompatActivity {

    private CardView mCard21;
    private CardView mCard30;
    private ArrayList<Double> selectedData;

    /**
     * Initializes the views, sets up listener, card content and loads any previously selected dataset, when activity is being created.
     *
     * @param savedInstanceState Bundle containing the activities previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.results_layout);

        selectedData = (ArrayList<Double>) getIntent().getSerializableExtra("selected_data");

        if (selectedData == null) {
            throw new IllegalArgumentException("Data is missing");
        }
        setUpCardContent(selectedData);

        mCard21 = findViewById(R.id.idCard21);
        mCard30 = findViewById(R.id.idCard30);

        setUpListeners();
    }

    /**
     * Sets up the different listeners for the Cards.
     * Calls Handlers to handle user interactions (card clicks).
     */
    private void setUpListeners() {
        mCard21.setOnClickListener(v -> goToFrequencies());
        mCard30.setOnClickListener(v -> goToClassify());
    }


    /**
     * Sets up the content for the Cards by finding the views via ID.  Calls Calculations and sets the result as TextView.
     *
     * @param selectedData Contains the previous selected Data set  as ArrayList of Double.
     */
    private void setUpCardContent(ArrayList<Double> selectedData) {
        TextView Card00, Card01, Card10, Card11, Card20;

        //Find Views
        Card00 = findViewById(R.id.idTextCard00);
        Card01 = findViewById(R.id.idTextCard01);
        Card10 = findViewById(R.id.idTextCard10);
        Card11 = findViewById(R.id.idTextCard11);
        Card20 = findViewById(R.id.idTextCard20);

        // Perform needed calculations
        double arithmetic = Calculations.calcArithmetic(selectedData);
        double median = Calculations.calcMedian(selectedData);
        double range = Calculations.calcRange(selectedData);
        double variance = Calculations.calcVariance(selectedData);
        double deviation = Calculations.calcDeviation(selectedData);

        // Setup text for Cards
        Card00.setText(String.format(Locale.US, "%.2f", arithmetic));
        Card01.setText(String.format(Locale.US, "%.2f", median));
        Card10.setText(String.format(Locale.US, "%.2f", range));
        Card11.setText(String.format(Locale.US, "%.2f", variance));
        Card20.setText(String.format(Locale.US, "%.2f", deviation));
    }


    /**
     * Handles CardClick event. Creates an Intent for starting FrequenciesActivity.
     */
    public void goToFrequencies() {

        Intent intent = new Intent(ResultActivity.this, FrequenciesActivity.class);
        intent.putExtra("selected_data", selectedData);

        startActivity(intent);
    }

    /**
     * Handles CardClick event. Creates an Intent for starting ClassificationActivity.
     */
    public void goToClassify() {
        Intent intent = new Intent(ResultActivity.this, ClassificationActivity.class);
        intent.putExtra("selected_data", selectedData);

        startActivity(intent);
    }

    /**
     * Handles back button click. Triggered when user clicks the "arrow button".
     * Finishes current activity and returns back to the previous screen.
     *
     * @param v View that was click (arrow button).
     */
    public void onBackClick(View v) {
        finish();
    }
}