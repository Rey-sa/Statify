package app.dev.statify;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CalculationsActivity extends AppCompatActivity {

    private TextView mTrTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            setContentView(R.layout.activity_calculations);

            mTrTextView = findViewById(R.id.idTrText);
    }


}