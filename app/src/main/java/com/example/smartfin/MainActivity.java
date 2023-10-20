package com.example.smartfin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner currencySpinner;
    ImageView budgetIcon, expenseIcon, homeIcon, goalIcon, insightIcon;
    TextView viewAllText;


    //string list of all the currencies
    String[] currencyList = {"USD", "INR", "CAD", "SGD", "EUR", "JPY", "AUD", "CNY", "HKD", "KRW"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //init all the views
        currencySpinner = findViewById(R.id.currencySpinner);
        budgetIcon = findViewById(R.id.budgetIcon);
        expenseIcon = findViewById(R.id.expenseIcon);
        homeIcon = findViewById(R.id.homeIcon);
        goalIcon = findViewById(R.id.goalIcon);
        insightIcon = findViewById(R.id.insightIcon);
        viewAllText = findViewById(R.id.viewAllText);




        //items for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
        currencySpinner.setSelection(0); // This sets the default selection to the first item.

        //set the listener for the spinner and setting the color of selected item
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //get the selected item
                String selectedItem = parent.getItemAtPosition(position).toString();
                //set color of the selected item
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.gradient_end_color));
                ((TextView) parent.getChildAt(0)).setTextSize(18);
                ((TextView) parent.getChildAt(0)).setText(selectedItem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });



        //-----------------NAVBAR-----------------//
        budgetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Budget", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
//                startActivity(intent);
            }
        });
        expenseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Expense", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
//                startActivity(intent);
            }
        });
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
        goalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Goal", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, GoalActivity.class);
//                startActivity(intent);
            }
        });
        insightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Insight", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, InsightActivity.class);
//                startActivity(intent);
            }
        });
        //-----------------------------------------//



        viewAllText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Expense", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
//                startActivity(intent);
            }
        });




    }
}