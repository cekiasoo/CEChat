/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseGroupList;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author CE Chen
 *
 * Group list
 * 
 */
public class EaseGroupListFragment extends EaseBaseFragment {
    private static final String TAG = "EaseGroupListFragment";
    protected List<EMGroup> groupList;
    protected ListView listView;
    protected boolean hidden;
    protected ImageButton clearSearch;
    protected EditText query;
    protected Handler handler = new Handler();
    protected EMGroup toBeProcessUser;
    protected String toBeProcessUsername;
    protected EaseGroupList groupListLayout;
    protected boolean isConflict;
    protected LinearLayout layContentContainer;
    protected FrameLayout contentContainer;
    
    private Map<String, EMGroup> groupsMap;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ease_fragment_group_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	//to avoid crash when open app after long time stay in background after user logged into another device
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            return;
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {
        layContentContainer = (LinearLayout) getView().findViewById(R.id.lay_content_container);
        contentContainer = (FrameLayout) getView().findViewById(R.id.content_container);
        
        groupListLayout = (EaseGroupList) getView().findViewById(R.id.group_list);
        listView = groupListLayout.getListView();
        
        //search
        query = (EditText) getView().findViewById(R.id.query);
        clearSearch = (ImageButton) getView().findViewById(R.id.search_clear);
    }

    @Override
    protected void setUpView() {
        EMClient.getInstance().addConnectionListener(connectionListener);
        
        groupList = new ArrayList<EMGroup>();
        getGroupList();
        //init list
        groupListLayout.init(groupList);
        
        if(listItemClickListener != null){
            listView.setOnItemClickListener(new OnItemClickListener() {
    
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EMGroup group = (EMGroup)listView.getItemAtPosition(position);
                    listItemClickListener.onListItemClicked(group);
                }
            });
        }

        if(listItemLongClickListener != null){
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    EMGroup group = (EMGroup)listView.getItemAtPosition(position);
                    return listItemLongClickListener.onListItemLongClicked(view, group);
                }
            });
        }
        
        query.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                groupListLayout.filter(s);
                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                } else {
                    clearSearch.setVisibility(View.INVISIBLE);
                    
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        clearSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                query.getText().clear();
                hideSoftKeyboard();
            }
        });
        
        listView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
        
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }


    /**
     * move group to blacklist
     */
    protected void moveToBlacklist(final String groupId){
        final ProgressDialog pd = new ProgressDialog(getActivity());
        String st1 = getResources().getString(R.string.Is_moved_into_blacklist);
        final String st2 = getResources().getString(R.string.Move_into_blacklist_success);
        final String st3 = getResources().getString(R.string.Move_into_blacklist_failure);
        pd.setMessage(st1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //move to blacklist
                    EMClient.getInstance().groupManager().blockGroupMessage(groupId);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getActivity(), st2, Toast.LENGTH_SHORT).show();
                            refresh();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getActivity(), st3, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
        
    }
    
    // refresh ui
    public void refresh() {
        getGroupList();
        groupListLayout.refresh();
    }
    

    @Override
    public void onDestroy() {
        
        EMClient.getInstance().removeConnectionListener(connectionListener);
        
        super.onDestroy();
    }
    

    /**
     * get Group list and sort, will filter out users in blacklist
     */
    protected void getGroupList() {
        groupList.clear();
        if(groupsMap == null){
            return;
        }
        synchronized (this.groupsMap) {
            Iterator<Entry<String, EMGroup>> iterator = groupsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, EMGroup> entry = iterator.next();
                // to make it compatible with data in previous version, you can remove this check if this is new app
                EMGroup user = entry.getValue();
                //TODO
                groupList.add(user);
            }
        }

        // sorting
        Collections.sort(groupList, new Comparator<EMGroup>() {

            @Override
            public int compare(EMGroup lhs, EMGroup rhs) {
                if(EaseCommonUtils.getInitialLetter(lhs).equals(EaseCommonUtils.getInitialLetter(rhs))){
                    return lhs.getGroupName().compareTo(rhs.getGroupName());
                }else{
                    if("#".equals(EaseCommonUtils.getInitialLetter(lhs))){
                        return 1;
                    }else if("#".equals(EaseCommonUtils.getInitialLetter(rhs))){
                        return -1;
                    }
                    return EaseCommonUtils.getInitialLetter(lhs).compareTo(EaseCommonUtils.getInitialLetter(rhs));
                }

            }
        });

    }
    
    
    
    protected EMConnectionListener connectionListener = new EMConnectionListener() {
        
        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED) {
                isConflict = true;
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onConnectionDisconnected();
                    }

                });
            }
        }
        
        @Override
        public void onConnected() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onConnectionConnected();
                }

            });
        }
    };

    private EaseGroupListItemClickListener listItemClickListener;

    private EaseGroupListItemLongClickListener listItemLongClickListener;

    
    protected void onConnectionDisconnected() {
        
    }
    
    protected void onConnectionConnected() {
        
    }
    
    /**
     * set groups map, key is the hyphenate id
     * @param groupsMap
     */
    public void setGroupsMap(Map<String, EMGroup> groupsMap){
        this.groupsMap = groupsMap;
    }
    
    public interface EaseGroupListItemClickListener {
        /**
         * on click event for item in group list
         * @param group --the group of item
         */
        void onListItemClicked(EMGroup group);
    }

    public interface EaseGroupListItemLongClickListener {
        /**
         * on click event for item in group list
         * @param view View
         * @param group --the group of item
         * @return true or false
         */
        boolean onListItemLongClicked(View view, EMGroup group);
    }
    
    /**
     * set group list item click listener
     * @param listItemClickListener
     */
    public void setGroupListListItemClickListener(EaseGroupListItemClickListener listItemClickListener){
        this.listItemClickListener = listItemClickListener;
    }

    /**
     * set group list item long click listener
     * @param listItemLongClickListener EaseGroupListItemLongClickListener
     */
    public void setGroupListItemLongClickListener(EaseGroupListItemLongClickListener listItemLongClickListener){
        this.listItemLongClickListener = listItemLongClickListener;
    }
    
}
