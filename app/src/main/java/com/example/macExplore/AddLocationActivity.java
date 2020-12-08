package com.example.macExplore;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class AddLocationActivity extends AppCompatActivity {

    // Used in addLocation function
    public Map<String, Object> locationMap = new HashMap<>();
    public EditText locationNameInput;
    public EditText contactInput;
    public EditText addressInput;
    public FirebaseFirestore db;
    public Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = FirebaseFirestore.getInstance();
        locationNameInput = (EditText) findViewById(R.id.location_name_textbox);
        contactInput = (EditText) findViewById(R.id.contact_textbox);
        addressInput = (EditText) findViewById(R.id.address_textbox);
        btn = (Button) findViewById(R.id.location_submit_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocationToDb(db);
                Snackbar.make(view, "Location added", Snackbar.LENGTH_LONG)
                  .setAction("Action", null).show();
            }
        });
    }

    /**
     * This function adds locations to the database
     */
    public void addLocationToDb(FirebaseFirestore db){
        locationMap.put("name", locationNameInput.getText().toString());
        locationMap.put("contact", contactInput.getText().toString());
        locationMap.put("address", addressInput.getText().toString());
        db.collection("locations").add(locationMap);
    }

}