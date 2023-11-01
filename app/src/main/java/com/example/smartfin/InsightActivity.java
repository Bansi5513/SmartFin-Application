package com.example.smartfin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class InsightActivity extends AppCompatActivity {
    ImageView budgetIcon, expenseIcon, homeIcon, goalIcon, insightIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight);

        //init all the views
        budgetIcon = findViewById(R.id.budgetIcon);
        expenseIcon = findViewById(R.id.expenseIcon);
        homeIcon = findViewById(R.id.homeIcon);
        goalIcon = findViewById(R.id.goalIcon);
        insightIcon = findViewById(R.id.insightIcon);

        //-----------------NAVBAR-----------------//
        budgetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InsightActivity.this, "Budget", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(InsightActivity.this, BudgetActivity.class);
//                startActivity(intent);
            }
        });
        expenseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(InsightActivity.this, "Expense", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(InsightActivity.this, ExpenseActivity.class);
//                startActivity(intent);
            }
        });
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(InsightActivity.this, "Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InsightActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        goalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(InsightActivity.this, "Goal", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InsightActivity.this, GoalActivity.class);
                startActivity(intent);
            }
        });
        insightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(InsightActivity.this, "Insight", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InsightActivity.this, InsightActivity.class);
                startActivity(intent);
            }
        });
        //-----------------------------------------//

    }
}