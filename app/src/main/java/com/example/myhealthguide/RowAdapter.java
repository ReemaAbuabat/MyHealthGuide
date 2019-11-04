package com.example.myhealthguide;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.MyViewHolder>   {
    private Context mContext;
    private List<Row> albumList;
    private FirebaseUser user;
private boolean check ;
    private boolean mycheck ;
    private int position ;
    private ProgressDialog progressDialog;
    ArrayList<Favourite> favouriteArrayList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,count2;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            // count2 = (TextView) view.findViewById(R.id.count1);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
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
        holder.count.setText(album.getNumOfallowdance());

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });

        holder.count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, InnerAllowedanceActivity.class);
                intent.putExtra("name",albumList.get(position).getName());
                intent.putExtra("details",albumList.get(position).getDetails());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        getList();
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                {


                    if(vlidate()){
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        String userId = user.getUid();


                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference myUser = reference.child(userId);
                        DatabaseReference favouriteReference = myUser.child("favouriteArrayList");

                        String id = favouriteReference.push().getKey();
                        Favourite favourite = new Favourite(id, albumList.get(position).getdName());
                        favouriteReference.child(id).setValue(favourite);
                        Dialog("added to your favourite !");


                    }else{
                            wrongInfoDialog("its already in your favourite list");
                    }


                    return true;

                }

                case R.id.action_play_next:
                    Toast.makeText(mContext, "share", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    private boolean vlidate() {



        Log.d("validate value", String.valueOf(check));

       if(check == true){
           return true;
       }else {
           return false;
       }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public void getList(){



        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference  reference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference  myUser = reference.child(userId);
        DatabaseReference  favouriteReference = myUser.child("favouriteArrayList");
        favouriteReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favouriteArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String id = postSnapshot.getKey();
                    Log.d("test",id);
                    Favourite favourite = postSnapshot.getValue(Favourite.class);
                    if(!(favourite.getFavName().equals("firstEmptyOne"))){
                        favouriteArrayList.add(favourite);
                        Log.d("add",favourite.getFavName());
                    }

                }
//
                if(favouriteArrayList.isEmpty()){
                    Log.d("empty", String.valueOf(favouriteArrayList.size()));
                     check = true;
                     mycheck = true;
                     return;
                }

                for(Favourite favourite: favouriteArrayList){

                    if(favourite.getFavName().equals(albumList.get(position).getdName())) {
                        Log.d("false",favourite.getFavName());
                        check = false;
                        mycheck = false;
                        return;
                    }
                    else{
                        check = true;
                        mycheck = true;
                    }

                }
                Log.d("check", String.valueOf(check));
            return;


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            });




    }
    private void wrongInfoDialog(String msg) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        // Setting Dialog Title
        alertDialog.setTitle(R.string.Wrong);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.exclamation);
        //Setting Negative "ok" Button
        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

    private void Dialog(String msg) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        // Setting Dialog Title


        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {




            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

    }
