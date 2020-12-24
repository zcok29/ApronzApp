/*
 * LocationAdapter
 */
package com.example.macExplore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {

    List<Bitmap> adapterImages;
    Context context;
    Button button;
    List<Location> locationData;

    public LocationAdapter(Context ct, List<Location> locationData, List<Bitmap> images, Button btn){
        context = ct;
        adapterImages = images;
        button = btn;
        this.locationData = locationData;
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
        String location = locationData.get(position).name;
        String contact = locationData.get(position).contact;
        holder.myText1.setText(location);
        holder.myText2.setText(contact);
        holder.myImage.setImageBitmap(adapterImages.get(position));
        holder.myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(context.getApplicationContext(),CommentPageActivity.class);
                startIntent.putExtra("LOCATION NAME", location);
                startIntent.putExtra("DOCUMENT ID", locationData.get(position).documentID);
                context.startActivity(startIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adapterImages.size();
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
