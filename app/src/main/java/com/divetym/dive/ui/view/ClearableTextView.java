package com.divetym.dive.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.divetym.dive.R;

/**
 * Created by kali_root on 6/11/2017.
 */

public class ClearableTextView extends CardView {
    private TextView text;
    private ImageView clearIcon;
    private OnClearListener onClearListener;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            clearIcon.setVisibility(TextUtils.isEmpty(charSequence) ? GONE : VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public ClearableTextView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ClearableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearableTextView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_clearable_text_view, this);
        text = (TextView) findViewById(R.id.text);
        clearIcon = (ImageView) findViewById(R.id.image_clear);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClearableTextView);
            if (a.hasValue(R.styleable.ClearableTextView_text_hint)) {
                CharSequence hint = a.getText(R.styleable.ClearableTextView_text_hint);
                text.setHint(hint);
            }
            if (a.hasValue(R.styleable.ClearableTextView_text_icon)) {
                Drawable drawable = a.getDrawable(R.styleable.ClearableTextView_text_icon);
                text.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }

        }
        text.addTextChangedListener(textWatcher);
        clearIcon.setOnClickListener(view -> {
            setText("");
            if (onClearListener != null) onClearListener.onCleared();
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        text.removeTextChangedListener(textWatcher);
        super.onDetachedFromWindow();
    }

    private void setClearIconVisible(boolean visible) {
        clearIcon.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setText(CharSequence text) {
        this.text.setText(text);
    }

    public CharSequence getText() {
        return text.getText();
    }

    public void setHint(@Nullable CharSequence hint) {
        text.setHint(hint);
    }

    public void setOnClearListener(OnClearListener onClearListener) {
        this.onClearListener = onClearListener;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        text.setOnClickListener(l);
    }

    public interface OnClearListener {
        void onCleared();
    }
}
