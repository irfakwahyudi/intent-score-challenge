package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import static id.putraprima.skorbola.MatchActivity.STATUS_KEY;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView winner = findViewById(R.id.textView3);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            winner.setText(extras.getString(STATUS_KEY));
        }
    }
}
