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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

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


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
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
