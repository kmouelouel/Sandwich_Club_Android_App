package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    // add more variables
    private Sandwich sandwich;
    // Create a TextView variables to show the data
    private TextView mAlsoKnownAsTextView;
    private TextView mIngredientsTextView;
    private TextView mOriginPlaceTextView;
    private TextView mDescriptionTextVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Use findViewById to get a reference to TextView Variables
        mAlsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
        mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        mOriginPlaceTextView = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTextVIew = (TextView) findViewById(R.id.description_tv);
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        //place of origin :string
        if (!sandwich.getPlaceOfOrigin().isEmpty())
            mOriginPlaceTextView.setText(sandwich.getPlaceOfOrigin());
        else mOriginPlaceTextView.setText(R.string.no_data);
        // description :string

        if (!sandwich.getDescription().isEmpty())
            mDescriptionTextVIew.setText(sandwich.getDescription());
        else mDescriptionTextVIew.setText(R.string.no_data);

        //AlsoKnownAs :List<String>
        if (!sandwich.getAlsoKnownAs().isEmpty())
            mAlsoKnownAsTextView.setText(convertListToString(sandwich.getAlsoKnownAs()));
        else mAlsoKnownAsTextView.setText(R.string.no_data);

        //Ingredients: List
        if (!sandwich.getIngredients().isEmpty())
            mIngredientsTextView.setText(convertListToString(sandwich.getIngredients()));
        else mIngredientsTextView.setText(R.string.no_data);
    }

    private StringBuilder convertListToString(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i) + "\n");
        }
        return result;
    }
}
