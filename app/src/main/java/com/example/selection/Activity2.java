package com.example.selection;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends BaseActivity {
  EditText txtEmployeeName,txtCode,txtSalary;
  TextView lblNetpay;
  Button btnCalculate;
    Double dblDeduction,dblSalary;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_net_pay;
    }

    @Override
    protected String getActivityName() {
        return "Activity 2";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       txtEmployeeName=findViewById(R.id.txtEmployeeName);
       txtSalary=findViewById(R.id.txtSalary);
       txtCode=findViewById(R.id.txtCode);
       btnCalculate=findViewById(R.id.btnCalculate);
       lblNetpay=findViewById(R.id.lblNetpay);

       btnCalculate.setOnClickListener(v->{

       });
        txtSalary.setFilters(new InputFilter[]{ new InputFilterMinMaxDouble(1,100000) });
        InputFilter filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                source = source.toString().toLowerCase();
                for (int i=start; i < end; i++){
                    if(!"fbke".contains(source.charAt(i)+"")){
                        String message = "Invalid code. Only allowed codes are F, B, K, or E.";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        return "";
                    }
                }
                return null;
            }
        };
        txtCode.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(1)});
        txtEmployeeName.addTextChangedListener(watcher);
        txtSalary.addTextChangedListener(watcher);
        txtCode.addTextChangedListener(watcher);
        btnCalculate.setOnClickListener(v->{
            dblSalary=Double.parseDouble(txtSalary.getText().toString());
            switch (txtCode.getText().toString().toUpperCase().charAt(0)){
                case 'F':
                    dblDeduction= 0.0;
                    break;
                case  'B':
                    dblDeduction=150.65;
                    break;
                case 'K':
                    dblDeduction=375.85;
                    break;
                case 'E':
                        dblDeduction=500.50;
                        break;
            }
            DisplayOutput(dblSalary-dblDeduction);
        });
    }

    private TextWatcher watcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            DisplayOutput(0);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private  void DisplayOutput(double salary){
        lblNetpay.setText(String.format("%,3.2f", salary));
    }
}