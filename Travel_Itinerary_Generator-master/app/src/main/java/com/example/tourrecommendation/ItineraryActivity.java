package com.example.tourrecommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ItineraryActivity extends AppCompatActivity {

    private ListView itineraryListView;
    private TextView destinationTextView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
        initCityLocations();

        // Find the views in the layout
        itineraryListView = findViewById(R.id.itinerary_list_view);
        destinationTextView = findViewById(R.id.destination_text_view);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Extract the data from the intent
        String destination = intent.getStringExtra("destination");
        int numDays = intent.getIntExtra("numDays", 0);
        int budget = intent.getIntExtra("budget", 0);

        // Set the destination text view to show the selected destination
        destinationTextView.setText("Your Itinerary for " + destination);

        // Populate the list view with the day-wise itinerary for the selected city
        // You will need to implement this method to populate the list view
        populateItineraryListView(destination, numDays,budget);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDF(destination, numDays, budget);
            }
        });

    }
    // Define sample locations for each city
    private Map<String, List<String>> cityLocations = new HashMap<>();

    private void initCityLocations() {
        List<String> delhiLocations = Arrays.asList(
                "India Gate",
                "Red Fort",
                "Qutub Minar",
                "Lotus Temple",
                "Humayun's Tomb"
        );

        List<String> mumbaiLocations = Arrays.asList(
                "Gateway of India",
                "Elephanta Caves",
                "Marine Drive",
                "Juhu Beach",
                "Siddhivinayak Temple"
        );

        List<String> chennaiLocations = Arrays.asList(
                "Marina Beach",
                "Kapaleeswarar Temple",
                "Fort St. George",
                "San Thome Basilica",
                "Government Museum"
        );

        List<String> kolkataLocations = Arrays.asList(
                "Victoria Memorial",
                "Howrah Bridge",
                "Belur Math",
                "St. Paul's Cathedral",
                "Indian Museum"
        );

        List<String> bangaloreLocations = Arrays.asList(
                "Lalbagh Botanical Garden",
                "Bangalore Palace",
                "Tipu Sultan's Summer Palace",
                "Cubbon Park",
                "Bull Temple"
        );

        List<String> hyderabadLocations = Arrays.asList(
                "Charminar",
                "Golconda Fort",
                "Hussain Sagar Lake",
                "Chowmahalla Palace",
                "Salar Jung Museum"
        );

        List<String> puneLocations = Arrays.asList(
                "Shaniwar Wada",
                "Aga Khan Palace",
                "Sinhagad Fort",
                "Dagadusheth Halwai Ganapati Temple",
                "Raja Dinkar Kelkar Museum"
        );

        List<String> jaipurLocations = Arrays.asList(
                "Amber Fort",
                "Hawa Mahal",
                "City Palace",
                "Jantar Mantar",
                "Nahargarh Fort"
        );

        List<String> ahmedabadLocations = Arrays.asList(
                "Sabarmati Ashram",
                "Jama Masjid",
                "Kankaria Lake",
                "Adalaj Stepwell",
                "Calico Museum of Textiles"
        );

        List<String> chandigarhLocations = Arrays.asList(
                "Rock Garden",
                "Sukhna Lake",
                "Chandigarh Rose Garden",
                "Pinjore Gardens",
                "Le Corbusier Centre"
        );

        cityLocations.put("Delhi", delhiLocations);
        cityLocations.put("Mumbai", mumbaiLocations);
        cityLocations.put("Chennai", chennaiLocations);
        cityLocations.put("Kolkata", kolkataLocations);
        cityLocations.put("Bangalore", bangaloreLocations);
        cityLocations.put("Hyderabad", hyderabadLocations);
        cityLocations.put("Pune", puneLocations);
        cityLocations.put("Jaipur", jaipurLocations);
        cityLocations.put("Ahmedabad", ahmedabadLocations);
        cityLocations.put("Chandigarh", chandigarhLocations);
    }
    private void populateItineraryListView(String destination,int numDays,int budget) {
        // Get the selected city, days, and budget from the Intent


        // Get the list view and create a new adapter
        ListView listView = findViewById(R.id.itinerary_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        // Get the locations for the selected city from the cityLocations map
        List<String> locations = new ArrayList<>(cityLocations.get(destination));


        // Calculate the number of locations to visit each day
        int locationsPerDay = (int) Math.ceil((double) locations.size() / numDays);

        // Initialize variables to keep track of the current day and budget
        int currentDay = 1;
        int currentBudget = budget;

        // Loop through the number of days
        for (int i = 0; i < numDays; i++) {
            // Add the day header to the list view
            adapter.add("Day " + currentDay + ":");

            // Loop through the locations for the current day
            for (int j = 0; j < locationsPerDay; j++) {
                // Check if there are any locations left to visit
                if (!locations.isEmpty()) {
                    // Get the first location from the list and remove it
                    String location = locations.get(0);
                    locations.remove(0);

                    // Calculate the cost of visiting the location
                    int cost = (int) Math.ceil((double) currentBudget / locations.size());

                    // Add the location and cost to the list view
                    adapter.add(location + " (" + cost + " rupees)");
                    currentBudget -= cost;
                }
            }

            // Increment the current day
            currentDay++;
        }

        // Set the adapter on the list view
        listView.setAdapter(adapter);
    }

//    private void populateItineraryListView(String city, int days) {
//        List<String> locations = cityLocations.get(city);
//        if (locations == null) {
//            // handle case where city is not found in the map
//            return;
//        }
//
//        List<String> itinerary = new ArrayList<>();
//        Random rand = new Random();
//        for (int i = 1; i <= days; i++) {
//            // shuffle the locations for each day
//            Collections.shuffle(locations, rand);
//            itinerary.add("Day " + i + ": " + locations.get(0));
//        }
//
//        // set the adapter for the ListView
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itinerary);
//        ListView listView = findViewById(R.id.itinerary_list_view);
//        listView.setAdapter(adapter);
//    }
private List<String> getLocationsForDay(String destination, int numDays, int day) {
    // Calculate the total number of locations available
    int numLocations = destination.length() + day;

    // Calculate the number of locations to be visited on the current day
    int numLocationsPerDay = (int) Math.ceil((double) numLocations / numDays);

    // Generate a list of all available locations
    List<String> allLocations = new ArrayList<>();
    for (int i = 1; i <= numLocations; i++) {
        allLocations.add("Location " + i);
    }

    // Calculate the index range of locations to be visited on the current day
    int startIndex = (day - 1) * numLocationsPerDay;
    int endIndex = Math.min(startIndex + numLocationsPerDay, allLocations.size());

    // Return the locations to be visited on the current day
    return allLocations.subList(startIndex, endIndex);
}


