package com.prembros.oliveforecast.ui.customviews;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Color.TRANSPARENT;

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

/*    public void setText(String text, long delay) {
        try {
            if (text != null && !text.contains("null")) {
                setVisibility(INVISIBLE);
                super.setText(text);
                animateViewIn(delay);
            }
            else setVisibility(GONE);
        } catch (Exception e) {
            e.printStackTrace();
            setVisibility(GONE);
        }
    }*/

    public void setText(String text) {
        try {
            if (text != null && !text.contains("null") && !text.isEmpty()) {
                setBackgroundColor(TRANSPARENT);
                super.setText(text);
                setVisibility(VISIBLE);
            }
            else if (text != null && text.isEmpty()) {
                setBackgroundColor(Color.parseColor("#E0E0E0"));
            }
            else setVisibility(GONE);
        } catch (Exception e) {
            e.printStackTrace();
            setVisibility(GONE);
        }
    }

    public void animateViewIn(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.VISIBLE);
            }
        }, delay);
    }
}