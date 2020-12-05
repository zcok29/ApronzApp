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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    // Used to add comment to database
    Map<String, Object> commentMap = new HashMap<>();
    public FirebaseFirestore db;
    public EditText editText;
    public String location;
    public String locationID;

//    public Map<String, String> locationIDMap = new HashMap<>();



    // Used in getComments function
    public List<Comment> comments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Find a way to add location ID automatically instead of manually.
//        locationIDMap.put("Leonard Center", "5rVYLk5xYwA36yTgUMeC");
//        locationIDMap.put("Campus Center", "J5ri7Dlp55HcZ4V0CQvo");
//        locationIDMap.put("Olin Rice", "up6LwcknYyqVJe1gxiKi");
        setContentView(R.layout.activity_second);

        // Get Firestore database instance
        db = FirebaseFirestore.getInstance();
        if (getIntent().hasExtra("LOCATION NAME")) {
            TextView tv = (TextView) findViewById(R.id.locationText);
            location = getIntent().getExtras().getString("LOCATION NAME");
            tv.setText(location);
//            locationID = locationIDMap.get(location);
            locationID = getIntent().getExtras().getString("DOCUMENT ID");
        }

        displayComment();

        editText = (EditText) findViewById(R.id.edit_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(SecondActivity.this, String.valueOf(actionId), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        ImageButton returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCommentToDb();
                Snackbar.make(view, "Comment added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    public void addCommentToDb() {
        // Gets the text and timestamp when the fab button is pressed
        String comment = editText.getText().toString();
        long epoch = System.currentTimeMillis() / 1000;

        // Prepares the comment data in a hash map to be sent to the database
        commentMap.put("content", comment);
        commentMap.put("timestamp", epoch);

        // Sends the prepared comment data to the database with doc ID "J5ri7Dlp55HcZ4V0CQvo"
        db.collection("locations").document(locationID).collection("comments").add(commentMap);
    }


    public void displayComment() {
        getComments(db, locationID);
        //System.out.println(comments.toString());
        //TextView comment1 = (TextView) findViewById(R.id.comment_text);
    }


    /**
     * This function gets the comment data of a specific location from the database
     * TODO: figure out how to get the correct document ID of the location that we want
     */
    protected void getComments(FirebaseFirestore db, String documentID) {

        TextView comment1 = (TextView) findViewById(R.id.comment_text);
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
                                // Currently I'm using a textview object to update the comments. This is really not ideal since we need the time stamps and user name in the future.
                                String commentStr = comment1.getText().toString();
                                commentStr = commentStr+"\n"+ comment.content;
                                comment1.setText(commentStr);
//                           commentData[i] = comment;
//                           i++;
                            }
                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }
                    }
                });
    }





}
