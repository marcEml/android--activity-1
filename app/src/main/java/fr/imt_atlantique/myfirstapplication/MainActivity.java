package fr.imt_atlantique.myfirstapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout phoneContainer;
    private int phoneCount = 0;
    private EditText birthdayInput;
    private EditText nameEditText;
    private EditText firstNameEditText;
    private EditText birthdayEditText;
    private EditText placeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        phoneContainer = findViewById(R.id.phoneContainer);
        nameEditText = findViewById(R.id.nameInput);
        firstNameEditText = findViewById(R.id.firstNameInput);
        birthdayEditText = findViewById(R.id.birthdayInput);
        placeEditText = findViewById(R.id.placeInput);

        // Get the Spinner from the layout
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // date picker : Définir un onClickListener pour afficher le DatePickerDialog
        birthdayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    public void validateAction(View v) {
        // user attributes
        String lastname = nameEditText.getText().toString();
        String firstname = firstNameEditText.getText().toString();
        String birthday = birthdayEditText.getText().toString();
        EditText placeEditText = findViewById(R.id.placeInput);
        String place = placeEditText.getText().toString();

        Snackbar snackbar =  Snackbar.make(v, lastname + " " + firstname + " was born in " + birthday + " in " + place, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.black));

        snackbar.show();
    }

    // Method to add a phone number input field
    public void addPhoneNumber(View view) {
        phoneCount++;

        // Create a new EditText for phone number
        EditText phoneInput = new EditText(this);
        phoneInput.setHint("Numéro de téléphone " + phoneCount);
        phoneInput.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Add the new phone number input to the container
        phoneContainer.addView(phoneInput);
    }

    private void showDatePickerDialog() {
        // Obtenir la date actuelle
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Créer un DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Mettre la date sélectionnée dans l'EditText
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        birthdayEditText.setText(date);
                    }
                }, year, month, dayOfMonth);

        // Afficher le DatePicker
        datePickerDialog.show();
    }

    // Method to remove the last added phone number input field
    public void removePhoneNumber(View view) {
        if (phoneContainer.getChildCount() > 0) {
            phoneContainer.removeViewAt(phoneContainer.getChildCount() - 1);
            phoneCount--;
        } else {
            Toast.makeText(this, "Aucun numéro à supprimer", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void resetAction(MenuItem item) {
        // Reset all user input fields
        nameEditText.setText("");
        firstNameEditText.setText("");
        birthdayEditText.setText("");
        placeEditText.setText("");

        // Remove all phone number inputs
        int childCount = phoneContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            phoneContainer.removeViewAt(phoneContainer.getChildCount() - 1);
        }

        // Reset the phone count to zero
        phoneCount = 0;
    }
}