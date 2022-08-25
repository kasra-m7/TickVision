package ir.mahchegroup.vision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ActSplash extends AppCompatActivity {
    private ImageView img;
    private Animation fadeIn, fadeOut;
    public static final String IS_FIRST_TIME = "is_first_time";
    public static SharedPreferences shared;
    public static SharedPreferences.Editor editor;
    private boolean isFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);


        // مقدار دهی اولیه
        init();


        // چک کردن اولین ورود کاربر به اپ
        isFirstTime = shared.getBoolean(IS_FIRST_TIME, true);


        // ست کردن انیمیشن تصویر معرفی
        setAnim(100, View.VISIBLE, fadeIn);
        setAnim(5000, View.INVISIBLE, fadeOut);


        // ست کردن اینتنت و مشخص کردن رفتن به کدام اکتیویتی در چه صورتی
        setNextActivity();
    }


    private void setNextActivity() {
        new Handler().postDelayed(() -> {
            Intent intent;
            if (isFirstTime) {
                intent = new Intent(ActSplash.this, ActSignup.class);
            } else {
                intent = new Intent(ActSplash.this, ActLogin.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        }, 1000);
    }


    private void setAnim(int time, int visit, Animation anim) {
        new Handler().postDelayed(() -> {
            img.setVisibility(visit);
            img.startAnimation(anim);
        }, time);
    }


    private void init() {
        img = findViewById(R.id.imgSplash);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_splash);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_splash);

        shared = getSharedPreferences("shared", MODE_PRIVATE);

        editor = shared.edit();
    }
}