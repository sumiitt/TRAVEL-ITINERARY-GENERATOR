package com.example.tourrecommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner destinationSpinner;
    private EditText numDaysEditText;
    private EditText budgetEditText;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the spinner view and edit text views in the layout
        destinationSpinner = findViewById(R.id.destination_spinner);
        numDaysEditText = findViewById(R.id.num_days_edittext);
        budgetEditText = findViewById(R.id.budget_edittext);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        // Set the spinner adapter and onItemSelectedListener
        String[] indianCities = {"Mumbai", "Delhi", "Bengaluru", "Hyderabad", "Chennai", "Kolkata", "Pune", "Jaipur", "Lucknow", "Ahmedabad"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, indianCities);
        destinationSpinner.setAdapter(adapter);
        destinationSpinner.setOnItemSelectedListener(this);

        // Find the "Generate Itinerary" button and set its onClickListener
        Button generateItineraryButton = findViewById(R.id.generate_itinerary_button);
        generateItineraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected destination, number of days, and budget
                String destination = destinationSpinner.getSelectedItem().toString();
                int numDays = Integer.parseInt(numDaysEditText.getText().toString());
                int budget = Integer.parseInt(budgetEditText.getText().toString());

                // Create an intent to start the itinerary activity

                Intent itineraryIntent = new Intent(MainActivity.this, ItineraryActivity.class);
                itineraryIntent.putExtra("destination", destination);
                itineraryIntent.putExtra("numDays", numDays);
                itineraryIntent.putExtra("budget", budget);
                startActivity(itineraryIntent);
            }
        });
    }

    // Implement the onItemSelected method for the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Do nothing
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}
