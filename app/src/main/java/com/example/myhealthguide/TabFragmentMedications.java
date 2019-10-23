package com.example.myhealthguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabFragmentMedications extends Fragment {

    private RecyclerView recyclerView;
    private MedicationAdapter mAdapter;
    public View view;
    private FirebaseUser user;
    List<Medication> medicationArrayList = new ArrayList<>();
    public FloatingActionButton floatingActionButton;
    DatabaseReference medicationReference;
    DatabaseReference myUser;
    DatabaseReference reference;
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_medications_layout, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        floatingActionButton =view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), addMedication.class);
                startActivity(intent);

            }
        });
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        String userId = user.getUid();
//        reference = FirebaseDatabase.getInstance().getReference();
//        myUser = reference.child(userId);
//        medicationReference = myUser.child("medicationList");
//
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//               medicationArrayList = new ArrayList<>();
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Medication medication = (Medication) ds.getValue();
//                    if(!(medication.getId().equals("0"))){
//                        medicationArrayList.add(medication);
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };

//        medicationReference.addListenerForSingleValueEvent(eventListener);
        getDataDetailProduct();


        return view;


    }
    private void init() {



    recyclerView = view.findViewById(R.id.recycler_viewMEd);
    mAdapter = new MedicationAdapter(medicationArrayList);
    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 2);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(mAdapter);
    recyclerView.setNestedScrollingEnabled(true);
        mAdapter.setOnItemClickListener(new MedicationAdapter.OnItemClickListener() {

            public void onItemClick(int position) {

                moveToNewActivity(position);

            }
        });
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
    recyclerView.addItemDecoration(dividerItemDecoration);


}

    private void moveToNewActivity(int position) {
        Intent intent = new Intent(getActivity() , MedicationInformation.class);
        Medication m = medicationArrayList.get(position);
         String id = m.getId();
         intent.putExtra("id", id);
        startActivity(intent);
    }

    private void getDataDetailProduct() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference();
        myUser = reference.child(userId);
        medicationReference = myUser.child("medicationList");
        medicationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                medicationArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                         String id = postSnapshot.getKey();
                         Log.d("test",id);
                        Medication medication = postSnapshot.getValue(Medication.class);
                        if(!(medication.getMedName().equals("firstEmptyOne"))){
                            medicationArrayList.add(medication);
                            Log.d("test",medication.getMedName());
                        }

                }
                progressDialog.hide();
                init();


                }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.hide();
            }
        });
    }


}
