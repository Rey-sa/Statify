package app.dev.statify.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import app.dev.statify.R;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class FrequenciesActivity extends AppCompatActivity {

    AnyChartView mAbsFreqChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_frequencies_layout);

        mAbsFreqChart = findViewById(R.id.idAbsFreqChart);

        ArrayList<Double> values = (ArrayList<Double>) getIntent().getSerializableExtra("values");
        ArrayList<Integer> frequencies = (ArrayList<Integer>) getIntent().getSerializableExtra("frequencies");


        if(values == null || frequencies == null){
            throw new IllegalArgumentException("Values,freq ore data are missing");
        }

        setUpPieChart(values, frequencies);

    }

    private void setUpPieChart(ArrayList<Double> values, ArrayList<Integer> frequencies){
        Pie pie = AnyChart.pie();
        List<DataEntry> chartItem = new ArrayList<>();

        for(int i = 0; i < values.size(); i++){
            String label = "Val: " + values.get(i) + " | Freq: " + frequencies.get(i);
            chartItem.add(new ValueDataEntry(label, frequencies.get(i)));
        }

        pie.data(chartItem);
        pie.title("Frequencies");
        pie.title().fontSize(30);
        pie.labels().fontSize(24);
        pie.labels().position("outside");
        pie.labels().rotation(0);
        pie.labels().format("{%Value}");
        pie.tooltip().fontSize(24);
        pie.legend().enabled(false);
        mAbsFreqChart.setChart(pie);
    }
}
