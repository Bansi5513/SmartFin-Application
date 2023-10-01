package com.example.smartfin;

//import android.view.animation.Animation;
import com.airbnb.lottie.LottieAnimationView;

public class ScreenItem {

    String Title, Description;
    int  Animation1;//ScreenImg,

    //Constructor method
    public ScreenItem(String title, String description, int animation) { //int screenImg, int animation
        Title = title;
        Description = description;
//        ScreenImg = screenImg;
        Animation1 = animation;
    }


    //Setter methods
    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

//    public void setScreenImg(int screenImg) {
//        ScreenImg = screenImg;
//    }
    public void getAnimation1(int animation) {
        Animation1 = animation;
    }

    //Getter methods
    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

//    public int getScreenImg() {
//        return ScreenImg;
//    }
    public int getAnimation1() {
        return Animation1;
    }
}
