package com.example.macExplore;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentPageActivity extends AppCompatActivity {
    // Used to add comment to database
    Map<String, Object> commentMap = new HashMap<>();
    public FirebaseFirestore db;
    public EditText editText;
    public String location;
    public String locationID;
    private FirebaseAuth mAuth;


    // Used in getComments function
    List<Comment> commentData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_page);
        mAuth = FirebaseAuth.getInstance();


        db = FirebaseFirestore.getInstance();
        String pageName = getResources().getString(R.string.title_activity_comment_page);

        if (getIntent().hasExtra("LOCATION NAME")) {
            location = getIntent().getExtras().getString("LOCATION NAME");
//            locationID = locationIDMap.get(location);
            pageName = String.format(pageName,location);
            locationID = getIntent().getExtras().getString("DOCUMENT ID");
        }

        FloatingActionButton fab = findViewById(R.id.addCommentButton);

        editText = new EditText(getApplicationContext());

        editText = (EditText) findViewById(R.id.add_comment_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(CommentPageActivity.this, String.valueOf(actionId), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addCommentToDb();
                Snackbar.make(view, "Comment added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(pageName);

        System.out.println(locationID);

        displayComment();

    }

    public void addCommentToDb() {
        // Gets the text and timestamp when the fab button is pressed
        String comment = editText.getText().toString();
        String user = mAuth.getCurrentUser().getEmail();
        long epoch = System.currentTimeMillis() / 1000;

        // Prepares the comment data in a hash map to be sent to the database
        commentMap.put("content", comment);
        commentMap.put("timestamp", epoch);
        commentMap.put("user",user);
        // Sends the prepared comment data to the database with doc ID "J5ri7Dlp55HcZ4V0CQvo"
        db.collection("locations").document(locationID).collection("comments").add(commentMap);
    }


    /**
     * This function gets the comment data of a specific location from the database
     */
    public void getComments(FirebaseFirestore db, String documentID) {
        db.collection("locations").document(documentID).collection("comments").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Creates an array of comments with the proper size
                            commentData = new ArrayList<>(task.getResult().size());

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                // Creates a new comment object with the data pulled from the location with the corresponding document ID
                                Comment comment = new Comment(doc.getData().get("content").toString(), doc.getData().get("timestamp").toString(),"user");
                                // Logs the comment object for testing purposes
                                Log.d("SINGLE COMMENT OBJECT", comment.content + ", " + comment.timestamp);
                                commentData.add(comment);
                            }
                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }
                        // Uses the retrieved location information to build the UI for the app
                        showComments(commentData);
                    }
                });

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showComments(List<Comment> commentData){
        commentData.sort(new CommentComparator());
        RecyclerView recyclerView = findViewById(R.id.recyclerViewComment);
        CommentAdapter commentAdapter = new CommentAdapter(this, commentData);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        for(Comment comment:commentData){
//            LinearLayout commentLayout = (LinearLayout) findViewById(R.id.comment_layout);
//            TextView commentView = new TextView(getApplicationContext());
//            String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (Integer.parseInt(comment.getTimestamp())*1000L));
//            commentView.setText(comment.getContent()+"   "+date);
//            commentView.setTextSize(30);
//            commentLayout.addView(commentView);
//        }

        // Display comments here, you will be able to access the comment objects added in commentData
    }


    public void displayComment() {

        db.collection("locations").document(locationID).collection("comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                LinearLayout commentLayout = (LinearLayout) findViewById(R.id.comment_layout);
                commentLayout.removeAllViews();
                getComments(db, locationID);
            }

        });
    }
}