package com.example.customslider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Slider.SliderChangeListener {

    private Slider mSlider = null;
    private TextView mTv = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlider = findViewById(R.id.slider);
        mTv = findViewById(R.id.tv);
        mSlider.setSliderChangeListener(this);
    }


    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    @Override
    public void onChange(float value) {
        String str = String.format("%d",(int)value);
    mTv.setText(str);
    }

    @Override
    public void onDoubleClick(float value) {
        String str = String.format("%d",(int)value);
        mTv.setText(str);
    }
}
