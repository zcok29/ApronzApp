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

import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    String s1[], s2[];
    int images[] = {R.drawable.aolinrice, R.drawable.cc, R.drawable.lc};
    Button button;
    List<Location> locationData;

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
        // Map<String, Object> userTest = new HashMap<>();
        // userTest.put("first", "Tester");
        // userTest.put("last", "Tester");
        // userTest.put("born", 1999);

        //db.collection("users").add(userTest);

        db.collection("locations")
          .get()
          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if (task.isSuccessful()) {
                      for (QueryDocumentSnapshot document : task.getResult()) {
                          Log.d("CHECK", document.getId() + " => " + document.getData().values());
                          Log.d("CHECK STRING", document.getData().get("contact").toString());
                          Location location = new Location(document.getData().get("name").toString(), document.getData().get("contact").toString());
                          // locationData.add(location);
                      }
                  } else {
                      Log.w("ERROR", "Error getting documents.", task.getException());
                  }
              }
          });
    }

}