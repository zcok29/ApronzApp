/*
 * MyAdaptor
 */
package com.example.mvp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder> {

    String data1[], data2[];
    int images[];
    Context context;
    Button button;

    public MyAdaptor(Context ct, String s1[], String s2[], int img[], Button btn){
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
        button = btn;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new
        MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String location = data1[position];
        String contact = data2[position];
        holder.myText1.setText(location);
        holder.myText2.setText(contact);
        holder.myImage.setImageResource(images[position]);
        holder.myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent startIntent = new Intent(context.getApplicationContext(), SecondActivity.class);
                startIntent.putExtra("SOMETHING", location);
                context.startActivity(startIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2;
        ImageView myImage;
        Button myButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.locationText);
            myText2 = itemView.findViewById(R.id.contactsText);
            myImage = itemView.findViewById(R.id.myImage);
            myButton = itemView.findViewById(R.id.moreInfoBtn);

        }
    }
}
