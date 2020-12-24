/*
 * LocationAdapter
 */
package com.example.macExplore;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    Context context;
    List<Comment> commentData;

    public CommentAdapter(Context ct, List<Comment> commentData){
        context = ct;
        this.commentData = commentData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_comment, parent, false);

        return new
                MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String comment = commentData.get(position).content;
        long seconds =  (System.currentTimeMillis() / 1000) - commentData.get(position).timestamp;
        String timestamp = convertTimestamp(seconds);
//        String timestamp = new java.text.SimpleDateFormat("HH:mm  MM/dd/yy").format(new java.util.Date (Integer.parseInt(commentData.get(position).timestamp)*1000L));
        String username = commentData.get(position).user;
        holder.myText1.setText(comment);
        holder.myText2.setText(timestamp);
        holder.myText3.setText(username);

        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),SingleCommentActivity.class);
                intent.putExtra("comment", comment);
                intent.putExtra("username",username);
                intent.putExtra("timestamp",timestamp);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View layout1,layout2;
        TextView myText1, myText2, myText3,viewMore;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.commentText);
            myText2 = itemView.findViewById(R.id.timestampText);
            myText3 = itemView.findViewById(R.id.usernameText);
            viewMore = itemView.findViewById(R.id.viewMoreText);
            layout1 = itemView.findViewById(R.id.constraintLayout);
            layout2 = itemView.findViewById(R.id.constraintLayout2);
            }
    }

    public String convertTimestamp(long seconds){
        if(seconds < 60){
            return seconds + "s";
        } else if (seconds > 59 && seconds < 3600){
            return (seconds / 60) + "m";
        } else if (seconds > 3599 && seconds < 86400){
            return (seconds / 60 / 60) + "h";
        } else if (seconds > 86399 && seconds < 2628000){
            return (seconds / 60 / 60/ 24) + "d";
        } else if (seconds > 2627999 && seconds < 31536000){
            return (seconds / 60 / 60 / 24 / 30) + "mo";
        } else {
            return Math.floor(seconds / 31536000) + "yr";
        }
    }

}

