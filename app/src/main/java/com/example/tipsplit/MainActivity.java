package com.example.tipsplit;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private EditText totalBill;
    private TextView TipResult;
    private TextView TotalBillWithTip;
    private EditText noOfPeople;
    private TextView totalPerPerson;
    private TextView overage;
    private RadioGroup radioGroup;

    double total_bill_D;
    double tip_percent;

    private Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalBill = findViewById(R.id.TotalBill);
        TipResult = findViewById(R.id.TipResult);
        TotalBillWithTip = findViewById(R.id.totalWithTip);
        noOfPeople = findViewById(R.id.NoOfPeople);
        totalPerPerson = findViewById(R.id.TotalPerPerson);
        overage = findViewById(R.id.Overage);
        clear = findViewById(R.id.clear);
        radioGroup = findViewById(R.id.radioGroup);

    }

    public void clear(View v){
        totalBill.getText().clear();
        radioGroup.clearCheck();
        noOfPeople.getText().clear();
        TipResult.setText("");
        TotalBillWithTip.setText("");
        totalPerPerson.setText("");
        overage.setText("");
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("TipResult", TipResult.getText().toString());
        outState.putString("TotalBillWithTip", TotalBillWithTip.getText().toString());
        outState.putString("totalPerPerson", totalPerPerson.getText().toString());
        outState.putString("overage", overage.getText().toString());
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TipResult.setText(savedInstanceState.getString("TipResult"));
        TotalBillWithTip.setText(savedInstanceState.getString("TotalBillWithTip"));
        totalPerPerson.setText(savedInstanceState.getString("totalPerPerson"));
        overage.setText(savedInstanceState.getString("overage"));
    }

    public void doRadioGroup(View v){
        String total_bill = totalBill.getText().toString();
        if(total_bill.isEmpty() || total_bill.equals("0")){
            Toast.makeText(this, "Bill must be greater then zero", Toast.LENGTH_SHORT).show();
            radioGroup.clearCheck();
            return;
        }
        total_bill_D = Double.parseDouble(total_bill);
        if(v.getId() == R.id.radio12){
            tip_percent = Math.ceil((total_bill_D*0.12)*100.0)/100.0;
        } else if(v.getId() == R.id.radio15){
            tip_percent = Math.ceil((total_bill_D*0.15)*100.0)/100.0;
        } else if(v.getId() == R.id.radio18){
            tip_percent = Math.ceil((total_bill_D*0.18)*100.0)/100.0;
        } else if(v.getId() == R.id.radio20){
            tip_percent = Math.ceil((total_bill_D*0.20)*100.0)/100.0;
        } else {
            Log.d(TAG, "doRadioGroup: Unknown Button!");
        }
        TipResult.setText(String.format("$%.2f", tip_percent));
        TotalBillWithTip.setText(String.format("$%.2f", total_bill_D + tip_percent));
    }

    public void billSplit(View v){
        noOfPeople.onEditorAction(EditorInfo.IME_ACTION_DONE);
        String people = noOfPeople.getText().toString();
        if(people.isEmpty() || people.equals("0")){
            Toast.makeText(this, "Value should be greater than zero", Toast.LENGTH_SHORT).show();
            return;
        }
        double peopleD = Double.parseDouble(people);
        double total_per_person = (Math.ceil(((total_bill_D+tip_percent)/peopleD)*100.0))/100.0;
        totalPerPerson.setText(String.format("$%.2f",total_per_person));
        double overageD = (total_per_person*peopleD) - (total_bill_D + tip_percent);
        overage.setText(String.format("$%.2f",overageD));
        Log.d(TAG, "billSplit: ");
        
    }

}