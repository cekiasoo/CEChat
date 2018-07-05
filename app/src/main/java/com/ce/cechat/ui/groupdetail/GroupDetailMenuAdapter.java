package com.ce.cechat.ui.groupdetail;

import android.content.Context;
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
public class GroupDetailMenuAdapter extends BaseAdapter {

    private boolean isOwner;

    private Context mContext;

    private final String[] LIST_TEXT_MEMBER = {"分享群聊", "举报", "退出群聊", "取消"};

    private final String[] LIST_TEXT_OWNER = {"分享群聊", "举报", "解散群", "取消"};

    private String[] mListText;

    public GroupDetailMenuAdapter(Context pContext, boolean isOwner) {
        this.mContext = pContext;
        this.isOwner = isOwner;
        if (isOwner) {
            mListText = LIST_TEXT_OWNER;
        } else {
            mListText = LIST_TEXT_MEMBER;
        }
    }

    @Override
    public int getCount() {
        return mListText.length;
    }

    @Override
    public Object getItem(int position) {
        return mListText[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.view_group_detail_popup_menu_item, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = view.findViewById(R.id.tv_text);

        if (position == 2) {
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.holo_red_light));
        } else {
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorDefaultMenu));
        }

        textView.setText(mListText[position]);

        return view;
    }
}
