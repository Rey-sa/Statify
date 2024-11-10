package app.dev.statify.Handler;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import app.dev.statify.Activities.ClassificationActivity;

import java.util.ArrayList;

public class ClassifyInputHandler {

        private ClassificationActivity mActivity;

        public ClassifyInputHandler(ClassificationActivity activity){
            this.mActivity = activity;
        }

        public boolean handleKey(View v, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                String classLimitsText = mActivity.getClassLimitInput().getText().toString().trim();

                if(classLimitsText.isEmpty()){
                    throw new IllegalArgumentException("Class limits missing");
                }

                String[] inputClassLimits =classLimitsText.split(" ");
                ArrayList<Double> classLimits = new ArrayList<>();

                for(String limit : inputClassLimits){
                    limit = limit.replace(",", ".");
                    try{
                        classLimits.add(Double.parseDouble(limit.trim()));
                    } catch (NumberFormatException e){
                        e.printStackTrace();
                        return false;
                    }
                }

                ArrayList<Integer> classificationResults = classifyData(mActivity.getSelectedData(), classLimits);

                showClassificationResults(classificationResults);

                hideClassLimitInput();

                return true;

            }

            return false;

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
            Double dataValue = mActivity.getSelectedData().get(i);
            Integer classIndex = classificationResults.get(i);

            // Ausgabe jedes Datenwerts und der zugehörigen Klasse
            System.out.println("Wert: " + dataValue + " gehört zur Klasse: " + classIndex);
        }
    }

    private void hideClassLimitInput(){
            mActivity.getClassLimitInput().setVisibility(EditText.GONE);
    }

}
