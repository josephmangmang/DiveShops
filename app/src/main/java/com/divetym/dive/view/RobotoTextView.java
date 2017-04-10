package com.divetym.dive.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.divetym.dive.R;
import com.divetym.dive.common.TypefaceManager;

/**
 * Created by kali_root on 4/6/2017.
 */

public class RobotoTextView extends AppCompatTextView {
    public RobotoTextView(Context context) {
        this(context, null);
    }

    public RobotoTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initTypeface(this, context, attrs);
        }
    }

    public RobotoTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            initTypeface(this, context, attrs);
        }
    }

    private void initTypeface(TextView textView, Context context, AttributeSet attrs) {
        Typeface typeface = null;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RobotoTextView);
            if (a.hasValue(R.styleable.RobotoTextView_typeface)) {
                int typefaceValue = a.getInt(R.styleable.RobotoTextView_typeface, TypefaceManager.Typefaces.ROBOTO_REGULAR);
                typeface = TypefaceManager.obtainTypeface(context, typefaceValue);
            }
            a.recycle();
        }
        if (typeface == null) {
            typeface = TypefaceManager.obtainTypeface(context, TypefaceManager.Typefaces.ROBOTO_REGULAR);
        }
        textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        textView.setTypeface(typeface);
    }

}
