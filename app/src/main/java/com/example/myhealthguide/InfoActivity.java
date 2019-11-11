package com.example.myhealthguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RowAdapter adapter;
    private List<Row> albumList;
    String name, allowedFood,notAllowedFood;
    Button healthBtn;
    private boolean check ;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    ImageView healthMin, share;




    ArrayList<Favourite> favouriteArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getExtras();
        initToolBar();
        initCollapsingToolbar();
        getList();





        healthMin = findViewById(R.id.healthMinstry);
        share=findViewById(R.id.shareBTN);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        healthBtn=(Button) findViewById(R.id.healthBtn);
        albumList = new ArrayList<>();
        adapter = new RowAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "disease:"+name+"\n allowed food: \n"+allowedFood+"\n Not allowed food: \n"+notAllowedFood;
                myIntent.putExtra(Intent.EXTRA_SUBJECT, name);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));
            }
        });

        prepareAlbums();

        healthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.moh.gov.sa/en/HealthAwareness/MedicalTools/Pages/default.aspx"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        healthMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.moh.gov.sa/en/HealthAwareness/MedicalTools/Pages/default.aspx"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


    }
    private boolean vlidate() {


        if(check == true){
            return true;
        }
        return false;

    }
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        //set toolbar back Button
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }//End of onClick()
        });//End of OnClickListener()

    }//End of initToolBar();


    private void getExtras() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            name = (String) intent.getSerializableExtra("name");
            allowedFood = (String) intent.getSerializableExtra("allowedFood");
            notAllowedFood = (String) intent.getSerializableExtra("notAllowedFood");

        }//End of if

    }//End of getExtras()

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(name);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(name);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(name);
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.allowed_food,
                R.drawable.not_allowed_food,
        };

        Row a = new Row("Allowed Food", "", covers[0],allowedFood,name);
        albumList.add(a);

        a = new Row("Forbidden Food", "", covers[1],notAllowedFood,name);
        albumList.add(a);


        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public void getList(){



        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
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

                    return;
                }

                for(Favourite favourite: favouriteArrayList){
                    int postion = adapter.getPosition();
                    if(favourite.getFavName().equals(albumList.get(postion).getdName())) {
                        Log.d("false",favourite.getFavName());
                        check = false;

                        return;
                    }
                    else{
                        check = true;

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
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this);
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
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this);
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
