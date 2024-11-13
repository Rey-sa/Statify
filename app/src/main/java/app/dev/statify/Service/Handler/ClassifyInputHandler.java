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

/**
 * Handles key events for classificaton related input from {@link ClassificationActivity}.
 * Listens for key events on {@link EditText} and classifies when Enter is pressed.
 */
public class ClassifyInputHandler {

    private final ClassificationActivity mActivity;

    /**
     * Constructs {@link ClassifyInputHandler}.
     *
     * @param activity {@link ClassificationActivity} instance to interact with.
     */
    public ClassifyInputHandler(ClassificationActivity activity) {
        this.mActivity = activity;
    }

    /**
     * Handles key events (enter) for class limit input.
     * Validates and parses the class limits, calls classifyData and hands over parsed classLimits.
     * Updates UI by hiding input field and displaying Barchart.
     *
     * @param v       {@link EditText} that triggered the event in {@link ClassificationActivity}.
     * @param keyCode key code of the pressed key.
     * @param event   {@link KeyEvent} representing the key press event.
     * @return {@code true} if key event was handled successfully otherwise {@code false}.
     * @throws IllegalArgumentException if class limits are missing or invalid
     */
    public boolean handleKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

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

            ArrayList<Integer> classificationResults = classifyData(mActivity.getSelectedData(), classLimits);

            hideClassLimitInputAndShowChart(classificationResults, classLimits);

            return true;
        }
        return false;
    }

    /**
     * Classifies selected data based on parsed class limits.
     *
     * @param selectedData ArrayList of Double containing values from chosen statistical row.
     * @param classLimits  parsed class limits (entered previously by user)
     * @return ArrayList of Integer containing counted elements by class.
     */
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

    /**
     * Hides {@link EditText} in {@link ClassificationActivity} and displays {@link AnyChartView} with results.
     *
     * @param classificationResults ArrayList of Integer containing classification results. Each entry corresponds to a class index.
     * @param classLimits           ArrayList of Double representing class limits.
     */

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

            Cartesian cartesian = AnyChart.column();
            Column column = cartesian.column(dataEntries);

            column.tooltip().format("Count: {%Value}");

            AnyChartView chartView = mActivity.getChartView();
            chartView.setChart(cartesian);
            chartView.setVisibility(AnyChartView.VISIBLE);
        }
    }
}