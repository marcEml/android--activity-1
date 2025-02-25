package fr.imt_atlantique.myfirstapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout phoneContainer;
    private int phoneCount = 0;
    private EditText nameEditText, firstNameEditText, placeEditText, birthdayEditText;
    private Spinner editDepartement;
    private Button btnValidate;

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

        // initialize edit text component
        phoneContainer = findViewById(R.id.phoneContainer);
        nameEditText = findViewById(R.id.nameInput);
        firstNameEditText = findViewById(R.id.firstNameInput);
        birthdayEditText = findViewById(R.id.birthdayInput);
        placeEditText = findViewById(R.id.placeInput);


        // Get the Spinner from the layout
        editDepartement = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.departments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editDepartement.setAdapter(adapter);

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
        // String place = placeEditText.getText().toString();
        String place = placeEditText.getText().toString();
        String department = editDepartement.getSelectedItem().toString();

        List<String> phoneNumbers = new ArrayList<>();
        for (int i = 0; i < phoneContainer.getChildCount(); i++) {
            View child = phoneContainer.getChildAt(i);
            if (child instanceof LinearLayout) {
                EditText phoneInput = (EditText) ((LinearLayout) child).getChildAt(0);
                String phoneNumber = phoneInput.getText().toString();
                if (!phoneNumber.isEmpty()) {
                    phoneNumbers.add(phoneNumber);
                }
            }
        }

        if (lastname.isEmpty() || firstname.isEmpty() || birthday.isEmpty() || place.isEmpty()) {
            Toast.makeText(this, "Complètez tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Snackbar snackbar =  Snackbar.make(v, lastname + " " + firstname + " was born in " + birthday + " in " + place, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.black));

        snackbar.show();

        User user = new User(firstname, lastname, place, birthday, department, phoneNumbers);

        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra("user_data", user);
        startActivity(intent);
    }

    // Method to add a phone number input field
    public void addPhoneNumber(View view) {
        phoneCount++;

        // Create the layout for the phone input and the icon button
        LinearLayout phoneLayout = new LinearLayout(this);
        phoneLayout.setOrientation(LinearLayout.HORIZONTAL);
        phoneLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Create the EditText for the phone number
        EditText phoneInput = new EditText(this);
        phoneInput.setHint("Numéro de téléphone " + phoneCount);
        phoneInput.setInputType(InputType.TYPE_CLASS_PHONE);
        phoneInput.setLayoutParams(new LinearLayout.LayoutParams(
                0, // Make this take up most of the space
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1 // This EditText will take 1 weight in the layout
        ));

        // Create the ImageButton (Icon button) to remove the phone input
        ImageButton removeButton = new ImageButton(this);
        removeButton.setImageResource(android.R.drawable.ic_delete); // Use delete icon
        removeButton.setContentDescription("Remove phone input");
        removeButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Set an OnClickListener to remove the phone input when clicked
        removeButton.setOnClickListener(v -> phoneContainer.removeView(phoneLayout));

        // Add the EditText and the ImageButton to the layout
        phoneLayout.addView(phoneInput);
        phoneLayout.addView(removeButton);

        // Add the phone layout to the container
        phoneContainer.addView(phoneLayout);
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

        // Reset Spinner to the first value
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setSelection(0);  // This sets the Spinner to the first item
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String place = placeEditText.getText().toString().trim();
        if (place.isEmpty() && item.getItemId() != R.id.resetAction) {
            Toast.makeText(this, "Aucune ville de naissance saisie", Toast.LENGTH_SHORT).show();
            return true;
        }

        String message = "Ma ville de naissance est : " + place;

        // Use if-else instead of switch
        if (item.getItemId() == R.id.menu_search_wikipedia) {
            openWikipedia();
            return true;
        } else if (item.getItemId() == R.id.resetAction) {
            resetAction(item);
            return true;
        } else if (item.getItemId() == R.id.action_share_whatsapp) {
            shareViaWhatsApp(message);
            return true;
        } else if (item.getItemId() == R.id.action_share_email) {
            shareViaEmail(message);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void openWikipedia() {
        // Get the city name entered by the user
        String place = placeEditText.getText().toString();

        if (place.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer une ville", Toast.LENGTH_SHORT).show();
            return;
        }

        // Encode the city name for use in the URL (in case there are spaces or special characters)
        String searchUrl = "http://fr.wikipedia.org/?search=" + Uri.encode(place);

        // Create an intent to open the URL in a browser
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl));

        // Check if there is an activity that can handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Aucun navigateur disponible", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareViaWhatsApp(String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "WhatsApp n'est pas installé", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareViaEmail(String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));  // Seules les apps email seront listées
        intent.putExtra(Intent.EXTRA_SUBJECT, "Ville de naissance");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(intent, "Envoyer via"));
        } catch (Exception e) {
            Toast.makeText(this, "Aucune application de messagerie trouvée", Toast.LENGTH_SHORT).show();
        }
    }


}