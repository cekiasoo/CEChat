package com.hyphenate.easeui.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.R;

public class EaseGroupUtils {


    /**
     * set group avatar
     */
    public static void setGroupAvatar(Context context, ImageView imageView) {
        Glide.with(context).load(R.drawable.ic_group).into(imageView);
    }

    /**
     * set user's nickname
     */
    public static void setGroupName(String groupName, TextView textView) {
        if (textView != null) {
            textView.setText(groupName);
        }
    }

}
