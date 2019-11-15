package com.example.myhealthguide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.MyViewHolder> {
    private Context mContext;
    private List<Row> albumList;
    private int position;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }


    public RowAdapter(Context mContext, List<Row> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Row album = albumList.get(position);
        this.position = position;
        holder.title.setText(album.getName());


        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, InnerAllowedanceActivity.class);
                intent.putExtra("name", albumList.get(position).getName());
                intent.putExtra("details", albumList.get(position).getDetails());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public int getPosition() {
        return position;
    }


}
