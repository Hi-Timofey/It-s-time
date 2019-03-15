package com.hitim.android.itstime;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoardActivity extends AppCompatActivity {

    private ViewPager vPager;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private LinearLayout mDotsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        mDotsLayout = findViewById(R.id.dots_manager);
        vPager = findViewById(R.id.slider_page);
        sliderAdapter = new SliderAdapter(this);
        vPager.setAdapter(sliderAdapter);
        addDots(0);
        vPager.addOnPageChangeListener(pageChangeListener);

    }

    public void onStartBtn(View view) {
        Intent i = new Intent(this, LogInActivity.class);
        //TODO: Нормальную анимацию и переключение
        startActivity(i);
    }

    public void addDots(int position){
        mDots = new TextView[3];
        mDotsLayout.removeAllViews();
        for(int i = 0;i < mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setGravity(Gravity.CENTER );
            mDots[i].setTextColor(getResources().getColor(R.color.b_primary));
            mDotsLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.b_primary_dark));
        }
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
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
