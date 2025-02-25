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

public class AnswerActivity extends AppCompatActivity {

    private EditText answerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String question = getIntent().getStringExtra("question");
        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(question);

        answerEditText = findViewById(R.id.answerInput);
    }

    public void submitAnswer(View v) {
        String answer = answerEditText.getText().toString();

        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("answer", answer);
        startActivity(intent);
    }
}