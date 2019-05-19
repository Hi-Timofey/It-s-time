package com.hitim.android.itstime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoardActivity extends AppCompatActivity {

    private ViewPager vPager;
    private LinearLayout mDotsLayout;
    private SharedPreferences.Editor editor;
    private String APP_PREFERENCES = "its_settings";
    private String APP_FIRST_OPEN = "first_open";
    private   SharedPreferences itsSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueApplicationStyle_LightTheme);
        itsSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        createSharedPref();
        if (!itsSettings.getBoolean(APP_FIRST_OPEN,true)) {
            Intent i = new Intent(this, LogInActivity.class);
            startActivity(i);
        } else {
            editor.putBoolean(APP_FIRST_OPEN, false);
            editor.apply();
            setContentView(R.layout.activity_on_board);
            mDotsLayout = findViewById(R.id.dots_manager);
            vPager = findViewById(R.id.slider_page);
            SliderAdapter sliderAdapter = new SliderAdapter(this);
            vPager.setAdapter(sliderAdapter);
            startAnimation();

            addDots(0);
            vPager.addOnPageChangeListener(pageChangeListener);
        }
    }

    //Инициализирует создание настроек приложения
    private void createSharedPref() {
        editor = itsSettings.edit();
        editor.apply();
    }


    private void startAnimation() {
        Animation fadeInWelcome = AnimationUtils.loadAnimation(this, R.anim.fade_in_welcome);
        Animation fadeInShort = AnimationUtils.loadAnimation(this, R.anim.fade_in_5);
        mDotsLayout.startAnimation(fadeInShort);
        vPager.startAnimation(fadeInWelcome);
    }

    public void onStartBtn(View view) {
        Intent i = new Intent(this, LogInActivity.class);
        startActivity(i);
        finish();
    }

    /*Slider and dots zone
     * Точки около кнопки меняются благодаря этому коду
     * Обработка OnPageListener'а
     *  */
    private void addDots(int position) {
        TextView[] mDots = new TextView[3];
        mDotsLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            //mDots[i].setGravity(Gravity.CENTER_HORIZONTAL);
            mDots[i].setTextColor(getResources().getColor(R.color.b_primary));
            mDotsLayout.addView(mDots[i]);
        }
        mDots[position].setTextColor(getResources().getColor(R.color.b_primary_dark));
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            addDots(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
