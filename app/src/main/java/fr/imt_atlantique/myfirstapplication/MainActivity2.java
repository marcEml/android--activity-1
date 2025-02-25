package fr.imt_atlantique.myfirstapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private int clickCount = 0;  // Counter for clicks
    private TextView clickCountTextView;  // Reference to the TextView
    private SharedPreferences sharedPreferences;  // SharedPreferences object to store data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        // Initialize the TextView
        clickCountTextView = findViewById(R.id.clickCountTextView);

        // Initialize SharedPreferences (private mode means only the app can access these preferences)
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        // Restore the click count from SharedPreferences
        clickCount = sharedPreferences.getInt("clickCount", 0);  // Default value is 0 if not found
        clickCountTextView.setText("Number of clicks: " + clickCount);

        // Apply padding to account for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set a touch listener to detect screen clicks
        findViewById(R.id.main).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Increment the click counter with each touch
                clickCount++;
                // Update the TextView with the new click count
                clickCountTextView.setText("Number of clicks: " + clickCount);

                // Save the new click count to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("clickCount", clickCount);
                editor.apply();  // Apply changes asynchronously

                return true;
            }
            return false;
        });
    }
}
