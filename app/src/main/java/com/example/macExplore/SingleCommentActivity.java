package com.example.macExplore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SingleCommentActivity extends AppCompatActivity {
    private String commentStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        commentStr = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_comment);
        final TextView comment = findViewById(R.id.singleComment);
        if (getIntent().hasExtra("comment")) {
            commentStr = getIntent().getExtras().getString("comment");
        }
        comment.setText(commentStr);
    }
}