package com.example.myhealthguide;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiseasesInnerAdapter extends RecyclerView.Adapter<DiseasesInnerAdapter.MyViewHolder> {

    private Context mContext;
    int postion;
    private FirebaseUser user;
    DatabaseReference favouriteReference;
    DatabaseReference myUser;
    DatabaseReference reference;
    private List<Disease> diseaseList = null;
    private ArrayList<Disease> arraylist;
    private OnItemClickListener mListener;
    Disease disease;
    Favourite deleteFav;
    ArrayList<Favourite> dataFav = new ArrayList<>();

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(int postion);
    }//End of OnItemClickListener

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView delete;


        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
            delete = (ImageView) view.findViewById(R.id.delete_fav);

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




    public DiseasesInnerAdapter(List<Disease> diseaseList, Context mContext) {
//        this.diseaseList = diseaseList;
this.mContext = mContext;
        /**
         * NEW, NOV 3  - HIND UPDATE -> CONSTRUCTOR CHANGED!
         */
        this.diseaseList = diseaseList;
        getDataDetailProduct();
//        inflater = LayoutInflater.from(mContext);
//        this.arraylist = new ArrayList<Disease>();
//        this.arraylist.addAll(diseaseList);
//        this.diseaseList = diseaseList;

//        getDataFav();
    }

//    private void getDataFav() {
//        for(Disease disease: diseaseList){
//            Favourite favourite = new Favourite("",disease.getName());
//            dataFav.add(favourite);
//        }
//
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        String userId = user.getUid();
//        reference = FirebaseDatabase.getInstance().getReference();
//        myUser = reference.child(userId);
//        favouriteReference = myUser.child("favouriteArrayList");
//        favouriteReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//
//                    String id = postSnapshot.getKey();
//                    Log.d("test",id);
//                    Favourite favourite = postSnapshot.getValue(Favourite.class);
//                    for(Favourite favourite1 : dataFav){
//                        if((favourite.getFavName().equals(favourite1.getFavName()))){
//                                favourite1.setId(favourite.getId());
//                        }
//                    }
//
//
//                }
//
//
//
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }
    private void getDataDetailProduct() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        myUser = reference.child(userId);
        favouriteReference = myUser.child("favouriteArrayList");
        favouriteReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataFav.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String id = postSnapshot.getKey();
                    Log.d("test",id);
                    Favourite favourite = postSnapshot.getValue(Favourite.class);
                    if(!(favourite.getFavName().equals("firstEmptyOne"))){
                        dataFav.add(favourite);
                        Log.d("test",favourite.getFavName());
                    }

                }



            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_inner_row, parent, false);

        return new MyViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


         disease = diseaseList.get(position);

        Log.d("TESTTEST",disease.getName());

        holder.name.setText(disease.getName());

//        Glide.with(mContext).load(disease.getImg()).into(holder.img);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postion = position;
                Dialog("Are you sure you want to unfavourite this disease");

            }
        });

    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }


    /**
     *
     * NEW, NOV 3
     * @param charText
     */

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        diseaseList.clear();
        if (charText.length() == 0) {
            diseaseList.addAll(arraylist);
        } else {
            for (Disease wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    diseaseList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    private void Dialog(String msg) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this.mContext);
        // Setting Dialog Title


        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            deleteFavList();

        }//end onClick
    });//end setPositiveButton
        alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

    private void deleteFavList() {

        for(Favourite favourite: dataFav){

            if(favourite.getFavName().equals(diseaseList.get(postion).getName()))
            {
                Log.d("favNAme",favourite.getFavName());
                Log.d("favDies",diseaseList.get(postion).getName());

                user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                String favIdDelete = favourite.getId();
                Log.d("IdFav",favIdDelete);
                DatabaseReference favL = FirebaseDatabase.getInstance().getReference().child(userId).child("favouriteArrayList").child(favIdDelete);
                favL.removeValue();
                break;


            }
        }
        ((Activity)(mContext)).finish();



    }



}
