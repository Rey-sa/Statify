package app.dev.statify.Handler;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import app.dev.statify.Activities.ClassificationActivity;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import java.util.ArrayList;

public class ClassifyInputHandler {

    private ClassificationActivity mActivity;

    public ClassifyInputHandler(ClassificationActivity activity) {
        this.mActivity = activity;
    }

    public boolean handleKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

            // Find class Limits
            String classLimitsText = mActivity.getClassLimitInput().getText().toString().trim();

            if (classLimitsText.isEmpty()) {
                throw new IllegalArgumentException("Class limits missing");
            }

            String[] inputClassLimits = classLimitsText.split(" ");
            ArrayList<Double> classLimits = new ArrayList<>();

            // Parse class limits
            for (String limit : inputClassLimits) {
                limit = limit.replace(",", ".");
                try {
                    classLimits.add(Double.parseDouble(limit.trim()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            // Classify
            ArrayList<Integer> classificationResults = classifyData(mActivity.getSelectedData(), classLimits);

            // Set EditText to non visible
            hideClassLimitInputAndShowChart(classificationResults);

            return true;
        }

        return false;
    }


    private ArrayList<Integer> classifyData(ArrayList<Double> data, ArrayList<Double> classLimits) {
        ArrayList<Integer> classifications = new ArrayList<>();

        for (Double value : data) {
            int classIndex = -1;
            for (int i = 0; i < classLimits.size() - 1; i++) {
                if (value >= classLimits.get(i) && value < classLimits.get(i + 1)) {
                    classIndex = i;
                    break;
                }
            }
            classifications.add(classIndex);
        }

        return classifications;
    }


    private void hideClassLimitInputAndShowChart(ArrayList<Integer> classificationResults) {

        mActivity.getClassLimitInput().setVisibility(EditText.GONE);


        ArrayList<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < classificationResults.size(); i++) {
            Integer classIndex = classificationResults.get(i);
            dataEntries.add(new ValueDataEntry("Class " + i, classIndex));
        }


        Cartesian cartesian = AnyChart.column();
        cartesian.column(dataEntries);


        AnyChartView chartView = mActivity.getChartView();
        chartView.setChart(cartesian);


        chartView.setVisibility(AnyChartView.VISIBLE);
    }
}
