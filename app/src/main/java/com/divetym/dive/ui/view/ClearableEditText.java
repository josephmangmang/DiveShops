package com.divetym.dive.ui.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.divetym.dive.R;

/**
 * Created by kali_root on 6/11/2017.
 */

public class ClearableEditText extends RelativeLayout {
    private TextView text;
    private ImageView clearIcon;
    private RobotoTextView hintText;
    private OnClearListener onClearListener;

    public ClearableEditText(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ClearableEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClearableEditText(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_clearable_edit_text, this);
        text = (TextView) findViewById(R.id.edit_text);
        clearIcon = (ImageView) findViewById(R.id.image_clear);
        hintText = (RobotoTextView) findViewById(R.id.text_hint);
        clearIcon.setOnClickListener(view -> {
            setText("");
            if (onClearListener != null) onClearListener.onCleared();
        });
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
        hintText.setText(hint);
    }

    public void setOnClearListener(OnClearListener onClearListener) {
        this.onClearListener = onClearListener;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        text.setOnClickListener(l);
    }

    interface OnClearListener {
        public void onCleared();
    }
}
