package com.ce.cechat.view.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author CE Chen
 *
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews = new SparseArray<>();

    public CommonViewHolder(View itemView) {
        super(itemView);
    }


    public static CommonViewHolder newInstance(Context pContext, int pLayoutId, ViewGroup parent) {
        View view = LayoutInflater.from(pContext).inflate(pLayoutId, parent, false);
        return new CommonViewHolder(view);
    }

    public <T> T findViewById(int pViewId) {
        View view = mViews.get(pViewId);
        if (view == null) {
            view = itemView.findViewById(pViewId);
            mViews.put(pViewId, view);
        }
        return (T) view;
    }

    public void setText(int pViewId, String pText) {
        TextView textView = findViewById(pViewId);
        textView.setText(pText);
    }

    public void setText(int pViewId, @StringRes int pResId) {
        TextView textView = findViewById(pViewId);
        textView.setText(pResId);
    }

    public void setImage(int pViewId, @DrawableRes int pResId) {
        ImageView imageView = findViewById(pViewId);
        imageView.setImageResource(pResId);
    }

    /**
     * 显示指定 id 的View
     * @param pViewId ViewId
     */
    public void showView(int pViewId) {
        View view = findViewById(pViewId);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏指定 id 的View
     * @param pViewId ViewId
     */
    public void hideView(int pViewId) {
        View view = findViewById(pViewId);
        view.setVisibility(View.GONE);
    }

    /**
     * 设置点击事件
     * @param pViewId ViewId
     * @param pOnClickListener OnClickListener
     */
    public void setOnClick(int pViewId, View.OnClickListener pOnClickListener) {
        View view = findViewById(pViewId);
        view.setOnClickListener(pOnClickListener);
    }

}
