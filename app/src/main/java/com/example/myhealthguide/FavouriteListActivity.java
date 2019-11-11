package com.example.myhealthguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavouriteListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DiseasesAdapter adapter;
    private FirebaseUser user;
    DatabaseReference favouriteReference;
    DatabaseReference myUser;
    DatabaseReference reference;
    private ProgressDialog progressDialog;
    ArrayList<Favourite> dataFav = new ArrayList<>();
    ArrayList<Disease> diseases = new ArrayList<>();
    ArrayList<Disease> finalFav = new ArrayList<>();
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        progressDialog = new ProgressDialog(this);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        initToolBar();
        initArray();
        getDataDetailProduct();
//        enableSwipeToDeleteAndUndo();

    }


    private void initArray() {

            Disease d = new Disease(
                    "1- Non-starchy: includes broccoli, carrots, greens, peppers, and tomatoes\n" +
                            "2- Starchy: includes potatoes, corn, and green peas\n" +
                            "3- Fruits: includes oranges, melon, berries, apples, bananas, and grapes\n" +
                            "4- Grains, at least half of your grains for the day should be whole grains\n" +
                            "- includes: wheat, rice, oats, cornmeal, barley, and quinoa\n" +
                            "- examples: bread, pasta, cereal, and tortillas\n" +
                            "5- Proteins:\n" +
                            "- Lean meat\n" +
                            "- Chicken or turkey without the skin\n" +
                            "- Fish\n" +
                            "- Eggs\n" +
                            "- Nuts and peanuts\n" +
                            "- Dried beans and certain peas, such as chickpeas and split peas\n" +
                            "6- Meat substitutes, such as tofu\n" +
                            "7- Dairy-nonfat or low fat\n" +
                            "- Milk or lactose-free milk if you have lactose intolerance\n" +
                            "- Yogurt\n" +
                            "- Cheese","\n" +
                    "8- Fried foods and other foods high in saturated fat and trans fat\n" +
                    "9- Foods high in salt, also called sodium\n" +
                    "10 - Vegetables\n" +
                    "11- Sweets, such as baked goods, candy, and ice cream\n" +
                    "12- Beverages with added sugars, such as juice, regular soda, and regular sports or energy drinks\n" +
                    "\n" +
                    "13- Drink water instead of sweetened beverages. Consider using a sugar substitute in your coffee or tea.\n" +
                    "\n","Diabetes",R.drawable.ic_img);
            diseases.add(d);
            Disease d1 = new Disease("1- Eating vegetables\n" +
                    "\n" +
                    "2- Fruits\n" +
                    "\t\n" +
                    "3- Whole grains\n" +
                    "\t\n" +
                    "4- Including fat-free or low-fat dairy products, fish, poultry, beans, nuts, and vegetable oils.\n" +
                    "\t\n" +
                    "5- Nuts, seeds, dry beans, and peas (4-5 times/week)\n","1- Limiting foods that are high in saturated fat, such as fatty meats, full-fat dairy products, and tropical oils such as coconut, palm kernel, and palm oils.\n" +
                    "\n" +
                    "2- Limiting sugar-sweetened beverages and sweets.\n" +
                    "\n" +
                    "3- Don't add salt when cooking rice, pasta, and hot cereals. \n","High Blood Pressure",R.drawable.ic_img);
            diseases.add(d1);

            Disease d2 = new Disease("Its called the 'HEALTHY HEART EATING'.\n " +
                    "1- Vegetables such as greens (spinach, collard greens, kale), broccoli, cabbage, and carrots\n" +
                    "2- Fruits such as apples, bananas, oranges, pears, grapes, and prunes\n" +
                    "3- Whole grains such as plain oatmeal, brown rice, and whole-grain bread or tortillas\n" +
                    "4- Fat-free or low-fat dairy foods such as milk, cheese, or yogurt\n" +
                    "5- Protein-rich foods:\n" +
                    "* Fish high in omega-3 fatty acids, such as salmon, tuna, and trout, about 8 ounces a week\n" +
                    "* Lean meats such as 95 percent lean ground beef or pork tenderloin\n" +
                    "* Poultry such as skinless chicken or turkey\n" +
                    "* Eggs\n" +
                    "* Nuts, seeds, and soy products\n" +
                    "* Legumes such as kidney beans, lentils, chickpeas, black-eyed peas, and lima beans\n" +
                    "6- Oils and foods containing high levels of monounsaturated and polyunsaturated fats that can help lower blood cholesterol levels and the risk of cardiovascular disease. Some sources of these oils are:\n" +
                    "* Canola, corn, olive, safflower, sesame, sunflower, and soybean oils\n" +
                    "* Nuts such as walnuts, almonds, and pine nuts\n" +
                    "* Nut and seed butters\n" +
                    "* Salmon and trout\n" +
                    "* Seeds such as sesame, sunflower, pumpkin, or flax\n" +
                    "* Avocados\n" +
                    "\n" +
                    "\n" +
                    "\"You should eat the right amount of calories for your body, which will vary based on your sex, age, and physical activity level.\n  ","1- Sodium\n" +
                    "\n" +
                    "2- Saturated and trans fats:\n" +
                    "\n" +
                    "*Eat less than 10 percent of your daily calories from saturated fats found naturally in foods that come from animals and some plants (example of saturated fats: fatty cuts of meat, poultry with skin, whole-milk dairy foods, butter, lard, and coconut and palm oils).\n" +
                    "\n" +
                    "*Limit intake of trans fats to as low as possible by limiting foods that contain high amounts of trans fats ( example of trans fats: high amounts in foods made with partially hydrogenated oils, such as some desserts, microwave popcorn, frozen pizza, stick margarines, and coffee creamers).\n" +
                    "\n" +
                    "3- Added sugars:\n" +
                    "*Sweetened drinks include soft drinks or sodas, fruit drinks, sweetened coffee and tea, energy drinks, alcoholic drinks, and favored waters.\n" +
                    "*Snacks and sweets include grain-based desserts such as cakes, pies, cookies, brownies, doughnuts; dairy desserts such as ice cream, frozen desserts, and pudding; candies; sugars; jams; syrups; and sweet toppings\n" +
                    "\n" +
                    "4- Alcohol\n","High Blood Cholesterol Level ",R.drawable.ic_img);
            diseases.add(d2);

            Disease d3 = new Disease("1- Increase your daily intake of iron-rich foods to help treat your iron-deficiency anemia (dried beans, dried fruits, eggs, lean red meat, salmon, iron-fortified breads and cereals, peas, tofu, dried fruits, and dark green leafy vegetables).\n" +
                    "\n" +
                    "2- Increase your intake of vitamin C to help your body absorb iron. , such as oranges, strawberries, and tomatoes, may help increase your absorption of iron.\n" +
                    "\n" +
                    "3- Foods high in vitamin B12 include:\n" +
                    "* Meat and poultry\n" +
                    "* Organ meats, such as liver\n" +
                    "* Fish and shellfish\n" +
                    "* Eggs, milk, and dairy products\n" +
                    "* Some fortified cereals, grains, and yeasts (check the label).\n" +
                    "4- Foods high in folic acid (folate) include:\n" +
                    "* Dark green leafy vegetables, such as spinach and broccoli\n" +
                    "* Asparagus\n" +
                    "* Beans\n" +
                    "* Peas\n", "1- Avoid drinking black tea, which reduces iron absorption.\n" +
                    "\n" +
                    "2- Limit sodium, saturated and trans fats, added sugars, and alcohol.\n","Anemia", R.drawable.ic_img);
            diseases.add(d3);
    }

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
                progressDialog.hide();
                setArrayValues();
                init();


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.hide();
            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new DiseasesAdapter(finalFav);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);

        adapter.setOnItemClickListener(new DiseasesAdapter.OnItemClickListener(){

            public void onItemClick(int position) {

                moveToNewActivity(position);

            }
        });
    }

    private void setArrayValues() {
        for(Favourite favourite: dataFav){

            for(Disease disease: diseases){
                if(favourite.getFavName().equals(disease.getName())){
                    finalFav.add(disease);
                    break;
                }
            }
        }
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fav);
        toolbar.setTitle("Favourite list");
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
    private void moveToNewActivity (int position) {
        Intent intent = new Intent(FavouriteListActivity.this , InfoActivity.class);
        Disease v = finalFav.get(position);
        String name = v.getName();
        String allowedFood = v.getAllowed();
        String notAllowedFood = v.getNotAllowed();
        intent.putExtra("name",name);
        intent.putExtra("allowedFood",allowedFood);
        intent.putExtra("notAllowedFood",notAllowedFood);
        startActivity(intent);



    }//End of moveToNewActivity()

}
