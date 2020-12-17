/*
 * LocationAdapter
 */
package com.example.macExplore;

import android.content.Context;
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
        String timestamp = new java.text.SimpleDateFormat("HH:mm  MM/dd/yy").format(new java.util.Date (Integer.parseInt(commentData.get(position).timestamp)*1000L));
        String username = commentData.get(position).user;
        holder.myText1.setText(comment);
        holder.myText2.setText(timestamp);
        holder.myText3.setText(username);

    }

    @Override
    public int getItemCount() {
        return commentData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myText1, myText2, myText3;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.commentText);
            myText2 = itemView.findViewById(R.id.timestampText);
            myText3 = itemView.findViewById(R.id.usernameText);

            }
    }
}

