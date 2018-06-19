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
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EMGroup;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseContactList;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * contact list
 * 
 */
public class EaseContactListFragment extends EaseBaseFragment {
    private static final String TAG = "EaseContactListFragment";
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected List<EMGroup> contactList;
    protected ListView listView;
    protected boolean hidden;
    protected ImageButton clearSearch;
    protected EditText query;
    protected Handler handler = new Handler();
    protected EMGroup toBeProcessUser;
    protected String toBeProcessUsername;
    protected EaseContactList contactListLayout;
    protected boolean isConflict;
    protected LinearLayout layContentContainer;
    protected FrameLayout contentContainer;
    
    private Map<String, EMGroup> contactsMap;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ease_fragment_contact_list, container, false);
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
        mSwipeRefreshLayout = getView().findViewById(R.id.lay_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        layContentContainer = (LinearLayout) getView().findViewById(R.id.lay_content_container);
        contentContainer = (FrameLayout) getView().findViewById(R.id.content_container);
        
        contactListLayout = (EaseContactList) getView().findViewById(R.id.contact_list);
        listView = contactListLayout.getListView();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(listView != null && listView.getChildCount() > 0){
                    // 第一条是不是可见了
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // 是不是到顶了
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // 第一条可见且到顶才可以下拉
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                mSwipeRefreshLayout.setEnabled(enable);
            }
        });
        //search
        query = (EditText) getView().findViewById(R.id.query);
        clearSearch = (ImageButton) getView().findViewById(R.id.search_clear);
    }

    @Override
    protected void setUpView() {
        EMClient.getInstance().addConnectionListener(connectionListener);
        
        contactList = new ArrayList<EMGroup>();
        getContactList();
        //init list
        contactListLayout.init(contactList);
        
        if(listItemClickListener != null){
            listView.setOnItemClickListener(new OnItemClickListener() {
    
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EMGroup user = (EMGroup)listView.getItemAtPosition(position);
                    listItemClickListener.onListItemClicked(user);
                }
            });
        }

        if(listItemLongClickListener != null){
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    EMGroup user = (EMGroup)listView.getItemAtPosition(position);
                    return listItemLongClickListener.onListItemLongClicked(view, user);
                }
            });
        }

        if (swipeRefreshListener != null) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshListener.onSwipeRefresh();
                }
            });
        }
        
        query.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactListLayout.filter(s);
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


    protected void stopRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
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
     * move user to blacklist
     */
    protected void moveToBlacklist(final String username){
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
                    EMClient.getInstance().contactManager().addUserToBlackList(username,false);
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
        getContactList();
        contactListLayout.refresh();
    }
    

    @Override
    public void onDestroy() {
        
        EMClient.getInstance().removeConnectionListener(connectionListener);
        
        super.onDestroy();
    }
    

    /**
     * get contact list and sort, will filter out users in blacklist
     */
    protected void getContactList() {
        contactList.clear();
        if(contactsMap == null){
            return;
        }
        synchronized (this.contactsMap) {
            Iterator<Entry<String, EMGroup>> iterator = contactsMap.entrySet().iterator();
            List<String> blackList = EMClient.getInstance().contactManager().getBlackListUsernames();
            while (iterator.hasNext()) {
                Entry<String, EMGroup> entry = iterator.next();
                // to make it compatible with data in previous version, you can remove this check if this is new app
                if (!entry.getKey().equals("item_new_friends")
                        && !entry.getKey().equals("item_groups")
                        && !entry.getKey().equals("item_chatroom")
                        && !entry.getKey().equals("item_robots")){
                    if(!blackList.contains(entry.getKey())){
                        //filter out users in blacklist
                        EMGroup user = entry.getValue();
                        EaseCommonUtils.setUserInitialLetter(user);
                        contactList.add(user);
                    }
                }
            }
        }

        // sorting
        Collections.sort(contactList, new Comparator<EMGroup>() {

            @Override
            public int compare(EMGroup lhs, EMGroup rhs) {
                if(lhs.getInitialLetter().equals(rhs.getInitialLetter())){
                    return lhs.getNick().compareTo(rhs.getNick());
                }else{
                    if("#".equals(lhs.getInitialLetter())){
                        return 1;
                    }else if("#".equals(rhs.getInitialLetter())){
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
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

    private EaseContactListItemClickListener listItemClickListener;

    private EaseContactListItemLongClickListener listItemLongClickListener;

    private SwipeRefreshListener swipeRefreshListener;

    
    protected void onConnectionDisconnected() {
        
    }
    
    protected void onConnectionConnected() {
        
    }
    
    /**
     * set contacts map, key is the hyphenate id
     * @param contactsMap
     */
    public void setContactsMap(Map<String, EMGroup> contactsMap){
        this.contactsMap = contactsMap;
    }
    
    public interface EaseContactListItemClickListener {
        /**
         * on click event for item in contact list 
         * @param user --the user of item
         */
        void onListItemClicked(EMGroup user);
    }

    public interface EaseContactListItemLongClickListener {
        /**
         * on click event for item in contact list
         * @param view View
         * @param user --the user of item
         * @return true or false
         */
        boolean onListItemLongClicked(View view, EMGroup user);
    }

    public interface SwipeRefreshListener {
        /**
         * on swipe refresh event
         */
        void onSwipeRefresh();
    }
    
    /**
     * set contact list item click listener
     * @param listItemClickListener listItemClickListener
     */
    public void setContactListItemClickListener(EaseContactListItemClickListener listItemClickListener){
        this.listItemClickListener = listItemClickListener;
    }

    /**
     * set contact list item long click listener
     * @param listItemLongClickListener EaseGroupListItemLongClickListener
     */
    public void setContactListItemLongClickListener(EaseContactListItemLongClickListener listItemLongClickListener){
        this.listItemLongClickListener = listItemLongClickListener;
    }

    /**
     * 下拉刷新
     * @param swipeRefreshListener EaseGroupListItemLongClickListener
     */
    public void setSwipeRefreshListener(SwipeRefreshListener swipeRefreshListener){
        this.swipeRefreshListener = swipeRefreshListener;
    }
    
}