//    private void populateItineraryListView(String destination, int numDays, int budget) {
//        // Create an array list to hold the day-wise itinerary for the selected city
//        ArrayList<String> itineraryList = new ArrayList<>();
//
//        // Calculate the daily budget for the trip
//        int dailyBudget = budget / numDays;
//
//        // Define an array of popular tourist attractions in the selected city
//        String[] attractions = new String[] {"Taj Mahal", "Red Fort", "Qutub Minar", "Hawa Mahal", "Gateway of India", "Ellora Caves", "Golden Temple", "Charminar", "Meenakshi Amman Temple", "Mysore Palace"};
//
//        // Generate the itinerary for each day of the trip
//        for (int i = 1; i <= numDays; i++) {
//            String itinerary = "Day " + i + ": " + destination;
//            int attractionIndex = i % attractions.length;
//            String attraction = attractions[attractionIndex];
//            itinerary += " - " + attraction;
//            if (dailyBudget >= 1000) {
//                itinerary += " (Luxury)";
//            } else if (dailyBudget >= 500) {
//                itinerary += " (Comfort)";
//            } else {
//                itinerary += " (Budget)";
//            }
//            itineraryList.add(itinerary);
//        }
//
//        // Create an array adapter to display the itinerary in the list view
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itineraryList);
//
//        // Set the adapter on the list view
//        itineraryListView.setAdapter(adapter);
//    }
private void generatePDF(String destination, int numDays, int budget) {
    // Create a new PDF document
    Document document = new Document();

    try {
        // Create a new file in the device's internal storage directory
        File file = new File(getFilesDir(), "itinerary.pdf");
        FileOutputStream outputStream = openFileOutput("itinerary.pdf", Context.MODE_PRIVATE);

        // Create a new PDF writer with the output stream
        PdfWriter.getInstance(document, outputStream);

        // Open the document for writing
        document.open();

        // Add the destination text to the document
        document.add(new Paragraph("Your Itinerary for " + destination));

        // Create a new table with 2 columns to display the day-wise itinerary
        PdfPTable table = new PdfPTable(2);

        // Set the table width to 100%
        table.setWidthPercentage(100);

        // Set the table headers
        table.addCell("Day");
        table.addCell("Locations");

        // Populate the table with the day-wise itinerary
        for (int i = 1; i <= numDays; i++) {
            // Create a new cell for the day number
            PdfPCell dayCell = new PdfPCell(new Phrase("Day " + i));

            // Create a new cell for the locations
            PdfPCell locationsCell = new PdfPCell();

            // Get the locations for the current day
            List<String> locations;
            locations = getLocationsForDay(destination, numDays, i);

            // Add each location to the locations cell
            for (String location : locations) {
                locationsCell.addElement(new Phrase(location));
            }

            // Add the day cell and locations cell to the table
            table.addCell(dayCell);
            table.addCell(locationsCell);
        }

        // Add the table to the document
        document.add(table);

        // Add the budget text to the document
        document.add(new Paragraph("Budget: " + budget));

        // Close the document
        document.close();

        // Show a toast message to indicate that the PDF was generated
        Toast.makeText(this, "PDF generated", Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}

