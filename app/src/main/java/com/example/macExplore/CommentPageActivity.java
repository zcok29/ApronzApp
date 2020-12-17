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
import com.google.firebase.firestore.DocumentSnapshot;
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
    Map<String, Object> commentMap = new HashMap<>();
    public FirebaseFirestore db;
    public EditText editText;
    public String location;
    public String locationID;
    private FirebaseAuth mAuth;

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
        String comment = editText.getText().toString();
        String userID = mAuth.getCurrentUser().getUid();
        long epoch = System.currentTimeMillis() / 1000;

        db.collection("users").document(userID).addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                commentMap.put("content", comment);
                commentMap.put("timestamp", epoch);
                commentMap.put("user", value.getString("username"));

                db.collection("locations").document(locationID).collection("comments").add(commentMap);
            }
        });
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
                            commentData = new ArrayList<>(task.getResult().size());

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Comment comment = new Comment(doc.getData().get("content").toString(), doc.getData().get("timestamp").toString(),doc.getData().get("user").toString());
                                Log.d("SINGLE COMMENT OBJECT", comment.content + ", " + comment.timestamp+", "+comment.user);
                                commentData.add(comment);
                            }
                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }
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