/*
 * Main Activity
 */

package com.example.mvp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String s1[], s2[];
    int images[] = {R.drawable.aolinrice, R.drawable.cc, R.drawable.lc};
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        // Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        s1 = getResources().getStringArray(R.array.locations);
        s2 = getResources().getStringArray(R.array.contacts);

        //initialization of class + passing all values:
        MyAdaptor myAdaptor = new MyAdaptor(this, s1, s2, images, button);
        recyclerView.setAdapter(myAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add a new location to the database
//        addLocation(db, "DeWitt Wallace Library", "651-696-6377", "1600 Grand Ave, St Paul, MN 55105");

        // Retrieves location data from the database
    //    locationData = getLocations(db);
//        Log.d("Random Index", locationData[2].name);
//        getLocations(db);

        // Retrieves location data of a requested location from the database
//        getOneLocation(db, "name", "Olin Rice");

        // Retrieves comment data of a specific location (this doc ID is for Campus Center)
//        getComments(db, "J5ri7Dlp55HcZ4V0CQvo");

    }




}