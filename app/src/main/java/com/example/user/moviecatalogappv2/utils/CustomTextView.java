package com.example.user.moviecatalogappv2.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.user.moviecatalogappv2.R;

public class CustomTextView extends AppCompatTextView{
    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        if (attrs != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String fontOfKind = typedArray.getString(R.styleable.CustomTextView_fontName);

            try {
                if (fontOfKind != null){
                    Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/"+fontOfKind);
                    setTypeface(typeface);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            typedArray.recycle();
        }
    }
}
