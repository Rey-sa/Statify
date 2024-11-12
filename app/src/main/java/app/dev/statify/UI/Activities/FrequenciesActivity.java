package app.dev.statify.UI.Activities;

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

public class FrequenciesActivity extends AppCompatActivity {

    AnyChartView mAbsFreqChart;
    private ArrayList<Double> mSelectedData;

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

    public void onBackClick(View v){
        finish();
    }
}
