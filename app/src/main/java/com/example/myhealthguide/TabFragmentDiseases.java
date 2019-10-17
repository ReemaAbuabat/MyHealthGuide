package com.example.myhealthguide;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class TabFragmentDiseases  extends Fragment {
    private RecyclerView recyclerView;
    private DiseasesAdapter mAdapter;
    private List<Disease> diseasesList = new ArrayList<>();
    private View view;

//    private EditText search ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_diseases_layout, container, false);
//        search = view.findViewById(R.id.search);
//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                filter(editable.toString());
//
//            }
//        });


        init();
        return view;
    }

    private void init() {


        Disease d = new Disease("\tvegetables\n" +
                "-\tnon-starchy: includes broccoli, carrots, greens, peppers, and tomatoes\n" +
                "-\tstarchy: includes potatoes, corn, and green peas\n" +
                "\tfruits-includes oranges, melon, berries, apples, bananas, and grapes\n" +
                "\tgrains-at least half of your grains for the day should be whole grains\n" +
                "-\tincludes wheat, rice, oats, cornmeal, barley, and quinoa\n" +
                "-\texamples: bread, pasta, cereal, and tortillas\n" +
                "\tprotein\n" +
                "-\tlean meat\n" +
                "-\tchicken or turkey without the skin\n" +
                "-\tfish\n" +
                "-\teggs\n" +
                "-\tnuts and peanuts\n" +
                "-\tdried beans and certain peas, such as chickpeas and split peas\n" +
                "-\tmeat substitutes, such as tofu\n" +
                "\tdairy-nonfat or low fat\n" +
                "-\tmilk or lactose-free milk if you have lactose intolerance\n" +
                "-\tyogurt\n" +
                "-\tcheese","\n" +
                "\tfried foods and other foods high in saturated fat and trans fat\n" +
                "\tfoods high in salt, also called sodium\n" +
                "\tsweets, such as baked goods, candy, and ice cream\n" +
                "\tbeverages with added sugars, such as juice, regular soda, and regular sports or energy drinks\n" +
                "\n" +
                "\tDrink water instead of sweetened beverages. Consider using a sugar substitute in your coffee or tea.\n" +
                "\n","Diabetes",R.drawable.ic_img);
        diseasesList.add(d);
        Disease d1 = new Disease("-\tEating vegetables\n" +
                "\n" +
                "-\tFruits\n" +
                "\t\n" +
                "-\twhole grains\n" +
                "\t\n" +
                "-\tIncluding fat-free or low-fat dairy products, fish, poultry, beans, nuts, and vegetable oils.\n" +
                "\t\n" +
                "-\tNuts, seeds, dry beans, and peas (4-5 times/week)\n","-\tLimiting foods that are high in saturated fat, such as fatty meats, full-fat dairy products, and tropical oils such as coconut, palm kernel, and palm oils.\n" +
                "\n" +
                "-\tLimiting sugar-sweetened beverages and sweets.\n" +
                "\n" +
                "-\tDon't add salt when cooking rice, pasta, and hot cereals. \n","High Blood Pressure",R.drawable.ic_img);
        diseasesList.add(d1);

        Disease d2 = new Disease("Its called the 'HEALTHY HEART EATING'.\n " +
                "-\tVegetables such as greens (spinach, collard greens, kale), broccoli, cabbage, and carrots\n" +
                "-\tFruits such as apples, bananas, oranges, pears, grapes, and prunes\n" +
                "-\tWhole grains such as plain oatmeal, brown rice, and whole-grain bread or tortillas\n" +
                "-\tFat-free or low-fat dairy foods such as milk, cheese, or yogurt\n" +
                "-\tProtein-rich foods:\n" +
                "* Fish high in omega-3 fatty acids, such as salmon, tuna, and trout, about 8 ounces a week\n" +
                "* Lean meats such as 95 percent lean ground beef or pork tenderloin\n" +
                "* Poultry such as skinless chicken or turkey\n" +
                "* Eggs\n" +
                "* Nuts, seeds, and soy products\n" +
                "* Legumes such as kidney beans, lentils, chickpeas, black-eyed peas, and lima beans\n" +
                "-   Oils and foods containing high levels of monounsaturated and polyunsaturated fats that can help lower blood cholesterol levels and the risk of cardiovascular disease. Some sources of these oils are:\n" +
                "* Canola, corn, olive, safflower, sesame, sunflower, and soybean oils\n" +
                "* Nuts such as walnuts, almonds, and pine nuts\n" +
                "* Nut and seed butters\n" +
                "* Salmon and trout\n" +
                "* Seeds such as sesame, sunflower, pumpkin, or flax\n" +
                "* Avocados\n" +
                "\n" +
                "\n" +
                "\"You should eat the right amount of calories for your body, which will vary based on your sex, age, and physical activity level.\n  ","-\tSodium\n" +
                "\n" +
                "-\tSaturated and trans fats:\n" +
                "\n" +
                "*Eat less than 10 percent of your daily calories from saturated fats found naturally in foods that come from animals and some plants (example of saturated fats: fatty cuts of meat, poultry with skin, whole-milk dairy foods, butter, lard, and coconut and palm oils).\n" +
                "\n" +
                "*Limit intake of trans fats to as low as possible by limiting foods that contain high amounts of trans fats ( example of trans fats: high amounts in foods made with partially hydrogenated oils, such as some desserts, microwave popcorn, frozen pizza, stick margarines, and coffee creamers).\n" +
                "\n" +
                "-\tAdded sugars:\n" +
                "*Sweetened drinks include soft drinks or sodas, fruit drinks, sweetened coffee and tea, energy drinks, alcoholic drinks, and favored waters.\n" +
                "*Snacks and sweets include grain-based desserts such as cakes, pies, cookies, brownies, doughnuts; dairy desserts such as ice cream, frozen desserts, and pudding; candies; sugars; jams; syrups; and sweet toppings\n" +
                "\n" +
                "-      Alcohol\n","High Blood Cholesterol Level ",R.drawable.ic_img);
        diseasesList.add(d2);

        Disease d3 = new Disease("-\tIncrease your daily intake of iron-rich foods to help treat your iron-deficiency anemia (dried beans, dried fruits, eggs, lean red meat, salmon, iron-fortified breads and cereals, peas, tofu, dried fruits, and dark green leafy vegetables).\n" +
                "\n" +
                "-\tIncrease your intake of vitamin C to help your body absorb iron. , such as oranges, strawberries, and tomatoes, may help increase your absorption of iron.\n" +
                "\n" +
                "Foods high in vitamin B12 include:\n" +
                "-\tmeat and poultry\n" +
                "-\torgan meats, such as liver\n" +
                "-\tfish and shellfish\n" +
                "-\teggs, milk, and dairy products\n" +
                "-\tsome fortified cereals, grains, and yeasts (check the label).\n" +
                "Foods high in folic acid (folate) include:\n" +
                "-\tdark green leafy vegetables, such as spinach and broccoli\n" +
                "-\tasparagus\n" +
                "-\tbeans\n" +
                "-\tpeas\n", "-\tAvoid drinking black tea, which reduces iron absorption.\n" +
                "\n" +
                "-\tlimit sodium, saturated and trans fats, added sugars, and alcohol.\n","Anemia", R.drawable.ic_img);
        diseasesList.add(d3);

        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new DiseasesAdapter( diseasesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(true);

        mAdapter.setOnItemClickListener(new DiseasesAdapter.OnItemClickListener(){

            public void onItemClick(int position) {

                moveToNewActivity(position);

            }
        });

    }//End of init()
//    private void filter(String text){
//        ArrayList<Disease> filteredList = new ArrayList<>();
//
//        for (Disease item : diseasesList){
//            if(item.getName().toLowerCase().contains(text.toLowerCase())){
//                filteredList.add(item);
//            }
//        }
//        mAdapter.filterList(filteredList);
//    }

    private void moveToNewActivity (int position) {
        Intent intent = new Intent(getActivity() , InfoActivity.class);
        Disease v = diseasesList.get(position);
        String name = v.getName();
        String allowedFood = v.getAllowed();
        String notAllowedFood = v.getNotAllowed();
        intent.putExtra("name",name);
        intent.putExtra("allowedFood",allowedFood);
        intent.putExtra("notAllowedFood",notAllowedFood);
        startActivity(intent);



    }//End of moveToNewActivity()

}
