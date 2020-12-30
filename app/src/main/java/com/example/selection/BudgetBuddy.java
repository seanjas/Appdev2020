package com.example.selection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class BudgetBuddy extends AppCompatActivity implements View.OnClickListener{

   Spinner spin;
   Button btnChoose;

   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_budget_buddy);

       spin=findViewById(R.id.spin);
       btnChoose=findViewById(R.id.btnVersion);

       String version[] = getResources().getStringArray(R.array.version);

       ArrayAdapter adapt = new ArrayAdapter(this,android.R.layout.simple_list_item_1,version);
       spin.setAdapter(adapt);
       btnChoose.setOnClickListener(this);
   }
   public void onClick(View v) {
       String str=spin.getSelectedItem().toString();
       Toast.makeText(this, "You chose"+str, Toast.LENGTH_SHORT).show();
   }
}