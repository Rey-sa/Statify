package app.dev.statify.UI.Activities;

//region Imports
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import app.dev.statify.Service.Calculations;
import app.dev.statify.R;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.LegendLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
//endregion

/**
 * Activity for managing and displaying Frequencies(absolute/relative) of selected datasets (statistical rows).
 * Allows the user switch between showing absolute and relative
 *
 */
public class FrequenciesActivity extends AppCompatActivity {

    AnyChartView mAbsFreqChart;
    private ArrayList<Double> mSelectedData;

    /**
     * Initializes the views loads any previously selected dataset and calls calculations for Frequencies and calls setUpPieChart
     * when activity is being created.
     * @param savedInstanceState Bundle containing the activities previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.frequencies_layout);

        mSelectedData = (ArrayList<Double>) getIntent().getSerializableExtra("selected_data");

        if(mSelectedData == null){
            throw new IllegalArgumentException("Selected Data is missing");
        }

        TreeMap<Double,Integer> freqMap = Calculations.calcAbsFreq(mSelectedData);
        setUpPieChart(freqMap);
    }

    /**
     * Sets up Pie Chart with given freqMap and formats labels, title and tooltip.
     * @param freqMap TreeMap containing absolute frequencies of the values from selectedDataset (statistical row)
     */
    private void setUpPieChart(TreeMap<Double, Integer> freqMap){
        Pie pie = AnyChart.pie();

        List<DataEntry> chartItem = new ArrayList<>();
        for(Map.Entry<Double,Integer> entry : freqMap.entrySet()){
            chartItem.add(new ValueDataEntry(entry.getKey().toString(), entry.getValue()));
        }

        pie.data(chartItem);
        mAbsFreqChart = findViewById(R.id.idAbsFreqChart);

        pie.labels().fontSize(24)
            .position("outside")
            .rotation(0)
            .format("{%Value}");

        pie.tooltip().fontSize(24);
        pie.title("Frequencies");

        pie.legend()
                .position("top")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align("center");

        mAbsFreqChart.setChart(pie);
    }

    /**
     * Handles back button click. Triggered when user clicks the "arrow button".
     * Finishes current activity and returns back to the previous screen.
     *
     * @param v View that was click (arrow button).
     */
    public void onBackClick(View v){
        finish();
    }
}
