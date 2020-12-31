package com.example.selection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class Activity3 extends BaseActivity {
    AutoCompleteTextView txtType,txtBreed;
    Button btnAdopt;
    String [] breeds;
    ArrayAdapter<String> adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_pet;
    }

    @Override
    protected String getActivityName() {
        return "Activity 3";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        txtType=findViewById(R.id.txtType);
        txtBreed=findViewById(R.id.txtBreed);
        btnAdopt=findViewById(R.id.btnAdopt);

        String[] types=getResources().getStringArray(R.array.types);
         adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,types);
        txtType.setAdapter(adapter);

        txtType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateBreedAutocomplete(parent.getItemAtPosition(position).toString());
            }
        });
        txtBreed.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                updateBreedAutocomplete(txtType.getText().toString());
            }
        });
        btnAdopt.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Thank you for adopting this pet!",Toast.LENGTH_LONG).show();
        });
    }
        private void updateBreedAutocomplete(String type){
            breeds = new String[]{};
            switch (type){
                case "dog":
                    breeds=getResources().getStringArray(R.array.dog);
                    break;
                case "cat":
                    breeds=getResources().getStringArray(R.array.cat);
                    break;
                case "snake":
                    breeds=getResources().getStringArray(R.array.snake);
                    break;
                case "pokemon":
                    breeds=getResources().getStringArray(R.array.pokemon);
                    break;
                default:
                    break;
            }
            adapter= new ArrayAdapter<>(txtBreed.getContext(),android.R.layout.simple_list_item_1,breeds);
            txtBreed.setAdapter(adapter);
        }
    }
