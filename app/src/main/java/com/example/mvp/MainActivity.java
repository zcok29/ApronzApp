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
    List<Location> locationData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        s1 = getResources().getStringArray(R.array.locations);
        s2 = getResources().getStringArray(R.array.contacts);

        //initialization of class + passing all values:
        MyAdaptor myAdaptor = new MyAdaptor(this, s1, s2, images, button);
        recyclerView.setAdapter(myAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a new user with a first and last name
//         Map<String, Object> user = new HashMap<>();
//         user.put("first", "Oliver");
//         user.put("last", "Smith");
//         user.put("born", 1989);
//         user.put("email", "osmith@macalester.edu");
//
//        db.collection("users").add(user);
//
//        user.put("first", "Alexander");
//        user.put("last", "Jones");
//        user.put("born", 1966);
//        user.put("email", "alexjones@gmail.com");
//
//        db.collection("users").add(user);



        // Add a new location to Firestore
//        Map<String, Object> location = new HashMap<>();
//        location.put("name", "DeWitt Wallace Library");
//        location.put("contact", "651-696-6377");
//        location.put("address", "1600 Grand Ave, St Paul, MN 55105");
//
//        db.collection("locations").add(location);

        // Retrieves location data from Firestore
        db.collection("locations")
          .get()
          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if (task.isSuccessful()) {
                      for (QueryDocumentSnapshot doc : task.getResult()) {
                          // Log.d("CHECK", document.getId() + " => " + document.getData().values());
                          // Log.d("CHECK STRING", document.getData().get("contact").toString());
                          Location location = new Location(doc.getData().get("name").toString(), doc.getData().get("contact").toString(), doc.getData().get("address").toString());
                          Log.d("LOCATION OBJECT", location.name + ", " + location.contact + ", " + location.address);
                          // locationData.add(location);
                      }
                  } else {
                      Log.w("ERROR", "Error getting documents.", task.getException());
                  }
              }
          });
    }





//    Map<String, Object> user = new HashMap<>();
//
//    // addUser function beginning
//    protected void addUser(FirebaseFirestore db, String first, String last, int born, String email){
//        // Create a new user with a first and last name
//        user.put("first", first);
//        user.put("last", last);
//        user.put("born", born);
//        user.put("email", email);
//        db.collection("users").add(user);
//    }

}