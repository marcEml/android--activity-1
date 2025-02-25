package fr.imt_atlantique.myfirstapplication;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {
    private TextView userInfoTextView;
    private LinearLayout phoneDisplayContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        userInfoTextView = findViewById(R.id.userInfoTextView);
        phoneDisplayContainer = findViewById(R.id.phoneDisplayContainer);

        User user = getIntent().getParcelableExtra("user_data");
        if (user != null) {
            String userInfo = "Nom: " + user.getLastName() + "\n"
                    + "Prénom: " + user.getFirstName() + "\n"
                    + "Ville de naissance: " + user.getBirthCity() + "\n"
                    + "Date de naissance: " + user.getBirthDate() + "\n"
                    + "Département: " + user.getDepartment();

            userInfoTextView.setText(userInfo);

            // Affichage des numéros de téléphone
            for (String phone : user.getPhoneNumbers()) {
                TextView phoneTextView = new TextView(this);
                phoneTextView.setText(phone);
                phoneDisplayContainer.addView(phoneTextView);
            }
        }
    }
}
