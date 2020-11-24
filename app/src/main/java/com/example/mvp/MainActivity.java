package com.example.mvp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
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
        FirebaseFirestore b = FirebaseFirestore.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        s1 = getResources().getStringArray(R.array.locations);
        s2 = getResources().getStringArray(R.array.contacts);

        //initialization of class + passing all values:
        MyAdaptor myAdaptor = new MyAdaptor(this, s1, s2, images, button);
        recyclerView.setAdapter(myAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a new user with a first and last name
        Map<String, Object> userTest = new HashMap<>();
        userTest.put("first", "Someome different 2.0");
        userTest.put("last", "Wallace");
        userTest.put("born", 1999);

        db.collection("users").add(userTest);


    }
}