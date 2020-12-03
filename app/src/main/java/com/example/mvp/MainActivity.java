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

    // Used in getLocations function
    //  List<Location> locationData;

    // Used in getComments function
    // List<Comment> commentData;

    // Used in addLocation function
    Map<String, Object> locationMap = new HashMap<>();


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

        // Add a new location to the database
//        addLocation(db, "DeWitt Wallace Library", "651-696-6377", "1600 Grand Ave, St Paul, MN 55105");

        // Retrieves location data from the database
//        getLocations(db);

        // Retrieves location data of a requested location from the database
//        getOneLocation(db, "name", "Olin Rice");

        // Retrieves comment data of a specific location (this doc ID is for Campus Center)
//        getComments(db, "J5ri7Dlp55HcZ4V0CQvo");

    }


    /**
     *  This function gets the comment data of a specific location from the database
     *  TODO: figure out how to get the correct document ID of the location that we want
     */
    protected void getComments(FirebaseFirestore db, String documentID){
        db.collection("locations").document(documentID).collection("comments").get()
          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if (task.isSuccessful()) {
                      for (QueryDocumentSnapshot doc : task.getResult()) {
                          // Creates a new comment object with the data pulled from the location with the corresponding document ID
                          Comment comment = new Comment(doc.getData().get("content").toString(), doc.getData().get("timestamp").toString());
                          // Logs the comment object for testing purposes
                          Log.d("SINGLE COMMENT OBJECT", comment.content + ", " + comment.timestamp);

                          // TODO: return comment data as a list of comments?
                          // commentData.add(comment);
                      }
                  } else {
                      Log.w("ERROR", "Error getting documents.", task.getException());
                  }
              }
          });
    }


    /**
     * This function gets the information of one location by passing in the key and value to make a
     * conditional query. This function shows how you can use .whereEqualTo() to perform simple
     * queries.
     *
     * Function Example:   getOneLocation(db, "name", "Leonard Center")
     *
     */
    protected void getOneLocation(FirebaseFirestore db, String key, String value){
        db.collection("locations").whereEqualTo(key, value).get()
          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if (task.isSuccessful()) {
                      for (QueryDocumentSnapshot doc : task.getResult()) {
                          // Creates a new location object with the data pulled of the location that fits the condition
                          Location location = new Location(doc.getData().get("name").toString(), doc.getData().get("contact").toString(), doc.getData().get("address").toString());
                          // Logs the location object for testing purposes
                          Log.d("SINGLE LOCATION OBJECT", location.name + ", " + location.contact + ", " + location.address);
                      }
                  } else {
                      Log.w("ERROR", "Error getting documents.", task.getException());
                  }
              }
          });
    }


    /**
    * This function adds locations to the database
    */
    protected void addLocation(FirebaseFirestore db, String name, String contact, String address){
        locationMap.put("name", name);
        locationMap.put("contact", contact);
        locationMap.put("address", address);
        db.collection("locations").add(locationMap);
    }


    /*
    * This function currently gets and logs the current locations in the database
     */
    protected void getLocations(FirebaseFirestore db){
        db.collection("locations").get()
          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if (task.isSuccessful()) {
                      for (QueryDocumentSnapshot doc : task.getResult()) {
                          // Keeping these commented logs to use as a reference
                          // Log.d("CHECK", document.getId() + " => " + document.getData().values());
                          // Log.d("CHECK STRING", document.getData().get("contact").toString());

                          // Creates a new location object with the data pulled from the database
                          Location location = new Location(doc.getData().get("name").toString(), doc.getData().get("contact").toString(), doc.getData().get("address").toString());
                          // Logs the location object for testing purposes
                          Log.d("LOCATION OBJECT", location.name + ", " + location.contact + ", " + location.address);

                          // TODO: return location data as a list?
                          // locationData.add(location);
                      }
                  } else {
                      Log.w("ERROR", "Error getting documents.", task.getException());
                  }
              }
          });
    }


    // This is a sample function of addUser function (example to build from)
//    protected void addUser(FirebaseFirestore db, String first, String last, int born, String email){
//        user.put("first", first);
//        user.put("last", last);
//        user.put("born", born);
//        user.put("email", email);
//        db.collection("users").add(user);
//    }

}