package deakinsit305.chalani.task31c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

public class ResultActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView congratsText = findViewById(R.id.congratsText);
        SharedPreferences prefs = getSharedPreferences("quizApp", MODE_PRIVATE);
        String name = prefs.getString("username", "User");
        congratsText.setText("Congratulations " + name + "!");

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);

        TextView resultText = findViewById(R.id.resultText);
        Button retryBtn = findViewById(R.id.retryBtn);
        Button finishBtn = findViewById(R.id.finishBtn);

        resultText.setText("Score: " + score + "/" + total);

        retryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        finishBtn.setOnClickListener(v -> finishAffinity());
    }
}
