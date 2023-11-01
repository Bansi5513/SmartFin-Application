package com.example.smartfin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class GoalActivity extends AppCompatActivity {

    ImageView budgetIcon, expenseIcon, homeIcon, goalIcon, insightIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

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
                Toast.makeText(GoalActivity.this, "Budget", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(GoalActivity.this, BudgetActivity.class);
//                startActivity(intent);
            }
        });
        expenseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(GoalActivity.this, "Expense", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(GoalActivity.this, ExpenseActivity.class);
//                startActivity(intent);
            }
        });
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(GoalActivity.this, "Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        goalIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(GoalActivity.this, "Goal", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoalActivity.this, GoalActivity.class);
                startActivity(intent);
            }
        });
        insightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(GoalActivity.this, "Insight", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoalActivity.this, InsightActivity.class);
                startActivity(intent);
            }
        });
        //-----------------------------------------//

    }
}