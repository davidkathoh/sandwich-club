package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {
    private TextView mOrigin,mIngredients,mDescription,mAlsoKnowAs,mMainName;


    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = "QuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_detail);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        ImageView ingredientsIv = findViewById(R.id.image_iv);
         new Sandwich();
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
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
            Log.e("JSON",sandwich.getMainName()+sandwich.getDescription()+sandwich.getPlaceOfOrigin());
        } catch (JSONException e) {
            e.printStackTrace();
            closeOnError();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        mOrigin.setText(sandwich.getPlaceOfOrigin());

        mDescription.setText(sandwich.getDescription());
        mIngredients.setText(sandwich.getIngredients().toString());
        mMainName.setText(sandwich.getMainName());

        if (!sandwich.getAlsoKnownAs().isEmpty()){
            for (String alsoKnownAs: sandwich.getAlsoKnownAs())
            mAlsoKnowAs.append(alsoKnownAs+", ");
        }
        if (!sandwich.getIngredients().isEmpty()){
            for (String ingredient:sandwich.getIngredients()){
                mIngredients.append(ingredient+" ,");
            }
        }
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
        mOrigin = findViewById(R.id.origin_tv);
        mAlsoKnowAs = findViewById(R.id.also_known_tv);
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mMainName = findViewById(R.id.main_name_tv);



    }

}
