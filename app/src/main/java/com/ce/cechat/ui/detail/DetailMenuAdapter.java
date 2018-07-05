package com.ce.cechat.ui.detail;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ce.cechat.R;

/**
 * @author CE Chen
 *
 *
 */
public class DetailMenuAdapter extends BaseAdapter {

    private Context mContext;

    private final String[] LIST_TEXT = {"设置备注及标签", "标为星标朋友", "发送该名片", "投诉", "加入黑名单", "删除"};
    private final int[] LIST_ICON = {
            R.drawable.ic_border_color_black_24dp,
            R.drawable.ic_star_black_24dp,
            R.drawable.ic_send_black_24dp,
            R.drawable.ic_warning_black_24dp,
            R.drawable.ic_block_black_24dp,
            R.drawable.ic_delete_black_24dp};

    public DetailMenuAdapter(Context pContext) {
        this.mContext = pContext;
    }

    @Override
    public int getCount() {
        return LIST_TEXT.length;
    }

    @Override
    public Object getItem(int position) {
        return LIST_TEXT[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.view_popup_menu_item, parent, false);
        } else {
            view = convertView;
        }

        AppCompatImageView imageView = view.findViewById(R.id.iv_icon);
        imageView.setImageResource(LIST_ICON[position]);

        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(LIST_TEXT[position]);

        return view;
    }
}
