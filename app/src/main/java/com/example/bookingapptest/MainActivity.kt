package com.example.bookingapptest

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var locationInput: EditText
    private lateinit var searchButton: Button
    private lateinit var hotelList: ListView

    // Hardcoded hotel data (you can add more)
    private val hotels = mapOf(
        "new york" to listOf("Hotel New York", "Downtown Inn", "City View Hotel"),
        "london" to listOf("London Plaza", "Royal Inn", "City Lodge"),
        "paris" to listOf("Paris Grand", "Eiffel Hotel", "Luxe Stay"),
        "madurai" to listOf("Taj","Madurai Residence","Hotel Marriot"),
        "chennai" to listOf("Taj Hotel", "ITC Grand Chola","Hotel Marriot")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set app title
        title = "OLX - Hotel Room Booking"

        // Initialize UI elements
        locationInput = findViewById(R.id.locationInput)
        searchButton = findViewById(R.id.searchButton)
        hotelList = findViewById(R.id.hotelList)

        // Set click listener for search button
        searchButton.setOnClickListener {
            searchHotels()
        }

        // Set click listener for hotel list items
        hotelList.setOnItemClickListener { _, _, position, _ ->
            val selectedHotel = hotelList.adapter.getItem(position) as String
            showBookingDialog(selectedHotel)
        }
    }

    private fun searchHotels() {
        val location = locationInput.text.toString().trim().lowercase()
        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show()
            return
        }

        // Get hotels for the entered location
        val nearbyHotels = hotels[location] ?: emptyList()

        if (nearbyHotels.isEmpty()) {
            Toast.makeText(this, "No hotels found in $location", Toast.LENGTH_SHORT).show()
            hotelList.adapter = null // Clear the list
        } else {
            // Display hotels in the ListView
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nearbyHotels)
            hotelList.adapter = adapter
        }
    }

    private fun showBookingDialog(hotelName: String) {
        AlertDialog.Builder(this)
            .setTitle("Book Room")
            .setMessage("Do you want to book a room at $hotelName?")
            .setPositiveButton("Yes") { _, _ ->
                // Show booking confirmation
                AlertDialog.Builder(this)
                    .setTitle("Booking Confirmed")
                    .setMessage("Your room at $hotelName is booked!")
                    .setPositiveButton("OK", null)
                    .show()
            }
            .setNegativeButton("No", null)
            .show()
    }
}