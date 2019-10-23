package com.example.myhealthguide;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MyViewHolder> {

private Context mContext;
private List<Medication> medicationAdapterList;
private OnItemClickListener mListener;

public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
        }
public interface OnItemClickListener {
    void onItemClick(int postion);
}//End of OnItemClickListener

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView image;


    public MyViewHolder(View view, final MedicationAdapter.OnItemClickListener listener) {
        super(view);

        name = (TextView) view.findViewById(R.id.med_name);
        image= view.findViewById(R.id.image_med);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }//End of inner if
                }//End of big if
            }
        });

    }
}

    public MedicationAdapter(List<Medication> medicationAdapterList) {
        this.medicationAdapterList = medicationAdapterList;
    }

    @Override
    public MedicationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        return new MedicationAdapter.MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(MedicationAdapter.MyViewHolder holder, int position) {

        Medication medication = medicationAdapterList.get(position);

        holder.name.setText(medication.getMedName());
      Bitmap bitmap = StringToBitMap(medication.getMedImg());
      holder.image.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return medicationAdapterList.size();
    }



    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }


}