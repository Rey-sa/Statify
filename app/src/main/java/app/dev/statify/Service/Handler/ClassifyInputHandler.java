package app.dev.statify.Service.Handler;
//region Imports

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import app.dev.statify.UI.Activities.ClassificationActivity;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;

import java.util.ArrayList;
//endregion

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
            hideClassLimitInputAndShowChart(classificationResults, classLimits);

            return true;
        }

        return false;
    }


    private ArrayList<Integer> classifyData(ArrayList<Double> selectedData, ArrayList<Double> classLimits) {
        ArrayList<Integer> classifications = new ArrayList<>();

        for (Double value : selectedData) {
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


    private void hideClassLimitInputAndShowChart(ArrayList<Integer> classificationResults, ArrayList<Double> classLimits) {

        mActivity.getClassLimitInput().setVisibility(EditText.GONE);

        ArrayList<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < classLimits.size() - 1; i++) {
            String classRange = classLimits.get(i) + " - " + classLimits.get(i + 1);

            int count = 0;
            for (Integer classIndex : classificationResults) {
                if (classIndex == i) {
                    count++;
                }
            }
            dataEntries.add(new ValueDataEntry(classRange, count));
            System.out.println("Hinzugefügter Eintrag: " + classRange + " = " + count);

            Cartesian cartesian = AnyChart.column();
            Column column = cartesian.column(dataEntries);

            column.tooltip()
                    .format("Count: {%Value}");

            AnyChartView chartView = mActivity.getChartView();
            chartView.setChart(cartesian);
            chartView.setVisibility(AnyChartView.VISIBLE);



        }
    }
}
