package app.dev.statify.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import app.dev.statify.R;

import java.util.ArrayList;

public class ClassificationActivity extends AppCompatActivity {

    private ArrayList<Double> mSelectedData;
    private EditText mClassLimitInput;
    private Button mClassifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.classification_layout);

        mSelectedData = (ArrayList<Double>) getIntent().getSerializableExtra("selected_data");

        if(mSelectedData == null){
            throw new IllegalArgumentException("Selected Data is missing");
        }

        mClassLimitInput = findViewById(R.id.idClassLimitInput);
        mClassifyButton = findViewById(R.id.idClassifyButton);

        mClassifyButton.setOnClickListener(view -> {
            String classLimitsText = mClassLimitInput.getText().toString().trim();

            if (classLimitsText.isEmpty()){
                throw new IllegalArgumentException("Classlimits are missing!");
            }

            // Parse classlimits seperated by space

            String[] inputClassLimits = classLimitsText.split(" ");
            ArrayList<Double> classLimits = new ArrayList<>();


                for(String limit : inputClassLimits){
                    limit = limit.replace(",", ".");

                    try {
                    classLimits.add(Double.parseDouble(limit.trim()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }
                }

            ArrayList<Integer> classificationResults= classifyData(mSelectedData, classLimits);

            showClassificationResults(classificationResults);

        });

    }

    private ArrayList<Integer> classifyData(ArrayList<Double> data, ArrayList<Double> classLimits){
        ArrayList<Integer> classifications = new ArrayList<>();

        for(Double value : data){
            int classIndex = -1;
            for (int i = 0; i < classLimits.size() -1; i++){
                if(value >= classLimits.get(i) && value < classLimits.get(i + 1)){
                    classIndex = i;
                    break;
                }
            }
            classifications.add(classIndex);
        }
        return classifications;
    }

    private void showClassificationResults(ArrayList<Integer> classificationResults) {
        // Ausgabe der Klassifizierungsergebnisse mit System.out.println
        System.out.println("Klassifizierungsergebnisse:");

        for (int i = 0; i < classificationResults.size(); i++) {
            Double dataValue = mSelectedData.get(i);
            Integer classIndex = classificationResults.get(i);

            // Ausgabe jedes Datenwerts und der zugehörigen Klasse
            System.out.println("Wert: " + dataValue + " gehört zur Klasse: " + classIndex);
        }
    }
}
