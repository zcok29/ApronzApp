/*
 * SecondActivity
 */
package com.example.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    // Used to add comment to database
    Map<String, Object> commentMap = new HashMap<>();
    public FirebaseFirestore db;
    public EditText editText;


    // Location Data
    public Location locationData[];

    // Used in getComments function
    public Comment commentData[];

    // Used in addLocation function
    Map<String, Object> locationMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Get Firestore database instance
        db = FirebaseFirestore.getInstance();

        editText = (EditText) findViewById(R.id.edit_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(SecondActivity.this,String.valueOf(actionId),Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        getLocations(db);
        ImageButton returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
               Intent intent = new Intent(SecondActivity.this, MainActivity.class);
               startActivity(intent);
            }
        });


//    TextView inputTextView = (TextView) findViewById(R.id.input_text);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCommentToDb();
                Snackbar.make(view, "Comment added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (getIntent().hasExtra("SOMETHING")) {
            TextView tv = (TextView) findViewById(R.id.myText2);
            String location = getIntent().getExtras().getString("SOMETHING");
            tv.setText(location);
        }
    }

    public void addCommentToDb(){
        // Gets the text from the editText box when the fab button is pressed
        String comment = editText.getText().toString();
        //  Gets current Timestamp when the fab button is pressed
        long epoch = System.currentTimeMillis()/1000;
        String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (epoch*1000));

        // Logs the comment in Run/Logcat window when the fab button is pressed (for testing)
        Log.d("Comment: ", comment);
        // Logs the timestamp in Run/Logcat window when the fab button is pressed  (for testing)
        Log.d("Epoch Timestamp: ", "" + epoch);
        Log.d("Date Timestamp: ", date);

        // Prepares the comment data in a hash map to be sent to the database
        commentMap.put("content", comment);
        commentMap.put("timestamp", epoch);

        // Sends the prepared comment data to the database with doc ID "J5ri7Dlp55HcZ4V0CQvo"
        db.collection("locations").document("J5ri7Dlp55HcZ4V0CQvo").collection("comments").add(commentMap);
    }

    public void displayComment(){
        // Retrieves location data from the database
        locationData = getLocations(db);
//        Log.d("Random Index", locationData[2].name);
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
                            // Creates an array of comments with the proper size
//                      commentData = new Comment[task.getResult().size()];
//                      int i = 0;
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                // Creates a new comment object with the data pulled from the location with the corresponding document ID
                                Comment comment = new Comment(doc.getData().get("content").toString(), doc.getData().get("timestamp").toString());
                                // Logs the comment object for testing purposes
                                Log.d("SINGLE COMMENT OBJECT", comment.content + ", " + comment.timestamp);

                                // TODO: return comment data as a list of comments?
//                           commentData[i] = comment;
//                           i++;
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
                                Location location = new Location(doc.getData().get("name").toString(), doc.getData().get("contact").toString(), doc.getData().get("address").toString(), doc.getId());
                                // Logs the location object for testing purposes
                                Log.d("SINGLE LOCATION OBJECT", location.documentID + " - " + location.name + ", " + location.contact + ", " + location.address);
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
    protected Location[] getLocations(FirebaseFirestore db){
        db.collection("locations").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Creates an array of locations with the proper size
                            locationData = new Location[task.getResult().size()];
                            int i = 0;
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                // Keeping these commented logs to use as a reference
                                // Log.d("CHECK", document.getId() + " => " + document.getData().values());
                                // Log.d("CHECK STRING", document.getData().get("contact").toString());

                                // Creates a new location object with the data pulled from the database
                                Location location = new Location(doc.getData().get("name").toString(), doc.getData().get("contact").toString(), doc.getData().get("address").toString(), doc.getId());
                                // Logs the location object for testing purposes
                                Log.d("LOCATION OBJECT", location.documentID + " - " + location.name + ", " + location.contact + ", " + location.address);

                                // TODO: return location data as a list?
                                locationData[i] = location;
                                Log.d("OBJECT AT INDEX " + i, locationData[i].name);
                                i++;
                            }
                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }

                        // Logs for testing
                        for (int i = 0; i < locationData.length; i++){
                            Log.d("1OBJECT AT INDEX " + i, locationData[i].name);
                        }
                        Log.d("ARRAY LENGTH", locationData.length + "");
                        Log.d("ARRAY LENGTH", task.getResult().size() + "");
                    }
                });


        // TODO: PROBLEM - the function is returning a null array even though it is successfully populated above ^
//        Log.d("ARRAY LENGTH", locationData.length + "");  // FAILS (array is null)
        return locationData;
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
