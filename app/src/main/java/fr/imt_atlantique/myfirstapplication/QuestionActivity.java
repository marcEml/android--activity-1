package fr.imt_atlantique.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class QuestionActivity extends AppCompatActivity {

    private EditText questionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.question_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        questionEditText = findViewById(R.id.questionInput);

        // Retrieve the answer passed from AnswerActivity
        String answer = getIntent().getStringExtra("answer");

        // If there's an answer, show it in a Snackbar
        if (answer != null && !answer.isEmpty()) {
            View rootView = findViewById(R.id.main);
            Snackbar.make(rootView, answer, Snackbar.LENGTH_SHORT).show();
        }
    }


    public void validateAction(View v) {
        String question = questionEditText.getText().toString();

        if (question.isEmpty()) {
            Toast.makeText(this, "Ecrivez votre question !", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, AnswerActivity.class);
        intent.putExtra("question", question);
        startActivity(intent);
    }

}
