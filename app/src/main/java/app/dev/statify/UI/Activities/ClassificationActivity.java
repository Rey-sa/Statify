package app.dev.statify.UI.Activities;

//region Imports
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import app.dev.statify.Service.Handler.ClassifyInputHandler;
import app.dev.statify.R;
import com.anychart.AnyChartView;
import java.util.ArrayList;
//endregion

/**
 *  Activity for managing and displaying Classification of selected datasets (statistical rows).
 *  Allows the user to add class limits and displays classified data in bar chart.
 */
public class ClassificationActivity extends AppCompatActivity {

    private ArrayList<Double> mSelectedData;
    private EditText mClassLimitInput;
    private ClassifyInputHandler mClassifyInputHandler;
    private AnyChartView mBarDiagram;

    /**
     * Initializes the views, sets up listener and loads any previously selected dataset, when activity is being created.
     * @param savedInstanceState Bundle containing the activities previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.classification_layout);

        mSelectedData = (ArrayList<Double>) getIntent().getSerializableExtra("selected_data");

        if (mSelectedData == null) {
            throw new IllegalArgumentException("Selected Data is missing");
        }

        mClassLimitInput = findViewById(R.id.idClassLimitInput);
        mBarDiagram = findViewById(R.id.idClassificationBar);

        mClassifyInputHandler = new ClassifyInputHandler(this);

        mClassLimitInput.setOnKeyListener(mClassifyInputHandler::handleKey);

    }

    /**
     * Handles back button click. Triggered when user clicks the "arrow button".
     * Finishes current activity and returns back to the previous screen.
     *
     * @param v View that was clicked (arrow button).
     */
    public void onBackClick(View v) {
        finish();
    }

    //region Getter

    /**
     * Gets the current ClassLimitInput.
     * @return ClassLimitInput (EditText)
     */
    public EditText getClassLimitInput() {
        return mClassLimitInput;
    }

    /**
     * Gets current selectedData.
     *
     * @return a list of selectedData (ArrayList of Double).
     */
    public ArrayList<Double> getSelectedData() {
        return mSelectedData;
    }

    /**
     * Gets current ChartView
     * @return BarDiagramm
     */
    public AnyChartView getChartView() {
        return mBarDiagram;
    }
    //endregion
}
