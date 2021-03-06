package com.hitim.android.itstime;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

class SliderAdapter extends PagerAdapter {

    private final Context context;

    /* Slider
    * Класс для обработки переключений слайдера и вывода необходимых картинок и информации
    * Картинки берутся из папки drawable
    * Текст напрямую из string русерсов
    * При переключении пользователем экрана идет простая замена View элементов
    * */
    private final int[] slide_images = {
            R.drawable.ic_alarms,
            R.drawable.ic_check_box,
            R.drawable.ic_trending_up
    };

    private final int[] slide_head = {
            R.string.head_1,
            R.string.head_2,
            R.string.head_3
    };

    private final int[] slide_text = {
            R.string.on_board_1,
            R.string.on_board_2,
            R.string.on_board_3
    };

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slide_head.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.slide_layout, container, false);
        ImageView imIcon = view.findViewById(R.id.imageIcon);
        TextView head = view.findViewById(R.id.textHead);
        TextView underHead = view.findViewById(R.id.textUnderHead);
        imIcon.setImageResource(slide_images[position]);
        head.setText(slide_head[position]);
        underHead.setText(slide_text[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
