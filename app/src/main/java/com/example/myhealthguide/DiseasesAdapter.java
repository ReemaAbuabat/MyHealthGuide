package com.example.myhealthguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class DiseasesAdapter extends RecyclerView.Adapter<DiseasesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Disease> diseaseList;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(int postion);
    }//End of OnItemClickListener

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;


        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);

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




    public DiseasesAdapter(List<Disease> diseaseList) {
        this.diseaseList = diseaseList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Disease disease = diseaseList.get(position);

        holder.name.setText(disease.getName());
//        Glide.with(mContext).load(disease.getImg()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }

    public void removeDisease(int position) {
        diseaseList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreDisease(Disease disease, int position) {
        diseaseList.add(position, disease);
        notifyItemInserted(position);
    }

    public List<Disease> getDiseaseList() {
        return diseaseList;
    }
}