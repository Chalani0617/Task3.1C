package deakinsit305.chalani.task31c;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.content.SharedPreferences;


public class QuizActivity extends AppCompatActivity{
    TextView questionText;
    Button[] options = new Button [4];
    Button submitBtn;
    ProgressBar progressBar;
    TextView progressText;

    List<Question> questions;
    int currentIndex = 0;
    int selectedOption = -1;
    int score = 0;
    boolean isSubmitPhase = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        TextView welcomeText = findViewById(R.id.welcomeText);
        SharedPreferences prefs = getSharedPreferences("quizApp", MODE_PRIVATE);
        String name = prefs.getString("username", "User");
        welcomeText.setText("Welcome " + name + "!");

        questionText = findViewById(R.id.questionText);
        options[0] = findViewById(R.id.option1);
        options[1] = findViewById(R.id.option2);
        options[2] = findViewById(R.id.option3);
        options[3] = findViewById(R.id.option4);
        submitBtn = findViewById(R.id.submitBtn);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        questions = loadQuestions();
        loadQuestion();

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            options[i].setOnClickListener(v -> {
                selectedOption = finalI;
                for (Button btn : options) btn.setBackgroundColor(Color.LTGRAY);
                options[finalI].setBackgroundColor(Color.YELLOW);
            });
        }

        submitBtn.setOnClickListener(v -> {
            if (isSubmitPhase) {
                // SUBMIT phase: check answer
                if (selectedOption == -1) return;

                int correct = questions.get(currentIndex).correctIndex;

                if (selectedOption == correct) {
                    options[selectedOption].setBackgroundColor(Color.GREEN);
                    score++;
                } else {
                    options[selectedOption].setBackgroundColor(Color.RED);
                    options[correct].setBackgroundColor(Color.GREEN);
                }

                submitBtn.setText("Next");
                isSubmitPhase = false;

            } else {
                // NEXT phase: load next question or go to result
                currentIndex++;
                if (currentIndex < questions.size()) {
                    loadQuestion();
                    submitBtn.setText("Submit");
                    isSubmitPhase = true;
                } else {
                    Intent i = new Intent(QuizActivity.this, ResultActivity.class);
                    i.putExtra("score", score);
                    i.putExtra("total", questions.size());
                    startActivity(i);
                    finish();
                }
            }
        });

    }

        void loadQuestion() {
            Question q = questions.get(currentIndex);

            TextView welcomeText = findViewById(R.id.welcomeText);
            if (currentIndex == 0) {
                SharedPreferences prefs = getSharedPreferences("quizApp", MODE_PRIVATE);
                String name = prefs.getString("username", "User");
                welcomeText.setText("Welcome " + name + "!");
                welcomeText.setVisibility(View.VISIBLE);
            } else {
                welcomeText.setVisibility(View.GONE);
            }

            questionText.setText(q.text);

            for (int i = 0; i < 4; i++) {
                options[i].setText(q.options[i]);
                options[i].setBackgroundColor(Color.LTGRAY);
            }

            progressBar.setProgress((currentIndex * 100) / questions.size());
            progressText.setText((currentIndex + 1) + "/" + questions.size());
            selectedOption = -1;
            submitBtn.setText("Submit");
        }

        List<Question> loadQuestions() {
            List<Question> list = new ArrayList<>();
            list.add(new Question("What is the capital of Australia?", new String[]{"Sydney", "Melbourne", "Canberra", "Perth"}, 2));
            list.add(new Question("2 + 2 = ?", new String[]{"3", "4", "5", "6"}, 1));
            list.add(new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Venus", "Jupiter"}, 1));
            list.add(new Question("What is acrophobia a fear of?", new String[]{"Fear of spiders", "Fear of heights", "Fear of deep waters", "fear of darkness"}, 1));
            list.add(new Question("Which country drinks the most coffee per capita?", new String[]{"Sweden", "England", "Australia", "Finland"}, 3));
            return list;
        }
    }
