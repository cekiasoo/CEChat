package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseGroupUtils;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.util.EMLog;

import java.util.ArrayList;
import java.util.List;


public class EaseGroupAdapter extends ArrayAdapter<EMGroup> implements SectionIndexer{
    private static final String TAG = "ContactAdapter";
    List<String> list;
    List<EMGroup> groupList;
    List<EMGroup> copyGroupList;
    private LayoutInflater layoutInflater;
    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    private int res;
    private MyFilter myFilter;
    private boolean notiyfyByFilter;

    public EaseGroupAdapter(Context context, int resource, List<EMGroup> objects) {
        super(context, resource, objects);
        this.res = resource;
        this.groupList = objects;
        copyGroupList = new ArrayList<EMGroup>();
        copyGroupList.addAll(objects);
        layoutInflater = LayoutInflater.from(context);
    }
    
    private static class ViewHolder {
        ImageView avatar;
        TextView nameView;
        TextView headerView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            if(res == 0) {
                convertView = layoutInflater.inflate(R.layout.ease_row_group, parent, false);
            } else {
                convertView = layoutInflater.inflate(res, null);
            }
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.headerView = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        
        EMGroup user = getItem(position);
        if(user == null) {
            Log.d("ContactAdapter", position + "");
        }
        String groupName = user.getGroupName();
        String header = EaseCommonUtils.getInitialLetter(user);
        
        if (position == 0 || header != null && !header.equals(EaseCommonUtils.getInitialLetter(getItem(position - 1)))) {
            if (TextUtils.isEmpty(header)) {
                holder.headerView.setVisibility(View.GONE);
            } else {
                holder.headerView.setVisibility(View.VISIBLE);
                holder.headerView.setText(header);
            }
        } else {
            holder.headerView.setVisibility(View.GONE);
        }

        EaseAvatarOptions avatarOptions = EaseUI.getInstance().getAvatarOptions();
        if(avatarOptions != null && holder.avatar instanceof EaseImageView) {
            EaseImageView avatarView = ((EaseImageView) holder.avatar);
            if (avatarOptions.getAvatarShape() != 0) {
                avatarView.setShapeType(avatarOptions.getAvatarShape());
            }
            if (avatarOptions.getAvatarBorderWidth() != 0) {
                avatarView.setBorderWidth(avatarOptions.getAvatarBorderWidth());
            }
            if (avatarOptions.getAvatarBorderColor() != 0) {
                avatarView.setBorderColor(avatarOptions.getAvatarBorderColor());
            }
            if (avatarOptions.getAvatarRadius() != 0) {
                avatarView.setRadius(avatarOptions.getAvatarRadius());
            }
        }

        EaseGroupUtils.setGroupName(groupName, holder.nameView);
        EaseGroupUtils.setGroupAvatar(getContext(), holder.avatar);
        
       
        if(primaryColor != 0) {
            holder.nameView.setTextColor(primaryColor);
        }
        if(primarySize != 0) {
            holder.nameView.setTextSize(TypedValue.COMPLEX_UNIT_PX, primarySize);
        }
        if(initialLetterBg != null) {
            holder.headerView.setBackgroundDrawable(initialLetterBg);
        }
        if(initialLetterColor != 0) {
            holder.headerView.setTextColor(initialLetterColor);
        }
        
        return convertView;
    }
    
    @Override
    public EMGroup getItem(int position) {
        return super.getItem(position);
    }
    
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPositionForSection(int section) {
        return positionOfSection.get(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }
    
    @Override
    public Object[] getSections() {
        positionOfSection = new SparseIntArray();
        sectionOfPosition = new SparseIntArray();
        int count = getCount();
        list = new ArrayList<String>();
        list.add(getContext().getString(R.string.search_header));
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        for (int i = 1; i < count; i++) {

            String letter = EaseCommonUtils.getInitialLetter(getItem(i));
            int section = list.size() - 1;
            if (list.get(section) != null && !list.get(section).equals(letter)) {
                list.add(letter);
                section++;
                positionOfSection.put(section, i);
            }
            sectionOfPosition.put(i, section);
        }
        return list.toArray(new String[list.size()]);
    }
    
    @Override
    public Filter getFilter() {
        if(myFilter==null){
            myFilter = new MyFilter(groupList);
        }
        return myFilter;
    }
    
    protected class  MyFilter extends Filter{
        List<EMGroup> mOriginalList = null;
        
        public MyFilter(List<EMGroup> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if(mOriginalList==null){
                mOriginalList = new ArrayList<EMGroup>();
            }
            EMLog.d(TAG, "contacts original size: " + mOriginalList.size());
            EMLog.d(TAG, "contacts copy size: " + copyGroupList.size());
            
            if(prefix==null || prefix.length()==0){
                results.values = copyGroupList;
                results.count = copyGroupList.size();
            }else{

                if (copyGroupList.size() > mOriginalList.size()) {
                    mOriginalList = copyGroupList;
                }
                String prefixString = prefix.toString();
                final int count = mOriginalList.size();
                final ArrayList<EMGroup> newValues = new ArrayList<EMGroup>();
                for(int i=0;i<count;i++){
                    final EMGroup user = mOriginalList.get(i);
                    String username = user.getGroupName();
                    
                    if(username.startsWith(prefixString)){
                        newValues.add(user);
                    }
                    else{
                         final String[] words = username.split(" ");
                         final int wordCount = words.length;
    
                         // Start at index 0, in case valueText starts with space(s)
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(user);
                                break;
                            }
                        }
                    }
                }
                results.values=newValues;
                results.count=newValues.size();
            }
            EMLog.d(TAG, "contacts filter results size: " + results.count);
            return results;
        }

        @Override
        protected synchronized void publishResults(CharSequence constraint,
                FilterResults results) {
            groupList.clear();
            groupList.addAll((List<EMGroup>)results.values);
            EMLog.d(TAG, "publish contacts filter results size: " + results.count);
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
                notiyfyByFilter = false;
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
    
    
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(!notiyfyByFilter){
            copyGroupList.clear();
            copyGroupList.addAll(groupList);
        }
    }
    
    protected int primaryColor;
    protected int primarySize;
    protected Drawable initialLetterBg;
    protected int initialLetterColor;

    public EaseGroupAdapter setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }


    public EaseGroupAdapter setPrimarySize(int primarySize) {
        this.primarySize = primarySize;
        return this;
    }

    public EaseGroupAdapter setInitialLetterBg(Drawable initialLetterBg) {
        this.initialLetterBg = initialLetterBg;
        return this;
    }

    public EaseGroupAdapter setInitialLetterColor(int initialLetterColor) {
        this.initialLetterColor = initialLetterColor;
        return this;
    }
    
}
