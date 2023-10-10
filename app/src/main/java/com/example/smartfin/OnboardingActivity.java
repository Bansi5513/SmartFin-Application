package com.example.smartfin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout indicator;
    ImageButton nextArrowButton;
    Button skipButton;
    Button getStartedButton;
    TextView scrollRightText;
    Animation buttonAnimation;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make activity on full screen
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);




        // when this activity is about to be launch we need to check if its opened before or not
        // ...... we need to do this checking also in LOGIN/SIGNUP screen ......
        if(restorePrefData()){

            Intent mainActivity = new Intent(getApplicationContext(), GetStartedActivity.class);
            startActivity(mainActivity);
            finish();

        }



        setContentView(R.layout.activity_onboarding);



        // hide action bar
//        getSupportActionBar().hide();

        // initialised views
        nextArrowButton = findViewById(R.id.nextArrowButton);
        skipButton = findViewById(R.id.skipButton);
        getStartedButton = findViewById(R.id.getStartedButton);
        indicator = findViewById(R.id.indicator);
        scrollRightText = findViewById(R.id.scrollRightText);
        buttonAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);


        // fill List Screen
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("WELCOME TO SMARTFIN", "Your Ultimate Financial Companion!",R.raw.welcm_animation));
        mList.add(new ScreenItem("Smart Expense Categorization", "Effortlessly organize your spending with AI-driven expense categorization that learns and adapts to your financial habits.",R.raw.smart_expense_categorization)); //R.raw.smart_expense_categorization
        mList.add(new ScreenItem("Predictive Budgeting and Insights", "Stay ahead of your financial goals with personalized budgets and predictive insights based on your spending history.",R.raw.predictive_budgeting_and_insights)); //R.raw.predictive_budgeting_and_insights
        mList.add(new ScreenItem("Bill Automation and Negotiation", "Save time and money by automating bill payments and leveraging negotiation tools to lower recurring expenses.",R.raw.bill_automation_and_negotiation));//R.raw.bill_automation_and_negotiation
        mList.add(new ScreenItem("Comprehensive Expense Analysis", "Gain a deep understanding of your spending patterns, receive proactive alerts, and stay in control of your finances.",R.raw.comprehensive_expense_analysis));//R.raw.comprehensive_expense_analysis
        mList.add(new ScreenItem("Goal Tracking with Milestones", "Visualize and celebrate your financial achievements by setting and achieving milestones within your financial goals.",R.raw.goal_tracking_with_milestones_2));//R.raw.goal_tracking_with_milestones_2   , R.drawable.trial_img1


        // setup viewPager
        screenPager = findViewById(R.id.screen_viewPager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);


        // setup tablelayout with viewpager
        indicator.setupWithViewPager(screenPager);

        // nextArrow Button click listener
        nextArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();

                if(position < mList.size()){

                    position++;
                    screenPager.setCurrentItem(position);

                }

                if(position == mList.size()-1){ //when we reach to last screen

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loadLastScreen();

                }

            }
        });



        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                screenPager.setCurrentItem(mList.size()-1);

                // TODO : show the GETSTARTED Button and hide the indicator and the next button

                loadLastScreen();

            }
        });


        // tablayout add change listener
        indicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == mList.size()-1){
                    loadLastScreen();

                }

                if(tab.getPosition() < mList.size()-1){
                    dontLoadLastScreen();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        // Get Started button click listener
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // open main activity(currently) ...... open signup/login page (TO DO) ... BANSU CHANGE HERE and delete this bansu part in comment
                Intent mainActivity = new Intent(getApplicationContext(), GetStartedActivity.class);
                startActivity(mainActivity);

                // Bansu do this also in SINGUP/LOGIN PAGE
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked itro(onboarding) screen activity
                // I'm going to use shared preferences to that process
                savePrefData();
                finish();

            }
        });

    }

    private boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isOnboardingActivityOpenedBefore = pref.getBoolean("isOnboardingOpened",false);
        return isOnboardingActivityOpenedBefore;

    }

    private void savePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isOnboardingOpened",true);
        editor.commit();
    }


    // show the GETSTARTED Button and hide the indicator and the next button
    private void loadLastScreen(){

        skipButton.setText("DONE");
        nextArrowButton.setVisibility(View.INVISIBLE);
        indicator.setVisibility(View.INVISIBLE);
        getStartedButton.setVisibility(View.VISIBLE);
        scrollRightText.setVisibility(View.INVISIBLE);

        // TODO : ADD an animation to getstarted button
        // setup animation
        getStartedButton.setAnimation(buttonAnimation);
    }

    private void dontLoadLastScreen(){

        skipButton.setText("SKIP");
        nextArrowButton.setVisibility(View.VISIBLE);
        indicator.setVisibility(View.VISIBLE);
        getStartedButton.setVisibility(View.INVISIBLE);
        scrollRightText.setVisibility(View.VISIBLE);

        // TODO : ADD an animation to getstarted button
        // setup animation
//        getStartedButton.setAnimation(buttonAnimation);
    }


}