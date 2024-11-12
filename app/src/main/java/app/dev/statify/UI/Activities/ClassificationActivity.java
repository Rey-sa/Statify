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

public class ClassificationActivity extends AppCompatActivity {

    private ArrayList<Double> mSelectedData;
    private EditText mClassLimitInput;
    private ClassifyInputHandler mClassifyInputHandler;
    private AnyChartView mBarDiagramm;

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
        mBarDiagramm = findViewById(R.id.idClassificationBar);

        mClassifyInputHandler = new ClassifyInputHandler(this);

        mClassLimitInput.setOnKeyListener(mClassifyInputHandler::handleKey);

    }


    //region Getter
    public EditText getClassLimitInput() {
        return mClassLimitInput;
    }

    public ArrayList<Double> getSelectedData() {
        return mSelectedData;
    }

    public AnyChartView getChartView() {
        return mBarDiagramm;
    }

    public void onBackClick(View v) {
        finish();
    }
    //endregion
}
