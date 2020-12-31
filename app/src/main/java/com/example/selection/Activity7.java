package com.example.selection;

import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Activity7 extends BaseActivity {
    Button btnCompute, btnClearChecks;
    EditText txtBudget;
    CheckBox chkHousing, chkTransportation, chkGroceries, chkUtilities;
    CheckBox chkInvestment, chkDebt, chkRetirement, chkEmergency;
    CheckBox chkDining, chkSocializing, chkClothing, chkTravel;
    CheckBox[] arrAbsolute;
    CheckBox[] arrFinancial;
    CheckBox[] arrVoluntary;
    double[] arrAbsoluteValue = {0, 0, 0, 0};
    double[] arrFinancialValue = {0, 0, 0, 0};
    double[] arrVoluntaryValue = {0, 0, 0, 0};
    String[] arrAbsoluteLabels = {"Housing", "Transportation", "Groceries", "Utilities"};
    String[] arrFinancialLabels = {"Investment", "Debt Payments", "Retirement", "Emergency Funds"};
    String[] arrVoluntaryLabels = {"Dining Out", "Socializing", "Clothing & Groceries", "Travel & Leisure"};

    // Dialog Box
    DialogWith2Buttons dialogResult;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_budget_buddy;
    }

    @Override
    protected String getActivityName() {
        return "Activity 7";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create connection to the layout components
        chkHousing = findViewById(R.id.chkHousing);
        chkTransportation = findViewById(R.id.chkTransportation);
        chkGroceries = findViewById(R.id.chkGroceries);
        chkUtilities = findViewById(R.id.chkUtilities);
        arrAbsolute = new CheckBox[]{chkHousing, chkTransportation, chkGroceries, chkUtilities};

        chkInvestment = findViewById(R.id.chkInvestment);
        chkDebt = findViewById(R.id.chkDebt);
        chkRetirement = findViewById(R.id.chkRetirement);
        chkEmergency = findViewById(R.id.chkEmergency);
        arrFinancial = new CheckBox[]{chkInvestment, chkDebt, chkRetirement, chkEmergency};

        chkDining = findViewById(R.id.chkDining);
        chkSocializing = findViewById(R.id.chkSocializing);
        chkClothing = findViewById(R.id.chkClothing);
        chkTravel = findViewById(R.id.chkTravel);
        arrVoluntary = new CheckBox[]{chkDining, chkSocializing, chkClothing, chkTravel};

        btnCompute = findViewById(R.id.btnCompute);
        btnCompute.setOnClickListener(v -> computeBudget());
        btnClearChecks = findViewById(R.id.btnClearChecks);
        btnClearChecks.setOnClickListener(v -> clearChecks());

        txtBudget = findViewById(R.id.txtBudget);

        // Create filter for txtSalary
        txtBudget.setFilters(new InputFilter[]{new InputFilterMinMaxDouble(1, 100000)});

        // Initialize Dialog
        dialogResult = new DialogWith2Buttons("Budget Buddy", "OK", "Clear", this) {
            @Override
            public void onPostiveClick() {
            }

            @Override
            public void onNegativeClick() {
                btnClearChecks.performClick();
            }
        };
    }

    public void computeBudget() {
        double dblBudget = 0;
        // Check first for blank input on budget
        try {
            dblBudget = Double.parseDouble(txtBudget.getText().toString());
        } catch (Exception e) {
            MsgBox("Add budget first!");
            return;
        }
        String message = "";
        double absoluteBudget = dblBudget * 0.5;
        double totalAbsolutePayable = 0;
        double financialBudget = dblBudget * 0.2;
        double totalFinancialPayable = 0;
        double voluntaryBudget = dblBudget * 0.3;
        double totalVoluntaryPayable = 0;
        double totalPayable = 0;
        boolean isDeficit = false;

        // For Absolute Necessities
        for (int i = 0; i < arrAbsoluteValue.length; i++) {
            if (arrAbsolute[i].isChecked()) {
                arrAbsoluteValue[i] = randomNumber(0, (int) absoluteBudget);
                totalAbsolutePayable += arrAbsoluteValue[i];
            } else arrAbsoluteValue[i] = 0;
        }
        // For Financial Obligations
        for (int i = 0; i < arrFinancialValue.length; i++) {
            if (arrFinancial[i].isChecked()) {
                arrFinancialValue[i] = randomNumber(0, (int) financialBudget);
                totalFinancialPayable += arrFinancialValue[i];
            } else arrFinancialValue[i] = 0;
        }
        // For Voluntary Obligations
        for (int i = 0; i < arrVoluntaryValue.length; i++) {
            if (arrVoluntary[i].isChecked()) {
                arrVoluntaryValue[i] = randomNumber(0, (int) voluntaryBudget);
                totalVoluntaryPayable += arrVoluntaryValue[i];
            } else arrVoluntaryValue[i] = 0;
        }
        // Get Total Payable and Check if Deficit
        totalPayable = totalAbsolutePayable + totalFinancialPayable + totalVoluntaryPayable;
        isDeficit = totalPayable > dblBudget;
        // Compose Message
        message += String.format("Net Pay: P%,3.2f%n%n", dblBudget);
        message += String.format("Absolute Necessities (P%,3.2f)%n", absoluteBudget);
        message += summarizeBudget(arrAbsoluteLabels, arrAbsoluteValue);
        message += String.format("SUBTOTAL: P%,3.2f%n", totalAbsolutePayable);
        message += String.format("%nFinancial Obligations (P%,3.2f)%n", financialBudget);
        message += summarizeBudget(arrFinancialLabels, arrFinancialValue);
        message += String.format("SUBTOTAL: P%,3.2f%n", totalFinancialPayable);
        message += String.format("%nVoluntary Obligations (P%,3.2f)%n", voluntaryBudget);
        message += summarizeBudget(arrVoluntaryLabels, arrVoluntaryValue);
        message += String.format("SUBTOTAL: P%,3.2f%n", totalVoluntaryPayable);
        message += String.format("%nTOTAL: P%,3.2f%n", totalPayable);
        message += String.format("%nREMARKS: %s", isDeficit ? "DEFICIT" : "ON-BUDGET");
        // Display Dialog
        dialogResult.setMessage(message);
        dialogResult.openDialog();
    }

    private void clearChecks() {
        for (CheckBox box : arrAbsolute) box.setChecked(false);
        for (CheckBox box : arrFinancial) box.setChecked(false);
        for (CheckBox box : arrVoluntary) box.setChecked(false);
        txtBudget.setText("");
    }

    private String summarizeBudget(String[] obligations, double[] payables) {
        String hold = "";
        for (int i = 0; i < obligations.length; i++) {
            hold += String.format("%s = P%,3.2f%n", obligations[i], payables[i]);
        }
        return hold;
    }
}