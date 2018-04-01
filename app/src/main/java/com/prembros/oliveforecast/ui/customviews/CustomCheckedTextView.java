package com.prembros.oliveforecast.ui.customviews;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import static android.view.Gravity.CENTER;
import static com.prembros.oliveforecast.utility.ViewUtils.getPixels;

public class CustomCheckedTextView extends AppCompatCheckedTextView {

    public CustomCheckedTextView(Context context) {
        super(context);
        init();
    }

    public CustomCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setAttributes();
    }

    private void setAttributes() {
        int defaultPadding = getPixels(getContext(), 14);
        setPadding(defaultPadding, defaultPadding / 2, defaultPadding, defaultPadding / 2);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        setTextColor(Color.parseColor("#333333"));
        setGravity(CENTER);
    }

    @Override public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        setAttributes();
    }
}