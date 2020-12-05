/*
 * SecondActivity
 */
package com.example.mvp;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    // Used to add comment to database
    Map<String, Object> commentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Get Firestore database instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final EditText editText = (EditText) findViewById(R.id.edit_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(SecondActivity.this,String.valueOf(actionId),Toast.LENGTH_SHORT).show();
                return false;
            }
        });


//    TextView inputTextView = (TextView) findViewById(R.id.input_text);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}
