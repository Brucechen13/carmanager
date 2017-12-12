package com.cc.carmanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.cc.carmanager.R;
import com.cc.carmanager.vollley.MySingleton;

/**
 * Created by chenc on 2017/12/11.
 */

public class ImageTextView extends LinearLayout {

    private NetworkImageView mImg;
    private TextView mText;
    private View view;

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_image_text, this);
        view = findViewById(R.id.imtv);
        mImg = (NetworkImageView) findViewById(R.id.img);
        mText = (TextView) findViewById(R.id.text);

    }

    /**
     * 设置文字
     *
     * @param info
     */
    public void setText(String info) {
        mText.setText(info);
    }

    /**
     * 设置图片
     *
     * @param info
     */
    public void setImg(String url) {
        mImg.setDefaultImageResId(R.mipmap.ic_launcher);
        mImg.setErrorImageResId(R.mipmap.ic_launcher);
        mImg.setImageUrl(url,
                MySingleton.getInstance().getImageLoader());
    }
    public void setImg(int imageId) {
        mImg.setDefaultImageResId(imageId);
    }

    public void setClick(OnClickListener listener) {
        view.setOnClickListener(listener);
    }
}